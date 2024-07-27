package de.ar.backgammon.flowcontrol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static de.ar.backgammon.flowcontrol.StepSequenceIf.*;

public class WorkFlowAdapter implements WorkFlowIf {
    static Logger logger = LoggerFactory.getLogger(WorkFlowAdapter.class);
    private int initalSeqId;
    private final int initalSeqId1;
    public HashMap<String, Object> wmap = new HashMap<>();
    private HashMap<Integer, StepIf> smap = new HashMap<>();
    private HashMap<Integer,StepSequenceIf> seqHash = new HashMap<>();

    int currentStepSeqIdx;

    public WorkFlowAdapter(int initalSeqId) {
        initalSeqId1 = initalSeqId;
    }
    public WorkFlowAdapter() {
        initalSeqId1 = SEQID_INITIALZED;
    }

    @Override
    public void registerStep(StepIf step, int stepId) {
        smap.put(stepId, step);
    }
    public void addStepSequence(int stepId,int seqId,int nextSeqId){
       StepSequence seq = new StepSequence(stepId,seqId,nextSeqId);
       seqHash.put(seqId,seq);
    }

    @Override
    public StepIf getStep(int stepId) {
        return smap.get(stepId);
    }


    @Override
    public int getCurrentStepId() {
        return seqHash.get(currentStepSeqIdx).getStepId();
    }

        @Override
    public boolean initialize() {
        currentStepSeqIdx = SEQID_INITIALZED;
        return true;
    }


    /**
     * do next checks the exit condition of the current step,and the entry condition
     * of the next step. if both true it switches to the first respectively the next step sequence
     * doAction of the step is not called
     * @return true if switchted to next stepsequence
     */
    @Override
    public boolean doNext() {
        return doNext(true,true);
    }

    /**
     * do next checks the exit condition of the current step,and the entry condition
     * of the next step. if both true it switches to the first respectively the next step sequence
     * doAction of the step is not called
     * @return true if switchted to next stepsequence
     */
         @Override
         public boolean doNext(boolean checkExit,boolean checkEntry) {
        if (isFinished()) {
            return false;
        }
        int nextStepSeqIdx=-1;
        if (currentStepSeqIdx ==SEQID_INITIALZED) {
            nextStepSeqIdx = initalSeqId;
        } else {
            StepSequenceIf scurseq = getCurrentStepSequence();
            nextStepSeqIdx = scurseq.getNextSeqId();
        }
        doNext(nextStepSeqIdx,true,true)

        return false;
}

    @Override
    public boolean doNext(int _stepSeqId, boolean checkExit, boolean checkEntry) {
        if (isFinished()) {
            logger.error("workflow is finished");
            return false;
        }
        if (currentStepSeqIdx > SEQID_INITIALZED) {
            StepIf currstep = getStep(currentStepSeqIdx);
            if (checkExit && !currstep.exitCondition()){
                logger.error("exit conditon of current step<{}> failed",currstep);
                return false;
            }
        }

        if (!checkEntry){
            currentStepSeqIdx = _stepSeqId;
            return true;
        }

        StepIf nextStep = getStep(_stepSeqId);
        if (nextStep.entryCondition()) {
            currentStepSeqIdx = _stepSeqId;
            logger.error("switch to seqId<{}> step<{}> failed",currentStepSeqIdx,nextStep);
            return true;
        }else{
            logger.error("entry conditon of step<{}> failed",nextStep);
            return false;
        }

    }

    private StepSequenceIf getStepSequence(int stepSeqIdx) {
        return seqHash.get(stepSeqIdx);
    }

    private StepSequenceIf getCurrentStepSequence() {
        return seqHash.get(currentStepSeqIdx);
    }

    public StepIf getCurrentStep() {
        return getStep(getCurrentStepSequence().getStepId());
    }

    @Override
    public boolean isFinished() {
       return currentStepSeqIdx == StepSequenceIf.SEQID_FINISHED;
    }


}

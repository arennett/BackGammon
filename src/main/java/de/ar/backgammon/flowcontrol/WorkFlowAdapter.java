package de.ar.backgammon.flowcontrol;

import de.ar.backgammon.model.BoardModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class WorkFlowAdapter implements WorkFlowIf {
    static Logger logger = LoggerFactory.getLogger(WorkFlowAdapter.class);
    public HashMap<String, Object> wmap = new HashMap<>();
    private HashMap<Integer, StepIf> smap = new HashMap<>();
    private Vector<Integer> svec = new Vector<>();

    int currentStepIdx;
    boolean finished = false;

    {
        finished = true;
    }

    public WorkFlowAdapter() {
        currentStepIdx = -1;
    }

    @Override
    public void registerStep(StepIf step, int stepId) {
        smap.put(stepId, step);
        svec.add(stepId);
    }

    @Override
    public StepIf getStep(int stepId) {
        return smap.get(stepId);
    }

    public StepIf getCurrentStep() {
        return smap.get(getCurrentStepId());
    }

    @Override
    public int getCurrentStepId() {
        return svec.get(currentStepIdx);
    }

        @Override
    public boolean initialize() {
        finished = false;
        currentStepIdx = -1;
        return false;
    }

    /**
     * do next checks the exit condition of the current step,and the entry condition
     * of the next step. if both true it switches to the first respectively the next step
     * doAction of the step is not called
     * @return
     */
    @Override
    public boolean doNext() {
        if (finished) {
            return false;
        }
        int nextStepIdx;
        if (currentStepIdx < 0) {
            nextStepIdx = 0;
        } else {
            StepIf step = getStep(currentStepIdx);
            if (!step.exitCondition()){
                logger.error("exit conditon of step<{}> failed",step);
                return false;
            }
            nextStepIdx = currentStepIdx + 1;
        }
        StepIf step = getStep(nextStepIdx);
        if (step.entryCondition()) {
            currentStepIdx = nextStepIdx;
            if (currentStepIdx >= svec.size()) {
                finished = true;
            }

        }else{
            logger.error("entry conditon of step<{}> failed",step);
            return false;
        }

        return false;
}

    public boolean doNext(int _stepId,boolean checkExit,boolean checkEntry) {
        if (finished) {
            return false;
        }
        int nextStepIdx=_stepId;
        if (currentStepIdx > -1) {
            StepIf currstep = getStep(currentStepIdx);
            if (checkExit && !currstep.exitCondition()){
                logger.error("exit conditon of current step<{}> failed",currstep);
                return false;
            }
        }

        if (!checkEntry){
            currentStepIdx = nextStepIdx;
            if (currentStepIdx >= svec.size()) {
                finished = true;
            }
            return true;
        }

        StepIf step = getStep(nextStepIdx);
        if (step.entryCondition()) {
            currentStepIdx = nextStepIdx;
            if (currentStepIdx >= svec.size()) {
                finished = true;
            }
        }else{
            logger.error("entry conditon of step<{}> failed",step);
            return false;
        }

        return false;
    }
    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public Iterator<Integer> iterator() {
        return svec.iterator();
    }
}

package de.ar.backgammon.flowcontrol;

public class StepSequence implements StepSequenceIf{

    private final int stepId;
    private final int seqId;
    private final int nextSeqId;



    public StepSequence(int stepId, int seqId, int nextSeqId){
        this.stepId = stepId;
        this.seqId = seqId;
        this.nextSeqId = nextSeqId;
    }

    @Override
    public int getNextSeqId() {
        return 0;
    }

    @Override
    public int getStepId() {
        return stepId;
    }
    @Override
    public int getSeqId() {
        return seqId;
    }
}

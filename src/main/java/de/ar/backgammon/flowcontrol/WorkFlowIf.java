package de.ar.backgammon.flowcontrol;

public interface WorkFlowIf {

    void registerStep(StepIf step,int stepId);
    StepIf getStep(int stepId);

    boolean doNext(boolean checkExit, boolean checkEntry);

    boolean doNext(int _stepSeqId, boolean checkExit, boolean checkEntry);

    StepIf getCurrentStep();

    int getCurrentStepId();

    boolean initialize();

    boolean doNext();

    boolean isFinished();
}

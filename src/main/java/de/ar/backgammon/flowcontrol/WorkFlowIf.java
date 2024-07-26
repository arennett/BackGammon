package de.ar.backgammon.flowcontrol;

public interface WorkFlowIf extends Iterable<Integer> {
    void registerStep(StepIf step,int stepId);
    StepIf getStep(int stepId);
    StepIf getCurrentStep();

    int getCurrentStepId();

    boolean initialize();

    boolean doNext();

    boolean isFinished();
}

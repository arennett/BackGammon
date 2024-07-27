package de.ar.backgammon.flowcontrol;

public interface StepIf {
    boolean entryCondition();

    boolean doAction();

    boolean exitCondition();

    String getName();

    int getStepId();

}

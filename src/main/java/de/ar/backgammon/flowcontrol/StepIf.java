package de.ar.backgammon.flowcontrol;

public interface StepIf {
    public boolean entryCondition();

    public boolean doAction();

    public boolean exitCondition();
}

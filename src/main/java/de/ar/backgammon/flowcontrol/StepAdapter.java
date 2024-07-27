package de.ar.backgammon.flowcontrol;

public abstract class StepAdapter implements StepIf{
    private final String stepName;

    public StepAdapter(String stepName){
        this.stepName = stepName;
    }

}

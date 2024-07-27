package de.ar.backgammon.flowcontrol;

public interface StepSequenceIf {
    int SEQID_INITIALZED =-1;
    int SEQID_FINISHED =-99;
    int getNextSeqId();

    int getStepId();

    int getSeqId();
}

package gg.ngl.hyspeech.action;

public abstract class HyspeechAction {

    public final Object[] properties;

    public HyspeechAction(Object... properties) {
        this.properties = properties;
    }

    public abstract boolean canExecute();
    public abstract boolean execute();
}

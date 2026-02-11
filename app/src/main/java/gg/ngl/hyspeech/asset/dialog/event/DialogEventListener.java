package gg.ngl.hyspeech.asset.dialog.event;

@FunctionalInterface
public interface DialogEventListener<T extends DialogEvent> {
    void onEvent(T event);
}
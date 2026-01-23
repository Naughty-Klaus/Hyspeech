package gg.ngl.hyspeech.action;

import java.util.concurrent.ConcurrentLinkedQueue;

public class HyspeechActionList {

    private final ConcurrentLinkedQueue<HyspeechAction> queuedActions;

    public ConcurrentLinkedQueue<HyspeechAction> getQueuedActions() {
        return getQueuedActions();
    }

    public HyspeechAction getNext() {
        return queuedActions.peek();
    }

    public HyspeechActionList() {
        queuedActions = new ConcurrentLinkedQueue<>();
    }

}

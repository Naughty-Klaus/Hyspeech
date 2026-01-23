package gg.ngl.hyspeech.action;

import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.world.World;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HyspeechActionExecutor {

    private World world;
    public final HyspeechActionList actionList = new HyspeechActionList();

    public HyspeechActionExecutor(World world) {
        this.world = world;
    }

    public void executeActions() {
        world.execute(() -> {
            for (HyspeechAction action : actionList.getQueuedActions()) {
                if (action.canExecute()) {
                    if(!action.execute())
                        throw new RuntimeException("Hyspeech action couldn't execute.");
                }

                actionList.getQueuedActions().remove(action);
            }
        });
    }

}

package gg.ngl.hyspeech.util.param;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public record BuildContext(
        PlayerRef player,
        Ref<EntityStore> entRef,
        Store<EntityStore> entStore
) {
}
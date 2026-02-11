package gg.ngl.hyspeech.asset.dialog.event;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import gg.ngl.hyspeech.Hyspeech;
import gg.ngl.hyspeech.util.param.ParameterContext;

public record DialogEventContext(
        String dialogId,
        PlayerRef player,
        Ref<EntityStore> entRef,
        Store<EntityStore> entStore,
        ParameterContext params,
        Hyspeech hyspeech
) {}
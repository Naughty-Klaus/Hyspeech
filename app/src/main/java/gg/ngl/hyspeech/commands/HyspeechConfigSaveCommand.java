package gg.ngl.hyspeech.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import gg.ngl.hyspeech.Hyspeech;

import javax.annotation.Nonnull;

public class HyspeechConfigSaveCommand extends AbstractPlayerCommand {
    public HyspeechConfigSaveCommand() {
        super("save", "Saves hyspeech config to disk.");
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Hyspeech.get().getConfig().save();
        commandContext.sendMessage(Message.raw("Hyspeech config saved to config JSON."));
    }
}

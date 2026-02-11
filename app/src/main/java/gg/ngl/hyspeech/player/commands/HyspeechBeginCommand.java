package gg.ngl.hyspeech.player.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import gg.ngl.hyspeech.player.ui.page.HyspeechDialogPage;

import javax.annotation.Nonnull;

/**
 *
 * Hyspeech - Character dialog system for Hytale
 * Copyright (C) 2026 Naughty-Klaus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

public class HyspeechBeginCommand extends AbstractPlayerCommand {

    private final RequiredArg<PlayerRef> playerArg;
    private final RequiredArg<String> dialogArg;

    public HyspeechBeginCommand() {
        super("begin", "Command for developer use only!");
        playerArg = withRequiredArg("player", "Username of the player who should open dialog.", ArgTypes.PLAYER_REF);
        dialogArg = withRequiredArg("dialog", "The dialog to open.", ArgTypes.STRING);
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        String label = commandContext.get(dialogArg);

        PlayerRef playerRef1 = playerArg.get(commandContext);
        Ref<EntityStore> ref1 = playerRef1.getReference();
        Store<EntityStore> store1 = ref1.getStore();

        Player playerComponent = store.getComponent(ref1, Player.getComponentType());

        if (playerComponent == null) {
            return;
        }

        playerComponent.getPageManager().openCustomPage(ref1, store1,
                new HyspeechDialogPage(ref1, store1, playerRef1, label));
    }
}

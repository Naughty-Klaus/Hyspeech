package gg.ngl.hyspeech.player.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import gg.ngl.hyspeech.Hyspeech;
import gg.ngl.hyspeech.asset.quest.HyspeechQuestAsset;
import gg.ngl.hyspeech.asset.quest.HyspeechQuestStatus;
import gg.ngl.hyspeech.player.HyspeechPlayerConfig;

import javax.annotation.Nonnull;

/**
 *
 *     Hyspeech - Character dialog system for Hytale
 *     Copyright (C) 2026 Naughty-Klaus
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

public class DeactivateHyspeechCommand extends AbstractPlayerCommand {
    public DeactivateHyspeechCommand() {
        super("hyde", "Allows control of hyspeech.");
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        for(PlayerRef refX : Hyspeech.hyspeechPlayerMap.keySet().toArray(PlayerRef[]::new)) {
            Hyspeech.hyspeechPlayerMap.computeIfPresent(refX, (a, y) -> {
                for(HyspeechQuestAsset asset : y.getConfig().get().quests) {
                    asset.setStatus(HyspeechQuestStatus.QuestStatus.FAILED);
                }
                return y;
            });
        }
    }
}

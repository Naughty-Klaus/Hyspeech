package gg.ngl.hyspeech.asset.dialog.action;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;

import gg.ngl.hyspeech.asset.dialog.action.builder.BuilderActionBeginDialog;
import gg.ngl.hyspeech.player.ui.page.HyspeechDialogPage;

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

public class ActionBeginDialog extends ActionBase {
    protected final String dialogId;

    public ActionBeginDialog(@Nonnull BuilderActionBeginDialog builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.dialogId = builder.getDialogId(support);
    }

    @Override
    public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        return super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null;
    }

    @Override
    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        if(canExecute(ref, role, sensorInfo, dt, store)) {
            Ref<EntityStore> playerReference = role.getStateSupport().getInteractionIterationTarget();
            if (playerReference == null) {
                return false;
            }

            PlayerRef playerRefComponent = store.getComponent(playerReference, PlayerRef.getComponentType());
            if (playerRefComponent == null) {
                return false;
            }

            Player playerComponent = store.getComponent(playerReference, Player.getComponentType());
            if (playerComponent == null) {
                return false;
            }

            playerComponent.getPageManager().openCustomPage(ref, store,
                    new HyspeechDialogPage(ref, role, sensorInfo, dt, store, playerRefComponent, this.dialogId));

            super.execute(ref, role, sensorInfo, dt, store);
            return true;
        }
        return false;
    }

}

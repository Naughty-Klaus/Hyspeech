package gg.ngl.hyspeech.asset.dialog.action.builder;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.InstructionType;
import com.hypixel.hytale.server.npc.asset.builder.holder.AssetHolder;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.instructions.Action;
import gg.ngl.hyspeech.asset.dialog.action.ActionBeginDialog;
import gg.ngl.hyspeech.asset.dialog.validator.HyspeechDialogExistsValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

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

public class BuilderActionBeginDialog extends BuilderActionBase {
    protected final AssetHolder dialogId = new AssetHolder();

    @Nullable
    @Override
    public String getShortDescription() {
        return "Begin the dialog for the current player";
    }

    @Nullable
    @Override
    public String getLongDescription() {
        return getShortDescription();
    }

    @Nullable
    @Override
    public Action build(@Nonnull BuilderSupport builderSupport) {
        return new ActionBeginDialog(this, builderSupport);
    }

    @Override
    @Nonnull
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }

    @Nonnull
    public BuilderActionBeginDialog readConfig(@Nonnull JsonElement data) {
        this.requireAsset(data, "Dialog", this.dialogId, HyspeechDialogExistsValidator.required(), BuilderDescriptorState.Stable, "The dialog to begin", null);
        this.requireInstructionType(EnumSet.of(InstructionType.Interaction));
        return this;
    }

    public String getDialogId(@Nonnull BuilderSupport support) {
        return this.dialogId.get(support.getExecutionContext());
    }
}

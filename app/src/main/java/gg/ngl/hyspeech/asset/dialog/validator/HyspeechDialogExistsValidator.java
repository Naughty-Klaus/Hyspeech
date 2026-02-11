package gg.ngl.hyspeech.asset.dialog.validator;

import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
import gg.ngl.hyspeech.asset.dialog.HyspeechDialogAsset;

import javax.annotation.Nonnull;
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

public class HyspeechDialogExistsValidator extends AssetValidator {
    private static final HyspeechDialogExistsValidator DEFAULT_INSTANCE = new HyspeechDialogExistsValidator();

    private HyspeechDialogExistsValidator() {
    }

    private HyspeechDialogExistsValidator(EnumSet<Config> config) {
        super(config);
    }

    public static HyspeechDialogExistsValidator required() {
        return DEFAULT_INSTANCE;
    }

    @Nonnull
    public static HyspeechDialogExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
        return new HyspeechDialogExistsValidator(config);
    }

    @Override
    @Nonnull
    public String getDomain() {
        return "HyspeechDialog";
    }

    @Override
    public boolean test(String marker) {
        return HyspeechDialogAsset.getAssetMap().getAsset(marker) != null;
    }

    @Override
    @Nonnull
    public String errorMessage(String marker, String attributeName) {
        return "The hyspeech dialog asset with the name \"" + marker + "\" does not exist for attribute \"" + attributeName + "\"";
    }

    @Override
    @Nonnull
    public String getAssetName() {
        return HyspeechDialogAsset.class.getSimpleName();
    }
}

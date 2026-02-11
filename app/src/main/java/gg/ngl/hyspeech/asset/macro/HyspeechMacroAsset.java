package gg.ngl.hyspeech.asset.macro;

import com.hypixel.hytale.assetstore.AssetExtraInfo;
import com.hypixel.hytale.assetstore.AssetKeyValidator;
import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.AssetStore;
import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
import com.hypixel.hytale.codec.validation.ValidatorCache;
import gg.ngl.hyspeech.asset.dialog.HyspeechDialogType;

import javax.annotation.Nonnull;
import java.util.Arrays;

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

public class HyspeechMacroAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, HyspeechMacroAsset>> {

    public static final AssetBuilderCodec<String, HyspeechMacroAsset> CODEC =
            AssetBuilderCodec
                    .builder(
                            HyspeechMacroAsset.class,
                            HyspeechMacroAsset::new,
                            Codec.STRING,
                            (asset, s) -> asset.id = s,
                            asset -> asset.id,
                            (asset, data) -> asset.extraData = data,
                            asset -> asset.extraData
                    )
                    .append(
                            new KeyedCodec<>("Commands", new ArrayCodec<>(ArrayCodec.STRING, String[]::new)),
                            (asset, commands) -> {
                                asset.commands = commands;
                            },
                            asset -> asset.commands
                    )
                    .documentation("Commands to execute in order. Use {username} to target the player who executed the dialog.")
                    .add()
                    .build();
    private static AssetStore<String, HyspeechMacroAsset, DefaultAssetMap<String, HyspeechMacroAsset>> ASSET_STORE;
    public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache(new AssetKeyValidator(HyspeechMacroAsset::getAssetStore));
    public AssetExtraInfo.Data extraData;
    public HyspeechDialogType type;
    public String id;
    public String[] commands;
    public String next;

    public HyspeechMacroAsset(String id, String[] commands) {
        this.id = id;
        this.commands = commands;
    }

    protected HyspeechMacroAsset() {
    }

    public static AssetStore<String, HyspeechMacroAsset, DefaultAssetMap<String, HyspeechMacroAsset>> getAssetStore() {
        if (ASSET_STORE == null) {
            ASSET_STORE = AssetRegistry.getAssetStore(HyspeechMacroAsset.class);
        }
        return ASSET_STORE;
    }

    public static DefaultAssetMap<String, HyspeechMacroAsset> getAssetMap() {
        return HyspeechMacroAsset.getAssetStore().getAssetMap();
    }

    @Override
    public String getId() {
        return this.id;
    }

    public HyspeechDialogType getType() {
        return this.type;
    }

    public String[] getCommands() {
        return this.commands;
    }

    public String getNext() {
        return this.next;
    }

    @Nonnull
    public String toString() {
        return "HyspeechMacro{id='" + this.id + "', commands='" + Arrays.toString(this.commands) + "'}";
    }

}

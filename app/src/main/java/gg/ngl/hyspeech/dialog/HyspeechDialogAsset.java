package gg.ngl.hyspeech.dialog;

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
import com.hypixel.hytale.codec.schema.metadata.ui.*;
import com.hypixel.hytale.codec.validation.ValidatorCache;

import gg.ngl.hyspeech.dialog.HyspeechDialogType.DialogType;

import javax.annotation.Nonnull;
import java.util.Arrays;

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

public class HyspeechDialogAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, HyspeechDialogAsset>> {

    public static final AssetBuilderCodec<String, HyspeechDialogAsset> CODEC =
            AssetBuilderCodec
                    .builder(
                            HyspeechDialogAsset.class,
                            HyspeechDialogAsset::new,
                            Codec.STRING,
                            (asset, s) -> asset.id = s,
                            asset -> asset.id,
                            (asset, data) -> asset.extraData = data,
                            asset -> asset.extraData
                    )
                    .append(
                            new KeyedCodec<>("Type", HyspeechDialogType.DIALOG_TYPE_ENUM_CODEC),
                            (obj, val) -> obj.type = val,
                            obj -> obj.type
                    )
                    .add()
                    .append(
                            new KeyedCodec<>("Entries", new ArrayCodec<HyspeechDialogEntry>(HyspeechDialogEntry.CODEC, HyspeechDialogEntry[]::new)),
                            (asset, entries) -> {
                                asset.entries = entries;
                            },
                            asset -> asset.entries
                    )
                    .documentation("Content of the dialog.\n\nThis will eventually be replaced with multiline components.")
                    .add()
                    .append(
                            new KeyedCodec<>("Next", Codec.STRING),
                            (asset, s) -> asset.next = s,
                            asset -> asset.next
                    )
                    .documentation("The next dialog that should open after continuing.\n\nThis will eventually be replaced with multiline components.")
                    .add()
                    .build();

    public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache(new AssetKeyValidator(HyspeechDialogAsset::getAssetStore));
    private static AssetStore<String, HyspeechDialogAsset, DefaultAssetMap<String, HyspeechDialogAsset>> ASSET_STORE;
    public AssetExtraInfo.Data extraData;
    public DialogType type;
    public String id;
    public String icon;
    public HyspeechDialogEntry[] entries;
    public String next;

    public static AssetStore<String, HyspeechDialogAsset, DefaultAssetMap<String, HyspeechDialogAsset>> getAssetStore() {
        if (ASSET_STORE == null) {
            ASSET_STORE = AssetRegistry.getAssetStore(HyspeechDialogAsset.class);
        }
        return ASSET_STORE;
    }

    public static DefaultAssetMap<String, HyspeechDialogAsset> getAssetMap() {
        return HyspeechDialogAsset.getAssetStore().getAssetMap();
    }

    public HyspeechDialogAsset(String id, String icon, HyspeechDialogEntry[] entries) {
        this.id = id;
        this.icon = icon;
        this.entries = entries;
    }

    protected HyspeechDialogAsset() {
    }

    @Override
    public String getId() {
        return this.id;
    }

    public DialogType getType() {
        return this.type;
    }

    public String getIcon() {
        return this.icon;
    }

    public HyspeechDialogEntry[] getEntries() {
        return this.entries;
    }

    public String getNext() {
        return this.next;
    }

    @Nonnull
    public String toString() {
        return "HyspeechDialogAsset{id='" + this.id + "', icon='" + this.icon + "', entries='" + Arrays.toString(this.entries) + "'}";
    }

}

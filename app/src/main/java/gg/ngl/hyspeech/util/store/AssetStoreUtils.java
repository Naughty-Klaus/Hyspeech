package gg.ngl.hyspeech.util.store;

import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.AssetStore;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import gg.ngl.hyspeech.asset.dialog.HyspeechDialogAsset;
import gg.ngl.hyspeech.asset.quest.HyspeechQuestAsset;

public class AssetStoreUtils {
    public static HyspeechQuestAsset[] getQuestAssets() {
        AssetStore<String, HyspeechQuestAsset, DefaultAssetMap<String, HyspeechQuestAsset>> hyspeechQuestStore =
                AssetRegistry.getAssetStore(HyspeechQuestAsset.class);

        if (hyspeechQuestStore == null) {
            return null;
        }

        DefaultAssetMap<String, HyspeechQuestAsset> assetMap = hyspeechQuestStore.getAssetMap();

        if (assetMap == null) {
            return null;
        }

        return assetMap.getAssetMap().values().toArray(HyspeechQuestAsset[]::new);
    }
}

package gg.ngl.hyspeech.asset.quest;

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

import static gg.ngl.hyspeech.asset.quest.HyspeechQuestStatus.QuestStatus;
import static gg.ngl.hyspeech.asset.quest.HyspeechQuestStatus.QUEST_STATUS_ENUM_CODEC;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class HyspeechQuestAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, HyspeechQuestAsset>> {

    private String id;
    private String name;
    private QuestStatus status;
    private HyspeechQuestObjective[] objectives;

    public static final AssetBuilderCodec<String, HyspeechQuestAsset> CODEC =
            AssetBuilderCodec
                    .builder(
                            HyspeechQuestAsset.class,
                            HyspeechQuestAsset::new,
                            Codec.STRING,
                            (asset, s) -> asset.id = s,
                            asset -> asset.id,
                            (asset, data) -> asset.extraData = data,
                            asset -> asset.extraData
                    )
                    .append(
                            new KeyedCodec<>("Title", Codec.STRING),
                            (obj, val) -> obj.name = val,
                            obj -> obj.name
                    )
                    .add()
                    .append(
                            new KeyedCodec<>("Status", QUEST_STATUS_ENUM_CODEC),
                            (obj, val) -> obj.status = val,
                            obj -> obj.status
                    )
                    .add()
                    .append(
                            new KeyedCodec<>("Objectives", new ArrayCodec<>(HyspeechQuestObjective.CODEC, HyspeechQuestObjective[]::new)),
                            (asset, entries) -> {
                                asset.objectives = entries;
                            },
                            asset -> asset.objectives
                    )
                    .documentation("Commands to execute in order. Use {username} to target the player who executed the dialog.")
                    .add()
                    .build();

    private static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache(new AssetKeyValidator(HyspeechQuestAsset::getAssetStore));
    private static AssetStore<String, HyspeechQuestAsset, DefaultAssetMap<String, HyspeechQuestAsset>> ASSET_STORE;
    private AssetExtraInfo.Data extraData;

    public static AssetStore<String, HyspeechQuestAsset, DefaultAssetMap<String, HyspeechQuestAsset>> getAssetStore() {
        if (ASSET_STORE == null) {
            ASSET_STORE = AssetRegistry.getAssetStore(HyspeechQuestAsset.class);
        }
        return ASSET_STORE;
    }

    public static DefaultAssetMap<String, HyspeechQuestAsset> getAssetMap() {
        return HyspeechQuestAsset.getAssetStore().getAssetMap();
    }

    public HyspeechQuestAsset(String id, HyspeechQuestObjective[] objectives) {
        this.id = id;
        this.objectives = objectives;
    }

    protected HyspeechQuestAsset() {
    }

    @Override
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public QuestStatus getStatus() {
        return this.status;
    }

    public HyspeechQuestObjective[] getObjectives() {
        return this.objectives;
    }

    public void setStatus(QuestStatus status) {
        this.status = status;
    }

    @Nonnull
    public String toString() {
        return "Quest{id='" + this.id + "', status=" + this.status.toString() + ", objectives='" + Arrays.toString(this.objectives) + "'}";
    }

}

package gg.ngl.hyspeech.player;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
import gg.ngl.hyspeech.Hyspeech;
import gg.ngl.hyspeech.asset.quest.HyspeechQuestAsset;
import gg.ngl.hyspeech.util.store.AssetStoreUtils;

import java.util.UUID;

public class HyspeechPlayerConfig {

    public static final BuilderCodec<HyspeechPlayerConfig> CODEC = BuilderCodec.builder(HyspeechPlayerConfig.class, HyspeechPlayerConfig::new)
            .append(new KeyedCodec<>("UUID", Codec.STRING),
                    (config, val) -> config.setUuid(UUID.fromString(val)),
                    config -> config.playerUuid.toString())
            .add()
            .append(new KeyedCodec<>("Quests", new ArrayCodec<>(HyspeechQuestAsset.CODEC, HyspeechQuestAsset[]::new)),
                    (config, quests) -> {
                        config.quests = quests;
                    },
                    config -> config.quests
            )
            .add()
            .build();

    public UUID playerUuid;

    public void setUuid(UUID uuid) {
        this.playerUuid = uuid;
    }

    public HyspeechQuestAsset[] quests = AssetStoreUtils.getQuestAssets();

    public void setQuests(HyspeechQuestAsset[] quests) {
        this.quests = quests;
    }

}

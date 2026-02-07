package gg.ngl.hyspeech.player;

import com.hypixel.hytale.assetstore.AssetExtraInfo;
import com.hypixel.hytale.assetstore.JsonAsset;
import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
import gg.ngl.hyspeech.Hyspeech;
import gg.ngl.hyspeech.util.store.AssetStoreUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class HyspeechPlayerConfig {

    public static final BuilderCodec<HyspeechPlayerConfig> CODEC =
            BuilderCodec
                    .builder(
                            HyspeechPlayerConfig.class,
                            HyspeechPlayerConfig::new
                    )
                    .append(new KeyedCodec<>("UUID", Codec.STRING),
                            (config, val) -> config.setUuid(UUID.fromString(val)),
                            config -> config.playerUuid.toString())
                    .add()
                    .build();

    public UUID playerUuid;

    public void setUuid(UUID uuid) {
        this.playerUuid = uuid;
    }
}

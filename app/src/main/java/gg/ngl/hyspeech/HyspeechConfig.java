package gg.ngl.hyspeech;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class HyspeechConfig {
    private boolean debugMode = false;

    public static final BuilderCodec<HyspeechConfig> CODEC = BuilderCodec.builder(HyspeechConfig.class, HyspeechConfig::new)
        .append(new KeyedCodec<>("DebugMode", Codec.BOOLEAN),
            (config, val) -> config.debugMode = val,
            config -> config.debugMode)
        .add()
        .build();

    public boolean isDebugMode() { return debugMode; }
}

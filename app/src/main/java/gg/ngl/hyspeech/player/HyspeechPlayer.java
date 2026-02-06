package gg.ngl.hyspeech.player;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.util.Config;
import gg.ngl.hyspeech.HyspeechConfig;

import java.io.File;

public class HyspeechPlayer {
    public HyspeechPlayer(PlayerRef player) {
        this.player = player;

        config = new Config<>(
                new File("config/hyspeech/player_data/").toPath(),
                player.getUsername(),
                HyspeechPlayerConfig.CODEC
        );

        getConfig().load().thenAccept((config) -> {
            if(config.playerUuid == null)
                config.setUuid(getPlayerRef().getUuid());
        });

        config.save();
    }

    protected HyspeechPlayer() {
    }

    private PlayerRef player;

    public PlayerRef getPlayerRef() {
        return this.player;
    }

    private Config<HyspeechPlayerConfig> config;

    public Config<HyspeechPlayerConfig> getConfig() {
        return config;
    }

    public void setConfig(Config<HyspeechPlayerConfig> config) {
        this.config = config;
    }
}

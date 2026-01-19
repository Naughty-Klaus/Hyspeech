package gg.ngl.hyspeech;

import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import com.hypixel.hytale.server.npc.NPCPlugin;
import gg.ngl.hyspeech.commands.HyspeechCommand;
import gg.ngl.hyspeech.dialog.HyspeechDialogAsset;
import gg.ngl.hyspeech.dialog.action.builder.BuilderActionBeginDialog;

public class Hyspeech extends JavaPlugin {
    private static Hyspeech instance;

    public static Hyspeech get() {
        return Hyspeech.instance;
    }

    private Config<HyspeechConfig> config;

    public Config<HyspeechConfig> getConfig() {
        return config;
    }

    public Hyspeech(JavaPluginInit init) {
        super(init);
        Hyspeech.instance = this;
        config = withConfig(HyspeechConfig.CODEC);
    }

    @Override
    public void setup() {
        this.getCommandRegistry().registerCommand(new HyspeechCommand());

        NPCPlugin.get().registerCoreComponentType("HyspeechBeginDialog", BuilderActionBeginDialog::new);

        HytaleAssetStore.Builder<String,HyspeechDialogAsset, DefaultAssetMap<String, HyspeechDialogAsset>> builder =
            HytaleAssetStore.builder(
                HyspeechDialogAsset.class,
                    new DefaultAssetMap<>()
            );

        this.getAssetRegistry().register(
            builder
                .setPath("HyspeechDialog")
                .setCodec(HyspeechDialogAsset.CODEC)
                .setKeyFunction(HyspeechDialogAsset::getId)
                .loadsAfter(Interaction.class)
                .build()
        );
    }

    @Override
    public void start() {
        enable();
    }

    @Override
    public void shutdown() {
        getConfig().save();
    }

    public void enable() {
        getConfig().save();
    }

    public void disable() {

    }
}

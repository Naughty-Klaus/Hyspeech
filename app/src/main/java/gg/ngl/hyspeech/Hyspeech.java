package gg.ngl.hyspeech;

import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.util.Config;
import com.hypixel.hytale.server.core.util.UUIDUtil;
import com.hypixel.hytale.server.npc.NPCPlugin;
import gg.ngl.hyspeech.commands.HyspeechCommand;
import gg.ngl.hyspeech.commands.macro.HyspeechMacroAsset;
import gg.ngl.hyspeech.dialog.HyspeechDialogAsset;
import gg.ngl.hyspeech.dialog.action.builder.BuilderActionBeginDialog;
import gg.ngl.hyspeech.param.ParameterProcessor;
import gg.ngl.hyspeech.param.ParameterResolver;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * Hyspeech - Character dialog system for Hytale
 * Copyright (C) 2026 Naughty-Klaus
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

public class Hyspeech extends JavaPlugin {

    private final Map<String, ParameterProcessor<?>> processors = new ConcurrentHashMap<>();

    public <C> void registerParameter(
            String key,
            Class<C> contextType,
            ParameterResolver<C> resolver
    ) {
        processors.put(key, new ParameterProcessor<>(key, contextType, resolver));
    }

    public ParameterProcessor<?> getProcessor(String key) {
        return processors.get(key);
    }

    public String process(String message, Object context) {
        for (ParameterProcessor<?> processor : processors.values()) {
            if (processor.supports(context)) {
                message = message.replace(
                        processor.key(),
                        processor.resolve(context)
                );
            }
        }
        return message;
    }

    private static Hyspeech INSTANCE;

    public static Hyspeech get() {
        return Hyspeech.INSTANCE;
    }

    private Config<HyspeechConfig> config;

    public Config<HyspeechConfig> getConfig() {
        return config;
    }

    public Hyspeech(JavaPluginInit init) {
        super(init);
        Hyspeech.INSTANCE = this;
        config = withConfig(HyspeechConfig.CODEC);
    }

    @Override
    public void setup() {
        get().registerParameter("{username}", PlayerRef.class, PlayerRef::getUsername);
        get().registerParameter("{uuid}", PlayerRef.class, p -> p.getUuid().toString());

        this.getCommandRegistry().registerCommand(new HyspeechCommand());

        NPCPlugin.get().registerCoreComponentType("HyspeechBeginDialog", BuilderActionBeginDialog::new);

        HytaleAssetStore.Builder<String, HyspeechDialogAsset, DefaultAssetMap<String, HyspeechDialogAsset>> dialogAssetBuilder =
                HytaleAssetStore.builder(
                        HyspeechDialogAsset.class,
                        new DefaultAssetMap<>()
                );

        HytaleAssetStore.Builder<String, HyspeechMacroAsset, DefaultAssetMap<String, HyspeechMacroAsset>> macroAssetBuilder =
                HytaleAssetStore.builder(
                        HyspeechMacroAsset.class,
                        new DefaultAssetMap<>()
                );

        this.getAssetRegistry().register(
                macroAssetBuilder
                        .setPath("HyspeechMacro")
                        .setCodec(HyspeechMacroAsset.CODEC)
                        .setKeyFunction(HyspeechMacroAsset::getId)
                        .loadsAfter(Interaction.class)
                        .build()
        );

        this.getAssetRegistry().register(
                dialogAssetBuilder
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

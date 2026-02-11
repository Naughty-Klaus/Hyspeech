package gg.ngl.hyspeech.player.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import gg.ngl.hyspeech.Hyspeech;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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

public class HyspeechSetupCommand extends AbstractPlayerCommand {
    public HyspeechSetupCommand() {
        super("setup", "Generates a writable Hyspeech demo pack.");
    }

    public static void extractFolderFromJar(
            Class<?> clazz,
            String folder,
            Path destination
    ) throws Exception {

        Path jarPath = Paths.get(
                clazz.getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI()
        );

        if (!Files.isDirectory(jarPath) && jarPath.toString().endsWith(".jar")) {
            try (JarFile jar = new JarFile(jarPath.toFile())) {
                Enumeration<JarEntry> entries = jar.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();

                    if (!entry.getName().startsWith(folder)) continue;

                    Path outPath = destination.resolve(entry.getName());

                    if (entry.isDirectory()) {
                        Files.createDirectories(outPath);
                    } else {
                        Files.createDirectories(outPath.getParent());
                        try (InputStream in = jar.getInputStream(entry)) {
                            Files.copy(in, outPath, StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        try {
            extractFolderFromJar(
                    Hyspeech.class,
                    "gg.ngl.hyspeech.Hyspeech/",
                    Paths.get("mods/")
            );

            commandContext.sender().sendMessage(Message.raw("Hyspeech setup has generated the demo pack. Restart server to load the pack."));
        } catch (Exception e) {
            commandContext.sender().sendMessage(Message.raw("An error has occurred when setting up the demo pack."));
            throw new RuntimeException(e);
        }
    }
}

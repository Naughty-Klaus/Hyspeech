package gg.ngl.hyspeech.ui.page;

import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.AssetStore;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import gg.ngl.hyspeech.Hyspeech;
import gg.ngl.hyspeech.dialog.HyspeechDialogAsset;

import static gg.ngl.hyspeech.ui.page.HyspeechDialogPage.PageData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.logging.Level;

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

public class HyspeechDialogPage extends InteractiveCustomUIPage<PageData> {

    public HyspeechDialogPage(PlayerRef playerRef, String key) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, PageData.CODEC);
        setKey(key);
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder commands, @Nonnull UIEventBuilder eventBuilder, @Nonnull Store<EntityStore> store) {
        HyspeechDialogAsset asset = getAsset();

        if(asset == null) {
            this.close();
            return;
        }

        switch (asset.getType()) {
            case CHOICE_2:
                currentDialogType = DialogType.CHOICE_2;
                commands.append("Pages/HyspeechChoice2.ui");

                for(int i = 0; i <= 1; i++) {
                    commands.set("#Content" + i + ".Text", Message.translation(asset.entries[i].content).param("username", playerRef.getUsername()));
                }
                break;
            case CHOICE_3:
                currentDialogType = DialogType.CHOICE_3;
                commands.append("Pages/HyspeechChoice3.ui");

                for(int i = 0; i <= 2; i++) {
                    commands.set("#Content" + i + ".Text", Message.translation(asset.entries[i].content).param("username", playerRef.getUsername()));
                }
                break;
            case CHOICE_4:
                currentDialogType = DialogType.CHOICE_4;
                commands.append("Pages/HyspeechChoice4.ui");

                for(int i = 0; i <= 3; i++) {
                    commands.set("#Content" + i + ".Text", Message.translation(asset.entries[i].content).param("username", playerRef.getUsername()));
                }
                break;
            case DIALOG_1:
                currentDialogType = DialogType.DIALOG_1;
                commands.append("Pages/HyspeechDialog1.ui");

                commands.set("#Content0.Text", Message.translation(asset.entries[0].content).param("username", playerRef.getUsername()));
                break;
            case DIALOG_2:
                currentDialogType = DialogType.DIALOG_2;
                commands.append("Pages/HyspeechDialog2.ui");

                for(int i = 0; i <= 1; i++) {
                    commands.set("#Content" + i + ".Text", Message.translation(asset.entries[i].content).param("username", playerRef.getUsername()));
                }
                break;
            case DIALOG_3:
                currentDialogType = DialogType.DIALOG_3;
                commands.append("Pages/HyspeechDialog3.ui");

                for(int i = 0; i <= 2; i++) {
                    commands.set("#Content" + i + ".Text", Message.translation(asset.entries[i].content).param("username", playerRef.getUsername()));
                }
                break;
            case DIALOG_4:
                currentDialogType = DialogType.DIALOG_4;
                commands.append("Pages/HyspeechDialog4.ui");

                for(int i = 0; i <= 3; i++) {
                    commands.set("#Content" + i + ".Text", Message.translation(asset.entries[i].content).param("username", playerRef.getUsername()));
                }
                break;

            default:
                currentDialogType = DialogType.UNSET;
                break;
        }

        if(currentDialogType.name().contains("DIALOG"))
            eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#NextButton", EventData.of("Next", "true"));
        else if (currentDialogType.name().contains("CHOICE")) {
            for (int i = 0; i < asset.entries.length; i++) {
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#Content" + i, EventData.of("Entry" + i, "true"));
            }
        }

        if(currentDialogType == DialogType.UNSET)
            this.close();

        commands.set("#NameTitle.Text", Message.translation("hyspeech.dialog." + asset.getId() + ".name")
                .param("username", playerRef.getUsername()));

        isProcessing = false;
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull PageData data) {
        boolean needsUpdate = false;

        HyspeechDialogAsset asset = getAsset();

        if(asset == null) {
            this.close();
            return;
        }

        if(!this.isProcessing) {
            if (data.doNext != null && data.doNext) {
                isProcessing = true;
                setKey(asset.getNext());
            }

            if (data.doEntry0 != null && data.doEntry0) {
                isProcessing = true;

                if(asset.entries.length > 0)
                    setKey(asset.getEntries()[0].getNext());
            }

            if (data.doEntry1 != null && data.doEntry1) {
                isProcessing = true;

                if(asset.entries.length > 1)
                    setKey(asset.getEntries()[1].getNext());
            }

            if (data.doEntry2 != null && data.doEntry2) {
                isProcessing = true;

                if(asset.entries.length > 2)
                    setKey(asset.getEntries()[2].getNext());
            }

            if (data.doEntry3 != null && data.doEntry3) {
                isProcessing = true;

                if(asset.entries.length > 3)
                    setKey(asset.getEntries()[3].getNext());
            }

            if(isProcessing) {
                this.rebuild();
                return;
            }
        }

        if (data.doNext != null && data.doNext && !this.isProcessing) {
            isProcessing = true;
            setKey(asset.getNext());

            this.rebuild();
            return;
        }

        if (needsUpdate) {
            this.sendUpdate();
        }
    }

    public HyspeechDialogAsset getAsset() {
        AssetStore<String, HyspeechDialogAsset, DefaultAssetMap<String, HyspeechDialogAsset>> hyspeechDialogStore =
                AssetRegistry.getAssetStore(HyspeechDialogAsset.class);

        if(hyspeechDialogStore == null) {
            return null;
        }

        DefaultAssetMap<String, HyspeechDialogAsset> assetMap = hyspeechDialogStore.getAssetMap();

        if(assetMap == null) {
            return null;
        }

        return assetMap.getAsset(key);
    }

    public enum DialogType {
        CHOICE_2,
        CHOICE_3,
        CHOICE_4,
        DIALOG_1,
        DIALOG_2,
        DIALOG_3,
        DIALOG_4,
        UNSET;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DialogType currentDialogType = DialogType.UNSET;
    public boolean isProcessing = true;
    public String key;

    public static class PageData {
        static final String KEY_NEXT = "Next";
        static final String KEY_ENTRY0 = "Entry0";
        static final String KEY_ENTRY1 = "Entry1";
        static final String KEY_ENTRY2 = "Entry2";
        static final String KEY_ENTRY3 = "Entry3";

        public static final BuilderCodec<PageData> CODEC =
                BuilderCodec.builder(PageData.class, PageData::new)
                        .append(
                                new KeyedCodec<>("Next", Codec.STRING),
                                (entry, s) -> entry.doNext = "true".equalsIgnoreCase(s),
                                entry -> Boolean.TRUE.equals(entry.doNext) ? "true" : null
                        )
                        .add()
                        .append(
                                new KeyedCodec<>("Entry0", Codec.STRING),
                                (entry, s) -> entry.doEntry0 = "true".equalsIgnoreCase(s),
                                entry -> Boolean.TRUE.equals(entry.doEntry0) ? "true" : null
                        )
                        .add()
                        .append(
                                new KeyedCodec<>("Entry1", Codec.STRING),
                                (entry, s) -> entry.doEntry1 = "true".equalsIgnoreCase(s),
                                entry -> Boolean.TRUE.equals(entry.doEntry1) ? "true" : null
                        )
                        .add()
                        .append(
                                new KeyedCodec<>("Entry2", Codec.STRING),
                                (entry, s) -> entry.doEntry2 = "true".equalsIgnoreCase(s),
                                entry -> Boolean.TRUE.equals(entry.doEntry2) ? "true" : null
                        )
                        .add()
                        .append(
                                new KeyedCodec<>("Entry3", Codec.STRING),
                                (entry, s) -> entry.doEntry3 = "true".equalsIgnoreCase(s),
                                entry -> Boolean.TRUE.equals(entry.doEntry3) ? "true" : null
                        )
                        .add()
                        .build();

        @Nullable
        private Boolean doNext;
        @Nullable
        private Boolean doEntry0;
        @Nullable
        private Boolean doEntry1;
        @Nullable
        private Boolean doEntry2;
        @Nullable
        private Boolean doEntry3;
    }
}

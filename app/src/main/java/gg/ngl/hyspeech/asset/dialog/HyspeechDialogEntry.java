package gg.ngl.hyspeech.asset.dialog;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import gg.ngl.hyspeech.asset.macro.HyspeechMacroAsset;

import javax.annotation.Nonnull;

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

public class HyspeechDialogEntry {
    public static final BuilderCodec<HyspeechDialogEntry> CODEC =
            BuilderCodec
                    .builder(HyspeechDialogEntry.class, HyspeechDialogEntry::new)
                    .append(
                            new KeyedCodec<>("Content", Codec.STRING),
                            (obj, val) -> obj.content = val,
                            obj -> obj.content
                    )
                    .addValidator(Validators.nonNull())
                    .add()
                    .append(
                            new KeyedCodec<>("Next", Codec.STRING),
                            (obj, val) -> obj.next = val,
                            obj -> obj.next
                    )
                    .add()
                    .append(
                            new KeyedCodec<>("Hyspeech Macro", HyspeechMacroAsset.CODEC),
                            (obj, val) -> obj.macro = val,
                            obj -> obj.macro
                    )
                    .add()
                    .build();

    public String content;
    public String next;
    public HyspeechMacroAsset macro;

    public HyspeechDialogEntry(String content, String next) {
        this.content = content;
        this.next = next;
    }

    protected HyspeechDialogEntry() {
    }

    public String getContent() {
        return this.content;
    }

    public String getNext() {
        return this.next;
    }

    public HyspeechMacroAsset getMacro() {
        return this.macro;
    }

    @Nonnull
    public String toString() {
        return "HyspeechDialogEntry{next=" + this.next + ", content=" + this.content + "}";
    }
}
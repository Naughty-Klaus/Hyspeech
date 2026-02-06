package gg.ngl.hyspeech.asset.dialog;

import com.hypixel.hytale.codec.codecs.EnumCodec;

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

public class HyspeechDialogEntryType {

    public enum DialogEntryType {
        LABEL,
        CHOICE
    }

    public static final EnumCodec<DialogEntryType> DIALOG_ENTRY_TYPE_ENUM_CODEC = new EnumCodec<>(DialogEntryType.class);

}

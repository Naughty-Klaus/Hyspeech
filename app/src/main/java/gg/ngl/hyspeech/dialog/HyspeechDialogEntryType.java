package gg.ngl.hyspeech.dialog;

import com.hypixel.hytale.codec.codecs.EnumCodec;

public class HyspeechDialogEntryType {

    public enum DialogEntryType {
        LABEL,
        CHOICE
    }

    public static final EnumCodec<DialogEntryType> DIALOG_ENTRY_TYPE_ENUM_CODEC = new EnumCodec<>(DialogEntryType.class);

}

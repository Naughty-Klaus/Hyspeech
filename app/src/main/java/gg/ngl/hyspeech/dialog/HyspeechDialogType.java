package gg.ngl.hyspeech.dialog;

import com.hypixel.hytale.codec.codecs.EnumCodec;

public class HyspeechDialogType {

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

    public static final EnumCodec<DialogType> DIALOG_TYPE_ENUM_CODEC = new EnumCodec<>(DialogType.class);

}

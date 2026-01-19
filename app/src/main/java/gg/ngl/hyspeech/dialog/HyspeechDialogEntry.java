package gg.ngl.hyspeech.dialog;

import com.hypixel.hytale.builtin.adventure.shop.barter.BarterItemStack;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
import com.hypixel.hytale.codec.validation.Validators;
import gg.ngl.hyspeech.dialog.HyspeechDialogEntryType.DialogEntryType;
import java.util.Arrays;
import javax.annotation.Nonnull;

public class HyspeechDialogEntry {
    public static final BuilderCodec<HyspeechDialogEntry> CODEC =
            BuilderCodec
                    .builder(HyspeechDialogEntry.class, HyspeechDialogEntry::new)
                    /*.append(
                            new KeyedCodec<>("Type", HyspeechDialogEntryType.DIALOG_ENTRY_TYPE_ENUM_CODEC),
                            (obj, val) -> obj.type = val,
                            obj -> obj.type
                    )
                    .addValidator(Validators.nonNull())
                    .add()*/
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
                    .build();

    //public DialogEntryType type;
    public String content;
    public String next;

    public HyspeechDialogEntry(/*DialogEntryType type,*/ String content, String next) {
        //this.type = type;
        this.content = content;
        this.next = next;
    }

    protected HyspeechDialogEntry() {
    }

    /*public DialogEntryType getType() {
        return this.type;
    }*/

    public String getContent() {
        return this.content;
    }

    public String getNext() {
        return this.next;
    }

    @Nonnull
    public String toString() {
        return "HyspeechDialogEntry{" + /*type=" + this.type + ", */ "next=" + this.next + ", content=" + this.content + "}";
    }
}
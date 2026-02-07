package gg.ngl.hyspeech.player.ui.page;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

import javax.annotation.Nullable;

public class HyspeechDialogPageData {
    public static final BuilderCodec<HyspeechDialogPageData> CODEC =
            BuilderCodec.builder(HyspeechDialogPageData.class, HyspeechDialogPageData::new)
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

    public Boolean doNext() {
        return this.doNext;
    }
    public Boolean doEntry0() {
        return this.doEntry0;
    }
    public Boolean doEntry1() {
        return this.doEntry1;
    }
    public Boolean doEntry2() {
        return this.doEntry2;
    }
    public Boolean doEntry3() {
        return this.doEntry3;
    }

    public Boolean getEntry(int i) {
        return switch (i) {
            case 0 -> doEntry0();
            case 1 -> doEntry1();
            case 2 -> doEntry2();
            case 3 -> doEntry3();
            default -> null;
        };
    }
}

package gg.ngl.hyspeech.asset.quest;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

import static gg.ngl.hyspeech.asset.quest.HyspeechQuestStatus.QUEST_STATUS_ENUM_CODEC;
import static gg.ngl.hyspeech.asset.quest.HyspeechQuestStatus.QuestStatus;

import javax.annotation.Nonnull;

public class HyspeechQuestObjective {
    public static final BuilderCodec<HyspeechQuestObjective> CODEC =
            BuilderCodec
                    .builder(HyspeechQuestObjective.class, HyspeechQuestObjective::new)
                    .append(
                            new KeyedCodec<>("Status", QUEST_STATUS_ENUM_CODEC),
                            (obj, val) -> obj.status = val,
                            obj -> obj.status
                    )
                    .add()
                    .append(
                            new KeyedCodec<>("Message", Codec.STRING),
                            (obj, val) -> obj.message = val,
                            obj -> obj.message
                    )
                    .add()
                    .build();

    private String message = "";
    private QuestStatus status = QuestStatus.INACTIVE;

    private boolean updated = true;

    public HyspeechQuestObjective(QuestStatus status, String message) {
        this.message = message;
        this.status = status;
    }

    protected HyspeechQuestObjective() {
    }

    public QuestStatus getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isUpdated() {
        return this.updated;
    }

    public void setStatus(QuestStatus status) {
        this.status = status;

        update();
    }

    public void setMessage(String message) {
        this.message = message;

        update();
    }

    private void update() {
        this.updated = true;
    }

    @Nonnull
    public String toString() {
        return "Objective{status=" + this.status + ", message='" + this.message + "'}";
    }
}


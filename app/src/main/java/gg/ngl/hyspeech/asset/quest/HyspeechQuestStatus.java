package gg.ngl.hyspeech.asset.quest;

import com.hypixel.hytale.codec.codecs.EnumCodec;

public class HyspeechQuestStatus {

    public enum QuestStatus {
        INACTIVE,
        AVAILABLE,
        ACTIVE,
        COMPLETED,
        FAILED
    }

    public static final EnumCodec<QuestStatus> QUEST_STATUS_ENUM_CODEC = new EnumCodec<>(QuestStatus.class);

}
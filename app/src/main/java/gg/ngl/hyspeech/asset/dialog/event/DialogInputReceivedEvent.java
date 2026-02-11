package gg.ngl.hyspeech.asset.dialog.event;

import gg.ngl.hyspeech.asset.dialog.HyspeechDialogAsset;

public record DialogInputReceivedEvent(
        DialogEventContext context,
        HyspeechDialogAsset asset,
        String input
) implements DialogEvent {}

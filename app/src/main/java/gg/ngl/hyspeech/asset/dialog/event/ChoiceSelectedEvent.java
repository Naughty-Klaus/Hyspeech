package gg.ngl.hyspeech.asset.dialog.event;

import gg.ngl.hyspeech.asset.dialog.HyspeechDialogAsset;
import gg.ngl.hyspeech.asset.dialog.HyspeechDialogEntry;

public record ChoiceSelectedEvent(
        DialogEventContext context,
        HyspeechDialogAsset asset,
        int selectedIndex,
        HyspeechDialogEntry selectedEntry
) implements DialogEvent {}

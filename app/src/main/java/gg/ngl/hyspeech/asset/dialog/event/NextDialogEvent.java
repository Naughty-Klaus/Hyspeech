package gg.ngl.hyspeech.asset.dialog.event;

import gg.ngl.hyspeech.asset.dialog.HyspeechDialogAsset;

public record NextDialogEvent(DialogEventContext context, HyspeechDialogAsset asset)
        implements DialogEvent {}

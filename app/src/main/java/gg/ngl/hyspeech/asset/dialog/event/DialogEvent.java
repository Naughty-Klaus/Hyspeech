package gg.ngl.hyspeech.asset.dialog.event;

public sealed interface DialogEvent
        permits NextDialogEvent, DialogInputReceivedEvent, ChoiceSelectedEvent {

    DialogEventContext context();
}


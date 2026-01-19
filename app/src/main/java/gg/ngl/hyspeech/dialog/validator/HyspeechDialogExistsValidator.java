package gg.ngl.hyspeech.dialog.validator;

import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
import gg.ngl.hyspeech.dialog.HyspeechDialogAsset;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class HyspeechDialogExistsValidator extends AssetValidator {
    private static final HyspeechDialogExistsValidator DEFAULT_INSTANCE = new HyspeechDialogExistsValidator();

    private HyspeechDialogExistsValidator() {
    }

    private HyspeechDialogExistsValidator(EnumSet<Config> config) {
        super(config);
    }

    @Override
    @Nonnull
    public String getDomain() {
        return "HyspeechDialog";
    }

    @Override
    public boolean test(String marker) {
        return HyspeechDialogAsset.getAssetMap().getAsset(marker) != null;
    }

    @Override
    @Nonnull
    public String errorMessage(String marker, String attributeName) {
        return "The hyspeech dialog asset with the name \"" + marker + "\" does not exist for attribute \"" + attributeName + "\"";
    }

    @Override
    @Nonnull
    public String getAssetName() {
        return HyspeechDialogAsset.class.getSimpleName();
    }

    public static HyspeechDialogExistsValidator required() {
        return DEFAULT_INSTANCE;
    }

    @Nonnull
    public static HyspeechDialogExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
        return new HyspeechDialogExistsValidator(config);
    }
}

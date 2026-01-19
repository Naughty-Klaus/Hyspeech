package gg.ngl.hyspeech.commands;

// Example from: hytale-docs/content/commands/creating-commands.en.md
// This file tests that ALL commands documentation examples compile correctly

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import gg.ngl.hyspeech.Hyspeech;
import gg.ngl.hyspeech.ui.page.HyspeechDialogPage;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Tests creating-commands.en.md documentation
 */
public class CreatingCommandsExample {

    // From "Basic Command Structure" section
    public static class HelloCommand extends AbstractPlayerCommand {

        public HelloCommand() {
            super("hello", "Sends a greeting message");
        }

        @Override
        protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
            Player playerComponent = store.getComponent(ref, Player.getComponentType());
            playerComponent.getPageManager().openCustomPage(ref, store, new HyspeechDialogPage(playerRef, "Hans_Intro"));
        }
    }

    // From "Command with Arguments" section
    public static class GiveCommand extends AbstractCommand {

        private final RequiredArg<PlayerRef> targetArg;
        private final RequiredArg<Item> itemArg;

        public GiveCommand() {
            super("give", "Give an item to a player");

            targetArg = withRequiredArg("player", "Target player", ArgTypes.PLAYER_REF);
            itemArg = withRequiredArg("item", "Item to give", ArgTypes.ITEM_ASSET);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            PlayerRef targetRef = context.get(targetArg);
            Item item = context.get(itemArg);

            // PlayerRef provides direct access to player info
            String username = targetRef.getUsername();

            // For ECS operations, use getReference() to access the EntityStore
            // Ref<EntityStore> entityRef = targetRef.getReference();

            // Give item to player...
            context.sendMessage(Message.raw("Gave item to " + username));

            return null;
        }
    }

    // From "Adding Aliases" section
    public static class TeleportCommand extends AbstractCommand {

        public TeleportCommand() {
            super("teleport", "Teleport to a location");
            addAliases("tp", "warp");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            return null;
        }
    }

    // From "Optional Arguments" section
    public static class GiveWithOptionalCommand extends AbstractCommand {

        private final RequiredArg<PlayerRef> targetArg;
        private final RequiredArg<Item> itemArg;
        private final OptionalArg<Integer> countArg;

        public GiveWithOptionalCommand() {
            super("giveopt", "Give items to a player");

            targetArg = withRequiredArg("player", "Target player", ArgTypes.PLAYER_REF);
            itemArg = withRequiredArg("item", "Item to give", ArgTypes.ITEM_ASSET);
            countArg = withOptionalArg("count", "Number of items", ArgTypes.INTEGER);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            PlayerRef targetRef = context.get(targetArg);
            Item item = context.get(itemArg);
            Integer count = context.get(countArg);  // May be null

            int amount = count != null ? count : 1;
            // Give items to player...

            return null;
        }
    }

    // From "Default Arguments" section
    public static class GiveWithDefaultCommand extends AbstractCommand {

        private final DefaultArg<Integer> countArg;

        public GiveWithDefaultCommand() {
            super("givedef", "Give items to a player");

            countArg = withDefaultArg("count", "Number of items",
                ArgTypes.INTEGER, 1, "defaults to 1");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            int count = context.get(countArg); // Never null, uses default
            return null;
        }
    }

    // From "Flag Arguments" section
    public static class BroadcastCommand extends AbstractCommand {

        private final FlagArg silentFlag;

        public BroadcastCommand() {
            super("broadcast", "Send a message to all players");

            silentFlag = withFlagArg("silent", "Don't show sender name");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            boolean silent = context.provided(silentFlag);
            return null;
        }
    }

    // From "Requiring Confirmation" section
    public static class ResetCommand extends AbstractCommand {

        public ResetCommand() {
            super("reset", "Reset all player data", true); // requiresConfirmation = true
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            // This only runs if --confirm was provided
            return null;
        }
    }
}

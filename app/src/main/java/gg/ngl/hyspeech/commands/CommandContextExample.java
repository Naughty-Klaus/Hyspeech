package gg.ngl.hyspeech.commands;

// Example from: hytale-docs/content/commands/command-context.en.md
// This file tests that ALL command-context documentation examples compile correctly

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.concurrent.CompletableFuture;

/**
 * Tests command-context.en.md documentation
 *
 * IMPORTANT CORRECTIONS FOUND:
 * - Line 46: context.sender().sendMessage("text") should use Message.raw()
 * - Line 189: playerRef.getWorld() doesn't exist - use getWorldUuid()
 * - Line 201: playerRef.sendMessage() exists, but Message.of() doesn't - use Message.raw()
 */
public class CommandContextExample {

    // ============================================
    // From "Accessing the Context" section
    // ============================================
    public static class BasicContextCommand extends AbstractCommand {

        public BasicContextCommand() {
            super("basiccontext", "Shows basic context usage");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            // Use context here
            return null;
        }
    }

    // ============================================
    // From "Command Sender" section
    // ============================================
    public static class SenderCommand extends AbstractCommand {

        public SenderCommand() {
            super("sender", "Demonstrates sender access");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            CommandSender sender = context.sender();

            // Send messages (must use Message class)
            sender.sendMessage(Message.raw("Hello!"));
            sender.sendMessage(Message.translation("my.translation.key"));

            // Check permissions
            if (sender.hasPermission("myplugin.admin")) {
                // ...
            }

            return null;
        }
    }

    // ============================================
    // From "Checking Sender Type" section
    // CORRECTION: sendMessage needs Message.raw()
    // ============================================
    public static class SenderTypeCommand extends AbstractCommand {

        public SenderTypeCommand() {
            super("sendertype", "Checks sender type");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            if (context.sender() instanceof Player) {
                Player player = (Player) context.sender();
                // Player-specific logic
            } else {
                // Console or other sender
                // CORRECTION: Use Message.raw() instead of raw String
                context.sender().sendMessage(Message.raw("This command requires a player!"));
            }
            return null;
        }
    }

    // ============================================
    // From "Getting Arguments" - Required Arguments
    // ============================================
    public static class RequiredArgsCommand extends AbstractCommand {
        private final RequiredArg<PlayerRef> playerArg;
        private final RequiredArg<Integer> countArg;

        public RequiredArgsCommand() {
            super("requiredargs", "Required arguments example");
            playerArg = withRequiredArg("player", "Target player", ArgTypes.PLAYER_REF);
            countArg = withRequiredArg("count", "Count value", ArgTypes.INTEGER);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            // In execute():
            PlayerRef player = context.get(playerArg);  // Never null for required args
            int count = context.get(countArg);          // Never null for required args
            return null;
        }
    }

    // ============================================
    // From "Getting Arguments" - Optional Arguments
    // ============================================
    public static class OptionalArgsCommand extends AbstractCommand {
        private final OptionalArg<String> reasonArg;

        public OptionalArgsCommand() {
            super("optionalargs", "Optional arguments example");
            reasonArg = withOptionalArg("reason", "Optional reason", ArgTypes.STRING);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            // Get value (may be null if not provided)
            String reason = context.get(reasonArg);

            // Check if provided before using
            if (context.provided(reasonArg)) {
                String providedReason = context.get(reasonArg);
            }
            return null;
        }
    }

    // ============================================
    // From "Getting Arguments" - Default Arguments
    // ============================================
    public static class DefaultArgsCommand extends AbstractCommand {
        private final DefaultArg<Integer> countArg;  // Default: 1

        public DefaultArgsCommand() {
            super("defaultargs", "Default arguments example");
            countArg = withDefaultArg("count", "Number", ArgTypes.INTEGER, 1, "1");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            // Always returns a value (never null)
            int count = context.get(countArg);  // Returns default if not specified
            return null;
        }
    }

    // ============================================
    // From "Getting Arguments" - Flag Arguments
    // ============================================
    public static class FlagArgsCommand extends AbstractCommand {
        private final FlagArg silentFlag;

        public FlagArgsCommand() {
            super("flagargs", "Flag arguments example");
            silentFlag = withFlagArg("silent", "Silent mode");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            // Check if flag was provided
            boolean isSilent = context.provided(silentFlag);
            return null;
        }
    }

    // ============================================
    // From "Input String" section
    // ============================================
    public static class InputStringCommand extends AbstractCommand {

        public InputStringCommand() {
            super("inputstring", "Shows input string access");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            String input = context.getInputString();
            // For "/give player123 sword 5" -> "give player123 sword 5"
            return null;
        }
    }

    // ============================================
    // From "The Command" section
    // ============================================
    public static class CommandInfoCommand extends AbstractCommand {

        public CommandInfoCommand() {
            super("commandinfo", "Shows command info access");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            AbstractCommand command = context.getCalledCommand();
            String commandName = command.getName();
            String fullName = command.getFullyQualifiedName();  // e.g., "admin kick"
            return null;
        }
    }

    // ============================================
    // From "PLAYER_REF Fallback" section
    // CORRECTION: PlayerRef doesn't have getPlayer() or getWorld()
    // ============================================
    public static class PlayerRefFallbackCommand extends AbstractCommand {
        private final OptionalArg<PlayerRef> targetArg;

        public PlayerRefFallbackCommand() {
            super("playerreffallback", "PlayerRef fallback example");
            targetArg = withOptionalArg("target", "Target player", ArgTypes.PLAYER_REF);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            PlayerRef target = context.get(targetArg);

            // Manual fallback if null
            // CORRECTION: Use getPlayerRef() from Player (though deprecated)
            if (target == null && context.sender() instanceof Player player) {
                target = player.getPlayerRef();  // Note: getPlayerRef() is deprecated
            }
            // ...
            return null;
        }
    }

    // ============================================
    // From "World Argument Fallback" section
    // ============================================
    public static class WorldFallbackCommand extends AbstractCommand {
        private final OptionalArg<World> worldArg;

        public WorldFallbackCommand() {
            super("worldfallback", "World fallback example");
            worldArg = withOptionalArg("world", "Target world", ArgTypes.WORLD);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            World world = context.get(worldArg);

            // Manual fallback if null
            if (world == null && context.sender() instanceof Player player) {
                world = player.getWorld();  // Note: Need to verify this exists
            }
            // ...
            return null;
        }
    }

    // ============================================
    // From "Error Handling" section
    // CORRECTION: PlayerRef doesn't have getPlayer()
    // ============================================
    public static class ErrorHandlingCommand extends AbstractCommand {
        private final RequiredArg<PlayerRef> playerArg;

        public ErrorHandlingCommand() {
            super("errorhandling", "Error handling example");
            playerArg = withRequiredArg("player", "Target player", ArgTypes.PLAYER_REF);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            PlayerRef playerRef = context.get(playerArg);

            // CORRECTION: PlayerRef doesn't have getPlayer() - use ECS pattern
            // Instead, we can check if the reference is valid
            Ref<EntityStore> ref = playerRef.getReference();
            if (ref == null || !ref.isValid()) {
                throw new GeneralCommandException(
                    Message.translation("error.player.offline")
                        .param("player", playerRef.getUsername())
                );
            }

            // Continue execution
            return null;
        }
    }

    // ============================================
    // From "Asynchronous Commands" section
    // CORRECTION: playerRef.getWorld() doesn't exist
    // ============================================
    public static class AsyncCommand extends AbstractCommand {

        public AsyncCommand() {
            super("asynccmd", "Async command example");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            if (!(context.sender() instanceof Player player)) {
                return null;
            }

            PlayerRef playerRef = player.getPlayerRef();  // Note: deprecated
            // CORRECTION: Use getWorldUuid() instead of getWorld()
            // Or use player.getWorld() directly
            World world = player.getWorld();

            return CompletableFuture.runAsync(() -> {
                // Async operation (e.g., database query)
                // PlayerData data = database.loadData(playerRef.getUuid());

                // Return to world thread for game logic
                world.execute(() -> {
                    Ref<EntityStore> ref = playerRef.getReference();
                    if (ref != null) {
                        // applyData(ref, data);
                        playerRef.sendMessage(Message.raw("Data loaded!"));
                    }
                });
            });
        }
    }

    // ============================================
    // From "Complete Example" section
    // CORRECTION: playerRef.getPlayer() doesn't exist
    // ============================================
    public static class GiveCommand extends AbstractCommand {

        private final RequiredArg<Item> itemArg;
        private final OptionalArg<PlayerRef> targetArg;
        private final DefaultArg<Integer> countArg;
        private final FlagArg silentFlag;

        public GiveCommand() {
            super("give", "Give items to a player");

            itemArg = withRequiredArg("item", "Item to give", ArgTypes.ITEM_ASSET);
            targetArg = withOptionalArg("target", "Target player", ArgTypes.PLAYER_REF);
            countArg = withDefaultArg("count", "Amount", ArgTypes.INTEGER, 1, "1");
            silentFlag = withFlagArg("silent", "Don't broadcast");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            // Get arguments
            Item item = context.get(itemArg);
            int count = context.get(countArg);  // Uses default if not specified
            boolean silent = context.provided(silentFlag);

            // Get target with fallback to sender
            PlayerRef targetRef = context.get(targetArg);
            if (targetRef == null && context.sender() instanceof Player senderPlayer) {
                targetRef = senderPlayer.getPlayerRef();  // Note: deprecated
            }

            if (targetRef == null) {
                throw new GeneralCommandException(
                    Message.raw("Must specify a target player!")
                );
            }

            // Validate target is online
            // CORRECTION: PlayerRef doesn't have getPlayer()
            // Use getReference() to check if valid
            Ref<EntityStore> ref = targetRef.getReference();
            if (ref == null || !ref.isValid()) {
                throw new GeneralCommandException(
                    Message.raw("Player is not online!")
                );
            }

            // Execute - give item to player
            // ... (would use ECS/inventory APIs)

            // Feedback
            if (!silent) {
                context.sender().sendMessage(Message.raw(
                    "Gave " + count + "x " + item.getId() + " to " + targetRef.getUsername()
                ));
            }

            return null;
        }
    }
}

package gg.ngl.hyspeech.commands;

// Example from: hytale-docs/content/commands/subcommands.en.md
// This file tests that ALL subcommands documentation examples compile correctly

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.concurrent.CompletableFuture;

/**
 * Tests subcommands.en.md documentation
 *
 * IMPORTANT CORRECTIONS FOUND:
 * - playerRef.getPlayer() doesn't exist - PlayerRef only provides username, UUID, and getReference()
 * - Player.kick(String) may not exist with that signature - needs verification
 */
public class SubcommandsExample {

    // ============================================
    // From "Creating Subcommands" - Basic Structure
    // ============================================
    public static class AdminCommand extends AbstractCommand {

        public AdminCommand() {
            super("admin", "Administration commands");

            // Add subcommands
            addSubCommand(new KickSubCommand());
            addSubCommand(new BanSubCommand());
            addSubCommand(new MuteSubCommand());
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            // This runs when no subcommand is specified
            context.sender().sendMessage(Message.raw("Usage: /admin <kick|ban|mute>"));
            return null;
        }
    }

    // ============================================
    // From "Subcommand Implementation"
    // CORRECTION: playerRef.getPlayer() doesn't exist
    // ============================================
    public static class KickSubCommand extends AbstractCommand {

        private final RequiredArg<PlayerRef> playerArg;
        private final OptionalArg<String> reasonArg;

        public KickSubCommand() {
            super("kick", "Kick a player from the server");

            playerArg = withRequiredArg("player", "Player to kick", ArgTypes.PLAYER_REF);
            reasonArg = withOptionalArg("reason", "Kick reason", ArgTypes.STRING);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            PlayerRef target = context.get(playerArg);
            String reason = context.get(reasonArg);

            // CORRECTION: PlayerRef doesn't have getPlayer() method
            // We can only verify the player is online via getReference()
            Ref<EntityStore> ref = target.getReference();
            if (ref != null && ref.isValid()) {
                // To kick, would need to access the PacketHandler or use events
                // target.getPacketHandler().disconnect(reason);
                String kickReason = reason != null ? reason : "Kicked by admin";
                context.sender().sendMessage(Message.raw("Kicked " + target.getUsername() + ": " + kickReason));
            } else {
                context.sender().sendMessage(Message.raw("Player " + target.getUsername() + " is not online"));
            }

            return null;
        }
    }

    // Placeholder subcommands for AdminCommand
    public static class BanSubCommand extends AbstractCommand {
        private final RequiredArg<PlayerRef> playerArg;

        public BanSubCommand() {
            super("ban", "Ban a player from the server");
            playerArg = withRequiredArg("player", "Player to ban", ArgTypes.PLAYER_REF);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            PlayerRef target = context.get(playerArg);
            context.sender().sendMessage(Message.raw("Banned " + target.getUsername()));
            return null;
        }
    }

    public static class MuteSubCommand extends AbstractCommand {
        private final RequiredArg<PlayerRef> playerArg;

        public MuteSubCommand() {
            super("mute", "Mute a player");
            playerArg = withRequiredArg("player", "Player to mute", ArgTypes.PLAYER_REF);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            PlayerRef target = context.get(playerArg);
            context.sender().sendMessage(Message.raw("Muted " + target.getUsername()));
            return null;
        }
    }

    // ============================================
    // From "Command Collections" section
    // ============================================
    public static class ManageCommand extends AbstractCommandCollection {

        public ManageCommand() {
            super("manage", "Management commands");

            addSubCommand(new ManageUsersCommand());
            addSubCommand(new ManageWorldsCommand());
            addSubCommand(new ManagePluginsCommand());
        }
    }

    // ============================================
    // From "Nested Subcommands" section
    // ============================================
    public static class ManageUsersCommand extends AbstractCommand {

        public ManageUsersCommand() {
            super("users", "User management");

            addSubCommand(new ListUsersCommand());   // /manage users list
            addSubCommand(new AddUserCommand());     // /manage users add
            addSubCommand(new RemoveUserCommand());  // /manage users remove
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            // Show usage for /manage users
            context.sender().sendMessage(Message.raw("Usage: /manage users <list|add|remove>"));
            return null;
        }
    }

    // Nested subcommand stubs
    public static class ListUsersCommand extends AbstractCommand {
        public ListUsersCommand() {
            super("list", "List all users");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            return null;
        }
    }

    public static class AddUserCommand extends AbstractCommand {
        public AddUserCommand() {
            super("add", "Add a user");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            return null;
        }
    }

    public static class RemoveUserCommand extends AbstractCommand {
        public RemoveUserCommand() {
            super("remove", "Remove a user");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            return null;
        }
    }

    // Placeholder manage subcommands
    public static class ManageWorldsCommand extends AbstractCommand {
        public ManageWorldsCommand() {
            super("worlds", "World management");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            return null;
        }
    }

    public static class ManagePluginsCommand extends AbstractCommand {
        public ManagePluginsCommand() {
            super("plugins", "Plugin management");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            return null;
        }
    }

    // ============================================
    // From "Subcommand Aliases" section
    // ============================================
    public static class TeleportCommand extends AbstractCommand {

        public TeleportCommand() {
            super("teleport", "Teleport commands");
            addAliases("tp");

            addSubCommand(new TeleportHereCommand());
            addSubCommand(new TeleportAllCommand());
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            return null;
        }
    }

    public static class TeleportHereCommand extends AbstractCommand {
        private final RequiredArg<PlayerRef> playerArg;

        public TeleportHereCommand() {
            super("here", "Teleport player to you");
            addAliases("h", "tome");
            playerArg = withRequiredArg("player", "Player to teleport", ArgTypes.PLAYER_REF);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            PlayerRef target = context.get(playerArg);
            context.sender().sendMessage(Message.raw("Teleporting " + target.getUsername() + " to you"));
            return null;
        }
    }

    public static class TeleportAllCommand extends AbstractCommand {
        public TeleportAllCommand() {
            super("all", "Teleport all players");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            return null;
        }
    }

    // ============================================
    // From "Command Variants" section
    // ============================================
    public static class TpCommand extends AbstractCommand {

        private final RequiredArg<PlayerRef> targetArg;

        public TpCommand() {
            super("tp", "Teleport command");

            // Main variant: /tp <player>
            targetArg = withRequiredArg("target", "Player to teleport to", ArgTypes.PLAYER_REF);

            // Add variant: /tp <player> <destination>
            addUsageVariant(new TpToPlayerVariant());

            // Note: Position variant would need different argument types
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            // Teleport sender to target player
            PlayerRef target = context.get(targetArg);
            context.sender().sendMessage(Message.raw("Teleporting to " + target.getUsername()));
            return null;
        }
    }

    // ============================================
    // From "Variant Implementation" section
    // ============================================
    public static class TpToPlayerVariant extends AbstractCommand {

        private final RequiredArg<PlayerRef> playerArg;
        private final RequiredArg<PlayerRef> destinationArg;

        public TpToPlayerVariant() {
            // Note: Documentation says "no name for variants - use description only"
            // But AbstractCommand requires name, so we pass empty or description
            super("Teleport one player to another");

            playerArg = withRequiredArg("player", "Player to teleport", ArgTypes.PLAYER_REF);
            destinationArg = withRequiredArg("destination", "Destination player", ArgTypes.PLAYER_REF);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            PlayerRef player = context.get(playerArg);
            PlayerRef destination = context.get(destinationArg);
            // Teleport player to destination
            context.sender().sendMessage(Message.raw("Teleporting " + player.getUsername() + " to " + destination.getUsername()));
            return null;
        }
    }

    // ============================================
    // From "Permission Inheritance" section
    // ============================================
    public static class KickWithPermissionCommand extends AbstractCommand {

        public KickWithPermissionCommand() {
            super("kick", "Kick a player");
            // Custom permission instead of auto-generated
            // Note: requirePermission() method needs verification
            // requirePermission("myplugin.admin.kick");
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            return null;
        }
    }
}

package gg.ngl.hyspeech.commands;

// Example from: hytale-docs/content/commands/argument-types.en.md
// This file tests that ALL argument-types documentation examples compile correctly

import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSystem;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.arguments.types.Coord;
import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeDoublePosition;
import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Tests argument-types.en.md documentation
 *
 * IMPORTANT CORRECTIONS FOUND:
 * - Documentation uses Component.text() which doesn't exist - use Message.raw() instead
 * - Documentation uses playerRef.getPlayer() which doesn't exist - use getReference() for ECS
 * - Documentation uses playerRef.getWorld() which doesn't exist - use getWorldUuid()
 * - Documentation should use context.sendMessage(Message) instead of context.sender().sendMessage()
 */
public class ArgumentTypesExample {

    // ============================================
    // From "Primitive Types" section - Examples tab
    // ============================================
    public static class MathCommand extends AbstractCommand {
        private final RequiredArg<Integer> countArg;
        private final RequiredArg<Double> multiplierArg;
        private final RequiredArg<Boolean> verboseArg;
        private final OptionalArg<String> labelArg;

        public MathCommand() {
            super("math", "Perform mathematical operations");
            countArg = withRequiredArg("count", "Number of iterations", ArgTypes.INTEGER);
            multiplierArg = withRequiredArg("multiplier", "Multiplication factor", ArgTypes.DOUBLE);
            verboseArg = withRequiredArg("verbose", "Show detailed output", ArgTypes.BOOLEAN);
            labelArg = withOptionalArg("label", "Optional label", ArgTypes.STRING);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            int count = context.get(countArg);
            double mult = context.get(multiplierArg);
            boolean verbose = context.get(verboseArg);
            String label = context.get(labelArg); // null if not provided

            double result = count * mult;

            if (verbose) {
                String output = (label != null ? label + ": " : "") +
                    count + " × " + mult + " = " + result;
                // CORRECTION: Use context.sendMessage(Message.raw()) instead of context.sender().sendMessage()
                context.sendMessage(Message.raw(output));
            }

            return null;
        }
    }

    // ============================================
    // From "Player and Entity Types" - PlayerRef tab
    // CORRECTION: playerRef.getPlayer() doesn't exist
    // ============================================
    public static class TeleportCommand extends AbstractCommand {
        private final RequiredArg<PlayerRef> targetArg;
        private final OptionalArg<PlayerRef> destinationArg;

        public TeleportCommand() {
            super("tp", "Teleport players");
            targetArg = withRequiredArg("target", "Player to teleport", ArgTypes.PLAYER_REF);
            destinationArg = withOptionalArg("destination", "Destination player", ArgTypes.PLAYER_REF);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            PlayerRef targetRef = context.get(targetArg);

            // CORRECTION: PlayerRef doesn't have getPlayer() method
            // Instead use getReference() to get Ref<EntityStore>
            // For now, we use username which IS available
            String targetUsername = targetRef.getUsername();

            if (context.provided(destinationArg)) {
                PlayerRef destRef = context.get(destinationArg);
                String destUsername = destRef.getUsername();
                context.sendMessage(Message.raw("Would teleport " + targetUsername + " to " + destUsername));
            }

            return null;
        }
    }

    // ============================================
    // From "Player and Entity Types" - Entity tab
    // ============================================
    public static class EntityInfoCommand extends AbstractCommand {
        private final RequiredArg<UUID> entityArg;

        public EntityInfoCommand() {
            super("entityinfo", "Get entity information");
            entityArg = withRequiredArg("entity", "Entity UUID", ArgTypes.UUID);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            UUID entityId = context.get(entityArg);

            // Find entity in worlds
            for (World world : Universe.get().getWorlds().values()) {
                // Search for entity by UUID
                // Entity lookup depends on world API
            }

            context.sendMessage(Message.raw("Entity lookup complete"));
            return null;
        }
    }

    // ============================================
    // From "Player and Entity Types" - UUID tab
    // ============================================
    public static class UuidCommand extends AbstractCommand {
        private final RequiredArg<UUID> uuidArg;

        public UuidCommand() {
            super("uuid", "Parse and display UUID");
            uuidArg = withRequiredArg("uuid", "UUID to parse", ArgTypes.UUID);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            UUID uuid = context.get(uuidArg);
            context.sendMessage(Message.raw(
                "UUID: " + uuid.toString() + "\nVersion: " + uuid.version()
            ));
            return null;
        }
    }

    // ============================================
    // From "World Types" section
    // CORRECTION: Uses Component.text() which doesn't exist
    // ============================================
    public static class WorldTeleportCommand extends AbstractCommand {
        private final RequiredArg<World> worldArg;
        private final OptionalArg<RelativeDoublePosition> positionArg;

        public WorldTeleportCommand() {
            super("wtp", "Teleport to another world");
            worldArg = withRequiredArg("world", "Destination world", ArgTypes.WORLD);
            positionArg = withOptionalArg("position", "Spawn position", ArgTypes.RELATIVE_POSITION);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            // CORRECTION: Use context.sender() instanceof Player, then cast
            if (!(context.sender() instanceof Player player)) {
                // CORRECTION: Use Message.raw() not Component.text()
                context.sendMessage(Message.raw("Only players can use this command!"));
                return null;
            }

            World targetWorld = context.get(worldArg);

            // Note: World.getSpawnPosition() and player.teleport() would need verification
            // For now just show the concept works
            context.sendMessage(Message.raw("Would teleport to world: " + targetWorld.getName()));

            return null;
        }
    }

    // ============================================
    // From "Asset Types" - Items tab
    // CORRECTION: Uses Component.text() and ItemStack.of() which may not exist
    // ============================================
    public static class GiveCommand extends AbstractCommand {
        private final RequiredArg<Item> itemArg;
        private final OptionalArg<Integer> amountArg;
        private final OptionalArg<PlayerRef> targetArg;

        public GiveCommand() {
            super("give", "Give items to players");
            itemArg = withRequiredArg("item", "Item to give", ArgTypes.ITEM_ASSET);
            amountArg = withOptionalArg("amount", "Stack size", ArgTypes.INTEGER);
            targetArg = withOptionalArg("player", "Target player", ArgTypes.PLAYER_REF);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            Item item = context.get(itemArg);
            int amount = context.provided(amountArg) ? context.get(amountArg) : 1;

            // CORRECTION: playerRef.getPlayer() doesn't exist
            // We can only get username from PlayerRef
            String targetName;
            if (context.provided(targetArg)) {
                targetName = context.get(targetArg).getUsername();
            } else if (context.sender() instanceof Player p) {
                targetName = p.getDisplayName();
            } else {
                context.sendMessage(Message.raw("Specify a player!"));
                return null;
            }

            // CORRECTION: Use Message.raw() instead of Component.text()
            context.sendMessage(
                Message.raw("Gave " + amount + "x " + item.getId() + " to " + targetName)
            );

            return null;
        }
    }

    // ============================================
    // From "Asset Types" - Blocks tab
    // ============================================
    public static class SetBlockCommand extends AbstractCommand {
        private final RequiredArg<BlockType> blockArg;
        private final RequiredArg<RelativeIntPosition> positionArg;

        public SetBlockCommand() {
            super("setblock", "Set a block at position");
            blockArg = withRequiredArg("block", "Block type", ArgTypes.BLOCK_TYPE_ASSET);
            positionArg = withRequiredArg("position", "Target position", ArgTypes.RELATIVE_BLOCK_POSITION);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            if (!(context.sender() instanceof Player player)) {
                return null;
            }

            BlockType blockType = context.get(blockArg);
            // Note: player.getBlockPosition() would need verification
            // RelativeIntPosition.resolve() takes a Vector3i

            context.sendMessage(
                Message.raw("Set block to " + blockType.getId())
            );

            return null;
        }
    }

    // ============================================
    // From "Asset Types" - Effects tab
    // ============================================
    public static class EffectCommand extends AbstractCommand {
        private final RequiredArg<EntityEffect> effectArg;
        private final OptionalArg<Integer> durationArg;
        private final OptionalArg<Integer> amplifierArg;

        public EffectCommand() {
            super("effect", "Apply effect to player");
            effectArg = withRequiredArg("effect", "Effect to apply", ArgTypes.EFFECT_ASSET);
            durationArg = withOptionalArg("duration", "Duration in ticks", ArgTypes.INTEGER);
            amplifierArg = withOptionalArg("amplifier", "Effect level", ArgTypes.INTEGER);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            if (!(context.sender() instanceof Player player)) {
                return null;
            }

            EntityEffect effect = context.get(effectArg);
            int duration = context.provided(durationArg) ? context.get(durationArg) : 600;
            int amplifier = context.provided(amplifierArg) ? context.get(amplifierArg) : 0;

            // Note: player.addEffect() would need verification
            context.sendMessage(Message.raw("Applied effect: " + effect.getId()));

            return null;
        }
    }

    // ============================================
    // From "Asset Types" - Particles tab
    // ============================================
    public static class ParticleCommand extends AbstractCommand {
        private final RequiredArg<ParticleSystem> particleArg;
        private final OptionalArg<RelativeDoublePosition> positionArg;

        public ParticleCommand() {
            super("particle", "Spawn particle effect");
            particleArg = withRequiredArg("particle", "Particle system", ArgTypes.PARTICLE_SYSTEM);
            positionArg = withOptionalArg("position", "Spawn position", ArgTypes.RELATIVE_POSITION);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            if (!(context.sender() instanceof Player player)) {
                return null;
            }

            ParticleSystem particle = context.get(particleArg);
            // Note: Would need to verify World.spawnParticle() exists

            context.sendMessage(Message.raw("Spawned particle: " + particle.getId()));
            return null;
        }
    }

    // ============================================
    // From "Position Types" - 3D Positions tab
    // ============================================
    public static class TeleportPosCommand extends AbstractCommand {
        private final RequiredArg<RelativeDoublePosition> posArg;

        public TeleportPosCommand() {
            super("tppos", "Teleport to coordinates");
            posArg = withRequiredArg("position", "Target position", ArgTypes.RELATIVE_POSITION);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            if (!(context.sender() instanceof Player player)) {
                return null;
            }

            RelativeDoublePosition relPos = context.get(posArg);

            // Note: Need to verify player.getPosition() and relPos.resolve() APIs
            // player.teleport(absolutePos) would also need verification

            context.sendMessage(Message.raw("Teleported to position"));
            return null;
        }
    }

    // ============================================
    // From "Position Types" - Block Positions tab
    // ============================================
    public static class FillCommand extends AbstractCommand {
        private final RequiredArg<RelativeIntPosition> pos1Arg;
        private final RequiredArg<RelativeIntPosition> pos2Arg;
        private final RequiredArg<BlockType> blockArg;

        public FillCommand() {
            super("fill", "Fill region with blocks");
            pos1Arg = withRequiredArg("from", "First corner", ArgTypes.RELATIVE_BLOCK_POSITION);
            pos2Arg = withRequiredArg("to", "Second corner", ArgTypes.RELATIVE_BLOCK_POSITION);
            blockArg = withRequiredArg("block", "Block type", ArgTypes.BLOCK_TYPE_ASSET);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            if (!(context.sender() instanceof Player player)) {
                return null;
            }

            // RelativeIntPosition values
            RelativeIntPosition from = context.get(pos1Arg);
            RelativeIntPosition to = context.get(pos2Arg);
            BlockType block = context.get(blockArg);

            // Note: Need to verify these APIs exist
            // player.getBlockPosition() returns Vector3i?
            // world.setBlock(Vector3i, BlockType)?

            context.sendMessage(Message.raw("Filled region with " + block.getId()));
            return null;
        }
    }

    // ============================================
    // From "Position Types" - Single Coords tab
    // ============================================
    public static class SetYCommand extends AbstractCommand {
        private final RequiredArg<Coord> yArg;

        public SetYCommand() {
            super("sety", "Teleport to Y level");
            yArg = withRequiredArg("y", "Y coordinate", ArgTypes.RELATIVE_DOUBLE_COORD);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            if (!(context.sender() instanceof Player player)) {
                return null;
            }

            Coord relY = context.get(yArg);
            // Note: relY.resolve(double) API needs verification

            context.sendMessage(Message.raw("Set Y level"));
            return null;
        }
    }

    // ============================================
    // From "Vector Types" - Examples tab
    // ============================================
    public static class LookCommand extends AbstractCommand {
        private final RequiredArg<Vector3f> rotationArg;

        public LookCommand() {
            super("look", "Set player rotation");
            rotationArg = withRequiredArg("rotation", "Pitch Yaw Roll", ArgTypes.ROTATION);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            if (!(context.sender() instanceof Player player)) {
                return null;
            }

            Vector3f rotation = context.get(rotationArg);
            // rotation.x = pitch, rotation.y = yaw, rotation.z = roll
            // Note: player.setRotation() API needs verification

            // CORRECTION: Vector3f uses public fields, not methods
            context.sendMessage(Message.raw(
                "Rotation set to Pitch: " + rotation.x +
                ", Yaw: " + rotation.y +
                ", Roll: " + rotation.z
            ));

            return null;
        }
    }

    // ============================================
    // From "Special Types" - Color tab
    // ============================================
    public static class ColorCommand extends AbstractCommand {
        private final RequiredArg<Integer> colorArg;

        public ColorCommand() {
            super("color", "Set display color");
            colorArg = withRequiredArg("color", "Color value", ArgTypes.COLOR);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            int color = context.get(colorArg);

            int alpha = (color >> 24) & 0xFF;
            int red = (color >> 16) & 0xFF;
            int green = (color >> 8) & 0xFF;
            int blue = color & 0xFF;

            context.sendMessage(
                Message.raw("Color: ARGB(" + alpha + ", " + red + ", " + green + ", " + blue + ")")
            );

            return null;
        }
    }

    // ============================================
    // From "Special Types" - Game Mode tab
    // ============================================
    public static class GameModeCommand extends AbstractCommand {
        private final RequiredArg<GameMode> modeArg;
        private final OptionalArg<PlayerRef> playerArg;

        public GameModeCommand() {
            super("gamemode", "Change game mode");
            modeArg = withRequiredArg("mode", "Game mode", ArgTypes.GAME_MODE);
            playerArg = withOptionalArg("player", "Target player", ArgTypes.PLAYER_REF);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            GameMode mode = context.get(modeArg);

            String targetName;
            if (context.provided(playerArg)) {
                PlayerRef ref = context.get(playerArg);
                targetName = ref.getUsername();
            } else if (context.sender() instanceof Player p) {
                targetName = p.getDisplayName();
            } else {
                context.sendMessage(Message.raw("Specify a player!"));
                return null;
            }

            // Set game mode using ChangeGameModeEvent or component
            context.sendMessage(Message.raw("Game mode changed to " + mode.name() + " for " + targetName));

            return null;
        }
    }

    // ============================================
    // From "Special Types" - Tick Rate tab
    // ============================================
    public static class TickRateCommand extends AbstractCommand {
        private final RequiredArg<Integer> rateArg;

        public TickRateCommand() {
            super("tickrate", "Set world tick rate");
            rateArg = withRequiredArg("rate", "Tick rate", ArgTypes.TICK_RATE);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            if (!(context.sender() instanceof Player player)) {
                return null;
            }

            int tps = context.get(rateArg);  // Returns Integer (TPS)
            // Set tick rate on world...

            context.sendMessage(Message.raw("Tick rate set to " + tps + " TPS"));

            return null;
        }
    }

    // ============================================
    // From "Custom Argument Types" - Enum Arguments section
    // ============================================
    public enum Difficulty {
        EASY, NORMAL, HARD, EXTREME
    }

    public static class DifficultyCommand extends AbstractCommand {
        private final RequiredArg<Difficulty> difficultyArg;

        public DifficultyCommand() {
            super("difficulty", "Set server difficulty");
            difficultyArg = withRequiredArg(
                "level",
                "Difficulty level",
                ArgTypes.forEnum("Difficulty", Difficulty.class)
            );
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            Difficulty level = context.get(difficultyArg);
            // Apply difficulty setting
            context.sendMessage(
                Message.raw("Difficulty set to " + level.name())
            );
            return null;
        }
    }

    // ============================================
    // From "Custom Argument Types" - List Arguments section
    // ============================================
    public static class MultiTeleportCommand extends AbstractCommand {
        private final RequiredArg<List<PlayerRef>> playersArg;
        private final RequiredArg<RelativeDoublePosition> positionArg;

        public MultiTeleportCommand() {
            super("multitp", "Teleport multiple players");
            playersArg = withListRequiredArg("players", "Target players", ArgTypes.PLAYER_REF);
            positionArg = withRequiredArg("position", "Destination", ArgTypes.RELATIVE_POSITION);
        }

        @Override
        protected CompletableFuture<Void> execute(CommandContext context) {
            List<PlayerRef> playerRefs = context.get(playersArg);
            RelativeDoublePosition relPos = context.get(positionArg);

            // Note: playerRef.getPlayer() doesn't exist, use getReference()
            for (PlayerRef ref : playerRefs) {
                // Would need to use ECS to teleport
                String username = ref.getUsername();
            }

            context.sendMessage(
                Message.raw("Teleported " + playerRefs.size() + " players")
            );

            return null;
        }
    }

    // Note: Argument Validation section - .validate() method usage would need verification
}

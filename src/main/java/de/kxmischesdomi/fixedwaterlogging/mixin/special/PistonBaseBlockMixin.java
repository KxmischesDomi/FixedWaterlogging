package de.kxmischesdomi.fixedwaterlogging.mixin.special;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 *
 * Contains additional needed logic for pistons.
 */
@Mixin(PistonBaseBlock.class)
public abstract class PistonBaseBlockMixin extends Block {

	@Shadow @Final public static BooleanProperty EXTENDED;
	private static final Map<Direction, VoxelShape> SHAPES;

	public PistonBaseBlockMixin(Properties properties) {
		super(properties);
	}

	@ModifyArgs(method = "triggerEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	public void triggerEventSetBlock(Args args, BlockState blockState, Level level, BlockPos blockPos, int i, int j) {
		BlockState movingBlockState = args.get(1);
		if (movingBlockState.is(Blocks.MOVING_PISTON)) {
			FluidState fluidState = level.getFluidState(args.get(0));
			boolean water = fluidState.getType() == Fluids.WATER;
			args.set(1, movingBlockState.setValue(BlockStateProperties.WATERLOGGED, water));
		}
	}

	@ModifyArgs(method = "triggerEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/piston/MovingPistonBlock;newMovingBlockEntity(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;ZZ)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
	public void triggerEventSetBlockEntity(Args args, BlockState blockState, Level level, BlockPos blockPos, int i, int j) {
		BlockState baseBlockState = args.get(2);
		FluidState fluidState = level.getFluidState(args.get(0));
		boolean water = fluidState.getType() == Fluids.WATER;
		args.set(2, baseBlockState.setValue(BlockStateProperties.WATERLOGGED, water));
	}

	@Override
	public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		if (blockState.getValue(EXTENDED)) {
			return super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
		}

		Direction facing = blockState.getValue(BlockStateProperties.FACING);
		return SHAPES.get(facing);
	}

	private static VoxelShape getHorizontalCollisionShape(Direction to, VoxelShape shape) {
		Direction from = Direction.NORTH;
		VoxelShape[] buffer = new VoxelShape[]{ shape, Shapes.empty() };

		int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
		for (int i = 0; i < times; i++) {
			buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.create(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = Shapes.empty();
		}

		return buffer[0];
	}

	static {

		VoxelShape HORIZONTAL_SHAPE = Stream.of(
				Block.box(1, 9, 0, 15, 16, 1),
				Block.box(0, 0, 1, 16, 16, 16),
				Block.box(9, 0, 0, 16, 16, 1),
				Block.box(0, 0, 0, 7, 16, 1),
				Block.box(1, 0, 0, 15, 7, 1)
		).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
		VoxelShape DOWN_SHAPE = Stream.of(
				Block.box(1, 0, 0, 15, 1, 7),
				Block.box(0, 1, 0, 16, 16, 16),
				Block.box(9, 0, 0, 16, 1, 16),
				Block.box(0, 0, 0, 7, 1, 16),
				Block.box(1, 0, 9, 15, 1, 16)
		).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

		SHAPES = new HashMap<>();

		for (Direction direction : Direction.values()) {
			if (direction == Direction.DOWN) {
				SHAPES.put(direction, DOWN_SHAPE);
			} else if (direction == Direction.UP) {
				SHAPES.put(direction, Shapes.block());
			} else {
				SHAPES.put(direction, getHorizontalCollisionShape(direction, HORIZONTAL_SHAPE));
			}
		}


	}

}

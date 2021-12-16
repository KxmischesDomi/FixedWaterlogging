package de.kxmischesdomi.fixedwaterlogging.mixin.special;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 */
@Mixin(PistonMovingBlockEntity.class)
public abstract class PistonMovingBlockEntityMixin extends BlockEntity {

	public PistonMovingBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	@ModifyArgs(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	private static void finalTickWaterloggedValue(Args args, Level level, BlockPos blockPos, BlockState blockState, PistonMovingBlockEntity pistonMovingBlockEntity) {
		BlockState state = args.get(1);
		if (state.getBlock() instanceof PistonBaseBlock) {
			args.set(1, state.setValue(BlockStateProperties.WATERLOGGED, pistonMovingBlockEntity.getMovedState().getValue(BlockStateProperties.WATERLOGGED)));
		}
	}

}

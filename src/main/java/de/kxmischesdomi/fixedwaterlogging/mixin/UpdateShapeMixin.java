package de.kxmischesdomi.fixedwaterlogging.mixin;

import de.kxmischesdomi.fixedwaterlogging.FixedWaterloggingMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 * 
 * Overrides the {@link Block#updateShape(BlockState, Direction, BlockState, LevelAccessor, BlockPos, BlockPos)} for block classes that override this method so else it wouldn't be executed from {@link BlockMixin}.
 */
@Mixin(value = {
		LeavesBlock.class, BellBlock.class, FenceGateBlock.class, RepeaterBlock.class, VineBlock.class,
		BedBlock.class, FlowerPotBlock.class, PistonHeadBlock.class, BannerBlock.class, WallBannerBlock.class,
		CakeBlock.class, CandleCakeBlock.class, DoorBlock.class, CandleCakeBlock.class, TorchBlock.class,
		RedstoneWallTorchBlock.class, CarpetBlock.class, BasePressurePlateBlock.class
})
public class UpdateShapeMixin {

	@Inject(method = "updateShape", at = @At("HEAD"))
	public void updateShapeWaterlogged(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2, CallbackInfoReturnable<BlockState> cir) {
		if (FixedWaterloggingMod.supportsWaterlogged((Block) (Object) this)) {
			if (blockState.getValue(BlockStateProperties.WATERLOGGED)) {
				levelAccessor.getLiquidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
			}
		}
	}

}

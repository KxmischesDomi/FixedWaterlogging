package de.kxmischesdomi.fixedwaterlogging.mixin;

import de.kxmischesdomi.fixedwaterlogging.FixedWaterloggingMod;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 *
 * Overrides the {@link Block#getStateForPlacement(BlockPlaceContext)} for block classes that override this method so else it wouldn't be executed from {@link BlockMixin}.
 */
@Mixin(value = {
		Block.class, EndPortalFrameBlock.class, LeavesBlock.class, LecternBlock.class, HopperBlock.class,
		BellBlock.class, StonecutterBlock.class, FenceGateBlock.class, RepeaterBlock.class, VineBlock.class,
		BedBlock.class, SkullBlock.class, PistonBaseBlock.class, BannerBlock.class, WallBannerBlock.class,
		AnvilBlock.class, DoorBlock.class, FaceAttachedHorizontalDirectionalBlock.class, WallSkullBlock.class,
		RedstoneWallTorchBlock.class, EndRodBlock.class, DiodeBlock.class
})
public class GetStateForPlacementMixin {

	@Inject(method = "getStateForPlacement", at = @At(value = "RETURN"), cancellable = true)
	public void getStateForPlacementWaterlogged(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<BlockState> cir) {
		if (FixedWaterloggingMod.supportsWaterlogged((Block) ((Object) this))) {
			FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
			boolean water = fluidState.getType() == Fluids.WATER;
			BlockState returnValue = cir.getReturnValue();
			if (returnValue != null) {
				cir.setReturnValue(returnValue.hasProperty(BlockStateProperties.WATERLOGGED) ? returnValue.setValue(BlockStateProperties.WATERLOGGED, water) : returnValue);
			}
		}
	}

}

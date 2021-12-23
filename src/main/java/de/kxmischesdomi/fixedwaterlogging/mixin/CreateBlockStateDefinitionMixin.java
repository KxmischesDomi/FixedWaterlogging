package de.kxmischesdomi.fixedwaterlogging.mixin;

import de.kxmischesdomi.fixedwaterlogging.FixedWaterloggingMod;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 * 
 * Overrides the {@link Block#createBlockStateDefinition(StateDefinition.Builder)} for block classes that override this method so else it wouldn't be executed from {@link BlockMixin}.
 */
@Mixin(value = {
		EndPortalFrameBlock.class, ComparatorBlock.class, GrindstoneBlock.class, LeavesBlock.class,
		LecternBlock.class, HopperBlock.class, BellBlock.class, StonecutterBlock.class, FenceGateBlock.class,
		RepeaterBlock.class, VineBlock.class, BedBlock.class, ButtonBlock.class, PistonHeadBlock.class,
		PressurePlateBlock.class, SkullBlock.class, PistonBaseBlock.class, BannerBlock.class, WallBannerBlock.class,
		BrewingStandBlock.class, AnvilBlock.class, DaylightDetectorBlock.class, CakeBlock.class, DoorBlock.class,
		CandleCakeBlock.class, LeverBlock.class, WallSkullBlock.class, RedstoneTorchBlock.class, RedstoneTorchBlock.class,
		RedstoneWallTorchBlock.class, EndRodBlock.class, MovingPistonBlock.class, WeightedPressurePlateBlock.class
})
public class CreateBlockStateDefinitionMixin {

	@Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
	public void createBlockStateDefinitionWaterlogged(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
		if (FixedWaterloggingMod.supportsWaterlogged((Block) ((Object) this))) {
			try {
				builder.add(BlockStateProperties.WATERLOGGED);
			} catch (Exception exception) {
			}
		}
	}

}

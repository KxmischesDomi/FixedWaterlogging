package de.kxmischesdomi.fixedwaterlogging.mixin;

import de.kxmischesdomi.fixedwaterlogging.FixedWaterloggingMod;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 * 
 * Overrides the constructor for block classes that override this method so else it wouldn't be executed from {@link BlockMixin}.
 * Also has additional mixin classes for blocks that have custom constructor parameters.
 * TODO: SEARCH FOR OTHER SOLUTION FOR CUSTOM CONSTRUCTOR PARAMETERS
 */
@Mixin(value = {
		EndPortalFrameBlock.class, ComparatorBlock.class, GrindstoneBlock.class, LeavesBlock.class, LecternBlock.class,
		HopperBlock.class, BellBlock.class, StonecutterBlock.class, FenceGateBlock.class, RepeaterBlock.class,
		VineBlock.class, AzaleaBlock.class, EnchantmentTableBlock.class, PistonHeadBlock.class, BrewingStandBlock.class,
		AnvilBlock.class, DaylightDetectorBlock.class, CakeBlock.class, DoorBlock.class, LeverBlock.class, RedstoneTorchBlock.class,
		RedstoneWallTorchBlock.class, EndRodBlock.class, CarpetBlock.class, MovingPistonBlock.class
})
public abstract class InitMixin extends Block {

	public InitMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void initWaterlogged(Properties properties, CallbackInfo ci) {
		if (FixedWaterloggingMod.supportsWaterlogged(this)) {
			registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
		}
	}

	@Mixin(BedBlock.class)
	private abstract static class BedBlockInitMixin extends Block {

		public BedBlockInitMixin(Properties properties) {
			super(properties);
		}

		@Inject(method = "<init>", at = @At("TAIL"))
		public void initWaterlogged(DyeColor dyeColor, Properties properties, CallbackInfo ci) {
			if (FixedWaterloggingMod.supportsWaterlogged(this)) {
				registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
			}
		}

	}

	@Mixin(ButtonBlock.class)
	private abstract static class ButtonBlockInitMixin extends Block {

		public ButtonBlockInitMixin(Properties properties) {
			super(properties);
		}

		@Inject(method = "<init>", at = @At("TAIL"))
		public void initWaterlogged(boolean bl, Properties properties, CallbackInfo ci) {
			if (FixedWaterloggingMod.supportsWaterlogged(this)) {
				registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
			}
		}

	}

	@Mixin(PistonBaseBlock.class)
	private abstract static class PistonBaseBlockInitMixin extends Block {

		public PistonBaseBlockInitMixin(Properties properties) {
			super(properties);
		}

		@Inject(method = "<init>", at = @At("TAIL"))
		public void initWaterlogged(boolean bl, Properties properties, CallbackInfo ci) {
			if (FixedWaterloggingMod.supportsWaterlogged(this)) {
				registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
			}
		}

	}

	@Mixin(value = { WallBannerBlock.class, BannerBlock.class })
	private abstract static class BannerBlockInitMixin extends Block {

		public BannerBlockInitMixin(Properties properties) {
			super(properties);
		}

		@Inject(method = "<init>", at = @At("TAIL"))
		public void initWaterlogged(DyeColor dyeColor, Properties properties, CallbackInfo ci) {
			if (FixedWaterloggingMod.supportsWaterlogged(this)) {
				registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
			}
		}

	}

	@Mixin(CandleCakeBlock.class)
	private abstract static class CandleCakeBlockInitMixin extends Block {

		public CandleCakeBlockInitMixin(Properties properties) {
			super(properties);
		}

		@Inject(method = "<init>", at = @At("TAIL"))
		public void initWaterlogged(Block block, Properties properties, CallbackInfo ci) {
			if (FixedWaterloggingMod.supportsWaterlogged(this)) {
				registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
			}
		}

	}

	@Mixin(AbstractSkullBlock.class)
	private abstract static class AbstractSkullBlockInitMixin extends Block {

		public AbstractSkullBlockInitMixin(Properties properties) {
			super(properties);
		}

		@Inject(method = "<init>", at = @At("TAIL"))
		public void initWaterlogged(SkullBlock.Type type, Properties properties, CallbackInfo ci) {
			if (FixedWaterloggingMod.supportsWaterlogged(this)) {
				registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
			}
		}

	}

	@Mixin(WoolCarpetBlock.class)
	private abstract static class WoolCarpetBlockInitMixin extends Block {

		public WoolCarpetBlockInitMixin(Properties properties) {
			super(properties);
		}

		@Inject(method = "<init>", at = @At("TAIL"))
		public void initWaterlogged(DyeColor dyeColor, Properties properties, CallbackInfo ci) {
			if (FixedWaterloggingMod.supportsWaterlogged(this)) {
				registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
			}
		}

	}

	@Mixin(WeightedPressurePlateBlock.class)
	private abstract static class WeightedPressurePlateBlockInitMixin extends Block {

		public WeightedPressurePlateBlockInitMixin(Properties properties) {
			super(properties);
		}

		@Inject(method = "<init>", at = @At("TAIL"))
		public void initWaterlogged(int i, Properties properties, CallbackInfo ci) {
			if (FixedWaterloggingMod.supportsWaterlogged(this)) {
				registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
			}
		}

	}

}

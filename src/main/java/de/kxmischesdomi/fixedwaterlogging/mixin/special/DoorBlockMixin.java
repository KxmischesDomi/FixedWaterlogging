package de.kxmischesdomi.fixedwaterlogging.mixin.special;

import de.kxmischesdomi.fixedwaterlogging.FixedWaterloggingMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 *
 * Contains additional needed logic for doors.
 */
@Mixin(DoorBlock.class)
public abstract class DoorBlockMixin extends Block {

	@Shadow @Final public static EnumProperty<DoubleBlockHalf> HALF;

	public DoorBlockMixin(Properties properties) {
		super(properties);
	}

	/**
	 * Makes the upper door not copy the waterlogged property of the lower door part
	 * TODO: CHANGE TO NOT REPLACE THE {@link Level#setBlock(BlockPos, BlockState, int)} CALL
	 */
	@Inject(method = "setPlacedBy", at = @At("HEAD"), cancellable = true)
	public void updateShapeWaterlogged(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack, CallbackInfo ci) {
		if (FixedWaterloggingMod.supportsWaterlogged(this)) {
			FluidState fluidState = level.getFluidState(blockPos.above());
			boolean water = fluidState.getType() == Fluids.WATER;
			level.setBlock(blockPos.above(), blockState.setValue(HALF, DoubleBlockHalf.UPPER).setValue(BlockStateProperties.WATERLOGGED, water), 3);
			ci.cancel();
		}
	}

}

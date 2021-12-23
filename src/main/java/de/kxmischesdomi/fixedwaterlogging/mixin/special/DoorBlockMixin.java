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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 *
 * Contains additional needed logic for doors.
 */
@Mixin(DoorBlock.class)
public abstract class DoorBlockMixin extends Block {

	public DoorBlockMixin(Properties properties) {
		super(properties);
	}

	/**
	 * Makes the upper door not copy the waterlogged property of the lower door part
	 */
	@ModifyArgs(method = "setPlacedBy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	public void setPlacedByWaterlogged(Args args, Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
		if (FixedWaterloggingMod.supportsWaterlogged(this)) {
			FluidState fluidState = level.getFluidState(blockPos.above());
			boolean water = fluidState.getType() == Fluids.WATER;
			args.set(1, args.<BlockState>get(1).setValue(BlockStateProperties.WATERLOGGED, water));
		}
	}

}

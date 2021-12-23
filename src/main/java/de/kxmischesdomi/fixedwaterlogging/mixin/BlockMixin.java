package de.kxmischesdomi.fixedwaterlogging.mixin;

import de.kxmischesdomi.fixedwaterlogging.FixedWaterloggingMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 *
 * Overrides the needed methods for all blocks and adds the waterlogged logic if they're an waterloggable block.
 */
@Mixin(value = { Block.class }, priority = 10000)
public abstract class BlockMixin extends BlockBehaviour {

	@Shadow protected abstract void registerDefaultState(BlockState blockState);

	@Shadow public abstract BlockState defaultBlockState();
	
	@Shadow private BlockState defaultBlockState;

	public BlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void initWaterlogged(BlockBehaviour.Properties properties, CallbackInfo ci) {
		if (FixedWaterloggingMod.supportsWaterlogged(getInstance())) {
			if (defaultBlockState().hasProperty(BlockStateProperties.WATERLOGGED)) {
				registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
			}
		}
	}

	@Inject(method = "registerDefaultState", at = @At(value = "TAIL"))
	public void registerDefaultStateWaterlogged(BlockState blockState, CallbackInfo ci) {
		if (FixedWaterloggingMod.supportsWaterlogged(getInstance())) {
			if (defaultBlockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
				defaultBlockState = defaultBlockState.setValue(BlockStateProperties.WATERLOGGED, false);
			}
		}
	}

	@Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
	public void createBlockStateDefinitionWaterlogged(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
		if (FixedWaterloggingMod.supportsWaterlogged(getInstance())) {
			builder.add(BlockStateProperties.WATERLOGGED);
		}
	}

	@Inject(method = "getStateForPlacement", at = @At(value = "RETURN"), cancellable = true)
	public void getStateForPlacementWaterlogged(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<BlockState> cir) {
		if (FixedWaterloggingMod.supportsWaterlogged(getInstance())) {
			BlockState returnValue = cir.getReturnValue();
			if (returnValue != null) {
				FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
				boolean water = fluidState.getType() == Fluids.WATER;
				cir.setReturnValue(returnValue.hasProperty(BlockStateProperties.WATERLOGGED) ? returnValue.setValue(BlockStateProperties.WATERLOGGED, water) : returnValue);
			}
		}
	}

	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		if (FixedWaterloggingMod.supportsWaterlogged(getInstance())) {
			if (blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED)) {
				levelAccessor.getLiquidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
			}
		}
		return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
	}

	@Override
	public FluidState getFluidState(BlockState blockState) {
		if (FixedWaterloggingMod.supportsWaterlogged(getInstance())) {
			if (blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED)) {
				return Fluids.WATER.getSource(false);
			}
		}
		return super.getFluidState(blockState);
	}

	private Block getInstance() {
		return (Block) ((Object) this);
	}

}

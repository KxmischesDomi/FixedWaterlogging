package de.kxmischesdomi.fixedwaterlogging;

import de.kxmischesdomi.fixedwaterlogging.common.FixedWaterlogging;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import virtuoel.statement.api.StateRefresher;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 */
public class FixedWaterloggingMod implements ModInitializer {

	@Override
	public void onInitialize() {
		// Didn't work properly on its own
//		RegistryEntryAddedCallback.event(Registry.BLOCK).register(((rawId, id, block) -> {
//			if(block instanceof FixedWaterlogging) {
//				injectBlockProperty(block, BlockStateProperties.WATERLOGGED, false);
//				StateRefresher.INSTANCE.reorderBlockStates();
//			}
//		}));
//		StateRefresher.INSTANCE.reorderBlockStates();
	}

	/**
	 * @return if the block should be waterloggable.
	 * Checks if the block class is an instance of {@link FixedWaterlogging}.
	 */
	public static boolean supportsWaterlogged(Block block) {
		return FixedWaterlogging.class.isAssignableFrom(block.getClass());
	}

	public static <T extends Comparable<T>> void injectBlockProperty(Class<? extends Block> cls, Property<T> property, T defaultValue) {
		for(Block block : Registry.BLOCK) {
			if(cls.isAssignableFrom(block.getClass())) {
				injectBlockProperty(block, property, defaultValue);
			}
		}
	}

	public static <T extends Comparable<T>> void injectBlockProperty(Block block, Property<T> property, T defaultValue) {
		StateRefresher.INSTANCE.addBlockProperty(block, property, defaultValue);
	}

}

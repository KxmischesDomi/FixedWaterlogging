package de.kxmischesdomi.fixedwaterlogging;

import de.kxmischesdomi.fixedwaterlogging.common.FixedWaterlogging;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.level.block.Block;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 */
public class FixedWaterloggingMod implements ModInitializer {

	@Override
	public void onInitialize() {
		
	}

	/**
	 * @return if the block should be waterloggable.
	 * Checks if the block class is an instance of {@link FixedWaterlogging}.
	 */
	public static boolean supportsWaterlogged(Block block) {
		return FixedWaterlogging.class.isAssignableFrom(block.getClass());
	}

}

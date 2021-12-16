package de.kxmischesdomi.fixedwaterlogging;

import de.kxmischesdomi.fixedwaterlogging.common.FixedWaterlogging;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0.0
 */
public class FixedWaterloggingMod implements ModInitializer {

	public static final String MOD_ID = "modid";
	public static final Logger LOGGER = LogManager.getLogger("modid");

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

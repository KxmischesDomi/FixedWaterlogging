package de.kxmischesdomi.fixedwaterlogging.mixin;

import de.kxmischesdomi.fixedwaterlogging.common.FixedWaterlogging;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0.0
 *
 * Makes all target block classes an instance of {@link FixedWaterlogging} so they can be determined as such.
 */
@Mixin(value = {
		EndPortalFrameBlock.class, ComparatorBlock.class, GrindstoneBlock.class, LeavesBlock.class, LecternBlock.class,
		HopperBlock.class, BellBlock.class, StonecutterBlock.class, FenceGateBlock.class, RepeaterBlock.class,
		VineBlock.class, AzaleaBlock.class, EnchantmentTableBlock.class, BedBlock.class, ButtonBlock.class,
		FlowerPotBlock.class, PistonHeadBlock.class, BasePressurePlateBlock.class, SkullBlock.class, PistonBaseBlock.class,
		AbstractBannerBlock.class, BrewingStandBlock.class, AnvilBlock.class, DaylightDetectorBlock.class, CakeBlock.class,
		DoorBlock.class, CandleCakeBlock.class, LeverBlock.class, AbstractSkullBlock.class, RedstoneTorchBlock.class,
		EndRodBlock.class, CarpetBlock.class, MovingPistonBlock.class
})
public abstract class WaterloggingBlockMixin implements FixedWaterlogging {

}

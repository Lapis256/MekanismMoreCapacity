package io.github.lapis256.mekanism_more_capacity.mixin;

import mekanism.common.lib.multiblock.MultiblockData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(value = MultiblockData.class, remap = false)
public abstract class MixinMultiblockData {
    @Shadow
    public abstract int getVolume();

    @Shadow
    public abstract int height();
}

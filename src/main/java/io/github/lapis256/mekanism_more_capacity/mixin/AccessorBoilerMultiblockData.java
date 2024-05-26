package io.github.lapis256.mekanism_more_capacity.mixin;

import mekanism.common.content.boiler.BoilerMultiblockData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BoilerMultiblockData.class, remap = false)
public interface AccessorBoilerMultiblockData {
    @Accessor()
    void setWaterTankCapacity(int capacity);
}

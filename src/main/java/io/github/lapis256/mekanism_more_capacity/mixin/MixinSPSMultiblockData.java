package io.github.lapis256.mekanism_more_capacity.mixin;

import io.github.lapis256.mekanism_more_capacity.Config;
import io.github.lapis256.mekanism_more_capacity.MekanismMoreCapacity;
import mekanism.common.content.sps.SPSMultiblockData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(value = SPSMultiblockData.class, remap = false)
class MixinSPSMultiblockData {
    @ModifyConstant(method = "getMaxInputGas", constant = @Constant(longValue = 2L))
    private long modifyInputTankCapacityMultiplier(long c) {
        return Config.INSTANCE.spsInputTankCapacityMultiplier.get();
    }

    @ModifyConstant(method = "lambda$new$3", constant = @Constant(longValue = 1000L))
    private static long modifyOutputTankCapacity(long c) {
        return Config.INSTANCE.spsOutputTankCapacity.get();
    }
}

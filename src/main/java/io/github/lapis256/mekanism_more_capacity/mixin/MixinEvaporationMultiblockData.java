package io.github.lapis256.mekanism_more_capacity.mixin;

import io.github.lapis256.mekanism_more_capacity.Config;
import mekanism.common.content.evaporation.EvaporationMultiblockData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = EvaporationMultiblockData.class, remap = false)
public abstract class MixinEvaporationMultiblockData extends MixinMultiblockData {
    @Inject(method = "getMaxFluid", at = @At("RETURN"), cancellable = true)
    private void modifyInputTank(@NotNull CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Integer.MAX_VALUE / 18 * height());
    }

    @Inject(method = "lambda$new$0", at = @At("RETURN"), cancellable = true)
    private static void modifyOutputTank(@NotNull CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Config.INSTANCE.evaporationOutputTankCapacity.get());
    }
}

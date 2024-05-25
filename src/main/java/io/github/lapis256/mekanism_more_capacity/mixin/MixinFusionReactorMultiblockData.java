package io.github.lapis256.mekanism_more_capacity.mixin;

import mekanism.api.math.FloatingLong;
import mekanism.generators.common.content.fusion.FusionReactorMultiblockData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = FusionReactorMultiblockData.class, remap = false)
public abstract class MixinFusionReactorMultiblockData {
    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lmekanism/generators/common/content/fusion/FusionReactorMultiblockData;MAX_ENERGY:Lmekanism/api/math/FloatingLong;"))
    private FloatingLong injectMaxEnergyCapacity() {
        return FloatingLong.MAX_VALUE;
    }

    @Inject(method = {"lambda$new$0", "lambda$new$1", "lambda$new$2"}, at = @At("RETURN"), cancellable = true)
    private static void injectGasCapacity(CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(1_000_000L);
    }
}

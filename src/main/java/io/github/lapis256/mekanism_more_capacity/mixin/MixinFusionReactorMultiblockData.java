package io.github.lapis256.mekanism_more_capacity.mixin;

import io.github.lapis256.mekanism_more_capacity.Config;
import mekanism.api.math.FloatingLong;
import mekanism.generators.common.content.fusion.FusionReactorMultiblockData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = FusionReactorMultiblockData.class, remap = false)
public abstract class MixinFusionReactorMultiblockData {
    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lmekanism/generators/common/content/fusion/FusionReactorMultiblockData;MAX_ENERGY:Lmekanism/api/math/FloatingLong;"))
    private @NotNull FloatingLong injectMaxEnergyCapacity() {
        return Config.INSTANCE.fusionEnergyCapacity.get();
    }

    @Inject(method = "lambda$new$0", at = @At("RETURN"), cancellable = true)
    private static void injectDeuteriumTankCapacity(@NotNull CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(Config.INSTANCE.fusionDeuteriumTankCapacity.get().longValue());
    }

    @Inject(method = "lambda$new$1", at = @At("RETURN"), cancellable = true)
    private static void injectTritiumTankCapacity(@NotNull CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(Config.INSTANCE.fusionTritiumTankCapacity.get().longValue());
    }

    @Inject(method = "lambda$new$2", at = @At("RETURN"), cancellable = true)
    private static void injectFuelTankCapacity(@NotNull CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(Config.INSTANCE.fusionFuelTankCapacity.get().longValue());
    }
}

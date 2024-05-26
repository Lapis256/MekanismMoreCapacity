package io.github.lapis256.mekanism_more_capacity.mixin;

import io.github.lapis256.mekanism_more_capacity.Config;
import mekanism.generators.common.content.fission.FissionReactorMultiblockData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = FissionReactorMultiblockData.class, remap = false)
public abstract class MixinFissionReactorMultiblockData extends MixinMultiblockData {
    @Inject(method = "lambda$new$0", at = @At("RETURN"), cancellable = true)
    private void modifyFluidCoolantTank(@NotNull CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(getVolume() * Config.INSTANCE.fissionFluidCoolantTankCapacity.get());
    }

    @Inject(method = "lambda$new$4", at = @At("RETURN"), cancellable = true)
    private void modifyGasCoolantTank(@NotNull CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(getVolume() * Config.INSTANCE.fissionGasCoolantTankCapacity.get());
    }

    @Inject(method = "lambda$new$12", at = @At("RETURN"), cancellable = true)
    private void modifyHeatedCoolantTank(@NotNull CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(getVolume() * Config.INSTANCE.fissionHeatedCoolantTankCapacity.get());
    }
}

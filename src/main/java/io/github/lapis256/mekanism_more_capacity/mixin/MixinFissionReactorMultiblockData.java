package io.github.lapis256.mekanism_more_capacity.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(value = mekanism.generators.common.content.fission.FissionReactorMultiblockData.class, remap = false)
public abstract class MixinFissionReactorMultiblockData extends MixinMultiblockData {
    @Inject(method = "lambda$new$0", at = @At("RETURN"), cancellable = true)
    private void injectFluidCoolantTank(org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(getVolume() * 368_224);
    }

    @Inject(method = "lambda$new$4", at = @At("RETURN"), cancellable = true)
    private void injectGasCoolantTank(org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(getVolume() * 368_224L);
    }

    @Inject(method = "lambda$new$12", at = @At("RETURN"), cancellable = true)
    private void injectHeatedCoolantTank(org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(getVolume() * 10_000_000L);
    }
}

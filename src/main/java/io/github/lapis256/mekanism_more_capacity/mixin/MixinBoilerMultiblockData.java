package io.github.lapis256.mekanism_more_capacity.mixin;

import io.github.lapis256.mekanism_more_capacity.Config;
import mekanism.common.content.boiler.BoilerMultiblockData;
import mekanism.common.util.HeatUtils;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;


@Mixin(value = BoilerMultiblockData.class, remap = false)
public class MixinBoilerMultiblockData {
    @Shadow
    private int waterVolume;

    @Redirect(method = "setWaterVolume", at = @At(value = "FIELD", target = "Lmekanism/common/content/boiler/BoilerMultiblockData;waterTankCapacity:I", opcode = Opcodes.PUTFIELD))
    private void modifyWaterTankCapacityMultiplier(BoilerMultiblockData boiler, int v) {
        ((AccessorBoilerMultiblockData) boiler).setWaterTankCapacity((int) Math.min(Integer.MAX_VALUE, waterVolume * Config.INSTANCE.boilerWaterTankCapacityMultiplier.get()));
    }

    @ModifyConstant(method = "setSteamVolume", constant = @Constant(longValue = 160_000L))
    private long modifySteamTankCapacityMultiplier(long c) {
        return Config.INSTANCE.boilerSteamTankCapacityMultiplier.get();
    }

    @ModifyConstant(method = "setSteamVolume", constant = @Constant(longValue = 256_000L))
    private long modifyCooledCoolantCapacityMultiplier(long c) {
        return Config.INSTANCE.boilerCooledCoolantCapacityMultiplier.get();
    }

    @ModifyConstant(method = "setWaterVolume", constant = @Constant(longValue = 256_000L))
    private long modifySuperheatedCoolantCapacityMultiplier(long c) {
        return Config.INSTANCE.boilerSuperheatedCoolantCapacityMultiplier.get();
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lmekanism/common/util/HeatUtils;getSteamEnergyEfficiency()D"))
    private double modifySteamEnergyEfficiency() {
        return HeatUtils.getSteamEnergyEfficiency() * Config.INSTANCE.boilerSteamEnergyEfficiencyMultiplier.get();
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lmekanism/common/util/HeatUtils;getWaterThermalEnthalpy()D"))
    private double modifyWaterEnergyEfficiency() {
        return HeatUtils.getWaterThermalEnthalpy() * Config.INSTANCE.boilerWaterEnergyEfficiencyMultiplier.get();
    }
}

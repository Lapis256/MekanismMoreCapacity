package io.github.lapis256.mekanism_more_capacity;

import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedDoubleValue;
import mekanism.common.config.value.CachedFloatingLongValue;
import mekanism.common.config.value.CachedIntValue;
import mekanism.common.config.value.CachedLongValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig.Type;


public class Config extends BaseMekanismConfig {
    public static final Config INSTANCE = new Config();

    private final ForgeConfigSpec configSpec;

    public final CachedLongValue boilerWaterTankCapacityMultiplier;
    public final CachedLongValue boilerSteamTankCapacityMultiplier;
    public final CachedLongValue boilerCooledCoolantCapacityMultiplier;
    public final CachedLongValue boilerSuperheatedCoolantCapacityMultiplier;
    public final CachedDoubleValue boilerSteamEnergyEfficiencyMultiplier;
    public final CachedDoubleValue boilerWaterEnergyEfficiencyMultiplier;


    public final CachedIntValue evaporationOutputTankCapacity;

    public final CachedIntValue fissionFluidCoolantTankCapacity;
    public final CachedLongValue fissionGasCoolantTankCapacity;
    public final CachedLongValue fissionHeatedCoolantTankCapacity;

    public final CachedFloatingLongValue fusionEnergyCapacity;
    public final CachedFloatingLongValue fusionDeuteriumTankCapacity;
    public final CachedFloatingLongValue fusionTritiumTankCapacity;
    public final CachedFloatingLongValue fusionFuelTankCapacity;

    public final CachedLongValue spsInputTankCapacityMultiplier;
    public final CachedLongValue spsOutputTankCapacity;

    Config() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Mekanism More Capacity Config. This config is synced from server to client.").push("more_capacity");

        builder.push("boiler");
        boilerWaterTankCapacityMultiplier = CachedLongValue.wrap(this, builder.comment("Water tank capacity multiplier. Default: 1,600,000, Vanilla: 16,000").defineInRange("waterTankCapacityMultiplier", 1_600_000L, 1L, Long.MAX_VALUE));
        boilerSteamTankCapacityMultiplier = CachedLongValue.wrap(this, builder.comment("Steam tank capacity multiplier. Default: 16,000,000, Vanilla: 160,000").defineInRange("steamTankCapacityMultiplier", 16_000_000L, 1L, Long.MAX_VALUE));
        boilerCooledCoolantCapacityMultiplier = CachedLongValue.wrap(this, builder.comment("Cooled coolant tank capacity multiplier. Default: 25,600,000, Vanilla: 256,000").defineInRange("cooledCoolantCapacityMultiplier", 25_600_000L, 1L, Long.MAX_VALUE));
        boilerSuperheatedCoolantCapacityMultiplier = CachedLongValue.wrap(this, builder.comment("Superheated coolant tank capacity multiplier. Default: 25,600,000, Vanilla: 256,000").defineInRange("superheatedCoolantCapacityMultiplier", 25_600_000L, 1L, Long.MAX_VALUE));
        boilerSteamEnergyEfficiencyMultiplier = CachedDoubleValue.wrap(this, builder.comment("Steam energy efficiency multiplier. Default: 100, Vanilla: 1").define("steamEnergyEfficiency", 100D));
        boilerWaterEnergyEfficiencyMultiplier = CachedDoubleValue.wrap(this, builder.comment("Water energy efficiency multiplier. Default: 1, Vanilla: 1").define("waterEnergyEfficiency", 1D));
        builder.pop();

        builder.push("evaporation");
        evaporationOutputTankCapacity = CachedIntValue.wrap(this, builder.comment("Output tank capacity (mB). Default: 2,147,483,647, Vanilla: 10,000").define("outputTankCapacity", Integer.MAX_VALUE));
        builder.pop();

        builder.push("fission");
        fissionFluidCoolantTankCapacity = CachedIntValue.wrap(this, builder.comment("Fluid coolant tank capacity (mB). Default: 368,224, Vanilla: 100,000").define("fluidCoolantTankCapacity", 368_224));
        fissionGasCoolantTankCapacity = CachedLongValue.wrap(this, builder.comment("Gas coolant tank capacity (mB). Default: 368,224, Vanilla: 100,000").defineInRange("gasCoolantTankCapacity", 368_224L, 1L, Long.MAX_VALUE));
        fissionHeatedCoolantTankCapacity = CachedLongValue.wrap(this, builder.comment("Heated coolant tank capacity (mB). Default: 10,000,000, Vanilla: 1,000,000").defineInRange("heatedCoolantTankCapacity", 10_000_000L, 1L, Long.MAX_VALUE));
        builder.pop();

        builder.push("fusion");
        fusionEnergyCapacity = CachedFloatingLongValue.define(this, builder, "Energy capacity (Joules). Default: 18,446,744,073,709,551,615.9999, Vanilla: 1,000,000,000", "energyCapacity", FloatingLong.MAX_VALUE);
        fusionDeuteriumTankCapacity = CachedFloatingLongValue.define(this, builder, "Deuterium tank capacity (mB). Default: 1,000,000, Vanilla: 1,000", "deuteriumTankCapacity", FloatingLong.createConst(1_000_000));
        fusionTritiumTankCapacity = CachedFloatingLongValue.define(this, builder, "Tritium tank capacity (mB). Default: 1,000,000, Vanilla: 1,000", "tritiumTankCapacity", FloatingLong.createConst(1_000_000));
        fusionFuelTankCapacity = CachedFloatingLongValue.define(this, builder, "Fuel tank capacity (mB). Default: 1,000,000, Vanilla: 1,000", "fuelTankCapacity", FloatingLong.createConst(1_000_000));
        builder.pop();

        builder.push("sps");
        spsInputTankCapacityMultiplier = CachedLongValue.wrap(this, builder.comment("Input tank capacity (mB). Default: 2,000, Vanilla: 2").defineInRange("inputTankCapacityMultiplier", 2_000L, 1L, Long.MAX_VALUE));
        spsOutputTankCapacity = CachedLongValue.wrap(this, builder.comment("Output tank capacity (mB). Default: 1,000,000, Vanilla: 1,000").defineInRange("outputTankCapacity", 1_000_000L, 1L, Long.MAX_VALUE));
        builder.pop();

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "more-capacity";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public Type getConfigType() {
        return Type.SERVER;
    }

    @Override
    public boolean addToContainer() {
        return false;
    }
}

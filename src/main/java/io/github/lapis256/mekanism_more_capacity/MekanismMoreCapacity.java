package io.github.lapis256.mekanism_more_capacity;

import mekanism.common.config.MekanismConfigHelper;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mod(MekanismMoreCapacity.MOD_ID)
public class MekanismMoreCapacity {
    public static final String MOD_ID = "mekanism_more_capacity";
    public static final String MOD_NAME = "Mekanism More Capacity";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public MekanismMoreCapacity() {
        MekanismConfigHelper.registerConfig(ModLoadingContext.get().getActiveContainer(), Config.INSTANCE);
    }
}

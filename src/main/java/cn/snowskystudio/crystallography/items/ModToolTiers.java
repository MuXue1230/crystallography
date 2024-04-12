package cn.snowskystudio.crystallography.items;

import cn.snowskystudio.crystallography.Crystallography;
import cn.snowskystudio.crystallography.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier STEEL = TierSortingRegistry.registerTier(
            new ForgeTier(5, 2500, 10.0f, 5.0f, 20,
                    ModTags.Blocks.NEEDS_STEEL_TOOL, () -> Ingredient.of(ModItems.STEEL_INGOT.get())),
            new ResourceLocation(Crystallography.MODID, "steel"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier SUPER_STEEL = TierSortingRegistry.registerTier(
            new ForgeTier(6, 5000, 20.0f, 10.0f, 50,
                    ModTags.Blocks.NEEDS_SUPER_STEEL_TOOL, () -> Ingredient.of(ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())),
            new ResourceLocation(Crystallography.MODID, "super_steel"), List.of(STEEL), List.of());

}

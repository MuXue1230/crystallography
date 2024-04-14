package cn.snowskystudio.crystallography.datagen;

import cn.snowskystudio.crystallography.Crystallography;
import cn.snowskystudio.crystallography.blocks.ModBlocks;
import cn.snowskystudio.crystallography.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> p_251297_) {
        oreBlasting(p_251297_, List.of(Items.IRON_INGOT), RecipeCategory.MISC,
                ModItems.STEEL_INGOT.get(), 0.5f, 50, "unfired_steel_ingot");
        oreSmelting(p_251297_, List.of(Items.IRON_INGOT), RecipeCategory.MISC,
                ModItems.STEEL_INGOT.get(), 0.5f, 100, "unfired_steel_ingot");

        oreBlasting(p_251297_, List.of(ModItems.FIRST_EXTRUDED_UNFIRED_STEEL_INGOT.get()), RecipeCategory.MISC,
                ModItems.FIRST_EXTRUDED_STEEL_INGOT.get(), 1.0f, 100, "unfired_steel_ingot");
        oreSmelting(p_251297_, List.of(ModItems.FIRST_EXTRUDED_UNFIRED_STEEL_INGOT.get()), RecipeCategory.MISC,
                ModItems.FIRST_EXTRUDED_STEEL_INGOT.get(), 1.0f, 200, "unfired_steel_ingot");
        oreBlasting(p_251297_, List.of(ModItems.SECONDARY_EXTRUDED_UNFIRED_STEEL_INGOT.get()), RecipeCategory.MISC,
                ModItems.SECONDARY_EXTRUDED_STEEL_INGOT.get(), 2.0f, 200, "unfired_steel_ingot");
        oreSmelting(p_251297_, List.of(ModItems.SECONDARY_EXTRUDED_UNFIRED_STEEL_INGOT.get()), RecipeCategory.MISC,
                ModItems.SECONDARY_EXTRUDED_STEEL_INGOT.get(), 2.0f, 400, "unfired_steel_ingot");
        oreBlasting(p_251297_, List.of(ModItems.TRIPLE_EXTRUDED_UNFIRED_STEEL_INGOT.get()), RecipeCategory.MISC,
                ModItems.TRIPLE_EXTRUDED_STEEL_INGOT.get(), 4.0f, 400, "unfired_steel_ingot");
        oreSmelting(p_251297_, List.of(ModItems.TRIPLE_EXTRUDED_UNFIRED_STEEL_INGOT.get()), RecipeCategory.MISC,
                ModItems.TRIPLE_EXTRUDED_STEEL_INGOT.get(), 4.0f, 800, "unfired_steel_ingot");
        oreBlasting(p_251297_, List.of(ModItems.QUADRUPLE_EXTRUDED_UNFIRED_STEEL_INGOT.get()), RecipeCategory.MISC,
                ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get(), 8.0f, 800, "unfired_steel_ingot");
        oreSmelting(p_251297_, List.of(ModItems.QUADRUPLE_EXTRUDED_UNFIRED_STEEL_INGOT.get()), RecipeCategory.MISC,
                ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get(), 8.0f, 1600, "unfired_steel_ingot");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRST_EXTRUDED_UNFIRED_STEEL_INGOT.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', ModItems.STEEL_INGOT.get())
                .define('B', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.STEEL_INGOT.get()), has(ModItems.STEEL_INGOT.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SECONDARY_EXTRUDED_UNFIRED_STEEL_INGOT.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', ModItems.FIRST_EXTRUDED_STEEL_INGOT.get())
                .define('B', ModItems.STEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.FIRST_EXTRUDED_STEEL_INGOT.get()), has(ModItems.FIRST_EXTRUDED_STEEL_INGOT.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TRIPLE_EXTRUDED_UNFIRED_STEEL_INGOT.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', ModItems.SECONDARY_EXTRUDED_STEEL_INGOT.get())
                .define('B', ModItems.FIRST_EXTRUDED_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.SECONDARY_EXTRUDED_STEEL_INGOT.get()), has(ModItems.SECONDARY_EXTRUDED_STEEL_INGOT.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CRYSTALLIZATION_PROCESSING_CORE.get())
                .pattern("CBC")
                .pattern("BAB")
                .pattern("CBC")
                .define('A', Items.NETHER_STAR)
                .define('B', Items.REDSTONE)
                .define('C', Items.DIAMOND)
                .unlockedBy(getHasName(Items.NETHER_STAR), has(Items.NETHER_STAR))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PRESS_MACHINE.get())
                .pattern("ACA")
                .pattern("AAA")
                .pattern("BBB")
                .define('A', ModBlocks.STEEL_BLOCK.get())
                .define('B', Items.IRON_INGOT)
                .define('C', ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get()), has(ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get()))
                .save(p_251297_);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> p_250654_, List<ItemLike> p_250172_, RecipeCategory p_250588_, ItemLike p_251868_, float p_250789_, int p_252144_, String p_251687_) {
        oreCooking(p_250654_, RecipeSerializer.SMELTING_RECIPE, p_250172_, p_250588_, p_251868_, p_250789_, p_252144_, p_251687_, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> p_248775_, List<ItemLike> p_251504_, RecipeCategory p_248846_, ItemLike p_249735_, float p_248783_, int p_250303_, String p_251984_) {
        oreCooking(p_248775_, RecipeSerializer.BLASTING_RECIPE, p_251504_, p_248846_, p_249735_, p_248783_, p_250303_, p_251984_, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> p_250791_, RecipeSerializer<? extends AbstractCookingRecipe> p_251817_, List<ItemLike> p_249619_, RecipeCategory p_251154_, ItemLike p_250066_, float p_251871_, int p_251316_, String p_251450_, String p_249236_) {
        for(ItemLike itemlike : p_249619_) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), p_251154_, p_250066_, p_251871_, p_251316_, p_251817_).group(p_251450_).unlockedBy(getHasName(itemlike), has(itemlike)).save(p_250791_, Crystallography.MODID + ":" +getItemName(p_250066_) + p_249236_ + "_" + getItemName(itemlike));
        }
    }
}

package cn.snowskystudio.crystallography.datagen;

import cn.snowskystudio.crystallography.Crystallography;
import cn.snowskystudio.crystallography.api.recipe.CrystallizerRecipeBuilder;
import cn.snowskystudio.crystallography.api.recipe.PressMachineRecipeBuilder;
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CRYSTALLIZER.get())
                .pattern("ADA")
                .pattern("BEB")
                .pattern("CCC")
                .define('A', ModItems.SECONDARY_EXTRUDED_STEEL_INGOT.get())
                .define('B', ModItems.TRIPLE_EXTRUDED_STEEL_INGOT.get())
                .define('C', ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .define('D', ModItems.FIRST_EXTRUDED_STEEL_INGOT.get())
                .define('E', ModItems.CRYSTALLIZATION_PROCESSING_CORE.get())
                .unlockedBy(getHasName(ModItems.CRYSTALLIZATION_PROCESSING_CORE.get()), has(ModItems.CRYSTALLIZATION_PROCESSING_CORE.get()))
                .save(p_251297_);

        PressMachineRecipeBuilder.builder(ModItems.FIRST_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(1, ModItems.STEEL_INGOT.get())
                .setIngredient(2, ModItems.STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.PRESS_MACHINE.get()), has(ModBlocks.PRESS_MACHINE.get()))
                .save(p_251297_);
        PressMachineRecipeBuilder.builder(ModItems.SECONDARY_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(1, ModItems.FIRST_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(2, ModItems.FIRST_EXTRUDED_STEEL_INGOT.get())
                .save(p_251297_);
        PressMachineRecipeBuilder.builder(ModItems.TRIPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(1, ModItems.SECONDARY_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(2, ModItems.SECONDARY_EXTRUDED_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.PRESS_MACHINE.get()), has(ModBlocks.PRESS_MACHINE.get()))
                .save(p_251297_);
        PressMachineRecipeBuilder.builder(ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(1, ModItems.TRIPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(2, ModItems.TRIPLE_EXTRUDED_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.PRESS_MACHINE.get()), has(ModBlocks.PRESS_MACHINE.get()))
                .save(p_251297_);

        CrystallizerRecipeBuilder.builder(ModItems.STEEL_SWORD.get())
                .setRequireTemperature(2400)
                .setIngredient(4, Items.NETHERITE_SWORD)
                .setIngredient(1, ModItems.STEEL_INGOT.get())
                .setIngredient(3, ModItems.STEEL_INGOT.get())
                .setIngredient(5, ModItems.STEEL_INGOT.get())
                .setIngredient(7, ModItems.STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.CRYSTALLIZER.get()), has(ModBlocks.CRYSTALLIZER.get()))
                .save(p_251297_);
        CrystallizerRecipeBuilder.builder(ModItems.STEEL_PICKAXE.get())
                .setRequireTemperature(2400)
                .setIngredient(4, Items.NETHERITE_PICKAXE)
                .setIngredient(1, ModItems.STEEL_INGOT.get())
                .setIngredient(3, ModItems.STEEL_INGOT.get())
                .setIngredient(5, ModItems.STEEL_INGOT.get())
                .setIngredient(7, ModItems.STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.CRYSTALLIZER.get()), has(ModBlocks.CRYSTALLIZER.get()))
                .save(p_251297_);
        CrystallizerRecipeBuilder.builder(ModItems.STEEL_AXE.get())
                .setRequireTemperature(2400)
                .setIngredient(4, Items.NETHERITE_AXE)
                .setIngredient(1, ModItems.STEEL_INGOT.get())
                .setIngredient(3, ModItems.STEEL_INGOT.get())
                .setIngredient(5, ModItems.STEEL_INGOT.get())
                .setIngredient(7, ModItems.STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.CRYSTALLIZER.get()), has(ModBlocks.CRYSTALLIZER.get()))
                .save(p_251297_);
        CrystallizerRecipeBuilder.builder(ModItems.STEEL_SHOVEL.get())
                .setRequireTemperature(2400)
                .setIngredient(4, Items.NETHERITE_SHOVEL)
                .setIngredient(1, ModItems.STEEL_INGOT.get())
                .setIngredient(3, ModItems.STEEL_INGOT.get())
                .setIngredient(5, ModItems.STEEL_INGOT.get())
                .setIngredient(7, ModItems.STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.CRYSTALLIZER.get()), has(ModBlocks.CRYSTALLIZER.get()))
                .save(p_251297_);
        CrystallizerRecipeBuilder.builder(ModItems.STEEL_HOE.get())
                .setRequireTemperature(2400)
                .setIngredient(4, Items.NETHERITE_HOE)
                .setIngredient(1, ModItems.STEEL_INGOT.get())
                .setIngredient(3, ModItems.STEEL_INGOT.get())
                .setIngredient(5, ModItems.STEEL_INGOT.get())
                .setIngredient(7, ModItems.STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.CRYSTALLIZER.get()), has(ModBlocks.CRYSTALLIZER.get()))
                .save(p_251297_);

        CrystallizerRecipeBuilder.builder(ModItems.SUPER_STEEL_SWORD.get())
                .setRequireTemperature(9600)
                .setIngredient(4, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(1, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(3, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(5, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(7, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.CRYSTALLIZER.get()), has(ModBlocks.CRYSTALLIZER.get()))
                .save(p_251297_);
        CrystallizerRecipeBuilder.builder(ModItems.SUPER_STEEL_PICKAXE.get())
                .setRequireTemperature(9600)
                .setIngredient(4, ModItems.STEEL_PICKAXE.get())
                .setIngredient(1, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(3, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(5, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(7, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.CRYSTALLIZER.get()), has(ModBlocks.CRYSTALLIZER.get()))
                .save(p_251297_);
        CrystallizerRecipeBuilder.builder(ModItems.SUPER_STEEL_AXE.get())
                .setRequireTemperature(9600)
                .setIngredient(4, ModItems.STEEL_AXE.get())
                .setIngredient(1, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(3, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(5, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(7, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.CRYSTALLIZER.get()), has(ModBlocks.CRYSTALLIZER.get()))
                .save(p_251297_);
        CrystallizerRecipeBuilder.builder(ModItems.SUPER_STEEL_SHOVEL.get())
                .setRequireTemperature(9600)
                .setIngredient(4, ModItems.STEEL_SHOVEL.get())
                .setIngredient(1, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(3, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(5, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(7, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.CRYSTALLIZER.get()), has(ModBlocks.CRYSTALLIZER.get()))
                .save(p_251297_);
        CrystallizerRecipeBuilder.builder(ModItems.SUPER_STEEL_HOE.get())
                .setRequireTemperature(9600)
                .setIngredient(4, ModItems.STEEL_HOE.get())
                .setIngredient(1, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(3, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(5, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .setIngredient(7, ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.CRYSTALLIZER.get()), has(ModBlocks.CRYSTALLIZER.get()))
                .save(p_251297_);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer, Crystallography.MODID + ":" +getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}

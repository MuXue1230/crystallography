package cn.snowskystudio.crystallography.api.recipe;

import cn.snowskystudio.crystallography.Crystallography;
import cn.snowskystudio.crystallography.recipe.ModRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class PressMachineRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final int count;
    @Nullable
    private Ingredient ingredient1;
    @Nullable
    private Ingredient ingredient2;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    public PressMachineRecipeBuilder(ItemLike result, int count) {
        this.result = result.asItem();
        this.count = count;
    }

    public static PressMachineRecipeBuilder builder(ItemLike result) {
        return builder(result, 1);
    }

    public static PressMachineRecipeBuilder builder(ItemLike result, int count) {
        return new PressMachineRecipeBuilder(result, count);
    }

    public PressMachineRecipeBuilder setIngredient(int id, ItemLike item) {
        return this.setIngredient(id, Ingredient.of(item));
    }

    public PressMachineRecipeBuilder setIngredient(int id, Ingredient item) {
        switch (id) {
            case 1: this.ingredient1 = item;
            case 2: this.ingredient2 = item;
        }
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return null;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> p_176503_, ResourceLocation p_176504_) {
        this.ensureValid(p_176504_);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_176504_)).rewards(AdvancementRewards.Builder.recipe(p_176504_)).requirements(RequirementsStrategy.OR);
        p_176503_.accept(new Result(p_176504_, this.result, this.count, ingredient1, ingredient2, this.advancement, p_176504_.withPrefix("recipes/")));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ResourceLocation resourcelocation = new ResourceLocation( Crystallography.MODID + ":" +
                this.result.toString() + "_from_pressing_" +
                this.ingredient1.getItems()[0].getItem().toString() + "_and_" +
                this.ingredient2.getItems()[0].getItem().toString());
        this.save(pFinishedRecipeConsumer, resourcelocation);
    }

    private void ensureValid(ResourceLocation loc) {
        if (this.ingredient1 == null || this.ingredient2 == null) {
            throw new IllegalStateException("No ingredient(s) provided "+loc+".");
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final Ingredient ingredient1;
        private final Ingredient ingredient2;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Item result, int count, Ingredient ingredient1, Ingredient ingredient2, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.ingredient1 = ingredient1;
            this.ingredient2 = ingredient2;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray ingredients = new JsonArray();
            ingredients.add(this.ingredient1.toJson());
            ingredients.add(this.ingredient2.toJson());
            pJson.add("ingredients", ingredients);

            JsonObject result = new JsonObject();
            result.addProperty("count", this.count);
            result.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            pJson.add("result", result);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.PRESS_MACHINE_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}

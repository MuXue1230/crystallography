package cn.snowskystudio.crystallography.api.recipe;

import cn.snowskystudio.crystallography.recipe.ModRecipes;
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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CrystallizerRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final int count;
    private int reqTemp = 0;
    private final Map<String, Ingredient> ingredients = new HashMap<>();
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    public CrystallizerRecipeBuilder(ItemLike result, int count) {
        this.result = result.asItem();
        this.count = count;
    }

    public static CrystallizerRecipeBuilder builder(ItemLike result) {
        return builder(result, 1);
    }

    public static CrystallizerRecipeBuilder builder(ItemLike result, int count) {
        return new CrystallizerRecipeBuilder(result, count);
    }

    public CrystallizerRecipeBuilder setIngredient(int id, ItemLike item) {
        return this.setIngredient(id, Ingredient.of(item));
    }

    public CrystallizerRecipeBuilder setIngredient(int id, Ingredient item) {
        this.ingredients.put(String.valueOf(id), item);
        return this;
    }

    public CrystallizerRecipeBuilder setRequireTemperature(int reqTemp) {
        this.reqTemp = reqTemp;
        return this;
    }

    @Override
    public CrystallizerRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public CrystallizerRecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.result, this.count, this.reqTemp, this.ingredients, this.advancement, pRecipeId.withPrefix("recipes/")));
    }

    private void ensureValid(ResourceLocation loc) {
        if (this.ingredients.isEmpty()) {
            throw new IllegalStateException("No any ingredient provided "+loc+".");
        } else if (this.reqTemp == 0) {
            throw new IllegalStateException("Haven't set require temperature "+loc+".");
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final int reqTemp;
        private final Map<String, Ingredient> ingredients;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Item result, int count, int reqTemp, Map<String, Ingredient> ingredients, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.reqTemp = reqTemp;
            this.ingredients = ingredients;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonObject ingredients = new JsonObject();

            for(Map.Entry<String, Ingredient> entry : this.ingredients.entrySet()) {
                ingredients.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }
            pJson.add("ingredients", ingredients);

            pJson.addProperty("require_temperature", this.reqTemp);

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
            return ModRecipes.CRYSTALLIZER_SERIALIZER.get();
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

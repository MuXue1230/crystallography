package cn.snowskystudio.crystallography.recipe;

import cn.snowskystudio.crystallography.Crystallography;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class CrystallizerRecipe implements Recipe<SimpleContainer> {
    private final Map<String, Ingredient> inputItems;
    private final ItemStack result;
    private final int requireTemperature;
    private final ResourceLocation id;

    public CrystallizerRecipe(Map<String, Ingredient> inputItems, ItemStack output, int requireTemperature, ResourceLocation id) {
        this.inputItems = inputItems;
        this.result = output;
        this.requireTemperature = requireTemperature;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        for (int i = 0; i < this.getIngredients().size(); i++) {
            if (!this.getIngredients().get(i).isEmpty() && !this.getIngredients().get(i).test(pContainer.getItem(i))) {
                return false;
            }
        }

        return true;
    }

    @NotNull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredientNonNullList = NonNullList.withSize(9, Ingredient.EMPTY);
        for (int i = 0; i < this.inputItems.values().size(); i++) {
            ingredientNonNullList.set(i, this.getIngredient(i));
        }
        return ingredientNonNullList;
    }

    @Override
    public boolean isIncomplete() {
        return this.inputItems.isEmpty();
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CrystallizerRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return CrystallizerRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<CrystallizerRecipe> {
        public static final CrystallizerRecipe.Type INSTANCE = new CrystallizerRecipe.Type();
        public static final String ID = "crystallizer";
    }

    public static class Serializer implements RecipeSerializer<CrystallizerRecipe> {
        public static final CrystallizerRecipe.Serializer INSTANCE = new CrystallizerRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Crystallography.MODID, "crystallizer");

        @Override
        public CrystallizerRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));

            int reqTemp = GsonHelper.getAsInt(pSerializedRecipe, "require_temperature");

            JsonObject ingredients = GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredients");
            Map<String, Ingredient> inputs = new HashMap<>();

            for (String id: ingredients.asMap().keySet()) {
                Ingredient ingredient = Ingredient.fromJson(ingredients.get(id));
                inputs.put(id, ingredient);
            }

            return new CrystallizerRecipe(inputs, output, reqTemp, pRecipeId);
        }

        @Override
        public @Nullable CrystallizerRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Map<String, Ingredient> inputs = new HashMap<>();

            for(int i = 0; i < 9; i++) {
                inputs.put(String.valueOf(pBuffer.readInt()), Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();

            int reqTemp = pBuffer.readInt();

            return new CrystallizerRecipe(inputs, output, reqTemp, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CrystallizerRecipe pRecipe) {
            for (String ingredientId : pRecipe.getIngredientsMap().keySet()) {
                pBuffer.writeInt(Integer.parseInt(ingredientId));
                pRecipe.getIngredientsMap().get(ingredientId).toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);

            pBuffer.writeInt(pRecipe.getReqTemp());
        }
    }

    public int getReqTemp() {
        return this.requireTemperature;
    }
    public Map<String, Ingredient> getIngredientsMap() {
        return this.inputItems;
    }
    public Ingredient getIngredient(int id) {
        return this.inputItems.getOrDefault(String.valueOf(id), Ingredient.EMPTY);
    }
}

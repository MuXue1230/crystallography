package cn.snowskystudio.crystallography.compat;

import cn.snowskystudio.crystallography.Crystallography;
import cn.snowskystudio.crystallography.blocks.ModBlocks;
import cn.snowskystudio.crystallography.recipe.CrystallizerRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CrystallizerCategory implements IRecipeCategory<CrystallizerRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Crystallography.MODID, "crystallizer");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Crystallography.MODID,
            "textures/gui/crystallizer_gui.png");

    public static final RecipeType<CrystallizerRecipe> CRYSTALLIZER_TYPE =
            new RecipeType<>(UID, CrystallizerRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public CrystallizerCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 100);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CRYSTALLIZER.get()));
    }

    @Override
    public RecipeType<CrystallizerRecipe> getRecipeType() {
        return CRYSTALLIZER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.crystallography.crystallizer");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrystallizerRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 22, 21).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 46, 21).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 70, 21).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 22, 45).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 46, 45).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 70, 45).addIngredients(recipe.getIngredients().get(5));
        builder.addSlot(RecipeIngredientRole.INPUT, 22, 69).addIngredients(recipe.getIngredients().get(6));
        builder.addSlot(RecipeIngredientRole.INPUT, 46, 69).addIngredients(recipe.getIngredients().get(7));
        builder.addSlot(RecipeIngredientRole.INPUT, 70, 69).addIngredients(recipe.getIngredients().get(8));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 135, 45).addItemStack(recipe.getResultItem(null));
    }
}

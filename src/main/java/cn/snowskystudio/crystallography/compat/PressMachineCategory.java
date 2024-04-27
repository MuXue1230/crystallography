package cn.snowskystudio.crystallography.compat;

import cn.snowskystudio.crystallography.Crystallography;
import cn.snowskystudio.crystallography.blocks.ModBlocks;
import cn.snowskystudio.crystallography.recipe.PressMachineRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class PressMachineCategory implements IRecipeCategory<PressMachineRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Crystallography.MODID, "press_machine");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Crystallography.MODID,
            "textures/gui/press_machine_gui.png");

    public static final RecipeType<PressMachineRecipe> PRESS_MACHINE_TYPE =
            new RecipeType<>(UID, PressMachineRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public PressMachineCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 88);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.PRESS_MACHINE.get()));
    }

    @Override
    public RecipeType<PressMachineRecipe> getRecipeType() {
        return PRESS_MACHINE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.crystallography.press_machine");
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
    public void setRecipe(IRecipeLayoutBuilder builder, PressMachineRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 29, 17).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 17).addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 123, 17).addItemStack(recipe.getResultItem(null));
    }
}

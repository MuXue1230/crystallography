package cn.snowskystudio.crystallography.compat;

import cn.snowskystudio.crystallography.Crystallography;
import cn.snowskystudio.crystallography.recipe.CrystallizerRecipe;
import cn.snowskystudio.crystallography.recipe.PressMachineRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEICrystallographyPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Crystallography.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new PressMachineCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrystallizerCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<PressMachineRecipe> pressRecipes = recipeManager.getAllRecipesFor(PressMachineRecipe.Type.INSTANCE);
        registration.addRecipes(PressMachineCategory.PRESS_MACHINE_TYPE, pressRecipes);
        List<CrystallizerRecipe> crystallizeRecipes = recipeManager.getAllRecipesFor(CrystallizerRecipe.Type.INSTANCE);
        registration.addRecipes(CrystallizerCategory.CRYSTALLIZER_TYPE, crystallizeRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //registration.addRecipeClickArea(PressMachineScreen.class, 60, 30, 20, 30,
        //        PressMachineCategory.PRESS_MACHINE_TYPE);
    }
}

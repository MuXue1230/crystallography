package cn.snowskystudio.crystallography.datagen;

import cn.snowskystudio.crystallography.Crystallography;
import cn.snowskystudio.crystallography.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Crystallography.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.STEEL_INGOT);
        simpleItem(ModItems.FIRST_EXTRUDED_UNFIRED_STEEL_INGOT);
        simpleItem(ModItems.FIRST_EXTRUDED_STEEL_INGOT);
        simpleItem(ModItems.SECONDARY_EXTRUDED_UNFIRED_STEEL_INGOT);
        simpleItem(ModItems.SECONDARY_EXTRUDED_STEEL_INGOT);
        simpleItem(ModItems.TRIPLE_EXTRUDED_UNFIRED_STEEL_INGOT);
        simpleItem(ModItems.TRIPLE_EXTRUDED_STEEL_INGOT);
        simpleItem(ModItems.QUADRUPLE_EXTRUDED_UNFIRED_STEEL_INGOT);
        simpleItem(ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Crystallography.MODID, "item/"+item.getId().getPath()));
    }
}
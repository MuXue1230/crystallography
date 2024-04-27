package cn.snowskystudio.crystallography.datagen;

import cn.snowskystudio.crystallography.Crystallography;
import cn.snowskystudio.crystallography.blocks.ModBlocks;
import cn.snowskystudio.crystallography.blocks.custom.CrystallizerBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Crystallography.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.STEEL_BLOCK);

        blockWithDirectionalRotation(ModBlocks.PRESS_MACHINE,
                new ModelFile.UncheckedModelFile(modLoc("block/press_machine")), 180);
        blockWithDirectionalRotation(ModBlocks.CRYSTALLIZER,
                new ModelFile.UncheckedModelFile(modLoc("block/crystallizer")), 0);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockWithDirectionalRotation(RegistryObject<Block> blockRegistryObject, ModelFile.UncheckedModelFile modelLocation, int mov) {
        simpleBlockItem(blockRegistryObject.get(), modelLocation);
        getVariantBuilder(blockRegistryObject.get())
                .forAllStates(state -> {
                    Direction facing = state.getValue(CrystallizerBlock.FACING);
                    int xRot = 0;
                    int yRot = 0;
                    switch (facing) {
                        case NORTH:
                            yRot = mov;
                            break;
                        case EAST:
                            yRot = (90 + mov)%360;
                            break;
                        case SOUTH:
                            yRot = (180 + mov)%360;
                            break;
                        case WEST:
                            yRot = (270 + mov)%360;
                            break;
                    }

                    return ConfiguredModel.builder()
                            .modelFile(modelLocation)
                            .rotationX(xRot)
                            .rotationY(yRot)
                            .build();
                });
    }
}

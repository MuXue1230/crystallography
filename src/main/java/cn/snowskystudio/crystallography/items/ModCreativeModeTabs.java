package cn.snowskystudio.crystallography.items;

import cn.snowskystudio.crystallography.Crystallography;
import cn.snowskystudio.crystallography.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Crystallography.MODID);

    public static final RegistryObject<CreativeModeTab> CRYSTALLOGRAPHY_TAB = CREATIVE_MODE_TABS.register("crystallography_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CRYSTALLIZATION_PROCESSING_CORE.get()))
                    .title(Component.translatable("creativetab.crystallography.crystallography_tab"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.STEEL_INGOT.get());
                        pOutput.accept(ModBlocks.STEEL_BLOCK.get());
                        pOutput.accept(ModItems.FIRST_EXTRUDED_STEEL_INGOT.get());
                        pOutput.accept(ModItems.SECONDARY_EXTRUDED_STEEL_INGOT.get());
                        pOutput.accept(ModItems.TRIPLE_EXTRUDED_STEEL_INGOT.get());
                        pOutput.accept(ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get());
                        pOutput.accept(ModItems.FIRST_EXTRUDED_UNFIRED_STEEL_INGOT.get());
                        pOutput.accept(ModItems.SECONDARY_EXTRUDED_UNFIRED_STEEL_INGOT.get());
                        pOutput.accept(ModItems.TRIPLE_EXTRUDED_UNFIRED_STEEL_INGOT.get());
                        pOutput.accept(ModItems.QUADRUPLE_EXTRUDED_UNFIRED_STEEL_INGOT.get());
                        pOutput.accept(ModItems.CRYSTALLIZATION_PROCESSING_CORE.get());

                        pOutput.accept(ModItems.STEEL_SWORD.get());
                        pOutput.accept(ModItems.STEEL_PICKAXE.get());
                        pOutput.accept(ModItems.STEEL_AXE.get());
                        pOutput.accept(ModItems.STEEL_SHOVEL.get());
                        pOutput.accept(ModItems.STEEL_HOE.get());

                        pOutput.accept(ModItems.SUPER_STEEL_SWORD.get());
                        pOutput.accept(ModItems.SUPER_STEEL_PICKAXE.get());
                        pOutput.accept(ModItems.SUPER_STEEL_AXE.get());
                        pOutput.accept(ModItems.SUPER_STEEL_SHOVEL.get());
                        pOutput.accept(ModItems.SUPER_STEEL_HOE.get());

                        pOutput.accept(ModItems.STEEL_HELMET.get());
                        pOutput.accept(ModItems.STEEL_CHESTPLATE.get());
                        pOutput.accept(ModItems.STEEL_LEGGINGS.get());
                        pOutput.accept(ModItems.STEEL_BOOTS.get());

                        pOutput.accept(ModItems.SUPER_STEEL_HELMET.get());
                        pOutput.accept(ModItems.SUPER_STEEL_CHESTPLATE.get());
                        pOutput.accept(ModItems.SUPER_STEEL_LEGGINGS.get());
                        pOutput.accept(ModItems.SUPER_STEEL_BOOTS.get());

                        pOutput.accept(ModBlocks.PRESS_MACHINE.get());
                        pOutput.accept(ModBlocks.CRYSTALLIZER.get());
                    }))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

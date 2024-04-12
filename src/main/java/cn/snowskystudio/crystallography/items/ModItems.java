package cn.snowskystudio.crystallography.items;

import cn.snowskystudio.crystallography.Crystallography;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Crystallography.MODID);

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRST_EXTRUDED_UNFIRED_STEEL_INGOT = ITEMS.register("first_extruded_unfired_steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRST_EXTRUDED_STEEL_INGOT = ITEMS.register("first_extruded_steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SECONDARY_EXTRUDED_UNFIRED_STEEL_INGOT = ITEMS.register("secondary_extruded_unfired_steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SECONDARY_EXTRUDED_STEEL_INGOT = ITEMS.register("secondary_extruded_steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRIPLE_EXTRUDED_UNFIRED_STEEL_INGOT = ITEMS.register("triple_extruded_unfired_steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRIPLE_EXTRUDED_STEEL_INGOT = ITEMS.register("triple_extruded_steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> QUADRUPLE_EXTRUDED_UNFIRED_STEEL_INGOT = ITEMS.register("quadruple_extruded_unfired_steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> QUADRUPLE_EXTRUDED_STEEL_INGOT = ITEMS.register("quadruple_extruded_steel_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CRYSTALLIZATION_PROCESSING_CORE = ITEMS.register("crystallization_processing_core",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword",
            () -> new SwordItem(ModToolTiers.STEEL, 7, 5, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STEEL, 3, 2, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe",
            () -> new AxeItem(ModToolTiers.STEEL, 10, 4, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel",
            () -> new ShovelItem(ModToolTiers.STEEL, 2, 1, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_HOE = ITEMS.register("steel_hoe",
            () -> new HoeItem(ModToolTiers.STEEL, 3, 2, new Item.Properties()));


    public static final RegistryObject<Item> SUPER_STEEL_SWORD = ITEMS.register("super_steel_sword",
            () -> new SwordItem(ModToolTiers.SUPER_STEEL, 14, 10, new Item.Properties()));
    public static final RegistryObject<Item> SUPER_STEEL_PICKAXE = ITEMS.register("super_steel_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SUPER_STEEL, 6, 4, new Item.Properties()));
    public static final RegistryObject<Item> SUPER_STEEL_AXE = ITEMS.register("super_steel_axe",
            () -> new AxeItem(ModToolTiers.SUPER_STEEL, 20, 8, new Item.Properties()));
    public static final RegistryObject<Item> SUPER_STEEL_SHOVEL = ITEMS.register("super_steel_shovel",
            () -> new ShovelItem(ModToolTiers.SUPER_STEEL, 4, 2, new Item.Properties()));
    public static final RegistryObject<Item> SUPER_STEEL_HOE = ITEMS.register("super_steel_hoe",
            () -> new HoeItem(ModToolTiers.SUPER_STEEL, 6, 4, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

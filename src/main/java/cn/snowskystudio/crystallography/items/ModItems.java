package cn.snowskystudio.crystallography.items;

import cn.snowskystudio.crystallography.Crystallography;
import net.minecraft.world.item.Item;
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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

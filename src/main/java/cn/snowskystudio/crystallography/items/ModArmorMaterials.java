package cn.snowskystudio.crystallography.items;

import cn.snowskystudio.crystallography.Crystallography;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    STEEL("steel", 45, Util.make(new EnumMap<>(ArmorItem.Type.class), (pMap) -> {
        pMap.put(ArmorItem.Type.BOOTS, 5);
        pMap.put(ArmorItem.Type.LEGGINGS, 8);
        pMap.put(ArmorItem.Type.CHESTPLATE, 10);
        pMap.put(ArmorItem.Type.HELMET, 5);
    }), 20, SoundEvents.ARMOR_EQUIP_IRON, 4.0F, 0.5F, () -> {
        return Ingredient.of(ModItems.STEEL_INGOT.get());
    }),
    SUPER_STEEL("super_steel", 60, Util.make(new EnumMap<>(ArmorItem.Type.class), (pMap) -> {
        pMap.put(ArmorItem.Type.BOOTS, 10);
        pMap.put(ArmorItem.Type.LEGGINGS, 16);
        pMap.put(ArmorItem.Type.CHESTPLATE, 20);
        pMap.put(ArmorItem.Type.HELMET, 10);
    }), 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 8.0F, 2.0F, () -> {
        return Ingredient.of(ModItems.QUADRUPLE_EXTRUDED_STEEL_INGOT.get());
    });

    public static final StringRepresentable.EnumCodec<ArmorMaterials> CODEC = StringRepresentable.fromEnum(ArmorMaterials::values);
    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (pMap) -> {
        pMap.put(ArmorItem.Type.BOOTS, 13);
        pMap.put(ArmorItem.Type.LEGGINGS, 15);
        pMap.put(ArmorItem.Type.CHESTPLATE, 16);
        pMap.put(ArmorItem.Type.HELMET, 11);
    });

    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionFunctionForType, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionFunctionForType = protectionFunctionForType;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return HEALTH_FUNCTION_FOR_TYPE.get(pType) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionFunctionForType.get(pType);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return Crystallography.MODID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}

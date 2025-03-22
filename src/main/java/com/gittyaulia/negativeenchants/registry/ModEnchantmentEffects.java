package com.gittyaulia.negativeenchants.registry;

import com.gittyaulia.negativeenchants.NegativeEnchants;
import com.gittyaulia.negativeenchants.enchantments.effects.UnequipEnchantmentEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {

    public static final RegistryKey<Enchantment> CURSE_OF_UNBINDING = of("curse_of_unbinding");
    public static MapCodec<UnequipEnchantmentEffect> UNEQUIP_EFFECT = register("unequip_effect", UnequipEnchantmentEffect.CODEC);

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = Identifier.of(NegativeEnchants.MOD_ID, path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, Identifier.of(NegativeEnchants.MOD_ID, id), codec);
    }

    public static void registerModEnchantmentEffects() {
        NegativeEnchants.LOGGER.info("Registering EnchantmentEffects for" + NegativeEnchants.MOD_ID);
    }
}

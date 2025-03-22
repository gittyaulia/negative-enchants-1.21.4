package com.gittyaulia.negativeenchants.datagen;

import com.gittyaulia.negativeenchants.enchantments.effects.UnequipEnchantmentEffect;
import com.gittyaulia.negativeenchants.registry.ModEnchantmentEffects;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

//#entrypoint
public class EnchantmentGenerator extends FabricDynamicRegistryProvider {
    public EnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
        System.out.println("REGISTERING ENCHANTS");
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {

        register(entries, ModEnchantmentEffects.CURSE_OF_UNBINDING, Enchantment.builder(
                                Enchantment.definition(
                                        registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.EQUIPPABLE_ENCHANTABLE),
                                        // this is the "weight" or probability of our enchantment showing up in the table
                                        10,
                                        // the maximum level of the enchantment
                                        1,
                                        // base cost for level 1 of the enchantment, and min levels required for something higher
                                        Enchantment.leveledCost(1, 10),
                                        // same fields as above but for max cost
                                        Enchantment.leveledCost(1, 15),
                                        // anvil cost
                                        5,
                                        // valid slots
                                        AttributeModifierSlot.ARMOR

                                )
                        )
                .addEffect(
                        EnchantmentEffectComponentTypes.TICK,
                        new UnequipEnchantmentEffect(EnchantmentLevelBasedValue.linear(0.4f, 0.2f))
                )
        );

    }

    private void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
        entries.add(key, builder.build(key.getValue()), resourceConditions);
    }

    @Override
    public String getName() {
        return "NegativeEnchantsEnchantmentGenerator";
    }
}

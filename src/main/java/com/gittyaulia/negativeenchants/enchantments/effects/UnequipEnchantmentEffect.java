package com.gittyaulia.negativeenchants.enchantments.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public record UnequipEnchantmentEffect(EnchantmentLevelBasedValue amount) implements EnchantmentEntityEffect {
    public static final MapCodec<UnequipEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("amount").forGetter(UnequipEnchantmentEffect::amount)
            ).apply(instance, UnequipEnchantmentEffect::new)
    );

    private static final Logger log = LoggerFactory.getLogger(UnequipEnchantmentEffect.class);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {

        if (target instanceof LivingEntity victim) {
            if (context.owner() != null && context.owner() instanceof PlayerEntity player) {

                PlayerInventory inventory = player.getInventory();
                List<ItemStack> armorInventoryList = inventory.armor;

                for (int i = 0; i < armorInventoryList.size(); i++) {
                    ItemStack itemStack = (ItemStack)armorInventoryList.get(i);

                    if (!itemStack.isEmpty() && itemStack.getEnchantments().toString().contains("curse_of_unbinding")) {
                        inventory.player.dropItem(itemStack, false, false);

                        if (!world.isClient) {
                            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BUNDLE_DROP_CONTENTS, player.getSoundCategory(), 3f, 1f);
                            log.info("sound played armor");
                        }

                        armorInventoryList.set(i, ItemStack.EMPTY);
                        log.info("dropped{}", itemStack);
                    }
                }

            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}

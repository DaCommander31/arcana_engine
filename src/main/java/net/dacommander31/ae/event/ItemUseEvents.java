package net.dacommander31.ae.event;

import net.dacommander31.ae.registry.SpellRegistry;
import net.dacommander31.ae.spells.behavior.RaycastSpellBehavior;
import net.dacommander31.ae.spells.type.DamageSpellType;
import net.dacommander31.ae.util.ParticleEffectData;
import net.dacommander31.ae.util.SpellBuilder;
import net.dacommander31.ae.util.SpellManager;
import net.dacommander31.ae.util.filter.BlockFilter;
import net.dacommander31.ae.util.filter.EntityFilter;
import net.dacommander31.ae.util.filter.PlayerFilter;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;

public class ItemUseEvents {
    public static void registerItemUseEvents() {
        BlockFilter blockFilter = new BlockFilter()
                .addBlockStateConditionToFilter(AbstractBlock.AbstractBlockState::isOpaque);

        EntityFilter entityFilter = new EntityFilter();

        ParticleEffectData flameParticleData = new ParticleEffectData(
                ParticleTypes.FLAME,
                new Vec3d(0.1, 0.1, 0.1),
                5,
                0.01f,
                true,
                true,
                PlayerFilter.allPlayers()
        );

        SpellBuilder testSpell = new SpellBuilder.Builder(new Identifier("ae", "test"))
                .setCooldown(10)
                .build();

        SpellRegistry.registerSpell(testSpell, new DamageSpellType(5f, DamageTypes.MAGIC), new RaycastSpellBehavior(10f, 0.1f, blockFilter, entityFilter, flameParticleData));

        UseItemCallback.EVENT.register(((player, world, hand) ->
        {
            if (player != null) {
                if (player.getStackInHand(hand).equals(new ItemStack(Items.BLAZE_POWDER))) {
                    SpellManager.addSpell(player, testSpell);
                }
                return TypedActionResult.pass(player.getStackInHand(hand));
            }
            assert false;
            return TypedActionResult.pass(player.getStackInHand(hand));
        }));
    }
}

package net.dacommander31.ae;

import net.dacommander31.ae.event.SpellKeybindEvents;
import net.dacommander31.ae.registry.SpellRegistry;
import net.dacommander31.ae.spells.type.DamageSpellType;
import net.dacommander31.ae.spells.behavior.RaycastSpellBehavior;
import net.dacommander31.ae.util.BlockFilter;
import net.dacommander31.ae.util.SpellBuilder;
import net.dacommander31.ae.util.SpellKeybinds;
import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcanaEngine implements ModInitializer {
	public static final String MOD_ID = "arcana_engine";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		SpellKeybinds.registerKeybinds();
		SpellKeybindEvents.registerKeybindEvents();

		BlockFilter blockFilter = new BlockFilter();

		SpellBuilder testSpell = new SpellBuilder.Builder(new Identifier("ae", "test"))
				.build();

		SpellRegistry.registerSpell(testSpell, new DamageSpellType(5, DamageTypes.MAGIC), new RaycastSpellBehavior(10f, 0.1f, blockFilter));
	}
}
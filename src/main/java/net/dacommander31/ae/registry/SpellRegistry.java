package net.dacommander31.ae.registry;

import net.dacommander31.ae.lang.SpellNotFoundException;
import net.dacommander31.ae.util.SpellBehavior;
import net.dacommander31.ae.util.SpellBuilder;
import net.dacommander31.ae.util.SpellType;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SpellRegistry {
    private static final Map<Identifier, SpellBuilder> spellBuilderMap = new HashMap<>();
    private static final Map<Identifier, SpellType> spellTypeMap = new HashMap<>();
    private static final Map<SpellType, Identifier> spellTypeReverseMap = new HashMap<>();
    private static final Map<Identifier, SpellBehavior> spellBehaviorMap = new HashMap<>();
    private static final Map<SpellBehavior, Identifier> spellBehaviorReverseMap = new HashMap<>();

    public static void registerSpell(SpellBuilder spell, SpellType spellType, SpellBehavior spellBehavior) {
        Identifier spellId = spell.getSpellId();
        if (spellBuilderMap.containsKey(spellId) || spellTypeMap.containsKey(spellId) || spellBehaviorMap.containsKey(spellId)) {
            throw new IllegalArgumentException("Spell ID " + spellId + " is already registered");
        }
        spellBuilderMap.put(spellId, spell);
        spellTypeMap.put(spellId, spellType);
        spellTypeReverseMap.put(spellType, spellId);
        spellBehaviorMap.put(spellId, spellBehavior);
        spellBehaviorReverseMap.put(spellBehavior, spellId);
    }

    public static SpellBuilder getSpell(Identifier spellId) throws SpellNotFoundException {
        SpellBuilder spell = spellBuilderMap.get(spellId);
        if (spell == null) {
            throw new SpellNotFoundException("No spell found with ID: " + spellId);
        }
        return spell;
    }

    public static SpellType getSpellType(Identifier spellId) throws SpellNotFoundException {
        SpellType spellType = spellTypeMap.get(spellId);
        if (spellType == null) {
            throw new SpellNotFoundException("No spell type associated with ID: " + spellId);
        }
        return spellType;
    }

    public static SpellBehavior getSpellBehavior(Identifier spellId) throws SpellNotFoundException {
        SpellBehavior spellBehavior = spellBehaviorMap.get(spellId);
        if (spellBehavior == null) {
            throw new SpellNotFoundException("No spell behavior associated with ID: " + spellId);
        }
        return spellBehavior;
    }

    public static Identifier getSpellTypeId(SpellType spellType) throws SpellNotFoundException {
        Identifier spellTypeId = spellTypeReverseMap.get(spellType);
        if (spellTypeId == null) {
            throw new SpellNotFoundException("No spell ID associated with spell type: " + spellType.toString());
        }
        return spellTypeId;
    }

    public static Identifier getSpellBehaviorId(SpellBehavior spellBehavior) throws SpellNotFoundException {
        Identifier spellBehaviorId = spellBehaviorReverseMap.get(spellBehavior);
        if (spellBehaviorId == null) {
            throw new SpellNotFoundException("No spell ID associated with spell behavior: " + spellBehavior.toString());
        }
        return spellBehaviorId;
    }

    public static SpellType getSpellTypeFromBehavior(SpellBehavior spellBehavior) throws SpellNotFoundException {
        Identifier spellId = getSpellBehaviorId(spellBehavior);
        return getSpellType(spellId);
    }

    public static SpellBehavior getSpellBehaviorFromType(SpellType spellType) throws SpellNotFoundException {
        Identifier spellId = getSpellTypeId(spellType);
        return getSpellBehavior(spellId);
    }

    public static boolean isSpellRegistered(Identifier spellId) {
        return spellBuilderMap.containsKey(spellId) || spellBehaviorMap.containsKey(spellId) || spellTypeMap.containsKey(spellId);
    }
}

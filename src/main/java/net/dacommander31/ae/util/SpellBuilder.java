package net.dacommander31.ae.util;

import net.dacommander31.ae.ArcanaEngine;
import net.minecraft.util.Identifier;

public class SpellBuilder {
    private final Identifier id;
    private final String name;
    private final String desc;
    private final float power;
    private final int cooldown;
    private final float delta;
    private final float range;
    private final float areaOfEffect;
    private final float areaOfEffectChange;
    private final boolean isTickable;

    private SpellBuilder(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.desc = builder.desc;
        this.power = builder.power;
        this.cooldown = builder.cooldown;
        this.delta = builder.delta;
        this.range = builder.range;
        this.areaOfEffect = builder.areaOfEffect;
        this.areaOfEffectChange = builder.areaOfEffectChange;
        this.isTickable = builder.isTickable;
    }

    public static class Builder {
        private final Identifier id;
        private String name;
        private String desc = "";
        private float power = 1f;
        private int cooldown = 20;
        private float delta = 0.1f;
        private float range  = 10f;
        private float areaOfEffect = 0.5f;
        private float areaOfEffectChange = 0f;
        private boolean isTickable = false;

        public Builder(Identifier spellId) {
            this.id = spellId;
        }

        public Builder setSpellName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder setPowerLevel(float power) {
            this.power = power;
            return this;
        }

        public Builder setCooldown(int cooldownInTicks) {
            this.cooldown = cooldownInTicks;
            return this;
        }

        public Builder setDelta(float delta) {
            this.delta = delta;
            return this;
        }

        public Builder setRange(float range) {
            this.range = range;
            return this;
        }

        public Builder setAreaOfEffect(float areaOfEffect) {
            this.areaOfEffect = areaOfEffect;
            return this;
        }

        public Builder setAreaOfEffectChange(float areaOfEffectChange) {
            this.areaOfEffectChange = areaOfEffectChange;
            return this;
        }

        public Builder tickable() {
            this.isTickable = true;
            return this;
        }

        public SpellBuilder build() {
            if (this.id != null) {
                if (this.name == null || this.name.isEmpty()) {
                    this.name = this.id.toString();
                }
                return new SpellBuilder(this);
            } else {
                ArcanaEngine.LOGGER.error("Unable to build spell. Spell id is required but none was provided. Skipping.");
                return null;
            }
        }
    }

    public Identifier getSpellId() {
        return id;
    }

    public String getSpellName() {
        return name;
    }

    public String getDescription() {
        return desc;
    }

    public float getPowerLevel() {
        return power;
    }

    public int getCooldown() {
        return cooldown;
    }

    public float getDelta() {
        return delta;
    }

    public float getRange() {
        return range;
    }

    public float getAreaOfEffect() {
        return areaOfEffect;
    }

    public float getAreaOfEffectChange() {
        return areaOfEffectChange;
    }

    public boolean isTickable() {
        return this.isTickable;
    }
}

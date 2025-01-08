package net.dacommander31.ae.util;

import net.dacommander31.ae.ArcanaEngine;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.text.Text;

public class SpellBuilder {
    private final Identifier id;
    private final Text name;
    private final Text desc;
    private final float power;
    private final int duration;
    private final int cooldown;
    private final double areaOfEffect;
    private final double areaGrowthPerTick;
    private final Vec3d offset;
    private final boolean isTickable;

    private SpellBuilder(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.desc = builder.desc;
        this.power = builder.power;
        this.duration = builder.duration;
        this.cooldown = builder.cooldown;
        this.areaOfEffect = builder.areaOfEffect;
        this.areaGrowthPerTick = builder.areaGrowthPerTick;
        this.offset = builder.offset;
        this.isTickable = builder.isTickable;
    }

    public static class Builder {
        private final Identifier id;
        private Text name;
        private Text desc = Text.empty();
        private float power = 1f;
        private int duration = 100;
        private int cooldown = 20;
        private double areaOfEffect = 0.5f;
        private double areaGrowthPerTick = 0f;
        private Vec3d offset = Vec3d.ZERO;
        private boolean isTickable = false;

        public Builder(Identifier spellId) {
            this.id = spellId;
        }

        public Builder setSpellName(Text name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(Text desc) {
            this.desc = desc;
            return this;
        }

        public Builder setPowerLevel(float power) {
            this.power = power;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setCooldown(int cooldownInTicks) {
            this.cooldown = cooldownInTicks;
            return this;
        }

        public Builder setAreaOfEffect(double areaOfEffect) {
            this.areaOfEffect = areaOfEffect;
            return this;
        }

        public Builder setAreaGrowthPerTick(float areaGrowthPerTick) {
            this.areaGrowthPerTick = areaGrowthPerTick;
            return this;
        }

        public Builder setOffset(Vec3d offset) {
            this.offset = offset;
            return this;
        }

        public Builder tickable() {
            this.isTickable = true;
            return this;
        }

        public SpellBuilder build() {
            if (this.id != null) {
                if (this.name == null) {
                    this.name = Text.literal(this.id.toString());
                }
                return new SpellBuilder(this);
            } else {
                ArcanaEngine.LOGGER.error("Unable to build spell. Spell id is required but none was provided. Skipping.");
                return null;
            }
        }
    }

    public Identifier getSpellId() {
        return this.id;
    }

    public String getSpellName() {
        return this.name.getString();
    }

    public Text getDescription() {
        return this.desc;
    }

    public float getPowerLevel() {
        return this.power;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public double getAreaOfEffect() {
        return this.areaOfEffect;
    }

    public double getAreaGrowthPerTick() {
        return this.areaGrowthPerTick;
    }

    public Vec3d getOffset() {
        return this.offset;
    }

    public boolean isTickable() {
        return this.isTickable;
    }
}

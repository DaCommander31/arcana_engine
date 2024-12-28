package net.dacommander31.ae.spells;

import net.dacommander31.ae.ArcanaEngine;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpellBuilder {
    private final Identifier id;
    private final String name;
    private final String desc;
    private final float power;
    private final int cooldown;

    private SpellBuilder(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.desc = builder.desc;
        this.power = builder.power;
        this.cooldown = builder.cooldown;
    }

    public static class Builder {
        private Identifier id;
        private String name;
        private String desc;
        private float power;
        private int cooldown;

        public Builder setSpellId(Identifier id) {
            this.id = id;
            return this;
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

        public Builder setCooldown(int cooldown) {
            this.cooldown = cooldown;
            return this;
        }

        public SpellBuilder build() {
            if (this.id != null) {
                if (this.name.isEmpty()) {
                    this.name = this.id.toString();
                }
                return new SpellBuilder(this);
            } else {
                ArcanaEngine.LOGGER.warn(String.valueOf(Text.translatable("ae.no_id_error")));
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
}

package net.dacommander31.ae.util.filter;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.tag.TagKey;

import java.util.*;
import java.util.function.Predicate;

public class EntityFilter {
    private final Set<Entity> ignored = new HashSet<>();
    private final Set<TagKey<EntityType<?>>> ignoredTags = new HashSet<>();
    private final Set<Predicate<Entity>> ignoredConditions = new HashSet<>();
    private final Set<UUID> ignoredUuids = new HashSet<>();

    public Set<Entity> getIgnored() {
        return this.ignored;
    }

    public Set<TagKey<EntityType<?>>> getIgnoredTags() {
        return this.ignoredTags;
    }

    public Set<Predicate<Entity>> getIgnoredConditions() {
        return this.ignoredConditions;
    }

    public Set<UUID> getIgnoredUuids() {
        return this.ignoredUuids;
    }

    public EntityFilter ignoreEntity(Entity... entities) {
        return this.ignoreEntity(List.of(entities));
    }

    public EntityFilter ignoreEntity(List<Entity> entities) {
        this.ignored.addAll(entities);
        return this;
    }

    @SafeVarargs
    public final EntityFilter ignoreEntityTag(TagKey<EntityType<?>>... entityTags) {
        return this.ignoreEntityTag(List.of(entityTags));
    }

    public EntityFilter ignoreEntityTag(List<TagKey<EntityType<?>>> entityTags) {
        this.ignoredTags.addAll(entityTags);
        return this;
    }

    @SafeVarargs
    public final EntityFilter ignoreEntityCondition(Predicate<Entity>... conditions) {
        return this.ignoreEntityCondition(List.of(conditions));
    }

    public EntityFilter ignoreEntityCondition(List<Predicate<Entity>> conditions) {
        this.ignoredConditions.addAll(conditions);
        return this;
    }

    public EntityFilter ignoreEntityUuid(UUID... uuids) {
        return this.ignoreEntityUuid(List.of(uuids));
    }

    public EntityFilter ignoreEntityUuid(List<UUID> uuids) {
        this.ignoredUuids.addAll(uuids);
        return this;
    }

    public boolean isEntityIgnored(Entity entity) {
        return this.ignored.contains(entity);
    }

    public boolean isEntityInIgnoredTags(Entity entity) {
        return this.ignoredTags.stream().anyMatch(tag -> entity.getType().isIn(tag));
    }

    public boolean isEntityConditionIgnored(Predicate<Entity> condition) {
        return this.ignoredConditions.contains(condition);
    }

    public boolean isEntityUuidIgnored(UUID uuid) {
        return this.ignoredUuids.contains(uuid);
    }

    public boolean shouldIncludeEntity(Entity entity) {
        if (this.ignored.isEmpty() && this.ignoredTags.isEmpty() && this.ignoredConditions.isEmpty() && this.ignoredUuids.isEmpty()) {
            return true;
        }

        if (this.isEntityIgnored(entity)) {
            return false;
        }

        if (this.isEntityInIgnoredTags(entity)) {
            return false;
        }

        for (Predicate<Entity> condition : ignoredConditions) {
            if (condition.test(entity)) {
                return false;
            }
        }

        if (this.isEntityUuidIgnored(entity.getUuid())) {
            return false;
        }

        return true;
    }
}

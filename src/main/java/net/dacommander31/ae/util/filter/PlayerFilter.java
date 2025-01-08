package net.dacommander31.ae.util.filter;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.function.Predicate;

public class PlayerFilter {
    private final Predicate<PlayerEntity> condition;

    public PlayerFilter(Predicate<PlayerEntity> condition) {
        this.condition = condition;
    }

    public boolean shouldIncludePlayer(PlayerEntity player) {
        return condition.test(player);
    }

    public static PlayerFilter allPlayers() {
        return new PlayerFilter(player -> true);
    }

    public static PlayerFilter byName(String name) {
        return new PlayerFilter(player -> player.getName().getString().equals(name));
    }

    public static PlayerFilter isScoreAbove(int minScore) {
        return new PlayerFilter(player -> player.getScore() > minScore);
    }

    public static PlayerFilter isScoreBelow(int maxScore) {
        return new PlayerFilter(player -> player.getScore() < maxScore);
    }

    public static PlayerFilter isScoreInRange(int minScore, int maxScore) {
        return new PlayerFilter(player -> player.getScore() > minScore && player.getScore() < maxScore);
    }

    public static PlayerFilter isScoreInRangeInclusive(int minScore, int maxScore) {
        return new PlayerFilter(player -> player.getScore() >= minScore && player.getScore() <= maxScore);
    }

    public static PlayerFilter near(Vec3d pos, double radius) {
        return new PlayerFilter(player -> player.getPos().isInRange(pos, radius));
    }
}

package net.dacommander31.ae.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.function.Predicate;

public class BlockFilter {
    private final Set<Block> filter = new HashSet<>();
    private final Set<TagKey<Block>> tagFilter = new HashSet<>();
    private final Set<Predicate<BlockState>> blockStateConditionFilter = new HashSet<>();

    public Set<Block> getFilter() {
        return this.filter;
    }

    public Set<TagKey<Block>> getTagFilter() {
        return this.tagFilter;
    }

    public Set<Predicate<BlockState>> getBlockStateConditionFilter() {
        return this.blockStateConditionFilter;
    }

    public BlockFilter addBlockToFilter(Block... blocks) {
        return addBlockToFilter(List.of(blocks));
    }

    public BlockFilter addBlockToFilter(Collection<Block> blocks) {
        filter.addAll(blocks);
        return this;
    }

    @SafeVarargs
    public final BlockFilter addBlockTagToFilter(TagKey<Block>... blockTags) {
        return addBlockTagToFilter(List.of(blockTags));
    }

    public BlockFilter addBlockTagToFilter(List<TagKey<Block>> blockTags) {
        this.tagFilter.addAll(blockTags);
        return this;
    }

    @SafeVarargs
    public final BlockFilter addBlockStateConditionToFilter(Predicate<BlockState>... conditions) {
        return addBlockStateConditionToFilter(List.of(conditions));
    }

    public BlockFilter addBlockStateConditionToFilter(List<Predicate<BlockState>> conditions) {
        this.blockStateConditionFilter.addAll(conditions);
        return this;
    }

    public boolean isBlockInFilter(Block block) {
        return this.filter.contains(block);
    }

    public boolean isBlockInTagFilter(Block block) {
        return this.tagFilter.stream().anyMatch(tag -> block.getDefaultState().isIn(tag));
    }

    public boolean isBlockStateConditionInFilter(Predicate<BlockState> condition) {
        return this.blockStateConditionFilter.contains(condition);
    }

    public boolean shouldIgnoreBlock(World world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();

        // If all filters are empty, return true (default: ignore all blocks)
        if (filter.isEmpty() && tagFilter.isEmpty() && blockStateConditionFilter.isEmpty()) {
            return true; // Default to ignoring all blocks
        }

        // Check filter for block types
        if (!filter.isEmpty() && isBlockInFilter(block)) {
            return false; // Do not ignore if in filter
        }
        if (!tagFilter.isEmpty() && isBlockInTagFilter(block)) {
            return false; // Do not ignore if in tag filter
        }

        // Check block state conditions for filter
        if (!blockStateConditionFilter.isEmpty()) {
            boolean matchesFilter = blockStateConditionFilter.stream().anyMatch(condition -> condition.test(blockState));
            if (matchesFilter) {
                return false; // Do not ignore if block state matches filter condition
            }
        }

        // If no conditions match, return true by default (ignore the block)
        return true;
    }
}

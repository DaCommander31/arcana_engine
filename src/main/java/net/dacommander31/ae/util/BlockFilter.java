package net.dacommander31.ae.util;

import net.minecraft.block.Block;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BlockFilter {
    private final Set<Block> whitelist = new HashSet<>();
    private final Set<Block> blacklist = new HashSet<>();
    private final Set<BlockTags> tagWhitelist = new HashSet<>();
    private final Set<BlockTags> tagBlackList = new HashSet<>();

    public void addBlockToWhitelist(Block... blocks) {
        whitelist.addAll(Arrays.asList(blocks));
    }

    public void addBlockToBlacklist(Block... blocks) {
        blacklist.addAll(Arrays.asList(blocks));
    }

    public void addBlockTagToWhitelist(BlockTags... blockTags) {
        tagWhitelist.addAll(Arrays.asList(blockTags));
    }

    public void addBlockTagToBlacklist(BlockTags... blockTags) {
        tagBlackList.addAll(Arrays.asList(blockTags));
    }

    public boolean isInWhitelist(Block block) {
        return whitelist.contains(block);
    }

    public boolean isInBlacklist(Block block) {
        return blacklist.contains(block);
    }

    public boolean isInWhitelist(BlockTags blockTag) {
        return tagWhitelist.contains(blockTag);
    }

    public boolean isInBlacklist(BlockTags blockTag) {
        return tagBlackList.contains(blockTag);
    }

    public boolean shouldIgnoreBlock(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        if (isInBlacklist(block)) {
            return false;
        }
        if (isInWhitelist(block)) {
            return true;
        }
        return true;
    }
}

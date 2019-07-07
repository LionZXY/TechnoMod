package ru.glitchless.tpmod.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.server.command.TextComponentHelper;
import ru.glitchless.tpmod.config.DimensionBlockPos;
import ru.glitchless.tpmod.config.HomeStorage;
import ru.glitchless.tpmod.items.HomeTeleportationItem;

public class HomeBlock extends Block {
    public static final PropertyEnum<HomeBlockEnum> PROPERTYSTATE = PropertyEnum.create("activatestate", HomeBlockEnum.class);

    public HomeBlock() {
        super(Material.IRON);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setHardness(3.0F);
        this.setResistance(8.0F);
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        final ItemStack stack = playerIn.getHeldItem(EnumHand.MAIN_HAND);
        final boolean isValid = stack != null && !stack.isEmpty() && stack.getItem() instanceof HomeTeleportationItem;
        final boolean isActivate = state.getProperties().get(PROPERTYSTATE) == HomeBlockEnum.ACTIVATE;

        if (worldIn.isRemote) {
            return true;
        }

        if (!isValid && !isActivate) {
            playerIn.sendMessage(TextComponentHelper.createComponentTranslation(playerIn, "tpmod.homeset_needitem_text"));
            return false;
        }

        if (isValid && !isActivate) {
            stack.shrink(1);
            setHome(worldIn, playerIn, pos, true);
        } else if (isValid) {
            playerIn.sendMessage(TextComponentHelper.createComponentTranslation(playerIn, "tpmod.homeset_already_text"));
        } else {
            setHome(worldIn, playerIn, pos, false);
        }

        invalidateState(worldIn, pos);
        return true;
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        onBlockDestroy(worldIn, pos);
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        onBlockDestroy(worldIn, pos);
    }

    private void invalidateState(World world, BlockPos pos) {
        final HomeStorage homeStorage = HomeStorage.getInstance(world);
        final String playerId = homeStorage.getHomeByPos(new DimensionBlockPos(pos, world));
        if (playerId == null) {
            world.setBlockState(pos, getDefaultState().withProperty(PROPERTYSTATE, HomeBlockEnum.DISACTIVATE), 3);
        } else {
            world.setBlockState(pos, getDefaultState().withProperty(PROPERTYSTATE, HomeBlockEnum.ACTIVATE), 3);
        }
    }

    private void setHome(World world, EntityPlayer player, BlockPos pos, boolean homeActive) {
        final HomeStorage homeStorage = HomeStorage.getInstance(world);
        final String playerKey = player.getGameProfile().getId().toString();
        final String homeForPlayer = homeStorage.getHomeByPos(new DimensionBlockPos(pos, world));
        final boolean isAllow = homeForPlayer == null || homeForPlayer.equals(playerKey);
        if (!isAllow) {
            player.sendMessage(TextComponentHelper.createComponentTranslation(player, "tpmod.homeset_allow_error_text"));
            return;
        }
        if (homeActive) {
            player.sendMessage(TextComponentHelper.createComponentTranslation(player, "tpmod.homeset_done_text"));
            homeStorage.setHome(player, new DimensionBlockPos(pos, world));
            return;
        }
        player.sendMessage(TextComponentHelper.createComponentTranslation(player, "tpmod.homeset_clear_text"));
        homeStorage.setHome(player, null);
    }

    private void onBlockDestroy(World world, BlockPos pos) {
        final HomeStorage homeStorage = HomeStorage.getInstance(world);
        final DimensionBlockPos dimPos = new DimensionBlockPos(pos, world);
        final String key = homeStorage.getHomeByPos(dimPos);
        if (key != null) {
            homeStorage.setHome(key, null);
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PROPERTYSTATE).getId();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PROPERTYSTATE);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 1));
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        if (blockState.getPropertyKeys().contains(PROPERTYSTATE) &&
                blockState.getValue(PROPERTYSTATE) == HomeBlockEnum.ACTIVATE) {
            return 15;
        } else {
            return 1;
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        HomeBlockEnum homeBlockEnum = HomeBlockEnum.DISACTIVATE;
        if (meta == 1) {
            homeBlockEnum = HomeBlockEnum.ACTIVATE;
        }
        return getDefaultState().withProperty(PROPERTYSTATE, homeBlockEnum);
    }
}

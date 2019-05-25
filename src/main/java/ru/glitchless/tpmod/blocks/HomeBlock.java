package ru.glitchless.tpmod.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.glitchless.tpmod.TpMod;
import ru.glitchless.tpmod.config.HomeStorage;
import ru.glitchless.tpmod.items.HomeTeleportationItem;

import javax.annotation.Nullable;

public class HomeBlock extends Block {
    public static final PropertyEnum<HomeBlockEnum> PROPERTYSTATE = PropertyEnum.create("activatestate", HomeBlockEnum.class);

    public HomeBlock() {
        super(Material.IRON);
        this.setCreativeTab(CreativeTabs.MISC);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public boolean isOpaqueCube(IBlockState iBlockState) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState iBlockState) {
        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new HomeData();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 1));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        HomeBlockEnum homeBlockEnum = HomeBlockEnum.DISACTIVATE;
        if (meta == 1) {
            homeBlockEnum = HomeBlockEnum.ACTIVATE;
        }
        return getDefaultState().withProperty(PROPERTYSTATE, homeBlockEnum);
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
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing blockFaceClickedOn, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (!(tileEntity instanceof HomeData)) {
            return getDefaultState().withProperty(PROPERTYSTATE, HomeBlockEnum.DISACTIVATE);
        }
        HomeData homeData = (HomeData) tileEntity;

        if (homeData.getUserAssign() != null && homeData.getUserAssign().length() > 0) {
            return getDefaultState().withProperty(PROPERTYSTATE, HomeBlockEnum.ACTIVATE);
        }

        return this.getDefaultState().withProperty(PROPERTYSTATE, HomeBlockEnum.DISACTIVATE);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return false;
        }
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (!(tileEntity instanceof HomeData)) {
            return false;
        }
        HomeData homeData = (HomeData) tileEntity;

        ItemStack stack = playerIn.getHeldItem(hand);

        if (playerIn.isSneaking() && stack == ItemStack.EMPTY) {
            homeData.setUser(null);
            HomeStorage homeStorage = TpMod.getInstance().getHomeStorage();
            BlockPos homePos = homeStorage.getHome(homeData.getUserAssign());
            if (homePos != null && homePos.equals(pos)) {
                homeStorage.setHome(homeData.getUserAssign(), null);
            }
            playerIn.sendMessage(new TextComponentString(I18n.format("tpmod.homeset_clear_text")));
        }

        if (homeData.getUserAssign() != null && homeData.getUserAssign().length() > 0) {
            playerIn.sendMessage(new TextComponentString(I18n.format("tpmod.homeset_already_text")));
            return true;
        }

        if (!(stack.getItem() instanceof HomeTeleportationItem)) {
            return true;
        }

        if (stack.getCount() == 1) {
            stack = ItemStack.EMPTY;
        } else {
            stack.setCount(stack.getCount() - 1);
        }
        playerIn.setHeldItem(hand, stack);

        homeData.setUser(playerIn);
        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ());
        TpMod.getInstance().getHomeStorage().setHome(playerIn, blockPos);
        playerIn.sendMessage(new TextComponentString(I18n.format("tpmod.homeset_text", pos.getX(), pos.getY(), pos.getY())));

        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (state.getValue(PROPERTYSTATE).getId() == HomeBlockEnum.ACTIVATE.getId()) {

        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.isRemote) {
            return;
        }
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (!(tileEntity instanceof HomeData)) {
            return;
        }
        HomeData data = (HomeData) tileEntity;
        HomeStorage homeStorage = TpMod.getInstance().getHomeStorage();

        if (data.getUserAssign() != null && data.getUserAssign().length() > 0) {
            BlockPos homePos = homeStorage.getHome(data.getUserAssign());
            if (homePos != null && homePos.equals(pos)) {
                homeStorage.setHome(data.getUserAssign(), null);
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}

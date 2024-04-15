package cn.snowskystudio.crystallography.blocks.custom;

import cn.snowskystudio.crystallography.blocks.entity.CrystallizerBlockEntity;
import cn.snowskystudio.crystallography.blocks.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class CrystallizerBlock extends BaseEntityBlock {
    public CrystallizerBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        if (p_60515_.getBlock() != p_60518_.getBlock()) {
            BlockEntity blockEntity = p_60516_.getBlockEntity(p_60517_);
            if (blockEntity instanceof CrystallizerBlockEntity) {
                ((CrystallizerBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (!p_60504_.isClientSide()) {
            BlockEntity entity = p_60504_.getBlockEntity(p_60505_);
            if(entity instanceof CrystallizerBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)p_60506_), (CrystallizerBlockEntity)entity, p_60505_);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new CrystallizerBlockEntity(p_153215_, p_153216_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        if(p_153212_.isClientSide()) {
            return null;
        }
        return createTickerHelper(p_153214_, ModBlockEntities.CRYSTALLIZER_BE.get(),
                ((p_155253_, p_155254_, p_155255_, p_155256_) -> p_155256_.tick(p_155253_, p_155254_, p_155255_)));
    }
}

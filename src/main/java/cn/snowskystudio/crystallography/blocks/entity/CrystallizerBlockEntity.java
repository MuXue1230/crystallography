package cn.snowskystudio.crystallography.blocks.entity;

import cn.snowskystudio.crystallography.recipe.CrystallizerRecipe;
import cn.snowskystudio.crystallography.screen.CrystallizerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CrystallizerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(10);

    private static final int INPUT_SLOTS = 0;
    private static final int OUTPUT_SLOT = 9;


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 500;

    public CrystallizerBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.CRYSTALLIZER_BE.get(), p_155229_, p_155230_);
        this.data = new ContainerData() {
            @Override
            public int get(int p_39284_) {
                return switch (p_39284_) {
                    case 0 -> CrystallizerBlockEntity.this.progress;
                    case 1 -> CrystallizerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int p_39285_, int p_39286_) {
                switch (p_39285_) {
                    case 0 -> CrystallizerBlockEntity.this.progress = p_39286_;
                    case 1 -> CrystallizerBlockEntity.this.maxProgress = p_39286_;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0;i < itemHandler.getSlots();i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.crystallography.crystallizer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new CrystallizerMenu(p_39954_, p_39955_, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        p_187471_.put("crystallizer.inventory", itemHandler.serializeNBT());
        p_187471_.putInt("crystallizer.progress", progress);

        super.saveAdditional(p_187471_);
    }

    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);

        itemHandler.deserializeNBT(p_155245_.getCompound("crystallizer.inventory"));
        progress = p_155245_.getInt("crystallizer.progress");
    }

    public void tick(Level p_155253_, BlockPos p_155254_, BlockState p_155255_) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(p_155253_, p_155254_, p_155255_);

            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<CrystallizerRecipe> recipe = getCrrectRcipe();
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        this.itemHandler.extractItem(INPUT_SLOTS, 1, false);
        this.itemHandler.extractItem(INPUT_SLOTS + 1, 1, false);
        this.itemHandler.extractItem(INPUT_SLOTS + 2, 1, false);
        this.itemHandler.extractItem(INPUT_SLOTS + 3, 1, false);
        this.itemHandler.extractItem(INPUT_SLOTS + 4, 1, false);
        this.itemHandler.extractItem(INPUT_SLOTS + 5, 1, false);
        this.itemHandler.extractItem(INPUT_SLOTS + 6, 1, false);
        this.itemHandler.extractItem(INPUT_SLOTS + 7, 1, false);
        this.itemHandler.extractItem(INPUT_SLOTS + 8, 1, false);
        this.itemHandler.extractItem(INPUT_SLOTS + 9, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private Optional<CrystallizerRecipe> getCrrectRcipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(CrystallizerRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<CrystallizerRecipe> recipe = getCrrectRcipe();

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
}

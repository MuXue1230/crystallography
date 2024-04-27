package cn.snowskystudio.crystallography.blocks.entity;

import cn.snowskystudio.crystallography.recipe.PressMachineRecipe;
import cn.snowskystudio.crystallography.screen.PressMachineMenu;
import com.mojang.logging.LogUtils;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PressMachineBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(5);

    private static final int INPUT_SLOT1 = 0;
    private static final int INPUT_SLOT2 = 1;
    private static final int INPUT_SLOT3 = 3;
    private static final int OUTPUT_SLOT1 = 2;
    private static final int OUTPUT_SLOT2 = 4;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;
    private int fireRest = 0;
    private int maxFireRest = 0;

    public PressMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PRESS_MACHINE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> PressMachineBlockEntity.this.progress;
                    case 1 -> PressMachineBlockEntity.this.maxProgress;
                    case 2 -> PressMachineBlockEntity.this.fireRest;
                    case 3 -> PressMachineBlockEntity.this.maxFireRest;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> PressMachineBlockEntity.this.progress = pValue;
                    case 1 -> PressMachineBlockEntity.this.maxProgress = pValue;
                    case 2 -> PressMachineBlockEntity.this.fireRest = pValue;
                    case 3 -> PressMachineBlockEntity.this.maxFireRest = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
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

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0;i < itemHandler.getSlots();i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
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

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.crystallography.press_machine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PressMachineMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("press_machine.inventory", itemHandler.serializeNBT());
        pTag.putInt("press_machine.progress", progress);
        pTag.putInt("press_machine.fireRest", fireRest);
        pTag.putInt("press_machine.maxFireRest", maxFireRest);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        itemHandler.deserializeNBT(pTag.getCompound("press_machine.inventory"));
        progress = pTag.getInt("press_machine.progress");
        fireRest = pTag.getInt("press_machine.fireRest");
        maxFireRest = pTag.getInt("press_machine.maxFireRest");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe() && this.fireRest != 0) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            if(hasRecipe() &&   this.fireRest == 0) {
                if (this.itemHandler.getStackInSlot(INPUT_SLOT3).isEmpty())
                    subProgress();
                else {
                    if (FurnaceBlockEntity.isFuel(this.itemHandler.getStackInSlot(INPUT_SLOT3))) {
                        this.itemHandler.extractItem(INPUT_SLOT3, 1, false);
                        this.fireRest = ForgeHooks.getBurnTime(this.itemHandler.getStackInSlot(INPUT_SLOT3), null);
                        this.maxFireRest = ForgeHooks.getBurnTime(this.itemHandler.getStackInSlot(INPUT_SLOT3), null);
                    }
                }
            }
            else
                resetProgress();
        }
        // Handle Fire
        fireTick();
        LogUtils.getLogger().debug("FIREREST: "+ fireRest);
    }

    private void fireTick() {
        if (this.fireRest != 0) {
            this.fireRest--;
            if (this.fireRest == 0) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT2, new ItemStack(Items.STICK,
                        this.itemHandler.getStackInSlot(OUTPUT_SLOT2).getCount() + 1));
            }
        }
    }

    private void subProgress() {
        progress -= 15;
        if (progress <= 0)
            resetProgress();
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<PressMachineRecipe> recipe = getCrrectRcipe();
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        this.itemHandler.extractItem(INPUT_SLOT1, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT2, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT1, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT1).getCount() + result.getCount()));
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<PressMachineRecipe> recipe = getCrrectRcipe();

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<PressMachineRecipe> getCrrectRcipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(PressMachineRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT1).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT1).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT1).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT1).getMaxStackSize();
    }
}

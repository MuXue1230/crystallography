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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

import static cn.snowskystudio.crystallography.blocks.custom.CrystallizerBlock.FACING;

public class CrystallizerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(12);

    private static final int INPUT_SLOTS = 0;
    private static final int OUTPUT_SLOT = 9;
    private static final int SPECIAL_SLOTS = 10;


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 1000;
    private int temperature = 0;
    private int water = 0;
    private int isIncreasingTemperature = 0;

    public CrystallizerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CRYSTALLIZER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> CrystallizerBlockEntity.this.progress;
                    case 1 -> CrystallizerBlockEntity.this.maxProgress;
                    case 2 -> CrystallizerBlockEntity.this.temperature;
                    case 3 -> CrystallizerBlockEntity.this.water;
                    case 4 -> CrystallizerBlockEntity.this.isIncreasingTemperature;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> CrystallizerBlockEntity.this.progress = pValue;
                    case 1 -> CrystallizerBlockEntity.this.maxProgress = pValue;
                    case 2 -> CrystallizerBlockEntity.this.temperature = pValue;
                    case 3 -> CrystallizerBlockEntity.this.water = pValue;
                    case 4 -> CrystallizerBlockEntity.this.isIncreasingTemperature = pValue;
                }
            }

            @Override
            public int getCount() {
                return 5;
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
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CrystallizerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("crystallizer.inventory", itemHandler.serializeNBT());
        pTag.putInt("crystallizer.progress", progress);
        pTag.putInt("crystallizer.temp", temperature);
        pTag.putInt("crystallizer.water", water);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        itemHandler.deserializeNBT(pTag.getCompound("crystallizer.inventory"));
        progress = pTag.getInt("crystallizer.progress");
        temperature = pTag.getInt("crystallizer.temp");
        water = pTag.getInt("crystallizer.water");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        tickTemp();
        tickWater();
        if(hasRecipe() && doesTemperatureValidate() && doesWaterValidate()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void tickWater() {
        if (this.water < 5000000 && !this.itemHandler.getStackInSlot(SPECIAL_SLOTS + 1).isEmpty()) {
            this.itemHandler.extractItem(SPECIAL_SLOTS + 1, 1, false);
            this.itemHandler.setStackInSlot(SPECIAL_SLOTS + 1, new ItemStack(Items.WATER_BUCKET));
            this.water += 1000;
        }
    }

    private void tickTemp() {
        if (hasRecipe() && !doesTemperatureValidate()) {
            if (!this.itemHandler.getStackInSlot(SPECIAL_SLOTS).isEmpty() && FurnaceBlockEntity.isFuel(this.itemHandler.getStackInSlot(SPECIAL_SLOTS)) && (this.temperature + (isIncreasingTemperature / 100)) < 10000) {
                this.isIncreasingTemperature = ForgeHooks.getBurnTime(this.itemHandler.getStackInSlot(SPECIAL_SLOTS), null);
                this.itemHandler.extractItem(SPECIAL_SLOTS, 1, false);
            }
        }
        if (isIncreasingTemperature > 0) {
            if (this.temperature < 10000) {
                this.temperature += isIncreasingTemperature / 100;
            }
            this.isIncreasingTemperature -= 100;
        }
        else if (this.temperature > 0) {
            this.temperature -= 50;
        }
    }

    private boolean doesWaterValidate() {
        return this.water >= 1000;
    }

    private boolean doesTemperatureValidate() {
        return getCrrectRcipe().get().getReqTemp() <= this.temperature;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<CrystallizerRecipe> recipe = getCrrectRcipe();
        ItemStack result = recipe.get().getResultItem(null);

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

        this.water -= 1000;
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

        ItemStack result = recipe.get().getResultItem(null);

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
}

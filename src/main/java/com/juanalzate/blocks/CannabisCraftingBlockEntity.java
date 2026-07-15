package com.juanalzate.blocks;

import com.juanalzate.component.BluntData;
import com.juanalzate.component.BluntWrapType;
import com.juanalzate.component.ModComponents;
import com.juanalzate.items.CannabisNuggetItem;
import com.juanalzate.items.ModItems;
import com.juanalzate.screen.CannabisCraftingScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CannabisCraftingBlockEntity extends BlockEntity implements Container, MenuProvider {
    private static final int CRAFT_TICKS = 200;
    private final NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    private int craftingTicks = 0;
    private boolean isCrafting = false;

    private final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> craftingTicks;
                case 1 -> CRAFT_TICKS;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) craftingTicks = value;
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public CannabisCraftingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CANNABIS_CRAFTING, pos, state);
    }

    public ContainerData getPropertyDelegate() {
        return propertyDelegate;
    }

    public static void tick(Level world, BlockPos pos, BlockState state, CannabisCraftingBlockEntity be) {
        if (world.isClientSide()) return;

        ItemStack recipeResult = be.resolveRecipe(be.items.get(0), be.items.get(1));

        if (recipeResult.isEmpty() || !be.canAcceptOutput(recipeResult)) {
            if (be.isCrafting) {
                be.isCrafting = false;
                be.craftingTicks = 0;
                be.setChanged();
            }
            return;
        }

        be.isCrafting = true;
        be.craftingTicks++;
        be.setChanged();

        if (be.craftingTicks >= CRAFT_TICKS) {
            be.items.get(0).shrink(1);
            be.items.get(1).shrink(1);

            ItemStack currentOutput = be.items.get(2);
            if (currentOutput.isEmpty()) {
                be.items.set(2, recipeResult.copy());
            } else {
                currentOutput.grow(1);
            }

            be.craftingTicks = 0;
            be.setChanged();
        }
    }

    private boolean canAcceptOutput(ItemStack result) {
        ItemStack current = items.get(2);
        if (current.isEmpty()) return true;
        if (!ItemStack.isSameItemSameComponents(current, result)) return false;
        return current.getCount() + 1 <= current.getMaxStackSize();
    }

    private ItemStack resolveRecipe(ItemStack a, ItemStack b) {
        ItemStack result = tryRecipe(a, b);
        if (result.isEmpty()) result = tryRecipe(b, a);
        return result;
    }

    private ItemStack tryRecipe(ItemStack first, ItemStack second) {
        if (first.isEmpty() || second.isEmpty()) return ItemStack.EMPTY;

        if (first.is(Items.PAPER)) {
            if (second.is(ItemTags.LEAVES)) {
                return new ItemStack(ModItems.BLUNT_WRAP_NATURAL);
            }
            if (second.is(Items.APPLE)) {
                return new ItemStack(ModItems.BLUNT_WRAP_APPLE);
            }
            if (second.is(Items.MELON_SLICE)) {
                return new ItemStack(ModItems.BLUNT_WRAP_WATERMELON);
            }
            if (second.is(Items.COCOA_BEANS)) {
                return new ItemStack(ModItems.BLUNT_WRAP_CHOCOLATE);
            }
            if (second.is(Items.HONEY_BOTTLE)) {
                return new ItemStack(ModItems.BLUNT_WRAP_HONEY);
            }
            if (second.is(Items.GOLD_BLOCK)) {
                return new ItemStack(ModItems.BLUNT_WRAP_GOLDEN);
            }
        }

        if (first.getItem() instanceof CannabisNuggetItem nuggetItem) {
            if (!first.getOrDefault(ModComponents.PROCESSED, false)) return ItemStack.EMPTY;

            BluntWrapType wrapType = getWrapType(second);
            if (wrapType == null) return ItemStack.EMPTY;

            ItemStack blunt = new ItemStack(ModItems.BLUNT);
            blunt.set(ModComponents.BLUNT_DATA, BluntData.from(wrapType, nuggetItem.getQuality()));
            return blunt;
        }

        return ItemStack.EMPTY;
    }

    private @Nullable BluntWrapType getWrapType(ItemStack stack) {
        if (stack.is(ModItems.BLUNT_WRAP_NATURAL)) return BluntWrapType.NATURAL;
        if (stack.is(ModItems.BLUNT_WRAP_APPLE)) return BluntWrapType.APPLE;
        if (stack.is(ModItems.BLUNT_WRAP_WATERMELON)) return BluntWrapType.WATERMELON;
        if (stack.is(ModItems.BLUNT_WRAP_CHOCOLATE)) return BluntWrapType.CHOCOLATE;
        if (stack.is(ModItems.BLUNT_WRAP_HONEY)) return BluntWrapType.HONEY;
        if (stack.is(ModItems.BLUNT_WRAP_GOLDEN)) return BluntWrapType.GOLDEN;
        return null;
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(items, slot, amount);
        if (!stack.isEmpty()) setChanged();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (slot == 0 || slot == 1) {
            craftingTicks = 0;
            isCrafting = false;
        }
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(
                worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("CraftingTicks", craftingTicks);
        tag.putBoolean("IsCrafting", isCrafting);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, items, registries);
        craftingTicks = tag.getInt("CraftingTicks");
        isCrafting = tag.getBoolean("IsCrafting");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.weirdsustancesmod.cannabis_crafting_table");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new CannabisCraftingScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}

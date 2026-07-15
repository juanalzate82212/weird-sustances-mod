package com.juanalzate.screen;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CannabisCraftingScreenHandler extends AbstractContainerMenu {
    private final Container inventory;
    private final ContainerData propertyDelegate;

    public CannabisCraftingScreenHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(ModScreenHandlers.CANNABIS_CRAFTING, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        checkContainerSize(inventory, 3);
        inventory.startOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 37, 35));
        this.addSlot(new Slot(inventory, 1, 71, 35));
        this.addSlot(new Slot(inventory, 2, 121, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory,
                        col + row * 9 + 9,
                        8 + col * 18,
                        84 + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

        this.addDataSlots(propertyDelegate);
    }

    public CannabisCraftingScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(3), new SimpleContainerData(2));
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    public float getProgress() {
        int current = propertyDelegate.get(0);
        int max = propertyDelegate.get(1);
        if (max <= 0) return 0f;
        return (float) current / max;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        Slot slot = this.slots.get(slotIndex);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();

        if (slotIndex == 2) {
            if (!this.moveItemStackTo(stack, 3, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (slotIndex < 2) {
            // Input slots → player inventory
            if (!this.moveItemStackTo(stack, 3, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            // Player inventory → input slots (only slots 0 and 1)
            if (!this.moveItemStackTo(stack, 0, 2, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return original;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
    }


}

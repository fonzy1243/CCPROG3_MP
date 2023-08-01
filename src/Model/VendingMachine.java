package Model;

import java.util.*;

/**
 * <h2>Vending Machine</h2>
 * A Vending Machine consists of item slots that act as an interface for the user to know what is
 * available for purchase. Each slot is mapped to a specific item, and it is assumed that the items stored
 * in the slots are unique. A machine should be able to receive payment rom the user in different denominations,
 * dispense the item based on user choice, and dispense change. Maintenance features include restocking/stocking items,
 * setting item price, withdrawing/depositing money, and printing a summary of transactions.
 */
public class VendingMachine
{
	private static final int itemLimit = 10;

	protected final Slot[] slots;

	private final Denomination denominations;

	private final TransactionTracker transactionTracker;

	public VendingMachine(int slotCount)
	{
		slots = new Slot[slotCount];

		for (int i = 0; i < slotCount; i++)
		{
			slots[i] = new Slot();
		}

		denominations = new Denomination();

		transactionTracker = new TransactionTracker(slots);
	}

	/**
	 * Get the transaction tracker
	 *
	 * @return transaction tracker object
	 */
	public TransactionTracker getTransactionTracker()
	{
		return transactionTracker;
	}

	/**
	 * Get the denominations used in the machine
	 *
	 * @return denomination object
	 */
	public Denomination getDenominations()
	{
		return denominations;
	}

	/**
	 * Get the array of slots in the machine
	 *
	 * @return slot array
	 */
	public Slot[] getSlots()
	{
		return slots;
	}

	/**
	 * Get the current quantity of an item
	 *
	 * @param itemName item to be searched
	 * @return item quantity
	 */
	public int getAvailability(String itemName)
	{
		int count = 0;

		for (Slot slot : slots)
		{
			if (slot.getItemList().size() > 0 &&
			    slot.getItemList().get(0).getName().equals(itemName))
			{
				count += slot.getItemList().size();
			}
		}

		return count;
	}

	/**
	 * Adds an item to the slot
	 *
	 * @param item      item to be added to a slot.
	 * @param slotIndex index of slot to be added to.
	 * @param quantity  amount of items to be added to the slot.
	 * @return true if successful, false if error occurred
	 */
	public boolean addItemToSlot(Item item, int slotIndex, int quantity)
	{
		for (Slot slot : slots)
		{
			if (slot.getItemList().size() > 0 &&
			    slot.getItemList().get(0).getName().equals(item.getName()) &&
			    slot != slots[slotIndex])
			{
				return false;
			}
		}

		if (slots[slotIndex].getItemList().size() > 0 &&
		    !item.getName().equals(slots[slotIndex].getItemList().get(0).getName()))
		{
			throw new IllegalStateException("Different item found in slot. Please submit a bug report.");
		}

		if (slots[slotIndex].getItemList().size() + quantity > itemLimit)
		{
			return false;
		}

		for (int i = 0; i < quantity; i++)
		{
			slots[slotIndex].getItemList().add(item);
		}

		return true;
	}

	/**
	 * Get the list of coins/bills used for change
	 *
	 * @param changeValue value of change used for calculating coins/bills returned
	 * @return list of change coins/bills
	 */
	private List<Integer> getChange(int changeValue) {
		List<Integer> selectedCoins = new ArrayList<>();
		List<Integer> currentCoins = new ArrayList<>();
		if (backtrack(changeValue, currentCoins, selectedCoins)) {
			// Update the stock of denominations based on the selected coins
			for (int coin : selectedCoins) {
				denominations.removeDenomination(coin, 1);
			}
		}
		return selectedCoins;
	}

	/**
	 * Backtracking algorithm to find the minimum number of coins/bills to return as change
	 *
	 * @param remainingValue remaining value to be returned as change
	 * @param currentCoins   current list of coins/bills
	 * @param selectedCoins  selected list of coins/bills
	 * @return true if change value is reached, false otherwise
	 */
	private boolean backtrack(int remainingValue, List<Integer> currentCoins, List<Integer> selectedCoins) {
		if (remainingValue == 0) {
			// Base case: Change value reached, check if it's better than the current selected coins
			if (selectedCoins.isEmpty() || currentCoins.size() < selectedCoins.size()) {
				selectedCoins.clear();
				selectedCoins.addAll(currentCoins);
			}
			return true;
		}

		for (int coin : denominations.getNonEmptyDenominations()) {
			int stock = denominations.getDenominationStock().get(coin);
			if (stock > 0 && remainingValue >= coin) {
				// Choose the coin and decrement its stock
				currentCoins.add(coin);
				denominations.removeDenomination(coin, 1);

				// Continue exploring with the remaining value
				if (backtrack(remainingValue - coin, currentCoins, selectedCoins)) {
					return true;
				}

				// Backtrack: Undo the choice
				currentCoins.remove(currentCoins.size() - 1);
				denominations.addDenomination(coin, 1);
			}
		}

		return false;
	}

	/**
	 * Dispense an item from a slot.
	 *
	 * @param slotIndex     slot index of the item to be dispensed.
	 * @param paymentAmount amount paid by the buyer.
	 * @return list of change coins/bills
	 */
	public List<Integer> dispenseItem(int slotIndex, int paymentAmount)
	{
		if (slots[slotIndex].getItemList().size() == 0)
		{
			// Error handling here for if no items in slot
			// Handle with exception
			// The action event should include slot size checking, so if this exception is thrown there is an error.
			throw new IllegalStateException("Empty slot dispense attempt. Please submit a bug report.");
		}

		if (slots[slotIndex].getItemList().get(0).getPrice() > paymentAmount)
		{
			// Error handling here for insufficient payment
			return null;
		}

		for (int denom : denominations.getDenominationList())
		{
			if (denominations.isDenomEmpty(denom))
			{
				return Collections.emptyList();
			}
		}

		List<Integer> coinChangeValues = getChange(paymentAmount -
		                                           slots[slotIndex].getItemList().get(0).getPrice());

		slots[slotIndex].getItemList().remove(0);

		return coinChangeValues;
	}

	/**
	 * Withdraws money from the machine.
	 *
	 * @param withdrawAmount amount to be withdrawn
	 * @return list of coins/bills received from the machine
	 */
	public List<Integer> withdrawMoney(int withdrawAmount)
	{
		return getChange(withdrawAmount);
	}

	/**
	 * Get the list of coins/bills used for change when buying ramen.
	 *
	 * @param paymentAmount amount paid by the user
	 * @return list of coins/bills received from the machine
	 */
	public List<Integer> getRamenChange(int paymentAmount)
	{
		return getChange(paymentAmount);
	}
}


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
	private static final int itemLimit = 69;

	private final Slot[] slots;

	private final Denomination denominations;

	public VendingMachine(int slotCount)
	{
		slots = new Slot[slotCount];

		for (int i = 0; i < slotCount; i++)
		{
			slots[i] = new Slot();
		}

		denominations = new Denomination();
	}

	/**
	 * Get the denominations used in the machine
	 * @return denomination object
	 */
	public Denomination getDenominations()
	{
		return denominations;
	}

	/**
	 * Get the array of slots in the machine
	 * @return slot array
	 */
	public Slot[] getSlots()
	{
		return slots;
	}

	/**
	 * Get the current quantity of an item
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
	 * @param item item to be added to a slot.
	 * @param slotIndex index of slot to be added to.
	 * @param quantity amount of items to be added to the slot.
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

		int totalItemCount = 0;

		for (Slot slot : slots)
		{
			totalItemCount += slot.getItemList().size();
		}

		if (totalItemCount + quantity > itemLimit)
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
	 * @param changeValue value of change used for calculating coins/bills returned
	 * @return list of change coins/bills
	 */
	private List<Integer> getChange(int changeValue)
	{
		int[] dp = new int[changeValue + 1];
		Arrays.fill(dp, Integer.MAX_VALUE);

		int[] coinsUsed = new int[changeValue + 1];

		// base case
		dp[0] = 0;

		for (int i = 1; i < changeValue + 1; i++)
		{
			for (int coin : denominations.getDenominationList())
			{
				if (i - coin >= 0 && dp[i - coin] != Integer.MAX_VALUE && dp[i - coin] + 1 < dp[i] &&
				    denominations.getDenominationStock().get(coin) > 0)
				{
					dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
					coinsUsed[i] = coin;
				}
			}
		}

		if (dp[changeValue] != Integer.MAX_VALUE)
		{
			List<Integer> selectedCoins = new ArrayList<>();
			int value = changeValue;

			while (value > 0)
			{
				selectedCoins.add(coinsUsed[value]);
				value -= coinsUsed[value];
			}

			for (int coin : selectedCoins)
			{
				denominations.removeDenomination(coin, 1);
			}

			return selectedCoins;
		}
		else
		{
			// Error handling here
			return Collections.emptyList();
		}
	}

	/**
	 * Dispense an item from a slot.
	 * @param slotIndex slot index of the item to be dispensed.
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

		List<Integer> coinChangeValues = getChange(paymentAmount -
		                                           slots[slotIndex].getItemList().get(0).getPrice());

		slots[slotIndex].getItemList().remove(0);

		return coinChangeValues;
	}

	/**
	 * Withdraws money from the machine.
	 * @param withdrawAmount amount to be withdrawn
	 * @return list of coins/bills received from the machine
	 */
	public List<Integer> withdrawMoney(int withdrawAmount)
	{
		return getChange(withdrawAmount);
	}

	// for testing change
	public void printChange(List<Integer> coinChangeValues)
	{
		int realChange = 0;

		for (int coinValue : coinChangeValues)
		{
			realChange += coinValue;
			System.out.print(((float) coinValue / 100) + ", ");
		}
		System.out.println();

		System.out.println("Real change = " + ((float) realChange / 100));
	}
}


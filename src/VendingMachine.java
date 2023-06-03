import java.util.LinkedList;
import java.util.List;

public class VendingMachine
{
	private static final int itemLimit = 69;

	private final Slot[] slots;

	private Denomination denominations;

	public VendingMachine(int slotCount)
	{
		slots = new Slot[slotCount];

		for (int i = 0; i < slotCount; i++)
		{
			slots[i] = new Slot();
		}

		denominations = new Denomination();
	}

	public Slot[] getSlots()
	{
		return slots;
	}

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
	 * #NEVERNEST
	 * see the guard clauses technique in its full glory
	 * @param item item to be added to a slot.
	 * @param slotNumber index of slot to be added to.
	 * @param quantity amount of items to be added to the slot.
	 */
	public void addItemToSlot(Item item, int slotNumber, int quantity)
	{
		for (Slot slot : slots)
		{
			if (slot.getItemList().size() > 0 &&
			    slot.getItemList().get(0).getName().equals(item.getName()) &&
			    slot != slots[slotNumber])
			{
				// Error handling here
				return;
			}
		}

		if (slots[slotNumber].getItemList().size() > 0 &&
		    !item.getName().equals(slots[slotNumber].getItemList().get(0).getName()))
		{
			// Error handling here
			return;
		}

		int totalItemCount = 0;

		for (Slot slot : slots)
		{
			totalItemCount += slot.getItemList().size();
		}

		if (totalItemCount + quantity > itemLimit)
		{
			// Error handling here
			return;
		}

		for (int i = 0; i < quantity; i++)
		{
			slots[slotNumber].getItemList().add(item);
		}
	}

	// TODO: greedy algorithm for coin change or dp whatever
	// https://personal.utdallas.edu/~sxb027100/cs6363/coin.pdf
	private List<Integer> getChange(int changeValue)
	{
		List<Integer> coinChangeValues = new LinkedList<>();

		while (changeValue > 0)
		{
			for (int value : denominations.getDenominationList())
			{
				if (value <= changeValue)
				{
					changeValue -= value;
					coinChangeValues.add(value);
				}
			}
		}

		return coinChangeValues;
	}

	/**
	 * #NEVERNEST PART 2
	 * @param slotNumber slot number of the item to be dispensed.
	 * @param paymentAmount amount paid by the buyer.
	 */
	public void dispenseItem(int slotNumber, int paymentAmount)
	{
		if (slots[slotNumber].getItemList().size() == 0)
		{
			// Error handling here for if no items in slot
			return;
		}

		if (slots[slotNumber].getItemList().get(0).getPrice() > paymentAmount)
		{
			// Error handling here for insufficient payment
			return;
		}

		List<Integer> coinChangeValues = getChange(paymentAmount -
		                                           slots[slotNumber].getItemList().get(0).getPrice());

		slots[slotNumber].getItemList().remove(0);

		System.out.println("Change = " + ((float) (paymentAmount - slots[slotNumber].getItemList().get(0).getPrice()) /
		                                  100));
		printChange(coinChangeValues);
	}

	// for testing change
	public void printChange(List<Integer> coinChangeValues)
	{
		for (int coinValue : coinChangeValues)
		{
			System.out.print(((float) coinValue / 100) + ", ");
		}
		System.out.println();
	}
}


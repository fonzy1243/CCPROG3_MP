 public class VendingMachine
{
	private static final int itemLimit = 69;

	private final Slot[] slots;

	public VendingMachine(int slotCount)
	{
		slots = new Slot[slotCount];

		for (int i = 0; i < slotCount; i++)
		{
			slots[i] = new Slot();
		}
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

	// TODO: coin change problem w/ dynamic programming
	private void getChange(int paymentAmount)
	{
	}

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

		getChange(paymentAmount);

		slots[slotNumber].getItemList().remove(0);
	}
}


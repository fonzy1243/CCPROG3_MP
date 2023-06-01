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
			if (slot.getItemList().get(0).getName().equals(itemName))
			{
				count += slot.getItemList().size();
			}
		}

		return count;
	}

	public void addItemToSlot(Item item, int slotNumber, int quantity)
	{
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

	public void dispenseItem(int slotNumber)
	{

	}
}


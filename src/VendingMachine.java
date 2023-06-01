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

	public int getAvailability(Item item)
	{
		int count = 0;

		for (Slot slot : slots)
		{
			if (slot.getItemList().get(0).getName().equals(item.getName()))
			{
				count += slot.getItemList().size();
			}
		}

		return count;
	}

	public void addItemToSlot(Item item, int slotNumber, int quantity)
	{
		int totalItemCount = 0;

		for (Slot slot : slots)
		{
			totalItemCount += slot.getItemList().size();
		}

		if (totalItemCount >= itemLimit)
		{
			return;
		}

		slots[slotNumber].addItem(item, quantity);
	}

	public void dispenseItem(int slotNumber)
	{

	}
}


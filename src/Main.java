public class Main
{
	public static void main(String[] args)
	{
		RegularVendingMachine machine1 = new RegularVendingMachine(8);

		Item newItem = new Item("deez nuts", 69699, 69);
		Item reverse = new Item("nuts deez", 9696, 96);
		machine1.addItemToSlot(newItem, 0, 3);
		machine1.addItemToSlot(reverse, 0, 2);

		machine1.getSlots()[0].printSlotItems();
		System.out.println("Deez nuts count: " + machine1.getAvailability("deez nuts"));

		machine1.getSlots()[0].printSlotItems();
		System.out.println("Deez nuts count: " + machine1.getAvailability("deez nuts"));

		machine1.dispenseItem(0, 71325);
	}
}
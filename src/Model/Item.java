package Model;

/**
 * Item is a real-world item that would be contained in a vending machine (e.g. snacks, soda).
 */
public class Item
{
	private final String name;
	private int price;
	private final int calories;

	public Item(String name, int priceInCents, int calories)
	{
		this.name = name;
		this.price = priceInCents;
		this.calories = calories;
	}

	/**
	 * Get the item's name
	 * @return item name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Get the item's price
	 * @return item price
	 */
	public int getPrice()
	{
		return price;
	}

	/**
	 * Set the item's price
	 * @param price new price
	 */
	public void setPrice(int price)
	{
		this.price = price;
	}

	/**
	 * Get the item's calorie count
	 * @return item calorie count
	 */
	public int getCalories()
	{
		return calories;
	}
}

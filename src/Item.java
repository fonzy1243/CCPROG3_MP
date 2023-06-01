public class Item
{
	private final String name;
	private final int price;
	private final int calories;

	public Item(String name, int priceInCents, int calories)
	{
		this.name = name;
		this.price = priceInCents;
		this.calories = calories;
	}
	public String getName()
	{
		return name;
	}
	public int getPrice()
	{
		return price;
	}

	public int getCalories()
	{
		return calories;
	}
}

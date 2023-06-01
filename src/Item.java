public class Item
{
	private String name;
	private int price;
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
}

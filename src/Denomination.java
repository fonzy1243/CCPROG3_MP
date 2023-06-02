import java.util.HashMap;
import java.util.Map;

public class Denomination
{
	private Map<Integer, Integer> denominationStock;

	public Denomination()
	{
		denominationStock = new HashMap<>();

		// Centavo coins
		denominationStock.put(1, 1000);
		denominationStock.put(5, 1000);
		denominationStock.put(25, 1000);

		// Peso bills
		denominationStock.put(100, 1000);
		denominationStock.put(500, 1000);
		denominationStock.put(1000, 1000);

		// Peso bills
		denominationStock.put(2000, 500);
		denominationStock.put(5000, 500);
		denominationStock.put(10000, 500);
		denominationStock.put(50000, 500);
		denominationStock.put(100000, 500);
	}

	public Map<Integer, Integer> getDenominationStock()
	{
		return denominationStock;
	}

	public void addDenomination(int value, int quantity)
	{
		if (!denominationStock.containsKey(value))
		{

			return;
		}
	}
}

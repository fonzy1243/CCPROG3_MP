package Model;

import java.util.*;

/**
 * Denomination handles the different coins and bills used in vending machine transactions.
 * Each coin/bill is represented in integer cents rather than its real value to avoid problems with checking
 * floating-point number equality.
 */
public class Denomination
{
	private final Map<Integer, Integer> denominationStock;
	private final List<Integer> denominationList;

	/**
	 * Initialize the denominations to be used when instantiating a Denomination object.
	 */
	public Denomination()
	{
		denominationStock = new HashMap<>();

		// Centavo coins
		denominationStock.put(1, 501);
		denominationStock.put(5, 500);
		denominationStock.put(10, 500);
		denominationStock.put(25, 500);

		// Peso bills
		denominationStock.put(100, 250);
		denominationStock.put(500, 250);
		denominationStock.put(1000, 250);

		// Peso bills
		denominationStock.put(2000, 100);
		denominationStock.put(5000, 100);
		denominationStock.put(10000, 100);
		denominationStock.put(50000, 100);
		denominationStock.put(100000, 100);

		denominationList = new ArrayList<>(denominationStock.keySet());
		denominationList.sort(Collections.reverseOrder());
	}

	/**
	 * Return the hash map of denominations in stock
	 * @return denomination hash map
	 */
	public Map<Integer, Integer> getDenominationStock()
	{
		return denominationStock;
	}

	/**
	 * Add denomination of a certain value
	 * @param value value of denomination to be added
	 * @param quantity quantity to be added
	 */
	public void addDenomination(int value, int quantity)
	{
		if (!denominationStock.containsKey(value))
		{
			// Error handling for no value found
			return;
		}

		if (quantity <= 0)
		{
			// Error handling for invalid quantity
			return;
		}

		int newDenominationQuantity = denominationStock.get(value) + quantity;

		denominationStock.remove(value);
		denominationStock.put(value, newDenominationQuantity);
	}

	/**
	 * Remove denomination of a certain value
	 * @param value value of denomination to be removed
	 * @param quantity quantity to be removed
	 */
	public void removeDenomination(int value, int quantity)
	{
		if (!denominationStock.containsKey(value))
		{
			// Error handling for no value found
			return;
		}

		if (quantity <= 0)
		{
			// Error handling for invalid quantity
			return;
		}

		if (denominationStock.get(value) - quantity < 0)
		{
			// Error handling for insufficient stock
			return;
		}

		int newDenominationQuantity = denominationStock.get(value) - quantity;

		denominationStock.put(value, newDenominationQuantity);
	}

	/**
	 * Get the list of denominations
	 * @return denomination list
	 */
	public List<Integer> getDenominationList()
	{
		return denominationList;
	}

	public List<Integer> getNonEmptyDenominations()
	{
		List<Integer> nonEmptydenominationList = new LinkedList<>();

		for (int denomination : denominationList)
		{
			if (denominationStock.get(denomination) > 0)
			{
				nonEmptydenominationList.add(denomination);
			}
		}

		return nonEmptydenominationList;
	}

	/**
	 * Calculate the summation of all denominations
	 * @return denomination total
	 */
	public long calculateTotal()
	{
		long total = 0;

		if (denominationStock.isEmpty())
		{
			return total;
		}

		for (Map.Entry<Integer, Integer> entry : denominationStock.entrySet())
		{
			int denomination = entry.getKey();
			int stock = entry.getValue();

			total += (long) denomination * stock;
		}

		return total;
	}

	/**
	 * Check if denomination of a certain value is empty
	 * @param denom value of denomination to be checked
	 * @return true if denomination is empty, false otherwise
	 */
	public boolean isDenomEmpty(int denom)
	{
		return denominationStock.get(denom) == 0;
	}
}

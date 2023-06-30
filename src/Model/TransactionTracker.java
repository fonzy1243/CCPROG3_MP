package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TransactionTracker
{
    private int totalAmountCollected;
    private Slot[] initialSlots;
    private Slot[] currentSlots;

    public TransactionTracker(Slot[] initialSlots)
    {
        this.initialSlots = initialSlots;
    }

    public void setInitialSlots(Slot[] initialSlots)
    {
        this.initialSlots = initialSlots;
    }

    public void setCurrentSlots(Slot[] currentSlots)
    {
        this.currentSlots = currentSlots;
    }

    public int getTotalAmountCollected()
    {
        return totalAmountCollected;
    }

    public void setTotalAmountCollected()
    {
        totalAmountCollected = 0;

        for (int i = 0; i < initialSlots.length; i++)
        {
            if (initialSlots[i].getItemList().size() > 0)
            {
                totalAmountCollected += (initialSlots[i].getItemsCount() - currentSlots[i].getItemsCount()) * initialSlots[i].getItemList().get(0).getPrice();
            }
        }
    }

    public void createTransactionFile()
    {
        File transactionFile = new File("transactions.txt");

        if (transactionFile.exists())
		{
			if (!transactionFile.delete())
			{
				System.out.println("Failed to delete existing file.");
			}
		}

        try
		{
			if (transactionFile.createNewFile())
			{
				System.out.println(transactionFile.getName());
			}
			else
			{
				System.out.println("Failed to create file.");
			}
		} catch (IOException exception)
		{
			System.out.println("Error occurred while creating file.");
			exception.printStackTrace();
		}

        for (int i = 0; i < initialSlots.length; i++)
        {
            try
            {
                FileWriter writer = new FileWriter(transactionFile, true);
                if (initialSlots[i].getItemsCount() > 0)
                {
                    writer.write(initialSlots[i].getItemList().get(0).getName() + ": " + (initialSlots[i].getItemsCount() - currentSlots[i].getItemsCount()) + "\n");
                    writer.close();
                }
            } catch (IOException exception)
            {
                System.out.println("Error occurred while writing to file.");
                exception.printStackTrace();
            }
        }

        try
        {
            FileWriter writer = new FileWriter(transactionFile, true);
            writer.write((float) totalAmountCollected / 100 + "\n");
            writer.close();
        } catch (IOException exception)
        {
            System.out.println("Error occurred while writing to file.");
            exception.printStackTrace();
        }
    }

    // TODO: do i need this... ill see later
    public Slot[] readTransactionFile()
    {
        File transactionFile = new File("transactions.txt");
        Slot[] slots = new Slot[10];

        for (int i = 0; i < slots.length; i++)
        {
            slots[i] = new Slot();
        }

        if (transactionFile.exists())
        {
            try
            {
                java.util.Scanner scanner = new java.util.Scanner(transactionFile);

                for (int i = 0; i < slots.length; i++)
                {
                    if (scanner.hasNextInt())
                    {
                        int count = scanner.nextInt();

                        if (count > 0)
                        {
                            slots[i] = initialSlots[i];

                            while (slots[i].getItemsCount() != count)
                            {
                                slots[i].getItemList().remove(0);
                            }
                        }
                    }
                }

                scanner.close();
            } catch (IOException exception)
            {
                System.out.println("Error occurred while reading file.");
                exception.printStackTrace();
            }
        }
        else
        {
            System.out.println("File does not exist.");
        }

        return slots;
    }
}

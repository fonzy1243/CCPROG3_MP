package Viewer;

import Model.Item;
import Model.Slot;
import Model.SpecialVendingMachine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;

/**
 * StockInterfaceViewer manages the Stock Interface's GUI.
 */
public class StockInterfaceViewer extends MenuViewer
{
	@FXML
	private Button addButton;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField priceTextField;
	@FXML
	private TextField calorieTextField;
	@FXML
	private HBox navButtons;
	@FXML
	private AnchorPane topBar;
	@FXML
	private Button minimizeButton;
	@FXML
	private VBox stockInterfaceVbox;
	@FXML
	private ComboBox<Slot> slotDropdown;

	public StockInterfaceViewer()
	{

	}

	/**
	 * Opens an interface where the user can add items to a slot.
	 */
	public void openStockInterface()
	{
		stockInterfaceVbox.setSpacing(15);

		slotDropdown.getItems().addAll(vendingMachineController.getVendingMachines().getLast().getSlots());
		slotDropdown.setConverter(new StringConverter<>()
		{
			@Override
			public String toString(Slot slot)
			{
				return "Slot " + (slotDropdown.getItems().indexOf(slot) + 1);
			}

			@Override
			public Slot fromString(String s)
			{
				return null;
			}
		});

		moveApp(topBar, stage);
		minimizeButton.setOnAction(actionEvent -> minimizeApp(stage));

		for (Node button : navButtons.getChildren())
		{
			ButtonAnimator.resizeWhenHovered((Button) button);
		}

		navButtons.setSpacing(7);

		addButton.setOnAction(event ->
		{
			if (slotDropdown.getValue() == null)
			{
				openPopup("No slot selected.");
				return;
			}

			int i = 0, slotIndex = -1;
			for (Slot slot : vendingMachineController.getVendingMachines().getLast().getSlots())
			{
				if (slot == slotDropdown.getValue())
				{
					slotIndex = i;
				}

				i++;
			}

			if (slotIndex == -1)
			{
				openPopup("Invalid slot.");
				try
				{
					back(event);
				} catch (IOException e)
				{
					throw new RuntimeException(e);
				}
			}
			else
			{
				addItem(slotIndex);
			}
		});
	}

	/**
	 * Handles adding items to a slot.
	 * @param slotIndex index of slot to be added to
	 */
	private void addItem(int slotIndex)
	{
		String numberString = priceTextField.getText();

		int decimalIndex = numberString.indexOf('.');
		int decimalPlaces = -1;

		if (decimalIndex != -1) {
			decimalPlaces = numberString.length() - decimalIndex - 1;
		}

		if (decimalPlaces > 2)
		{
			openPopup("Your set price has been truncated.");
		}

		Item newItem = new Item(nameTextField.getText(),
				(int) (Float.parseFloat(priceTextField.getText()) * 100),
				Integer.parseInt(calorieTextField.getText()));

		if (vendingMachineController.getVendingMachines().getLast() instanceof SpecialVendingMachine &&
			(newItem.getName().equals("tonkotsu broth") || newItem.getName().equals("shio broth") ||
					newItem.getName().equals("miso broth")))
		{
			if (!((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast()).
					enoughBrothIngredients(newItem.getName()))
			{
				openPopup("Not enough ingredients for adding a broth.");
				return;
			}
		}

		if (slotDropdown.getValue().getItemList().size() > 0)
		{
			Item slotItem = vendingMachineController.getVendingMachines().getLast().getSlots()[slotIndex]
					.getItemList().get(0);

			if (newItem.getPrice() != slotItem.getPrice() || newItem.getCalories() != slotItem.getCalories())
			{
				openPopup("Please make sure that the price and calories have not changed.");
				return;
			}
		}

		if (!vendingMachineController.getVendingMachines().getLast().addItemToSlot(newItem, slotIndex, 1))
		{
			openPopup("Cannot add item.");
		}
		else
		{
			if (vendingMachineController.getVendingMachines().getLast() instanceof SpecialVendingMachine &&
					(newItem.getName().equals("tonkotsu broth") || newItem.getName().equals("shio broth") ||
							newItem.getName().equals("miso broth")))
			{
				((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast()).
						removeBrothIngredients(newItem.getName());
			}

			vendingMachineController.getVendingMachines().getLast().getTransactionTracker().setInitialSlots(vendingMachineController.getVendingMachines().getLast().getSlots());
		}
	}

	/**
	 * Returns to the maintenance menu.
	 * @param event button click
	 * @throws IOException if error occurred while opening maintenance menu FXML
	 */
	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceMenu.fxml"));
		openMenuScene(event, loader, "maintenance", null, null);
	}
}

package Controller;

import Model.Item;
import Model.Slot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Arrays;

public class StockInterfaceController extends MenuController
{
	public Button addButton;
	public TextField nameTextField;
	public TextField priceTextField;
	public TextField calorieTextField;
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

	private VendingMachineController vendingMachineController;
	private final ButtonAnimator buttonAnimator;

	public StockInterfaceController()
	{
		this.buttonAnimator = new ButtonAnimator();
	}

	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

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
			buttonAnimator.resizeWhenHovered((Button) button);
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
	}

	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceMenu.fxml"));
		openMenuScene(event, loader, "maintenance", vendingMachineController);
	}
}

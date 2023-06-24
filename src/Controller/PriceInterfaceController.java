package Controller;

import Model.Item;
import Model.Slot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;

public class PriceInterfaceController extends MenuController
{
	@FXML
	private HBox navButtons;
	@FXML
	private Label priceAmountLabel;
	@FXML
	private TextField priceTextField;
	@FXML
	private Button addButton;
	@FXML
	private AnchorPane topBar;
	@FXML
	private Button minimizeButton;
	@FXML
	private VBox priceInterfaceVbox;
	@FXML
	private ComboBox<Slot> slotDropdown;

	private VendingMachineController vendingMachineController;
	private final ButtonAnimator buttonAnimator;

	public PriceInterfaceController()
	{
		this.buttonAnimator = new ButtonAnimator();
	}

	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

	public void openPriceInterface()
	{
		priceInterfaceVbox.setSpacing(15);

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

		slotDropdown.setOnAction(event ->
		{
			if (slotDropdown.getValue().getItemList().size() > 0)
			{
				priceAmountLabel.setText("₱" + (float) (slotDropdown.getValue().getItemList().get(0).getPrice() / 100));
			}
			else
			{
				priceAmountLabel.setText("₱0");
			}
		});

		addButton.setOnAction(event ->
		{
			if (slotDropdown.getValue().getItemList() == null || slotDropdown.getValue().getItemList().size() == 0)
			{
				openPopup("No items in slot.");
				return;
			}
			if ((int) (Float.parseFloat(priceTextField.getText()) * 100) <= 0)
			{
				openPopup("Invalid price.");
				return;
			}
			if (slotDropdown.getValue().getItemList().size() > 0)
			{
				changeItemPrices();
			}
		});
	}

	private void changeItemPrices()
	{
		int i = 0, slotIndex = -1;

		for (Slot slot : vendingMachineController.getVendingMachines().getLast().getSlots())
		{
			if (slot == slotDropdown.getValue())
			{
				slotIndex = i;
			}

			i++;
		}

		for (Item item : vendingMachineController.getVendingMachines().getLast()
				.getSlots()[slotIndex].getItemList())
		{
			item.setPrice((int) (Float.parseFloat(priceTextField.getText()) * 100));
			priceAmountLabel.setText("₱" + (float) (slotDropdown.getValue().getItemList().get(0).getPrice() / 100));
		}
	}

	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceMenu.fxml"));
		openMenuScene(event, loader, "maintenance", vendingMachineController);
	}
}

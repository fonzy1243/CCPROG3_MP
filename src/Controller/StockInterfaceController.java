package Controller;

import Model.Slot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;

public class StockInterfaceController extends MenuController
{
	@FXML
	private HBox navButtons;
	@FXML
	private AnchorPane topBar;
	@FXML
	private Button minimizeButton;
	@FXML
	private VBox stockInterfaceVbox;
	@FXML
	private Button backButton;
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
	}

	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceMenu.fxml"));
		openMenuScene(event, loader, "maintenance", vendingMachineController);
	}
}

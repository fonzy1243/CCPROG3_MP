package Controller;

import Model.Slot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class StockInterfaceController extends MenuController
{
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
	}
}

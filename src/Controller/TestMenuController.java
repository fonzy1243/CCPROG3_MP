package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TestMenuController extends MenuController
{
	@FXML
	private Button minimizeButton;
	@FXML
	private AnchorPane topBar;
	@FXML
	private VBox testMenuVbox;
	@FXML
	private Button vendingFeaturesButton;
	@FXML
	private Button maintenanceButton;
	@FXML
	private Button returnButton;
	private VendingMachineController vendingMachineController;
	private final ButtonAnimator buttonAnimator;

	public TestMenuController()
	{
		this.buttonAnimator = new ButtonAnimator();
	}

	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

	public void openTestMenu()
	{
		testMenuVbox.setSpacing(12);

		buttonAnimator.resizeWhenHovered(vendingFeaturesButton);
		buttonAnimator.resizeWhenHovered(maintenanceButton);
		buttonAnimator.resizeWhenHovered(returnButton);

		moveApp(topBar, stage);
		minimizeButton.setOnAction(event -> minimizeApp(stage));
	}

	@FXML
	private void goToVendingMenu(ActionEvent event) throws IOException
	{
		if (vendingMachineController == null)
		{
			System.out.println("Error: No vending machine controller.");
			return;
		}

		if (vendingMachineController.getVendingMachines().size() == 0)
		{
			System.out.println("No vending machines");
			return;
		}

		openMenuScene(event, null, "vending", vendingMachineController);
	}
}

package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controls user input inside the test menu.
 * Allows the user to navigate to submenus.
 * @see Controller.MenuController
 */
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

	public TestMenuController()
	{
	}

	/**
	 * Sets the controller's vending machine controller
	 * @param vendingMachineController controller's vending machine controller
	 * @see VendingMachineController
	 */
	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

	/**
	 * Sets up test menu UI elements.
	 */
	public void openTestMenu()
	{
		testMenuVbox.setSpacing(12);

		ButtonAnimator.resizeWhenHovered(vendingFeaturesButton);
		ButtonAnimator.resizeWhenHovered(maintenanceButton);
		ButtonAnimator.resizeWhenHovered(returnButton);

		moveApp(topBar, stage);
		minimizeButton.setOnAction(event -> minimizeApp(stage));
	}

	/**
	 * Opens the vending menu scenes.
	 * @param event button click
	 * @throws IOException if error occurred while opening an FXML (should not happen)
	 */
	@FXML
	private void goToVendingMenu(ActionEvent event) throws IOException
	{
		if (hasNoMachine())
			return;

		openMenuScene(event, null, "vending", vendingMachineController);
	}

	/**
	 * Opens the maintenance menu scene.
	 * @param event button click
	 * @throws IOException if error occurred while loading maintenance menu FXML
	 */
	@FXML
	private void goToMaintenanceMenu(ActionEvent event) throws IOException
	{
		if (hasNoMachine())
			return;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceMenu.fxml"));
		openMenuScene(event, loader, "maintenance", vendingMachineController);
	}

	/**
	 * Checks if the vending machine list is not empty.
	 * @return true if the list is empty, else false
	 */
	private boolean hasNoMachine()
	{
		if (vendingMachineController == null)
		{
			System.out.println("Error: No vending machine controller.");
			return true;
		}

		if (vendingMachineController.getVendingMachines().size() == 0)
		{
			System.out.println("No vending machines");
			return true;
		}

		return false;
	}
}

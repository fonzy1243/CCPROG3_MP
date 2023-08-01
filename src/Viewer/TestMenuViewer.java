package Viewer;

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
 * @see MenuViewer
 */
public class TestMenuViewer extends MenuViewer
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

	public TestMenuViewer()
	{
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

		openMenuScene(event, null, "vending", null, null);
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
		openMenuScene(event, loader, "maintenance", null, null);
	}

	/**
	 * Checks if the vending machine list is not empty.
	 * @return true if the list is empty, else false
	 */
	private boolean hasNoMachine()
	{
		if (vendingMachineController == null)
		{
			throw new RuntimeException("Vending machine controller object is null");
		}

		if (vendingMachineController.getVendingMachines().size() == 0)
		{
			System.out.println("No vending machines");
			return true;
		}

		return false;
	}
}

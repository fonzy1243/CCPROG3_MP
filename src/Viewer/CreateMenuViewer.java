package Viewer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controls user input in the create menu.
 * Allows the user to create vending machines.
 * @see MenuViewer
 */
public class CreateMenuViewer extends MenuViewer
{
	@FXML
	private AnchorPane topBar;
	@FXML
	private Button minimizeButton;
	@FXML
	private VBox createMenuVBox;
	@FXML
	private Button createRegularButton;
	@FXML
	private Button createSpecialButton;
	@FXML
	private Button returnButton;

	public CreateMenuViewer()
	{

	}

	/**
	 * Opens the menu for creating vending machines.
	 */
	public void openCreateMenu()
	{
		createMenuVBox.setSpacing(12);

		ButtonAnimator.resizeWhenHovered(createRegularButton);
		ButtonAnimator.resizeWhenHovered(createSpecialButton);
		ButtonAnimator.resizeWhenHovered(returnButton);

		moveApp(topBar, stage);
		minimizeButton.setOnAction(event -> minimizeApp(stage));
	}

	/**
	 * Creates a new regular vending machine for the current machine controller.
	 */
	@FXML
	private void createRegularMachine()
	{
		vendingMachineController.createRegularMachine(10);
	}

	@FXML
	private void createSpecialMachine()
	{
		vendingMachineController.createSpecialMachine(10);
	}
}

package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controls user input in the create menu.
 * Allows the user to create vending machines.
 * @see Controller.MenuController
 */
public class CreateMenuController extends MenuController
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

	public CreateMenuController()
	{

	}

	/**
	 * Opens the menu for creating vending machines.
	 */
	public void openCreateMenu()
	{
		createMenuVBox.setSpacing(12);
		createSpecialButton.setDisable(true);

		ButtonAnimator.resizeWhenHovered(createRegularButton);
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
}

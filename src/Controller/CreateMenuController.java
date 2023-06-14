package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class CreateMenuController extends MenuController
{
	@FXML
	AnchorPane topBar;
	@FXML
	Button minimizeButton;
	@FXML
	VBox createMenuVBox;
	@FXML
	Button createRegularButton;
	@FXML
	Button createSpecialButton;
	@FXML
	Button returnButton;

	private final ButtonAnimator buttonAnimator;
	private VendingMachineController vendingMachineController;

	public CreateMenuController()
	{
		this.buttonAnimator = new ButtonAnimator();
	}

	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

	public void openCreateMenu()
	{
		createMenuVBox.setSpacing(12);
		createSpecialButton.setDisable(true);

		buttonAnimator.resizeWhenHovered(createRegularButton);
		buttonAnimator.resizeWhenHovered(returnButton);

		moveApp(topBar, stage);
		minimizeButton.setOnAction(event -> minimizeApp(stage));
	}
	@FXML
	private void createRegularMachine()
	{
		vendingMachineController.createRegularMachine(10);
	}

}

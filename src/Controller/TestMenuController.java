package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TestMenuController
{
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
	}

	public void returnToMainMenu(ActionEvent event)
	{

	}
}

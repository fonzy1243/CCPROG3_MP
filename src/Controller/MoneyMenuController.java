package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MoneyMenuController extends MenuController
{
	@FXML
	private VBox moneyMenuVbox;
	@FXML
	private Button withdrawButton;
	@FXML
	private Button depositButton;
	@FXML
	private Button returnButton;
	@FXML
	private AnchorPane topBar;
	@FXML
	private Button minimizeButton;

	private VendingMachineController vendingMachineController;
	private final ButtonAnimator buttonAnimator;

	public MoneyMenuController()
	{
		this.buttonAnimator = new ButtonAnimator();
	}

	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

	public void openMoneyMenu()
	{
		moneyMenuVbox.setSpacing(10);

		buttonAnimator.resizeWhenHovered(withdrawButton);
		buttonAnimator.resizeWhenHovered(depositButton);
		buttonAnimator.resizeWhenHovered(returnButton);

		moveApp(topBar, stage);
		minimizeButton.setOnAction(actionEvent -> minimizeApp(stage));
	}

	@FXML
	private void goToWithdrawInterface(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WithdrawInterface.fxml"));
		openMenuScene(event, loader, "withdraw", vendingMachineController);
	}

	@FXML
	private void goToPriceInterface(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PriceInterface.fxml"));
		openMenuScene(event, loader, "price", vendingMachineController);
	}

	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceMenu.fxml"));
		openMenuScene(event, loader, "maintenance", vendingMachineController);
	}
}

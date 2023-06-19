package Controller;

import Model.Slot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class MaintenanceMenuController extends MenuController
{
	@FXML
	private Button historyButton;
	@FXML
	private AnchorPane topBar;
	@FXML
	private Button minimizeButton;
	@FXML
	private VBox maintenanceMenuVbox;
	@FXML
	private Button stockButton;
	@FXML
	private Button priceButton;
	@FXML
	private Button moneyButton;
	@FXML
	private Button returnButton;

	private VendingMachineController vendingMachineController;
	private final ButtonAnimator buttonAnimator;

	public MaintenanceMenuController()
	{
		this.buttonAnimator = new ButtonAnimator();
	}

	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

	public void openMaintenanceMenu()
	{
		maintenanceMenuVbox.setSpacing(10);

		buttonAnimator.resizeWhenHovered(stockButton);
		buttonAnimator.resizeWhenHovered(priceButton);
		buttonAnimator.resizeWhenHovered(moneyButton);
		buttonAnimator.resizeWhenHovered(returnButton);

		moveApp(topBar, stage);
		minimizeButton.setOnAction(actionEvent -> minimizeApp(stage));
	}

	@FXML
	private void goToStockInterface(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StockInterface.fxml"));
		openMenuScene(event, loader, "stock", vendingMachineController);
	}

	@FXML
	private void goToPriceInterface(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PriceInterface.fxml"));
		openMenuScene(event, loader, "price", vendingMachineController);
	}

	@FXML
	private void goToMoneyMenu(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MoneyMenu.fxml"));
		openMenuScene(event, loader, "money", vendingMachineController);
	}

	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TestMenu.fxml"));
		openMenuScene(event, loader, "test", vendingMachineController);
	}
}
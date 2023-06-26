package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


import java.io.IOException;

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

	public MaintenanceMenuController()
	{

	}

	/**
	 * Sets the menu's vending machine controller
	 * @param vendingMachineController menu's vending machine controller
	 * @see VendingMachineController
	 */
	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

	/**
	 * Opens the menu from which the user can select which feature to use.
	 */
	public void openMaintenanceMenu()
	{
		maintenanceMenuVbox.setSpacing(10);

		ButtonAnimator.resizeWhenHovered(stockButton);
		ButtonAnimator.resizeWhenHovered(priceButton);
		ButtonAnimator.resizeWhenHovered(moneyButton);
		ButtonAnimator.resizeWhenHovered(returnButton);

		moveApp(topBar, stage);
		minimizeButton.setOnAction(actionEvent -> minimizeApp(stage));
	}

	/**
	 * Opens the interface for editing stock.
	 * @param event button click
	 * @throws IOException if error occurred while opening stock interface FXML
	 */
	@FXML
	private void goToStockInterface(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StockInterface.fxml"));
		openMenuScene(event, loader, "stock", vendingMachineController);
	}

	/**
	 * Opens the interface for editing price
	 * @param event button click
	 * @throws IOException if error occurred while opening price interface FXML
	 */
	@FXML
	private void goToPriceInterface(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PriceInterface.fxml"));
		openMenuScene(event, loader, "price", vendingMachineController);
	}

	/**
	 * Opens the menu for picking between withdrawing or depositing money.
	 * @param event button click
	 * @throws IOException if error occurred while opening money menu FXML
	 */
	@FXML
	private void goToMoneyMenu(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MoneyMenu.fxml"));
		openMenuScene(event, loader, "money", vendingMachineController);
	}

	/**
	 * Returns to the test menu
	 * @param event button click
	 * @throws IOException if error occurred while opening test menu FXML
	 */
	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TestMenu.fxml"));
		openMenuScene(event, loader, "test", vendingMachineController);
	}
}
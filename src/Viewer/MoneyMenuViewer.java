package Viewer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * WithdrawInterfaceViewer manages the Money Menu's GUI.
 */
public class MoneyMenuViewer extends MenuViewer
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

	public MoneyMenuViewer()
	{

	}

	/**
	 * Open the menu where a user can choose whether to withdraw or deposit money.
	 */
	public void openMoneyMenu()
	{
		moneyMenuVbox.setSpacing(10);

		ButtonAnimator.resizeWhenHovered(withdrawButton);
		ButtonAnimator.resizeWhenHovered(depositButton);
		ButtonAnimator.resizeWhenHovered(returnButton);

		moveApp(topBar, stage);
		minimizeButton.setOnAction(actionEvent -> minimizeApp(stage));
	}

	/**
	 * Opens the withdraw interface
	 * @param event button click
	 * @throws IOException if error occurred while loading withdraw interface FXML
	 */
	@FXML
	private void goToWithdrawInterface(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WithdrawInterface.fxml"));
		openMenuScene(event, loader, "withdraw", vendingMachineController);
	}

	/**
	 * Opens the dispense interface
	 * @param event button click
	 * @throws IOException if error occurred while loading dispense interface FXML
	 */
	@FXML
	private void goToDispenseInterface(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DispenseInterface.fxml"));
		openMenuScene(event, loader, "dispense", vendingMachineController);
	}

	/**
	 * Returns to the maintenance menu.
	 * @param event button click
	 * @throws IOException if error occurred while loading maintenance menu
	 */
	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceMenu.fxml"));
		openMenuScene(event, loader, "maintenance", vendingMachineController);
	}
}

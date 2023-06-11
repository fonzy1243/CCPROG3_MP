package Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController extends MenuController
{
	@FXML
	Button testVendingMachineButton;
	@FXML
	Button createMenuButton;
	@FXML
	Button exitButton;
	@FXML
	VBox mainMenuVBox;
	@FXML
	Label mainMenuLabel;

	private final ButtonAnimator buttonAnimator;
	private static VendingMachineController vendingMachineController;

	public MainMenuController()
	{
		this.buttonAnimator = new ButtonAnimator();
	}

	public void setVendingMachineController(VendingMachineController controller)
	{
		vendingMachineController = controller;
	}


	public void openMainMenu(Stage stage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root, 720, 720);

			if (mainMenuVBox == null)
			{
				mainMenuVBox = (VBox) root.lookup("#mainMenuVBox");
			}

			String css = Objects
					.requireNonNull(this.getClass()
							.getResource("/styles/application.css"))
					.toExternalForm();

			scene.getStylesheets().add(css);

			mainMenuVBox.setSpacing(12);

			if (createMenuButton == null)
			{
				createMenuButton = (Button) root.lookup("#createMenuButton");
			}

			buttonAnimator.resizeWhenHovered(createMenuButton);

			if (testVendingMachineButton == null)
			{
				testVendingMachineButton = (Button) root.lookup("#testVendingMachineButton");
			}

			buttonAnimator.resizeWhenHovered(testVendingMachineButton);

			if (exitButton == null)
			{
				exitButton = (Button) root.lookup("#exitButton");
			}

			buttonAnimator.resizeWhenHovered(exitButton);

			System.out.println(vendingMachineController.getVendingMachines().size());

			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Vending Machine Factory Simulator");
			stage.show();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	@FXML
	private void goToCreateMenu(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateMenu.fxml"));
		openMenuScene(event, loader, "create", vendingMachineController);
	}

	@FXML
	private void goToTestMenu(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TestMenu.fxml"));
		openMenuScene(event, loader, "test", vendingMachineController);
	}

	@FXML
	private void exit()
	{
		Platform.exit();
		System.exit(0);
	}
}

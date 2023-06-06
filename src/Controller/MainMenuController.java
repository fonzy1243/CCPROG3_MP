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

public class MainMenuController
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
	private Scene scene;
	private Parent root;

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
			root = loader.load();

			scene = new Scene(root, 720, 720);

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

			stage.setOnCloseRequest(windowEvent ->
			{
				Platform.exit();
				System.exit(0);
			});

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
		root = loader.load();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);

		CreateMenuController createMenuController = loader.getController();
		createMenuController.setVendingMachineController(vendingMachineController);
		createMenuController.openCreateMenu();

		String css = Objects
				.requireNonNull(this.getClass()
						.getResource("/styles/application.css"))
				.toExternalForm();

		stage.setOnCloseRequest(windowEvent ->
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Close window attempted");
			alert.setHeaderText("You tried to close the window.");
			alert.setContentText("This window cannot be closed in this menu.");
			alert.showAndWait();
			windowEvent.consume();
		});

		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void exit()
	{
		Platform.exit();
		System.exit(0);
	}
}

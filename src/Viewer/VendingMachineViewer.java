package Viewer;

import Controller.VendingMachineController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VendingMachineViewer extends Application
{
	@FXML
	VBox mainMenuVBox;
	@FXML
	VBox createMenuVBox;
	@FXML
	Button createRegularButton;
	@FXML
	Button createMenuButton;
	@FXML
	Button testButton;
	@FXML
	Button returnButton;

	@FXML
	Scene scene;
	@FXML
	Parent root;
	private VendingMachineController controller;

	public VendingMachineViewer()
	{
		this.controller = new VendingMachineController();
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		try
		{
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainMenu.fxml")));

			scene = new Scene(root, 720, 720);

			String css = Objects
					.requireNonNull(this.getClass()
					.getResource("styles/application.css"))
					.toExternalForm();

			scene.getStylesheets().add(css);
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

	public void switchToMainMenu(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainMenu.fxml")));
		displayMenuScene(event);
	}

	@FXML
	public void switchToCreateMenu(ActionEvent event) throws IOException
	{
//		testButton.setDisable(true);
		displayMenuScene(event);
	}

	private void displayMenuScene(ActionEvent event)
	{
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);

		String css = Objects
				.requireNonNull(this.getClass()
						.getResource("styles/application.css"))
				.toExternalForm();

		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}
}

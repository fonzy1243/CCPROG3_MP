package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SceneAntialiasing;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import java.io.IOException;
import java.util.Objects;

public class MainMenuController
{
	@FXML
	VBox mainMenuVBox;
	@FXML
	Button createMenuButton;
	private Stage stage;
	private Scene scene;
	private Parent root;

	public MainMenuController()
	{
	}


	public void openMainMenu(Stage stage)
	{
		this.stage = stage;
		try
		{
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainMenu.fxml")));

			scene = new Scene(root, 720, 720);

			String css = Objects
					.requireNonNull(this.getClass()
							.getResource("/styles/application.css"))
					.toExternalForm();

			scene.getStylesheets().add(css);
			System.out.println(scene.getAntiAliasing());
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
	public void goToCreateMenu(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateMenu.fxml"));
		root = loader.load();

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);

		CreateMenuController createMenuController = loader.getController();
		createMenuController.openCreateMenu();

		String css = Objects
				.requireNonNull(this.getClass()
						.getResource("/styles/application.css"))
				.toExternalForm();

		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}


}

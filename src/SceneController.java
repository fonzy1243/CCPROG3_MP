import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class SceneController
{
	private Stage stage;
	private Scene scene;

	public void switchToMainMenu(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainMenu.fxml")));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);

		stage.setScene(scene);
		stage.show();
	}

	public void switchToCreateMenu(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/CreateMenu.fxml")));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);

		stage.setScene(scene);
		stage.show();
	}
}

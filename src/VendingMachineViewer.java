import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class VendingMachineViewer extends Application
{
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
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainMenu.fxml")));

			Scene scene = new Scene(root, 720, 720);

			Font defaultFont = Font.loadFont(getClass().getResourceAsStream("fonts/Nunito-Regular.ttf"), 12);

			stage.setScene(scene);
			stage.setTitle("Vending Machine Factory Simulator");
			stage.show();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
}

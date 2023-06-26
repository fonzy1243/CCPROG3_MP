package Viewer;

import Controller.VendingMachineController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VendingMachineAppViewer extends Application
{
	private static VendingMachineController controller;

	public VendingMachineAppViewer()
	{
		controller = new VendingMachineController();
	}

	@Override
	public void start(Stage stage)
	{
		stage.initStyle(StageStyle.TRANSPARENT);

		MainMenuViewer mainMenuViewer = new MainMenuViewer();

		mainMenuViewer.setVendingMachineController(controller);
		mainMenuViewer.openMainMenu(stage);
	}
}


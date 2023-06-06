package Viewer;

import Controller.MainMenuController;
import Controller.VendingMachineController;
import javafx.application.Application;
import javafx.stage.Stage;

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
		MainMenuController mainMenuController = new MainMenuController();

		mainMenuController.openMainMenu(stage);
	}




}

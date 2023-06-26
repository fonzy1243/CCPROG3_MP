package Model;

import Viewer.VendingMachineViewer;
import javafx.application.Application;

public class Main
{
	public static void main(String[] args)
	{
		// Change text antialiasing
		System.setProperty("prism.lcdtext", "false");
		// Launch the GUI application
		Application.launch(VendingMachineViewer.class, args);
	}
}
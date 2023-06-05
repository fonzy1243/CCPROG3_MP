import javafx.application.Application;

public class Main
{
	public static void main(String[] args)
	{
		VendingMachine model = new RegularVendingMachine(8);
		VendingMachineController controller = new VendingMachineController(model);

		Application.launch(VendingMachineViewer.class, args);
	}
}
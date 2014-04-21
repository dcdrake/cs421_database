package cs421_project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Database {

	protected Shell Database;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Database window = new Database();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		Database.open();
		Database.layout();
		while (!Database.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		Database = new Shell();
		Database.setSize(432, 279);
		Database.setText("Database GUI");
		
		Label lblUniversity = new Label(Database, SWT.NONE);
		lblUniversity.setBounds(10, 10, 59, 14);
		lblUniversity.setText("University");
		
		Label lblNewLabel = new Label(Database, SWT.NONE);
		lblNewLabel.setBounds(99, 69, 59, 14);
		lblNewLabel.setText("City");
		
		List list = new List(Database, SWT.BORDER);
		list.setBounds(10, 30, 98, 33);
		
		List list_1 = new List(Database, SWT.BORDER);
		list_1.setBounds(10, 92, 59, 33);
		
		List list_2 = new List(Database, SWT.BORDER);
		list_2.setBounds(99, 92, 98, 33);
		
		Label lblState = new Label(Database, SWT.NONE);
		lblState.setBounds(10, 69, 59, 14);
		lblState.setText("State");
		
		List list_3 = new List(Database, SWT.BORDER);
		list_3.setBounds(300, 30, 98, 33);
		
		Label lblRatingAbove = new Label(Database, SWT.NONE);
		lblRatingAbove.setBounds(300, 10, 77, 14);
		lblRatingAbove.setText("Rating above:");

	}
}

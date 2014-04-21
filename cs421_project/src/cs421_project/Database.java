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
	private String[] states;
	private String[] ratings;

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
		
		states = new String[] {"AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI",
				"ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO",
				"MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI",
				"SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};
		
		ratings = new String[] {"0", "1", "2", "3", "4"};
		
		Label lblUniversity = new Label(Database, SWT.NONE);
		lblUniversity.setBounds(10, 10, 59, 14);
		lblUniversity.setText("University");
		
		Label lblNewLabel = new Label(Database, SWT.NONE);
		lblNewLabel.setBounds(99, 69, 59, 14);
		lblNewLabel.setText("City");
		
		List list = new List(Database, SWT.BORDER);
		list.setBounds(10, 30, 98, 33);
		
		List state_list = new List(Database, SWT.BORDER);
		state_list.setBounds(10, 92, 59, 60);
		
		for(int i=0; i<states.length; i++)
			  state_list.add(states[i]);
		
		List list_2 = new List(Database, SWT.BORDER);
		list_2.setBounds(99, 92, 98, 60);
		
		Label lblState = new Label(Database, SWT.NONE);
		lblState.setBounds(10, 69, 59, 14);
		lblState.setText("State");
		
		List rating_list = new List(Database, SWT.BORDER);
		rating_list.setBounds(300, 30, 98, 53);
		
		for(int i=0; i<ratings.length; i++)
			  rating_list.add(ratings[i]);
		
		Label lblRatingAbove = new Label(Database, SWT.NONE);
		lblRatingAbove.setBounds(300, 10, 77, 19);
		lblRatingAbove.setText("Rating above:");
		
		Button btnNewButton = new Button(Database, SWT.NONE);
		btnNewButton.setBounds(172, 219, 94, 28);
		btnNewButton.setText("Search");

	}
}

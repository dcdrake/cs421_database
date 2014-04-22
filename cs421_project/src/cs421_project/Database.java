package cs421_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;

public class Database {

	protected Shell Database;
	private String[] states;
	private String[] ratings;
	private String[] schools;
	private List city_list;
	private Text text;

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
	 * @throws SQLException 
	 */
	public void open() throws SQLException {
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
	 * @throws SQLException 
	 */
	protected void createContents() throws SQLException {
		Database = new Shell();
		Database.setSize(600, 475);
		Database.setText("Database GUI");

		String url = "jdbc:postgresql://localhost:63333/jagabel_project";
		Properties props = new Properties();
		props.setProperty("user","user");
		props.setProperty("password","pass");
		final Connection conn = DriverManager.getConnection(url, props);
		if(conn.isValid(0)){
			System.out.println("Connection Established");
		}

		states = new String[] {"AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI",
				"ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO",
				"MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI",
				"SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};

		ratings = new String[] {"0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5"};

		schools = new String[] {"Brown University", "California Institute of Technology", 
				"California Polytechnic State University", "Carnegie Mellon University", 
				"Columbia University", "Cornell University", "Georgia Institute of Technology", 
				"Harvard University", "Harvey Mudd College", "Massachusetts Institute of Technology", 
				"Princeton University", "Purdue University", "Rensselaer Polytechnic Institute", 
				"Rice University", "Stanford University", "University of California - Los Angeles", 
				"University of California - San Diego", "University of California at Berkeley", 
				"University of Illinois - Urbana-Champaign", "University of Maryland - College Park", 
				"University of Massachusetts - Amherst", "University of Michigan - Ann Arbor", 
				"University of North Carolina - Chapel Hill", "University of Pennsylvania", 
				"University of Southern California", "University of Texas - Austin", "University of Washington", 
				"University of Waterloo", "University of Wisconsin - Madison", "Virginia Tech"};

		Label lblUniversity = new Label(Database, SWT.NONE);
		lblUniversity.setBounds(10, 10, 59, 14);
		lblUniversity.setText("University");

		Label lblNewLabel = new Label(Database, SWT.NONE);
		lblNewLabel.setBounds(99, 157, 59, 19);
		lblNewLabel.setText("City");

		final List school_list = new List(Database, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		school_list.setBounds(10, 30, 280, 100);

		for(int i=0; i<schools.length; i++)
			school_list.add(schools[i]);


		final List state_list = new List(Database, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		state_list.setBounds(10, 177, 59, 92);

		for(int i=0; i<states.length; i++)
			state_list.add(states[i]);

		city_list = new List(Database, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		city_list.setBounds(99, 177, 191, 92);

		state_list.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				String[] state_array = state_list.getSelection();
				text.setText(state_array[0]);

				try {
					Statement st;
					st = conn.createStatement();
					String state = state_array[0];
					String query = "SELECT DISTINCT city FROM business WHERE state = " + state;
					ResultSet rs = st.executeQuery(query);
					while (rs.next())
					{
						String city  = rs.getString("city");
						city_list.add(city);
					} rs.close();
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Label lblState = new Label(Database, SWT.NONE);
		lblState.setBounds(10, 157, 59, 14);
		lblState.setText("State");

		List rating_list = new List(Database, SWT.BORDER | SWT.V_SCROLL);
		rating_list.setBounds(345, 30, 98, 100);

		for(int i=0; i<ratings.length; i++)
			rating_list.add(ratings[i]);

		Label lblRatingAbove = new Label(Database, SWT.NONE);
		lblRatingAbove.setBounds(345, 10, 87, 19);
		lblRatingAbove.setText("Rating above:");

		Button searchButton = new Button(Database, SWT.NONE);
		searchButton.setBounds(129, 411, 94, 28);
		searchButton.setText("Search");

		List category_list = new List(Database, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		category_list.setBounds(320, 177, 156, 92);

		Statement st2 = conn.createStatement();
		ResultSet rs2 = st2.executeQuery("SELECT DISTINCT category FROM business_categories ORDER BY category");
		while (rs2.next())
		{
			String category  = rs2.getString("category");
			category_list.add(category);
		} rs2.close();
		st2.close();

		Label lblCategories = new Label(Database, SWT.NONE);
		lblCategories.setBounds(320, 157, 69, 19);
		lblCategories.setText("Categories");

		Button btnNewButton_1 = new Button(Database, SWT.NONE);
		btnNewButton_1.setBounds(247, 411, 133, 28);
		btnNewButton_1.setText("I'm Feeling Lucky");

		text = new Text(Database, SWT.BORDER | SWT.V_SCROLL);
		text.setBounds(310, 302, 280, 102);

		Label lblResults = new Label(Database, SWT.NONE);
		lblResults.setBounds(10, 283, 59, 14);
		lblResults.setText("Results:");

		List list = new List(Database, SWT.BORDER);
		list.setBounds(10, 302, 244, 103);

		Label lblDetailedInformation = new Label(Database, SWT.NONE);
		lblDetailedInformation.setBounds(310, 282, 133, 14);
		lblDetailedInformation.setText("Detailed Information:");




		searchButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent event) {
				String[] selected  = school_list.getSelection();
				text.setText(selected[0]);
			}

			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});

	}
}

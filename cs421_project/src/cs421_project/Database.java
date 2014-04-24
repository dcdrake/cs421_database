package cs421_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.lang.Math.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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

	private String schoolResult = "";
	private String cityResult = "";
	private String stateResult = "";
	private String cateResult = "";
	private String ratingResult = "";
	private Text review_text;

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
		Database.setSize(1300, 575);
		Database.setText("Database GUI");

		String url = "jdbc:postgresql://localhost:63333/jagabel_project";
		Properties props = new Properties();
		props.setProperty("user","jagabel");
		props.setProperty("password","tnO81cW");
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
		lblUniversity.setBounds(10, 28, 59, 14);
		lblUniversity.setText("University");

		Label lblNewLabel = new Label(Database, SWT.NONE);
		lblNewLabel.setBounds(477, 28, 59, 19);
		lblNewLabel.setText("City");

		final List school_list = new List(Database, SWT.BORDER | SWT.V_SCROLL);
		school_list.setBounds(10, 48, 314, 100);

		for(int i=0; i<schools.length; i++)
			school_list.add(schools[i]);

		final List state_list = new List(Database, SWT.BORDER | SWT.V_SCROLL);
		state_list.setBounds(384, 48, 59, 100);

		for(int i=0; i<states.length; i++)
			state_list.add(states[i]);

		city_list = new List(Database, SWT.BORDER | SWT.V_SCROLL);
		city_list.setBounds(477, 48, 191, 100);

		state_list.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {

				try {

					String[] state_array = state_list.getSelection();
					city_list.removeAll();

					Statement st;
					st = conn.createStatement();
					String state = "'" +state_array[0] + "'";
					ResultSet rs = st.executeQuery("SELECT DISTINCT city FROM business WHERE state = "+ state + "ORDER BY city");
					while (rs.next())
					{
						String city  = rs.getString("city");
						city_list.add(city);
					} rs.close();
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (ArrayIndexOutOfBoundsException AE)
				{

				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Label lblState = new Label(Database, SWT.NONE);
		lblState.setBounds(384, 28, 59, 14);
		lblState.setText("State");

		final List rating_list = new List(Database, SWT.BORDER | SWT.V_SCROLL);
		rating_list.setBounds(82, 179, 98, 100);

		for(int i=0; i<ratings.length; i++)
			rating_list.add(ratings[i]);

		Label lblRatingAbove = new Label(Database, SWT.NONE);
		lblRatingAbove.setBounds(81, 154, 87, 19);
		lblRatingAbove.setText("Rating above:");

		Button searchButton = new Button(Database, SWT.NONE);
		searchButton.setBounds(144, 515, 94, 28);
		searchButton.setText("Search");

		final List category_list = new List(Database, SWT.BORDER | SWT.V_SCROLL);
		category_list.setBounds(284, 179, 222, 92);

		Statement st2 = conn.createStatement();
		ResultSet rs2 = st2.executeQuery("SELECT DISTINCT category FROM business_categories ORDER BY category");
		while (rs2.next())
		{
			String category  = rs2.getString("category");
			category_list.add(category);
		} rs2.close();
		st2.close();

		Label lblCategories = new Label(Database, SWT.NONE);
		lblCategories.setBounds(284, 154, 120, 19);
		lblCategories.setText("Categories");

		Button recommend_button = new Button(Database, SWT.NONE);
		recommend_button.setBounds(244, 515, 133, 28);
		recommend_button.setText("I'm Feeling Lucky");

		text = new Text(Database, SWT.BORDER | SWT.V_SCROLL);
		text.setBounds(310, 317, 280, 159);

		Label lblResults = new Label(Database, SWT.NONE);
		lblResults.setBounds(10, 296, 59, 14);
		lblResults.setText("Results:");

		final List resultsList = new List(Database, SWT.BORDER | SWT.V_SCROLL);
		resultsList.setBounds(10, 316, 244, 160);

		Label lblDetailedInformation = new Label(Database, SWT.NONE);
		lblDetailedInformation.setBounds(310, 296, 133, 14);
		lblDetailedInformation.setText("Detailed Information:");

		Label lblSelect = new Label(Database, SWT.BORDER);
		lblSelect.setBounds(10, 8, 59, 14);
		lblSelect.setText("Select:");

		Label lblOr = new Label(Database, SWT.NONE);
		lblOr.setBounds(345, 80, 59, 14);
		lblOr.setText("OR");

		Label lblWith = new Label(Database, SWT.NONE);
		lblWith.setBounds(10, 211, 59, 14);
		lblWith.setText("WITH");

		Label lblAnd = new Label(Database, SWT.NONE);
		lblAnd.setBounds(219, 211, 59, 14);
		lblAnd.setText("AND");

		Button clearButton = new Button(Database, SWT.NONE);
		clearButton.setBounds(383, 515, 114, 28);
		clearButton.setText("Clear Selection");

		review_text = new Text(Database, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		review_text.setBounds(662, 249, 610, 227);

		Label lblReviews = new Label(Database, SWT.NONE);
		lblReviews.setBounds(662, 229, 59, 14);
		lblReviews.setText("Reviews:");

		clearButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				city_list.deselectAll();
				state_list.deselectAll();
				school_list.deselectAll();
				category_list.deselectAll();
				rating_list.deselectAll();

				schoolResult = "";
				review_text.setText("");
			}

		});

		recommend_button.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String category, school, state, city, rating = "";
				category = category_list.getSelection()[0];
				rating = rating_list.getSelection()[0];
				school = school_list.getSelection()[0];
				//state = state_list.getSelection()[0];
				//city = city_list.getSelection()[0];
				Statement s;
				try {
					s = conn.createStatement();
					ResultSet r = s.executeQuery("SELECT DISTINCT business.name, business.stars FROM business, business_categories, school WHERE business.business_id = business_categories.business_id AND business.stars > '"+ rating + "' AND business_categories.category = '" + category + "' AND school.name = '" + school + "' ORDER BY business.stars DESC LIMIT 1");

					r.next();

					String biz_name = r.getString("name");
					fireSingleBusinessQuery(biz_name, conn);

					r.close();
					s.close();



				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});


		// Listener section

		searchButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent event) {

				Statement s;
				try {
					s = conn.createStatement();
					ResultSet r = s.executeQuery(buildQuery());
					resultsList.removeAll();
					while (r.next())
					{
						resultsList.add(r.getString(1));
					}	

					r.close(); 
					s.close();

					if (resultsList.getItems().length == 0)
					{
						resultsList.add("No results found");
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

			}

			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});

		school_list.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				try
				{
					schoolResult = school_list.getSelection()[0];
				}
				catch (ArrayIndexOutOfBoundsException AE)
				{

				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		city_list.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

				try
				{
					cityResult = city_list.getSelection()[0];

					if (cityResult.contains("'"))
					{
						cityResult = cityResult.substring(0, cityResult.indexOf("'")) +
								"''" + 
								cityResult.substring(cityResult.indexOf("'") 
										+ 1, cityResult.length());
					}
				}
				catch (ArrayIndexOutOfBoundsException AE)
				{

				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}


		});

		state_list.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				try
				{
					stateResult = state_list.getSelection()[0];
				}
				catch (Exception AE)
				{

				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}


		});

		category_list.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				try{

					cateResult = category_list.getSelection()[0];
					if (cateResult.contains("'"))
					{
						cateResult = cateResult.substring(0, cateResult.indexOf("'"))
								+ "''" + cateResult.substring(cateResult.indexOf("'")
										+ 1, cateResult.length());

					}

				}
				catch (ArrayIndexOutOfBoundsException AE)
				{

				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}


		});

		rating_list.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				try 
				{
					if (rating_list.getSelection()[0] != "")
					{
						ratingResult = rating_list.getSelection()[0];
					}
				}
				catch (ArrayIndexOutOfBoundsException AE)
				{

				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		resultsList.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				try
				{	text.setText("");
				fireSingleBusinessQuery(resultsList.getSelection()[0], conn);
				}
				catch (ArrayIndexOutOfBoundsException AE)
				{

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

		recommend_button.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub				

			}

		});

	}

	private String buildQuery()
	{
		String result = "";
		String catePortion = "";
		String cityPortion = "";
		String statePortion = "";
		String schoolPortion = "";
		String ratingPortion = "";

		boolean hasWhere = false;

		if (cateResult !="")
		{

			catePortion = "business_categories.category = '" + 
					cateResult + "' AND business_categories.business_id = business.business_id AND ";

			hasWhere = true;
		}

		if (schoolResult != "")
		{			schoolPortion = "school.name = '" + schoolResult + 
		"' AND school.city = business.city AND ";
		hasWhere = true;
		}

		if (cityResult != "")
		{
			cityPortion = "business.city = '" + cityResult + "' AND ";
			hasWhere = true;
		}

		if (stateResult != "" && cityPortion == "")
		{
			statePortion = "business.state = '" + stateResult + "' AND ";
			hasWhere = true;
		}

		if (ratingResult != "")
		{
			ratingPortion = "business.stars > " + ratingResult + " AND ";
			hasWhere = true;
		}

		if (hasWhere && (schoolPortion == "" && cityPortion == "" && statePortion == ""))
		{
			result = "SELECT DISTINCT business.name, business.stars FROM business, business_categories WHERE ";
			result += catePortion;
			result += "ORDER BY business.stars DESC LIMIT 10 ";
		}
		else if (hasWhere && schoolPortion == "")
		{
			result = "SELECT DISTINCT business.name, business.stars FROM business, business_categories WHERE "
					+ cityPortion + statePortion + ratingPortion + catePortion;
			result = result.substring(0, result.length() - 4);
			result += "ORDER BY business.stars DESC LIMIT 10 ";
		}
		else if (hasWhere)
		{
			result = "SELECT DISTINCT business.name, business.stars FROM business, school,"
					+ " business_categories WHERE " + schoolPortion + ratingPortion + catePortion;
			result = result.substring(0, result.length() - 4) ;

			result += "ORDER BY business.stars DESC LIMIT 10 ";

		}
		else
		{
			result = "SELECT DISTINCT business.name FROM business, business_categories ORDER BY business.stars DESC LIMIT 10";
		}

		System.out.println(result);
		return result;
	}

	private void fireSingleBusinessQuery(String name, Connection conn) throws SQLException
	{
		System.out.println(name);
		Statement s = conn.createStatement();
		Statement s2 = conn.createStatement();
		//For issues where business names contain the ' character
		//postgres throws a fit and so they must be represented by ''
		if (name.contains("'"))
		{
			name = name.substring(0, name.indexOf("'")) + "''" + 
					name.substring(name.indexOf("'") + 1, name.length());
		}

		//Very, very messy reconstruction of the query from buildQuery().
		//In essence, takes the query from buildQuery and then modifies it so that
		//Only the desired business's data is returned (to avoid for example giving info
		//On a Best Buy in New York when the user wanted it from Virginia)
		String query = buildQuery();
		query = query.replaceFirst("name", "//");
		if (query.substring(query.indexOf("//") - 1, query.indexOf("//")).equals("."));
		{
			query = query.replaceFirst("business.", "");

			query = query.replaceFirst("//", "business.name, business.address, "
					+ "business.city, business.state, business.stars, "
					+ "business.review_count, business.open");
		}
		query = query.substring(0, query.indexOf("ORDER"))+ " AND business.name = '" + name + "'";


		/*Business Reviews query */
		ResultSet r2 = s.executeQuery("SELECT review.text, review.votes FROM review, business WHERE business.business_id = review.business_id "
				+ "AND review.business_id = ("
				+ "SELECT business_id FROM business WHERE business.name = '" + name + "'" +") "
				+ "ORDER BY review.votes DESC LIMIT 3");
		r2.next();
		String raw_review_string = r2.getString("text");
		review_text.setText(raw_review_string);

		/*Additional info query*/
		ResultSet r = s2.executeQuery(query);
		String resultsString = "";
		r.next();

		resultsString = "Name: " + r.getString(1) + "\n";
		resultsString += "Address: " + r.getString(2) + "\n";
		resultsString += "City: " + r.getString(3) + "\n";
		resultsString += "State: " + r.getString(4) + "\n";
		resultsString += "Stars: " + r.getDouble(5) + "\n";
		resultsString += "Reviews: " + r.getLong(6) + "\n";
		if (r.getBoolean(7))
			resultsString += "Currently Open";
		else
			resultsString += "Currently Closed";

		text.setText(resultsString);


		r.close();
		s.close();

	}

}
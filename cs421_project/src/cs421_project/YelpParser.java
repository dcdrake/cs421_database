package cs421_project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class YelpParser {

	public static void main(String[] args) throws SQLException {

		String url = "jdbc:postgresql://localhost:63333/jagabel_project";
		Properties props = new Properties();
		props.setProperty("user","USER");
		props.setProperty("password","PASSWORD"); // DON'T COMMIT UNTIL YOU CHANGE THIS TO SOME NONSENSE!!!!!!!!
		//props.setProperty("ssl","true");
		Connection conn = DriverManager.getConnection(url, props);
		if(conn.isValid(0)){
			System.out.println("Connection Established");
		}

		String file_url = "/Users/dcdrake/Desktop/yelp_reviews.json";
		populateBusiness(file_url, conn);

	}

	private static void populateBusiness(String file_url, Connection conn) throws SQLException{
		BufferedReader br = null;
		JSONParser parser = new JSONParser();

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(file_url));

			while ((sCurrentLine = br.readLine()) != null) {

				Object obj;
				try {
					obj = parser.parse(sCurrentLine);
					JSONObject jsonObject = (JSONObject) obj;
					
					//Review object
					String review_id = (String) jsonObject.get("review_id");
					//System.out.println("Review id: " + review_id);
					
					String business_id = (String) jsonObject.get("business_id");
					//System.out.println("Business id: " + business_id);
					
					String user_id = (String) jsonObject.get("user_id");
					System.out.println("User id: " + user_id);

					long stars = (Long) jsonObject.get("stars");
					//System.out.println("Stars: " + stars);

					String text = (String) jsonObject.get("text");
					//System.out.println("Text: " + text);

					String date = (String) jsonObject.get("date");
					//System.out.println("Date: " + date);

					JSONObject votes = (JSONObject) jsonObject.get("votes");
					long useful = (Long) votes.get("useful");
					//System.out.println("Votes: " + useful);
					
					String query = "INSERT INTO review VALUES (?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement p = null;
					p = conn.prepareStatement(query);
					p.setString(1, review_id);
					p.setString(2, business_id);
					p.setString(3, user_id);
					p.setLong(4, stars);
					p.setString(5, text);
					p.setString(6, date);
					p.setLong(7, useful);
					p.executeUpdate();

					p.close();

					// Business object
					/*String business_id = (String) jsonObject.get("business_id");
					//System.out.println("Business id: " + business_id);

					String name = (String) jsonObject.get("name");
					System.out.println("Business name: " + name);

					String full_address = (String) jsonObject.get("full_address");
					String[] street_address = full_address.split("\\n");
					String address  = street_address[0]; //Returns only the street address
					System.out.println("Address: " + address);

					String city = (String) jsonObject.get("city");
					//System.out.println("City: " + city);

					String state = (String) jsonObject.get("state");
					//System.out.println("State: " + state);

					double latitude = (Double) jsonObject.get("latitude");
					//System.out.println("Latitude: " + latitude);

					double longitude = (Double) jsonObject.get("longitude");
					//System.out.println("Longitude: " + longitude);

					double stars = (Double) jsonObject.get("stars");
					//System.out.println("Stars: " + stars);

					long review_count = (Long) jsonObject.get("review_count");
					//System.out.println("Review Count: " + review_count);

					boolean isOpen = (Boolean) jsonObject.get("open");

					String query = "INSERT INTO business VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement p = null;
					p = conn.prepareStatement(query);
					p.setString(1, business_id);
					p.setString(2, name);
					p.setString(3, address);
					p.setString(4, city);
					p.setString(5, state);
					p.setDouble(6, latitude);
					p.setDouble(7, longitude);
					p.setDouble(8, stars);
					p.setLong(9, review_count);
					p.setBoolean(10, isOpen);
					p.executeUpdate();

					p.close();*/


					//Business_category object
					/*String business_id = (String) jsonObject.get("business_id");
					JSONArray categories = (JSONArray) jsonObject.get("categories");
					for(int i=0; i < categories.size(); i++){
						//System.out.println(categories.get(i));
						String category = (String) categories.get(i);

						String query = "INSERT INTO business_categories VALUES (?, ?)";
						PreparedStatement p = null;
						p = conn.prepareStatement(query);
						p.setString(1, business_id);
						p.setString(2, category);
						p.executeUpdate();
						p.close();

					}*/

					//User object
					/*String user_id = (String) jsonObject.get("user_id");
					System.out.println("User id: " + user_id);

					String name = (String) jsonObject.get("name");
					System.out.println("Name: " + name);

					long review_count = (Long) jsonObject.get("review_count");
					System.out.println("Reviews: " + review_count);

					double avg_stars = (Double) jsonObject.get("average_stars");
					System.out.println("Stars: " + avg_stars);

					JSONObject votes = (JSONObject) jsonObject.get("votes");
					long funny = (Long) votes.get("funny");
					long useful = (Long) votes.get("useful");
					long cool = (Long) votes.get("cool");
					long total_votes = funny + useful + cool;

					String query = "INSERT INTO users VALUES (?, ?, ?, ?, ?)";
					PreparedStatement p = null;
					p = conn.prepareStatement(query);
					p.setString(1, user_id);
					p.setString(2, name);
					p.setLong(3, review_count);
					p.setDouble(4, avg_stars);
					p.setLong(5, total_votes);
					p.executeUpdate();

					p.close();
					 */

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}


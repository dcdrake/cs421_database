package cs421_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JFrame;

public class HelloWorld{
	

	public static void main(String[] args) throws SQLException {
		
	    
		String url = "jdbc:postgresql://localhost:63333/jagabel_project";
		Properties props = new Properties();
		props.setProperty("user","jagabel");
		props.setProperty("password","tnO81cW");
		//props.setProperty("ssl","true");
		Connection conn = DriverManager.getConnection(url, props);
		if(conn.isValid(0)){
			System.out.println("Connection Established");
		}
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT DISTINCT category FROM business_categories ORDER BY category");
		while (rs.next())
		{
			String category  = rs.getString("category");
		    System.out.print('"' + category + '"'+ ", ");
		} rs.close();
		st.close();

		//Example insert statement
		/*String query = "INSERT INTO school VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement p = null;
		p = conn.prepareStatement(query);
		p.setInt(1, 19);
		p.setString(2, "Brown");
		p.setString(3, "75 Waterman St");
		p.setString(4, "Providence");
		p.setString(5, "Rhode Island");
		p.setFloat(6, (float) 41.826758);
		p.setFloat(7, (float) -71.402254);
		p.executeUpdate();

		p.close();

		//Select statement to see the newly inserted data
		st = conn.createStatement();
		rs = st.executeQuery("SELECT * FROM school");
		while (rs.next())
		{
			System.out.print("Column 1 returned ");
			System.out.println(rs.getString(1));
			int school_id  = rs.getInt("school_id");
		    int latitude = rs.getInt("latitude");
		    String state = rs.getString("state");
		    System.out.println("school_id: " + school_id);
		    System.out.println("latitude: " + latitude);
		    System.out.println("state: " + state);
		} rs.close();
		st.close();

		//example delete statement
		st = conn.createStatement();
		st.executeUpdate("DELETE FROM school WHERE school_id > 11");
		st.close();

		//example updates statement
		st = conn.createStatement();
		st.executeUpdate("UPDATE school SET state = 'newTest'");

		//select statement to show the final data
		st = conn.createStatement();
		rs = st.executeQuery("SELECT * FROM school");
		while (rs.next())
		{
			System.out.print("Column 1 returned ");
			System.out.println(rs.getString(1));
			int school_id  = rs.getInt("school_id");
		    int latitude = rs.getInt("latitude");
		    String state = rs.getString("state");
		    System.out.println("school_id: " + school_id);
		    System.out.println("latitude: " + latitude);
		    System.out.println("state: " + state);
		} rs.close();
		st.close();

		conn.close(); 	*/
	}

}
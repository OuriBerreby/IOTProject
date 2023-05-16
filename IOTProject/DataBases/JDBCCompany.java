package DataBases;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class JDBCCompany {
	
	public static int addCompany(int company_ID, String company_name, String address, String phone_number) throws SQLException{
		Connection conn = ConnectionTools.getMySQLConnection();
		Statement st = conn.createStatement();
		int rows = 5;

		try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO companies (company_ID, company_name, address, phone_number) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setObject(1, company_ID);
            preparedStatement.setObject(2, company_name);
            preparedStatement.setObject(3, address);
            preparedStatement.setObject(4, phone_number);
            rows = preparedStatement.executeUpdate();
              
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
		st.close();
		conn.close();
		
		return rows;
	}

}

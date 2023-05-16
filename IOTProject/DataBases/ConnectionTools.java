package DataBases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTools {
	private static final String USER = "root";
	private static final String PASS = "1905";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/IOTCompanies";
	private static final String MONGO_URL = "jdbc:mysql://localhost:3306/IOTDataBase";
	
	public static Connection getMySQLConnection() throws SQLException {
		try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
		return DriverManager.getConnection(DB_URL,USER,PASS);
	}
	
	public static Connection getMongoDBConnection() throws SQLException {
		try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
		return DriverManager.getConnection(MONGO_URL,USER,PASS);
	}

}

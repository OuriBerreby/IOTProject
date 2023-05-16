package DataBases;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCProduct {
	public static int addProduct(int company_ID, String product_id, String product_name, int price) throws SQLException{
		Connection conn = ConnectionTools.getMySQLConnection();
		
		Statement st = conn.createStatement();
		int rows = 5;	
		
		try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO products (company_ID, product_id, product_name, price) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setObject(1, company_ID);
            preparedStatement.setObject(2, product_id);
            preparedStatement.setObject(3, product_name);
            preparedStatement.setObject(4, price);
            rows = preparedStatement.executeUpdate();
              
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
		st.close();
		conn.close();
		
		return rows;
	}
}

package appServerServlets.companies;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import DataBases.JDBCCompany;
import DataBases.JDBCProduct;
import JSONServices.JSONService;

@WebServlet(name = "registerProductServlet", urlPatterns = {"/productRegister"})
public class RegisterProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	  public RegisterProductServlet() {
	        super();
	    }

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("In Product");
			
			JSONObject json = JSONService.createJsonObject(request);
		    int id_company = json.getInt("company_ID");
		    String product_id = json.getString("product_id");
		    String product_name = json.getString("product_name");
		    int price = json.getInt("price");
		    int rows = 0;
		    try {
				rows = JDBCProduct.addProduct(id_company, product_id, product_name, price);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/gatewayServer");
			response.getWriter().println("NB of rows" + rows);	
		}
		
		
		protected void doPut(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {   
	    }
		

		  
		  public static String requestToString(HttpServletRequest request) throws IOException {
		        InputStream inputStream = request.getInputStream();
		        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		        StringBuilder stringBuilder = new StringBuilder();
		        String line;
		        while((line = reader.readLine()) != null) {
		            stringBuilder.append(line);
		        }
		    
		        return stringBuilder.toString();
		               
		    } 

}

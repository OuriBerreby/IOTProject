package appServerServlets.companies;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

import org.json.JSONObject;

import DataBases.JDBCCompany;
import JSONServices.JSONService;
import gatewayServer.SingletonCommandFactory;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.*;


@WebServlet(name = "createCompanyServlet", urlPatterns = {"/companyRegister"})
public class CreateCompanyServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
      
		public CreateCompanyServlet() {
	        super();
	   	}

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}

		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		    JSONObject json = JSONService.createJsonObject(request);
		    int id_company = json.getInt("company_ID");
		    String company_name = json.getString("company_name");
		    String adress = json.getString("address");
		    String phone_number = json.getString("phone_number");
		    int rows = 0;
		    try {
				rows = JDBCCompany.addCompany(id_company, company_name, adress, phone_number);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    request.setAttribute("jsonData", json.toString());

		    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/iotGateWay");
	        dispatcher.forward(request, response);
		    
		    // Envoyer JSON 
			/*URL url = new URL("http://localhost:8080/IOT/gate");
			// Create a new HttpURLConnection object
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			
			String stringRequest = requestToString(request);
			con.setRequestProperty("Content-Length", Integer.toString(stringRequest.length()));

			// Enable output and write the request body to the output stream
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(stringRequest);
			out.flush();
			out.close();
			
			// Get the response code and response message
			int responseCode = con.getResponseCode();
			String responseMessage = con.getResponseMessage();*/
			response.getWriter().println("INSERT row number : " + rows);	
		}
		
		
		protected void doPut(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
			//JSONObject jason = CreateJsonObj(request);
			//int rows = UpdateDB(jason);
			 //response.getWriter().println("UPDATE row number : " + rows + " " + jason.getString("name"));     
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



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
		    try {
				JDBCCompany.addCompany(id_company, company_name, adress, phone_number);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    request.setAttribute("jsonData", json.toString());

		    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/iotGateWay");
	        dispatcher.forward(request, response);
		}
		
		
		protected void doPut(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
			doPost(request, response);
	    }
		
}



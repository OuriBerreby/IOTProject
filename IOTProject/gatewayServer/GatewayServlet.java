package gatewayServer;

import gatewayServer.ThreadPool.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.json.JSONObject;

import DataBases.IOTDataBase;
import DataBases.JDBCCompany;
import JSONServices.JSONService;

@WebServlet(name = "gateWayServlet", urlPatterns = {"/iotGateWay"})
public class GatewayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SingletonCommandFactory scf = SingletonCommandFactory.getInstance(); 
	ThreadPool threadPool = new ThreadPool(30);
   
    public GatewayServlet() {
        super();
        scf.addCommand(Commands_ID.REGISTER_COMPANY, this::companyRegister);
        //scf.addCommand(Commands_ID.REGISTER_USER);
        //scf.addCommand(Commands_ID.REGISTER_PRODUCT);
        //scf.addCommand(Commands_ID.UPDATE_IOT);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonData = (String) request.getAttribute("jsonData");
		JSONObject jsonObject = new JSONObject(jsonData);
		String command = jsonObject.getString("command_id");
	    Commands_ID command_id = Commands_ID.fromId(command);
	    threadPoolTransfer(command_id, jsonObject, response);
	}
	
	private int companyRegister(JSONObject json)  {
	    int id_company = json.getInt("company_ID");
	    String company_name = json.getString("company_name");
	    String adress = json.getString("address");
	    String phone_number = json.getString("phone_number");
	    
		int res = 0;
		res = IOTDataBase.mongoDBaddCompany(id_company, company_name, adress, phone_number);
		
		return res;
	}
	
	private void threadPoolTransfer(Commands_ID command_id, JSONObject jsonObject, HttpServletResponse response) {
		Callable callableTask = new Callable() {
			@Override
			public Object call() throws Exception {
				return scf.applyCommand(command_id, jsonObject);
			};
		};
		
		Callable futureCallable = new Callable () {
			@Override
			public Object call() {
				Future future = threadPool.submit(callableTask);
				try {
					Object getFromFuture = future.get();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
				
			};
		};
		threadPool.submit(futureCallable);
	}

}

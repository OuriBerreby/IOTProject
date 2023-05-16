package gatewayServer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Function;

import org.json.JSONObject;

import DataBases.JDBCCompany;
import JSONServices.JSONService;

public class SingletonCommandFactory  {
	
	private HashMap<Commands_ID, Function<JSONObject, Integer>> listOfCommands = new HashMap<>();
	private static SingletonCommandFactory  scf = new SingletonCommandFactory();
	private SingletonCommandFactory() {}
	
	// when company modify JarLoader
	public void addCommand(Commands_ID command, Function<JSONObject, Integer> query) {
		listOfCommands.put(command, query);
	}
	
	
	public Object applyCommand(Commands_ID command , JSONObject json) {
		Function sqlCommand = listOfCommands.get(command);
		return sqlCommand.apply(json);
	}
	
	public static SingletonCommandFactory getInstance(){
		return scf;
	}
	
}

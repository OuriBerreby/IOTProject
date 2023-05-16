package JSONServices;

import java.io.IOException;
import java.util.stream.Collectors;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;

public class JSONService {
	
	public static JSONObject createJsonObject(HttpServletRequest request) throws IOException {

        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        return new JSONObject(requestBody);
    }
}

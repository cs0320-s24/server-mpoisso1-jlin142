package edu.brown.cs.student.main.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.acs.BroadbandAPI;
import edu.brown.cs.student.main.acs.BroadbandData;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * This is the Broadband Handler class. It implements Route.
 */
public class BroadbandHandler implements Route {
  private final BroadbandAPI state;

  /**
   * This constructor accepts the current state.
   *
   * @param state
   */
  public BroadbandHandler(BroadbandAPI state){
    this.state = state;
  }

  /**
   * This method handles the api request and response.
   *
   * @param request server request
   * @param response server response
   * @return json object
   */
  @Override
  public Object handle(Request request, Response response) {
    Moshi moshi = new Moshi.Builder().build();
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
    JsonAdapter<BroadbandData> broadbandDataAdapter = moshi.adapter(BroadbandData.class);
    Map<String, Object> responseMap = new HashMap<>();

    String stateName = request.queryParams("stateName");
    String countyName = request.queryParams("countyName");

    if (stateName == null || countyName == null) {
      responseMap.put("type", "error");
      responseMap.put("error_type", "datasource");
      responseMap.put("details", "Input values cannot be null!");
      responseMap.put("stateName", stateName);
      responseMap.put("countyName", countyName);
      Date date = new Date();
      responseMap.put("time", date);
      return adapter.toJson(responseMap);
    }
    try {
      double accessPercentage = state.getAccessPercentage(stateName, countyName);
      responseMap.put("type", "success");
      responseMap.put("accessPercentage",
          broadbandDataAdapter.toJson(new BroadbandData(accessPercentage)));
      responseMap.put("stateName", stateName);
      responseMap.put("countyName", countyName);
      Date date = new Date();
      responseMap.put("time", date);
      return adapter.toJson(responseMap);
    } catch (Exception e) {
      responseMap.clear();
      responseMap.put("type", "error");
      responseMap.put("error_type", "datasource");
      responseMap.put("details", e.getMessage());
      responseMap.put("stateName", stateName);
      responseMap.put("countyName", countyName);
      Date date = new Date();
      responseMap.put("time", date);
      return adapter.toJson(responseMap);
    }
  }
}
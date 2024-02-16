package edu.brown.cs.student.main.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.CSV.ParsedData;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * This is the viewcsv handler class.
 */
public class viewcsv implements Route {
  private ParsedData state;

  /**
   * The constructor of the searchcsv class.
   *
   * @param state an instance of the ParsedData class that holds the most recently loaded CSV
   */
  public viewcsv(ParsedData state) {
    this.state = state;
  }

  /**
   * The handle method that is defined in this class is declared in the Route Interface.
   *
   * @param request The request object providing information about the HTTP request
   * @param response The response object providing functionality for modifying the response
   * @return returns a responseMap with key value pairs
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    Map<String, Object> responseMap = new HashMap<>();
    String headers = request.queryParams("headers");
    if (this.state != null){
      if (!this.state.getLoadedVal()) {
        responseMap.put("error_datasource", "No CSV file has been loaded!");
        return this.serialize(responseMap);
      }
    }
    else{
      responseMap.put("error_datasource", "No CSV file has been loaded!");
      return this.serialize(responseMap);
    }
    if(headers == null) {
      responseMap.put("error_bad_request","request was missing the headers field");
      return this.serialize(responseMap);
    }else if (headers.equals("true")){
      responseMap.put("result","success");
      responseMap.put("data",this.state.getContent());
      return this.serialize(responseMap);
    }
    return null;
  }

  private String serialize(Map<String, Object> responseMap) {
    Type stringObjectMap = Types.newParameterizedType(Map.class,String.class,Object.class);
    try {
      Moshi moshi = new Moshi.Builder().build();
      JsonAdapter<Map<String,Object>> adapter = moshi.adapter(stringObjectMap);
      return adapter.toJson(responseMap);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}

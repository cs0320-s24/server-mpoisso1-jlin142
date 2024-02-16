package edu.brown.cs.student.main.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.CSV.CSVParser;
import edu.brown.cs.student.main.CSV.ParsedData;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * This is the loadcsv handler class.
 */
public class loadcsv implements Route {
  private final ParsedData data;

  /**
   * The constructor of the loadcsv class.
   *
   * @param data an instance of the ParsedData class that holds the most recently loaded CSV
   */
  public loadcsv(ParsedData data) {
    this.data = data;
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
    String filename = request.queryParams("filepath");
    Map<String, Object> responseMap = new HashMap<>();
    if (!filename.contains("/Data")){
      responseMap.put("error_datasource", "File not accessible!");
      return this.serialize(responseMap);
    }
    try {
      FileReader fReader = new FileReader(filename);
      CSVParser<List<List<String>>> parser = new CSVParser<>(fReader,"true");
      this.data.setContent(parser.parse(fReader,"true"));
      this.data.setLoadedVal(true);
      responseMap.put("result", "success");
      responseMap.put("filepath", filename);
      return this.serialize(responseMap);
    } catch (FileNotFoundException e) {
      responseMap.put("error_datasource", filename);
      return this.serialize(responseMap);
    }
  }

  /**
   * This helper method converts the passed into Map into a JSON Map.
   *
   * @param responseMap A Map of String and Object key pairs that is returned
   * @return returns a converted JSON object
   */
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

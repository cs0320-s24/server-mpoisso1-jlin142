package edu.brown.cs.student.main.Server;

import edu.brown.cs.student.main.CSV.CSVParser;
import edu.brown.cs.student.main.CSV.ParsedData;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * This is the loadcsv handler class.
 */
public class LoadCSVHandler implements Route {
  private final ParsedData data;
  private final Serializer serializer;

  /**
   * The constructor of the loadcsv class.
   *
   * @param data an instance of the ParsedData class that holds the most recently loaded CSV
   */
  public LoadCSVHandler(ParsedData data) {
    this.data = data;
    this.serializer = new Serializer();
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

    String filename;
    try {
      filename = request.queryParams("filepath");
      if (!filename.contains("/Data")){
        responseMap.put("message", "File not accessible!");
        return this.serializer.serialize(responseMap);
      }
      FileReader fReader = new FileReader(filename);
      CSVParser<List<List<String>>> parser = new CSVParser<>();
      this.data.setContent(parser.parse(fReader,true));
      this.data.setLoadedVal(true);
      responseMap.put("result", "success");
      responseMap.put("filepath", filename);
      return this.serializer.serialize(responseMap);
    } catch (FileNotFoundException e) {
      responseMap.put("result", "error");
      responseMap.put("message", "filename not found");
      return this.serializer.serialize(responseMap);
    }
  }

//  /**
//   * This helper method converts the passed into Map into a JSON Map.
//   *
//   * @param responseMap A Map of String and Object key pairs that is returned
//   * @return returns a converted JSON object
//   */
//  private String serialize(Map<String, Object> responseMap) {
//    Type stringObjectMap = Types.newParameterizedType(Map.class,String.class,Object.class);
//    try {
//      Moshi moshi = new Moshi.Builder().build();
//      JsonAdapter<Map<String,Object>> adapter = moshi.adapter(stringObjectMap);
//      return adapter.toJson(responseMap);
//    } catch (Exception e) {
//      e.printStackTrace();
//      throw e;
//    }
//  }
}
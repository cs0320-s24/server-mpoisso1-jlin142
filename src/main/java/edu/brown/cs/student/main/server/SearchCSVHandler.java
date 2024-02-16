package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.csv.CSVSearcher;
import edu.brown.cs.student.main.csv.ParsedData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * This is the searchcsv handler class.
 */
public class SearchCSVHandler implements Route {
  private final ParsedData state;
  private final Serializer serializer;

  /**
   * The constructor of the searchcsv class.
   *
   * @param state an instance of the ParsedData class that holds the most recently loaded CSV
   */
  public SearchCSVHandler(ParsedData state) {
    this.state = state;
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
    CSVSearcher searcher = new CSVSearcher();
    String headers = request.queryParams("headers");
    String target = request.queryParams("target");
    String colName = request.queryParams("colName");
    String colIndex = request.queryParams("colIndex");
    if (target == null) {
      responseMap.put("error_bad_request","request was missing the target field");
      return this.serializer.serialize(responseMap);
    }
    if (this.state != null) {
      if (!this.state.getLoadedVal()) {
        responseMap.put("error_datasource", "No CSV file has been loaded!");
        return this.serializer.serialize(responseMap);
      }
    }
    if (colName == null && colIndex == null && headers != null){
      boolean header=Boolean.parseBoolean(headers);
      List<List<String>> result = searcher.search(this.state.getContent(),target,header);
      responseMap.put("result","success");
      responseMap.put("parameters",List.of(target,headers));
      responseMap.put("data",this.state.getContent());
      responseMap.put("retrieved data",result);
      return this.serializer.serialize(responseMap);
    }
    else if(headers != null && colIndex != null){
      Integer index = Integer.getInteger(colIndex);
      boolean header=Boolean.parseBoolean(headers);
      List<List<String>> result = searcher.search(this.state.getContent(),target,header,index);
      responseMap.put("success", this.state);
      responseMap.put("parameters", List.of(target,colName));
      responseMap.put("data",this.state.getContent());
      responseMap.put("retrieved data",result);
      return this.serializer.serialize(responseMap);
    }
    else if(colName != null){
      List<List<String>> result = searcher.search(this.state.getContent(),target,colName);
      responseMap.put("success", this.state);
      responseMap.put("parameters",List.of(target,colName));
      responseMap.put("data",this.state.getContent());
      responseMap.put("retrieved data",result);
      return this.serializer.serialize(responseMap);
    }
    return null;
  }

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

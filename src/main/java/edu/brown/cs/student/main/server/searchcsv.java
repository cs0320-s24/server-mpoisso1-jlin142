package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.CSV.CSVSearcher;
import edu.brown.cs.student.main.CSV.ParsedData;
import edu.brown.cs.student.main.SerializerDeserializer.Serializer;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class searchcsv implements Route {
  private final ParsedData state;

  public searchcsv(ParsedData state) {
    this.state = state;
  }

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
      Serializer serializer = new Serializer();
      return serializer.serialize(responseMap);
    }
    if (colName == null && colIndex == null){
    }


    if (this.state != null) {
      if (!this.state.getLoadedVal()) {
        responseMap.put("error_datasource", "No CSV file has been loaded!");
        Serializer serializer = new Serializer();
        return serializer.serialize(responseMap);
      }
    }
    responseMap.put("success", this.state);
    Serializer serializer = new Serializer();
    return serializer.serialize(responseMap);
  }
}

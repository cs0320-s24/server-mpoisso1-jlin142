package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.CSV.CSVParser;
import edu.brown.cs.student.main.CSV.ParsedData;
import edu.brown.cs.student.main.SerializerDeserializer.Serializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class viewcsv implements Route {
  private ParsedData state;

  public viewcsv(ParsedData state) {
    this.state = state;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    Map<String, Object> responseMap = new HashMap<>();
    String headers = request.queryParams("headers");
    if (this.state != null){
      if (!this.state.getLoadedVal()) {
        responseMap.put("error_datasource", "No CSV file has been loaded!");
        Serializer serializer = new Serializer();
        return serializer.serialize(responseMap);
      }
    }
    else{
      responseMap.put("error_datasource", "No CSV file has been loaded!");
      Serializer serializer = new Serializer();
      return serializer.serialize(responseMap);
    }
    if(headers == null) {
      responseMap.put("error_bad_request","request was missing the headers field");
      Serializer serializer = new Serializer();
      return serializer.serialize(responseMap);
    }
    responseMap.put("result","success");
    CSVParser<List<List<String>>> parser = new CSVParser<>(this.state.getfReader(),headers);
    List<List<String>> data = parser.parse(this.state.getfReader(),headers);
    this.state.setContent(data);
    responseMap.put("data",data);
    Serializer serializer = new Serializer();
    return serializer.serialize(responseMap);

  }
}

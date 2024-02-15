package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.SerializerDeserializer.Serializer;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class viewcsv implements Route {
  private final CSVData state;
  public viewcsv(CSVData state){
    this.state = state;

  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    Map<String, Object> responseMap = new HashMap<>();
    if (this.state == null) {
      responseMap.put("error_datasource","No CSV file has been loaded!");
      return new Serializer(responseMap);
    }
    responseMap.put("data",this.state);
    return new Serializer(responseMap);

  }
}

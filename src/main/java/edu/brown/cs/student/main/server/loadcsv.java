package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.CSV.ParsedData;
import edu.brown.cs.student.main.SerializerDeserializer.Serializer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class loadcsv implements Route {
  public ParsedData data;

  public loadcsv(ParsedData data) {
    this.data = data;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String filename = request.queryParams("filepath");
    Map<String, Object> responseMap = new HashMap<>();
    try {
      FileReader fReader = new FileReader(filename);
      this.data.setfReader(fReader);
      this.data.setLoadedVal(true);
      responseMap.put("result", "success");
      responseMap.put("filepath", filename);
      Serializer serializer = new Serializer();
      return serializer.serialize(responseMap);
    } catch (FileNotFoundException e) {
      responseMap.put("error_datasource", filename);
      Serializer serializer = new Serializer();
      return serializer.serialize(responseMap);
    }
  }
}

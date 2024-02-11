package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.SerializerDeserializer.Serializer;
import java.io.FileReader;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class loadcsv implements Route {
  public Serializer serializer;

  public loadcsv(String filepath, Serializer serializer){
    this.serializer = serializer;
  }


  @Override
  public Object handle(Request request, Response response) throws Exception {
    String filename = request.queryParams("filename");
    try{
      FileReader fReader = new FileReader(filename);
      return
    }
    catch{

    }
    return null;
  }

  public record LCSVSuccessResponse(String response_type, Map<String, Object> responseMap) {
    public LCSVSuccessResponse(Map<String, Object> responseMap) {
      this("success", responseMap);
    }
    }


}

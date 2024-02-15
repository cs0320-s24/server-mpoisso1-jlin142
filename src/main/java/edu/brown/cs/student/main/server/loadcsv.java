package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.SerializerDeserializer.Serializer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class loadcsv implements Route {


  public loadcsv(){
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String filename = request.queryParams("filepath");
    String headers = request.queryParams("headers");
    Map<String, Object> responseMap = new HashMap<>();
    try{
      FileReader fReader = new FileReader(filename);
      new CSVData().CSVData(fReader,headers);
      responseMap.put("success",filename);
      return new Serializer(responseMap);
    }
    catch(FileNotFoundException e){
      responseMap.put("error_datasource",filename);
      return new Serializer(responseMap);
    }
  }
}

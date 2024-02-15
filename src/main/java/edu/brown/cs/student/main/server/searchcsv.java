package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.SerializerDeserializer.Serializer;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class searchcsv implements Route {
  private final CSVData state;

  public searchcsv(CSVData state){
    this.state = state;

  }


  @Override
  public Object handle(Request request, Response response) throws Exception {
    Map<String, Object> responseMap = new HashMap<>();
    if (this.state == null) {
      responseMap.put("error_datasource","No CSV file has been loaded!");
      return new Serializer(responseMap);
    }
    String searchVal = request.queryParams("searchval");
    String colName = request.queryParams("colName");
    String colName = request.queryParams("colName");

    try{
      CSVSearcher searcher = new CSVSearcher();
      searcher.search(this.state,searchVal,)
      responseMap.put("success",filename);
      return new Serializer(responseMap);
    }
    catch(FileNotFoundException e){
      responseMap.put("error_datasource",filename);
      return new Serializer(responseMap);
    }
  }
}

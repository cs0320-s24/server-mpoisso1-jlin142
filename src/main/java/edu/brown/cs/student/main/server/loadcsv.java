package edu.brown.cs.student.main.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import spark.Request;
import spark.Response;
import spark.Route;

public class loadcsv implements Route {

  public loadcsv(String filepath){

  }


  @Override
  public Object handle(Request request, Response response) throws Exception {
    Set<String> params = request.queryParams();
    Map<String, Object> responseMap = new HashMap<>();
    return null;
  }


}

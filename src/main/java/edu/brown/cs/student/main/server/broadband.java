package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.activity.ACSDataSource;
import edu.brown.cs.student.main.activity.Activity;
import edu.brown.cs.student.main.activity.ActivityAPIUtilities;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import spark.Request;
import spark.Response;
import spark.Route;

public class broadband implements Route {
  private ACSDataSource acsDataSource;

  public broadband(){

  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String state = request.queryParams("state");
    String county = request.queryParams("county");

    Map<String, Object> responseMap = new HashMap<>();
    try {
      String stateJson = this.sendRequest("state");
      String countyJson = this.sendRequest("county");
      Activity activity = ActivityAPIUtilities.deserializeActivity(stateJson);

      // Adds results to the responseMap
      responseMap.put("state", "success");
      responseMap.put("county", activity);
      return responseMap;
    } catch (Exception e) {
      e.printStackTrace();
      // This is a relatively unhelpful exception message. An important part of this sprint will be
      // in learning to debug correctly by creating your own informative error messages where Spark
      // falls short.
      responseMap.put("result", "Exception");
    }
    return responseMap;
  }

    private String sendRequest(String info) throws URISyntaxException, IOException, InterruptedException {
      // Build a request to this BoredAPI. Try out this link in your browser, what do you see?
      // TODO 1: Looking at the documentation, how can we add to the URI to query based
      // on participant number?
      HttpRequest buildBoredApiRequest =
          HttpRequest.newBuilder()
              .uri(new URI("http://www.boredapi.com/api/activity/"))
              .GET()
              .build();

      // Send that API request then store the response in this variable. Note the generic type.
      HttpResponse<String> sentBoredApiResponse =
          HttpClient.newBuilder()
              .build()
              .send(buildBoredApiRequest, HttpResponse.BodyHandlers.ofString());

      return sentBoredApiResponse.body();
    }
}

package edu.brown.cs.student.main.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.acs.BroadbandAPI;
import edu.brown.cs.student.main.acs.BroadbandData;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class BroadbandHandler implements Route {
  private final BroadbandAPI state;


  public BroadbandHandler(BroadbandAPI state){
    this.state = state;
  }

  @Override
  public Object handle(Request request, Response response) {
    Moshi moshi = new Moshi.Builder().build();
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
    JsonAdapter<BroadbandData> broadbandDataAdapter = moshi.adapter(BroadbandData.class);
    Map<String, Object> responseMap = new HashMap<>();

    String stateName = request.queryParams("stateName");
    String countyName = request.queryParams("countyName");
    try {
      double accessPercentage = state.getAccessPercentage(stateName, countyName);
      responseMap.put("type", "success");
      responseMap.put("accessPercentage",
          broadbandDataAdapter.toJson(new BroadbandData(accessPercentage)));
      return adapter.toJson(responseMap);
    } catch (Exception e) {
      // throw error
      return null;
    }
  }

    // if stateName or countyName is null, throw error



//    Map<String, Object> responseMap = new HashMap<>();
//    try {
//      String stateJson = this.sendRequest("state");
//      String countyJson = this.sendRequest("county");
//      Activity activity = ActivityAPIUtilities.deserializeActivity(activityJson);
//      // Adds results to the responseMap
//      responseMap.put("state", "success");
//      responseMap.put("county", activity);
//      return responseMap;
//    } catch (Exception e) {
//      e.printStackTrace();
//      // This is a relatively unhelpful exception message. An important part of this sprint will be
//      // in learning to debug correctly by creating your own informative error messages where Spark
//      // falls short.
//      responseMap.put("result", "Exception");
//    }
//    return responseMap;
//  }

//    private String sendRequest(String info) throws URISyntaxException, IOException, InterruptedException {
//      // Build a request to this BoredAPI. Try out this link in your browser, what do you see?
//      // TODO 1: Looking at the documentation, how can we add to the URI to query based
//      // on participant number?
//      HttpRequest buildBoredApiRequest =
//          HttpRequest.newBuilder()
//              .uri(new URI("http://www.boredapi.com/api/activity/"))
//              .GET()
//              .build();
//
//      // Send that API request then store the response in this variable. Note the generic type.
//      HttpResponse<String> sentBoredApiResponse =
//          HttpClient.newBuilder()
//              .build()
//              .send(buildBoredApiRequest, HttpResponse.BodyHandlers.ofString());
//
//      return sentBoredApiResponse.body();
//    }
}

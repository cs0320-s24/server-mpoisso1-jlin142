package edu.brown.cs.student.main.acs;

//import static spark.Spark.connect;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okio.Buffer;

/**
 * This is the ACS Broadband API class
 */
public class BroadbandAPI {
  private final String API_KEY = "317cfd7983252344b882203a9f6bbaa62cfc8885";
  private final Map<String, String> stateCodeMap;

  /**
   * This is the constructor for the BroadbandAPI class. It serves as an intermediary for a user
   * getting broadband data from the ACS API.
   *
   * @throws IOException
   * @throws DatasourceException
   */
  public BroadbandAPI() throws IOException, DatasourceException {
    this.stateCodeMap = this.initStateCodeMap();
  }

  /**
   * Gets the broadband access percentage for a state and county
   *
   * @param stateName name of state
   * @param countyName name of county
   * @return
   * @throws DatasourceException
   * @throws IOException
   */
  public double getAccessPercentage(String stateName, String countyName)
      throws DatasourceException, IOException {
    // if is mock data, do something

    // else, do something else

    String stateCode = "";
    String countyCode = "";

    if (this.stateCodeMap.containsKey(stateName)) {
      stateCode = this.stateCodeMap.get(stateName);
      Map<String, String> countyCodeMap = this.getCountyCodeMap(stateCode);
      if (countyCodeMap.containsKey(countyName)) {
        countyCode = countyCodeMap.get(countyName);
      }
    }

    URL requestURL = new URL(
        "https://api.census.gov/data/2021/acs/acs1/subject/variables?get=NAME,S2802_C03_022E&for=county:"
            +
            countyCode +
            "&in=state:" +
            stateCode +
            "&key=" +
            this.API_KEY
    );
    HttpURLConnection clientConnection = connect(requestURL);
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<List<List<String>>> adapter = moshi.adapter(
        Types.newParameterizedType(List.class, List.class, String.class)
    );
    List<List<String>> response = adapter.fromJson(
        new Buffer().readFrom(clientConnection.getInputStream())
    );
    clientConnection.disconnect();

    if (response.size() > 1 && response.get(1).size() > 0) {
      return Double.parseDouble(response.get(1).get(1));
    } else {
      throw new DatasourceException("There exists no data for " + countyName + ", " + stateName);
    }
  }


  /**
   * Gets the county code map
   *
   * @param stateCode code of state to find county
   * @return map of county name to county code
   * @throws DatasourceException
   * @throws IOException
   */
  public Map<String, String> getCountyCodeMap(String stateCode)
      throws DatasourceException, IOException {
    URL requestURL = new URL(
        "https://api.census.gov/data/2010/dec/sf1?get=NAME&for=county:*&in=state:"
            + stateCode);
    HttpURLConnection clientConnection = connect(requestURL);
    Moshi moshi = new Moshi.Builder().build();

    JsonAdapter<List<List<String>>> adapter = moshi.adapter(
        Types.newParameterizedType(List.class, List.class, String.class)
    );
    List<List<String>> response = adapter.fromJson(
        new Buffer().readFrom(clientConnection.getInputStream())
    );
    clientConnection.disconnect();

    Map<String, String> countyCodeMap = new HashMap<>();
    for (List<String> row : response) {
      String countyNameAndState = row.get(0);
      String[] splitCountyNameAndState = countyNameAndState.split(",");
      String countyName = splitCountyNameAndState[0];
      String countyCode = row.get(2);
      countyCodeMap.put(countyName, countyCode);
    }

    return countyCodeMap;
  }

  /**
   * Initializes the state code map
   *
   * @return Map from state name to state code
   * @throws DatasourceException
   * @throws IOException
   */
  private Map<String, String> initStateCodeMap() throws DatasourceException, IOException {
    URL requestURL = new URL("https://api.census.gov/data/2010/dec/sf1?get=NAME&for=state:*");
    HttpURLConnection clientConnection = connect(requestURL);
    Moshi moshi = new Moshi.Builder().build();

    JsonAdapter<List<List<String>>> adapter = moshi.adapter(
        Types.newParameterizedType(List.class, List.class, String.class)
    );
    List<List<String>> response = adapter.fromJson(
        new Buffer().readFrom(clientConnection.getInputStream())
    );
    clientConnection.disconnect();

    Map<String, String> stateCodeMap = new HashMap<>();
    for (List<String> row : response) {
      if (row.get(0).equals("NAME")) {
        continue;
      }
      // stateName, stateCode
      stateCodeMap.put(row.get(0), row.get(1));
    }

    return stateCodeMap;
  }

  /**
   * Establishes a http connection to the api server
   *
   * @param requestURL url requested to connect to
   * @return the connection
   * @throws IOException
   * @throws DatasourceException
   */
  private static HttpURLConnection connect(URL requestURL) throws IOException, DatasourceException {
    URLConnection urlConnection = requestURL.openConnection();
    if(! (urlConnection instanceof HttpURLConnection)) {
      // throw error
      throw new DatasourceException("unexpected: result of connection wasn't HTTP");
    }
    HttpURLConnection clientConnection = (HttpURLConnection) urlConnection;
    clientConnection.connect();
    if (clientConnection.getResponseCode() != 200) {
      // throw error
      throw new DatasourceException("unexpected: API connection not success status "+clientConnection.getResponseMessage());
    }

    return clientConnection;
  }



}

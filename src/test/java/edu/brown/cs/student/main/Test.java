package edu.brown.cs.student.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.CSV.ParsedData;
import edu.brown.cs.student.main.server.broadband;
import edu.brown.cs.student.main.server.loadcsv;
import edu.brown.cs.student.main.server.searchcsv;
import edu.brown.cs.student.main.server.viewcsv;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import spark.Spark;

public class Test {

  @BeforeAll
  public static void setup_before_everything() {
    Spark.port(0);

    Logger.getLogger("").setLevel(Level.WARNING);
  }

  /**
   * Shared state for all tests. We need to be able to mutate it (adding recipes etc.) but never
   * need to replace the reference itself. We clear this state out after every test runs.
   */
  static ParsedData data = new ParsedData(false,null);

  @BeforeEach
  public void setup() {
    data = new ParsedData(false,null);

    Spark.get("loadcsv", new loadcsv(data));
    Spark.get("searchcsv", new searchcsv(data));
    Spark.get("viewcsv", new viewcsv(data));
    Spark.get("broadband", new broadband());
    Spark.init();
    Spark.awaitInitialization();
  }

  @AfterEach
  public void teardown() {
    Spark.unmap("loadcsv");
    Spark.unmap("viewcsv");
    Spark.unmap("searchcsv");
    Spark.get("broadband", new broadband());
    Spark.awaitStop();
  }

  private static HttpURLConnection tryRequest(String apiCall) throws IOException {
    URL requestURL = new URL("http://localhost:" + Spark.port() + "/" + apiCall);
    HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();
    clientConnection.setRequestMethod("GET");
    clientConnection.connect();
    return clientConnection;
}

  @org.junit.jupiter.api.Test
  public void testAPINoFilepath() throws IOException {
    HttpURLConnection clientConnection = tryRequest("loadcsv");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();

    Type stringObjectMap = Types.newParameterizedType(Map.class,String.class,Object.class);
    Map<String, Object> response = moshi.adapter(stringObjectMap)
            .fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    clientConnection.disconnect();
  }

}

package edu.brown.cs.student.main.Server;

import static spark.Spark.after;

import edu.brown.cs.student.main.CSV.ParsedData;
import edu.brown.cs.student.main.ACS.BroadbandAPI;
import edu.brown.cs.student.main.ACS.DatasourceException;
import java.io.IOException;
import spark.Spark;

public class Server {
  private static ParsedData CSVState;
  private static BroadbandAPI broadbandState;

  public Server() {
  }


  public static void main(String[] args) throws DatasourceException, IOException {
    int port = 3232;
    Spark.port(port);

    after((request, response) -> {
      response.header("Access-Control-Allow-Origin", "*");
      response.header("Access-Control-Allow-Methods", "*");
    });

    CSVState = new ParsedData(false,null);
    broadbandState = new BroadbandAPI();
    Spark.get("loadcsv", new LoadCSVHandler(CSVState));
    Spark.get("searchcsv", new SearchCSVHandler(CSVState));
    Spark.get("viewcsv", new ViewCSVHandler(CSVState));
    Spark.get("broadband", new BroadbandHandler(broadbandState));
    Spark.init();
    Spark.awaitInitialization();

    // Notice this link alone leads to a 404... Why is that?
    System.out.println("Server started at http://localhost:" + port);
  }
}

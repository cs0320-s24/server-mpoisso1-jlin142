package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import edu.brown.cs.student.main.CSV.ParsedData;
import spark.Spark;

public class Server {
  private static ParsedData state;

  public Server() {
  }


  public static void main(String[] args) {
    int port = 3232;
    Spark.port(port);

    state = new ParsedData(null,false,null);
    Spark.get("loadcsv", new loadcsv(state));
    Spark.get("searchcsv", new searchcsv(state));
    Spark.get("viewcsv", new viewcsv(state));
    Spark.get("broadband", new broadband());
    Spark.init();
    Spark.awaitInitialization();

    // Notice this link alone leads to a 404... Why is that?
    System.out.println("Server started at http://localhost:" + port);
  }
}

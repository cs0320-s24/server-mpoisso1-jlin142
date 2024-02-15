package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import spark.Spark;

public class Server {
  public static void main(String[] args) {
    int port = 3232;
    Spark.port(port);

    // Setting up the handler for the GET /order and /activity endpoints
    Spark.get("searchcsv", new searchcsv());
    Spark.get("loadcsv", new loadcsv());
    Spark.get("viewcsv", new viewcsv());
    Spark.get("broadband", new BroadbandHandler());
    Spark.init();
    Spark.awaitInitialization();

    // Notice this link alone leads to a 404... Why is that?
    System.out.println("Server started at http://localhost:" + port);
  }
}

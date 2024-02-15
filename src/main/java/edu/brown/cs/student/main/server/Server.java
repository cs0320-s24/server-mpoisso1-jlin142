package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import spark.Spark;

public class Server {
  private static CSVData state;

  public Server(CSVData state){
    this.state = state;
  }
  public static void main(String[] args) {
    int port = 3232;
    Spark.port(port);

    // Setting up the handler for the GET /order and /activity endpoints
    Spark.get("searchcsv", new searchcsv(this.state));
    Spark.get("loadcsv", new loadcsv());
    Spark.get("viewcsv", new viewcsv(this.state));
    Spark.get("broadband", new broadband());
    Spark.init();
    Spark.awaitInitialization();

    // Notice this link alone leads to a 404... Why is that?
    System.out.println("Server started at http://localhost:" + port);
  }
}

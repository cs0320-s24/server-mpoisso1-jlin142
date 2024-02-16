package edu.brown.cs.student.main.mocks;

import edu.brown.cs.student.main.acs.BroadbandAPI;
import edu.brown.cs.student.main.acs.BroadbandData;
import spark.Request;

public class MockedBroadbandData{
  private final BroadbandData broadbandData;
  public MockedBroadbandData(BroadbandData broadbandData) {
    this.broadbandData = broadbandData;
  }

}

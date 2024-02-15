package edu.brown.cs.student.main.SerializerDeserializer;

import com.squareup.moshi.Moshi;
import java.util.Map;

public class Serializer {
  private Map<String, Object> responseMap;

  public Serializer(Map<String, Object> responseMap){
    this.responseMap = responseMap;
    this.serialize(this.responseMap);
  }

  String serialize(Map<String, Object> responseMap) {
    try {
      Moshi moshi = new Moshi.Builder().build();
      return moshi.adapter(responseMap.getClass()).toJson();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

}

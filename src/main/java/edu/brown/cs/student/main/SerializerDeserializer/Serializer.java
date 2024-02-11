package edu.brown.cs.student.main.SerializerDeserializer;

import com.squareup.moshi.Moshi;

public class Serializer {
  private String message;

  public Serializer(String message){
    this.message = message;
    this.serialize(message);
  }

  String serialize(String responseMessage) {
    try {
      Moshi moshi = new Moshi.Builder().build();
      return moshi.adapter(responseMessage.getClass()).toJson(this);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

}

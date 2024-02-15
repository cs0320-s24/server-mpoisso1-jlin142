package edu.brown.cs.student.main.SerializerDeserializer;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.util.Map;

public class Serializer {

  public Serializer() {
  }

  public String serialize(Map<String, Object> responseMap) {
    try {
      Moshi moshi = new Moshi.Builder().build();
      Type listType = Types.newParameterizedType(Map.class, String.class, Object.class);
      JsonAdapter<Map<String, Object>> jsonAdapter = moshi.adapter(listType);
      return jsonAdapter.toJson(responseMap);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }


}

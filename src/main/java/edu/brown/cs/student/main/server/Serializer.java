package edu.brown.cs.student.main.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * This is the Serializer class.
 */
public class Serializer {
  private final JsonAdapter<Map<String, Object>> adapter;

  /**
   * This constructor sets up the required variables to serialize a map.
   */
  public Serializer() {
    Moshi moshi = new Moshi.Builder().build();
    Type type = Types.newParameterizedType(Map.class, String.class, Object.class);
    this.adapter = moshi.adapter(type);
  }

  /**
   * This method serializes the data.
   *
   * @param map Map of data.
   * @return Json map of data
   */
  public String serialize(Map<String, Object> map) {
    return adapter.toJson(map);
  }

}

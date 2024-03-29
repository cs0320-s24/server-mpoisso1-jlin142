package edu.brown.cs.student.main.csv;

import java.util.List;


/**
 * This class represented the parsed data.
 */
public class ParsedData {
  private boolean loaded;
  private List<List<String>> content;

  /**
   * The constructor accepts the parsed data content and a boolean indicated whether it is loaded.
   *
   * @param loaded
   * @param content
   */
  public ParsedData(boolean loaded,List<List<String>> content) {
    this.loaded = loaded;
    this.content = content;
  }

  public boolean getLoadedVal() {
    return this.loaded;
  }
  public List<List<String>> getContent(){
    return this.content;
  }
  public void setLoadedVal(boolean val) {
    this.loaded = val;
  }

  public void setContent(List<List<String>> content) {
    this.content = content;
  }
}

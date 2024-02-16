package edu.brown.cs.student.main.CSV;

import java.io.FileReader;
import java.util.List;

public class ParsedData {
  private boolean loaded;
  private List<List<String>> content;

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

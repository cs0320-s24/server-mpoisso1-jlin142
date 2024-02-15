package edu.brown.cs.student.main.CSV;

import java.io.FileReader;
import java.util.List;

public class ParsedData {
  private FileReader fReader;
  private boolean loaded;
  private List<List<String>> content;

  public ParsedData(FileReader fReader, boolean loaded,List<List<String>> content) {
    this.fReader = fReader;
    this.loaded = loaded;
    this.content = content;
  }

  public FileReader getfReader() {
    return this.fReader;
  }

  public boolean getLoadedVal() {
    return this.loaded;
  }
  public List<List<String>> getContent(){
    return this.content;
  }

  public void setfReader(FileReader fileReader) {
    this.fReader = fileReader;
  }

  public void setLoadedVal(boolean val) {
    this.loaded = val;
  }

  public void setContent(List<List<String>> content) {
    this.content = content;
  }
}

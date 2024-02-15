package edu.brown.cs.student.main.CSV;

import java.io.FileReader;

public class ParsedData {
  private FileReader fReader;
  private boolean loaded;

  public ParsedData(FileReader fReader, boolean loaded) {
    this.fReader = fReader;
    this.loaded = loaded;
  }

  public FileReader getfReader() {
    return this.fReader;
  }

  public boolean getLoadedVal() {
    return this.loaded;
  }

  public void setfReader(FileReader fileReader) {
    this.fReader = fileReader;
  }

  public void setLoadedVal(boolean val) {
    this.loaded = val;
  }
}

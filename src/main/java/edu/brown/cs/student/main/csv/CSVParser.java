package edu.brown.cs.student.main.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class CSVParser<T> {
  static final Pattern regexSplitCSVRow =
      Pattern.compile("," + "(?=([^\\\"]*\\\"[^\\\"]*\\\")*(?![^\\\"]*\\\"))");


  public CSVParser() {
  }

  /**
   * This helper function utilizes a while loop to parse through each row and convert each row to a
   * type specified by the user in their definition of the create method.
   *
   * @param reader A generic reader object that can read information in various different formats
   *     (Strings, files,etc.)
   * @param headers Boolean indicator of header appearance
   */
  public List<List<String>> parse(Reader reader, Boolean headers) {
    int rowCounter = 0;
    int numElems = 0;
    BufferedReader bufferedReader = new BufferedReader(reader);
    List<List<String>> rows = new ArrayList<>();
    try {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        List<String> row = Arrays.asList(regexSplitCSVRow.split(line));
        for (int i = 0; i < row.size(); i++) {
          String e = row.get(i);
          row.set(i, postprocess(e));
        }
        if (headers && rowCounter == 0) {
          numElems = row.size();
        }
        if (headers && rowCounter > 0 && row.size() != numElems) {
          return new ArrayList<>();
        }
        rows.add(row);
        rowCounter++;
      }
    } catch (IOException e) {
      System.err.println("EOF reached");
    }
    return rows;
  }

  /**
   * Postprocesses a string after regular expression.
   *
   * @param arg the string to be postprocessed
   * @return
   */
  public static String postprocess(String arg) {
    return arg
        // Remove extra spaces at beginning and end of the line
        .trim()
        // Remove a beginning quote, if present
        .replaceAll("^\"", "")
        // Remove an ending quote, if present
        .replaceAll("\"$", "")
        // Replace double-double-quotes with double-quotes
        .replaceAll("\"\"", "\"");
  }
}


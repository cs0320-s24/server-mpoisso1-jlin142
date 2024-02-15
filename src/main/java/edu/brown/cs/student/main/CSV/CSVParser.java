package edu.brown.cs.student.main.CSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class CSVParser<T> {
  private final List<List<String>> rows;

  public CSVParser(Reader reader, String headers) {
    this.rows = this.parse(reader, headers);
  }

  /**
   * This helper function utilizes a while loop to parse through each row and convert each row to a
   * type specified by the user in their definition of the create method.
   *
   * @param reader A generic reader object that can read information in various different formats
   *     (Strings, files,etc.)
   * @param headers Boolean indicator of header appearance
   */
  private List<List<String>> parse(Reader reader, String headers) {
    int rowCounter = 0;
    int numElems = 0;
    BufferedReader bufferedReader = new BufferedReader(reader);
    List<List<String>> rows = new ArrayList<>();
    try {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] result = regexSplitCSVRow.split(line);
        List<String> row = List.of(result);
        if (headers == "true" && rowCounter == 0) {
          numElems = row.size();
        }
        if (headers == "true" && rowCounter > 0 && row.size() != numElems) {
          // Should not be doing this
          // error message should be malformed csv
          // return empty list
          System.err.println("Row " + rowCounter + " was malformed!");
          continue;
        }
        rows.add(row);
        rowCounter++;
      }
    } catch (IOException e) {
      System.err.println("EOF reached");
    }
    return rows;
  }

  public List<List<String>> getRows() {
    // proxy for rows
    return Collections.unmodifiableList(this.rows);
  }

  static final Pattern regexSplitCSVRow =
      Pattern.compile("," + "(?=([^\\\"]*\\\"[^\\\"]*\\\")*(?![^\\\"]*\\\"))");
}

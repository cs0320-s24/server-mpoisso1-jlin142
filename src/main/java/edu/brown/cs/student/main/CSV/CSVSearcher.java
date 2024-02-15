package edu.brown.cs.student.main.CSV;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVSearcher {

  /**
   * To be instantiated, this class need a parser object to search over.
   *
   * @throws IOException
   */
  public CSVSearcher() throws IOException {
  }

  /**
   * First of three search() methods. Method overloading is done to account for different search
   * needs.
   *
   * @param value value to search for
   * @param header boolean describing if there is a header
   * @return List of row indexes that were printed
   */
  public List<List<String>> search(List<List<String>> rows, String value, boolean header) {
    if (this.checkEmpty()) {
      // Fix this for later
      System.err.println("CSV is empty. There is nothing to search over.");
      return new ArrayList<>();
    }

    List<List<String>> res = new ArrayList<>();

    if (header) {
      rows.remove(0);
    }

    for (int i = 0; i < rows.size(); i++) {
      List<String> row = rows.get(i);
      for (String s : row) {
        if (value.equals(s)) {
          res.add(row);
          //          System.out.println(row);
          break;
        }
      }
    }
    return res;
  }

  /**
   * Second of three search() methods.
   *
   * @param value same as above
   * @param colName name of the column that we are restricted to
   * @return same as above
   */
  public List<List<String>> search(List<List<String>> rows,String value, String colName) {
    if (this.checkEmpty()) {
      System.err.println("CSV is empty. There is nothing to search over.");
      return new ArrayList<>();
    }
    if (colName == null) {
      System.err.println("Column value should not be null.");
      return new ArrayList<>();
    }

    List<List<String>> res = new ArrayList<>();
    List<String> firstRow = rows.remove(0);

    int colNum = -1;
    for (int i = 0; i < firstRow.size(); i++) {
      if (colName.equals(firstRow.get(i))) {
        // returns first column that matches
        colNum = i;
        break;
      }
    }
    if (colNum == -1) {
      System.err.println("No column with value: " + colName);
      return new ArrayList<>();
    }

    for (int i = 0; i < rows.size(); i++) {
      try {
        List<String> row = rows.get(i);
        if (value.equals(row.get(colNum))) {
          res.add(row);
          //          System.out.println(row);
        }
      } catch (Exception e) {
        System.err.println("Row " + i + " does not have a value for " + colName + ".");
      }
    }
    return res;
  }

  /**
   * Third of three parse() methods.
   *
   * @param value same as above
   * @param header same as above
   * @param colIndex index of column we are restricted to
   * @return same as above
   */
  public List<List<String>> search(List<List<String>> rows,String value, boolean header, int colIndex) {
    if (this.checkEmpty()) {
      System.err.println("CSV is empty. There is nothing to search over.");
      return new ArrayList<>();
    }

    List<List<String>> res = new ArrayList<>();
    if (header) {
      rows.remove(0);
    }

    int maxColVal = 0;
    for (List<String> row : rows) {
      if (row.size() > maxColVal) {
        maxColVal = row.size();
      }
    }

    // column should be 0 indexed
    if (colIndex < 0 || colIndex >= maxColVal) {
      // throw error saying column is out of index
      System.err.println("Column index is out of bounds.");
      return new ArrayList<>();
    }

    for (int i = 0; i < rows.size(); i++) {
      try {
        List<String> row = rows.get(i);
        if (value.equals(row.get(colIndex))) {
          res.add(row);
          //          System.out.println(row);
        }
      } catch (Exception e) {
        // throw error saying that col value dne
        System.err.println(
            "Row " + i + " does not have a value for column index " + colIndex + ".");
      }
    }
    return res;
  }

  /**
   * This method checks if the CSV object is empty.
   *
   * @return
   */
  private boolean checkEmpty(List<List<String>> rows) {
    if (rows.size() == 0) {
      return true;
    }
    return false;
  }

  /**
   * This method ensures that we are in a protected directory.
   *
   * @param filepath path of file to be checked
   * @return
   */
  public static boolean validDirectory(String filepath) {
    return !filepath.contains("..");
  }
}

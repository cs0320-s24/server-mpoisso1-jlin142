package edu.brown.cs.student.main.acs;

/**
 * This class extends the exceptions.
 */
public class DatasourceException extends Exception {
  // The root cause of this datasource problem
  private final Throwable cause;

  public DatasourceException(String message) {
    super(message); // Exception message
    this.cause = null;
  }
  public DatasourceException(String message, Throwable cause) {
    super(message); // Exception message
    this.cause = cause;
  }
  public Throwable getCause() { return this.cause; }
}

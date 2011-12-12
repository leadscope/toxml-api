/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.io;

/**
 * An exception that occurred in the toxml reader, but was application specific
 * and did not violate XML - includes the line information
 */
public class ToxmlReaderException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private int lineNumber;
  
  public ToxmlReaderException(int lineNumber, Throwable t) {
    super(t.getMessage() + " at line: " + lineNumber, t);
    this.lineNumber = lineNumber;
  }
  
  public ToxmlReaderException(int lineNumber, String msg) {    
    super(msg + " at line: " + lineNumber);
    this.lineNumber = lineNumber;
  }
  
  public int getLineNumber() {
    return lineNumber;
  }
}

/**
 * Stucco ToxML API - a Java interface for parsing and creating ToxML documents
 * Copyright (C) 2016 Scott Miller - Leadscope, Inc.
 *
 * Leadscope, Inc. licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

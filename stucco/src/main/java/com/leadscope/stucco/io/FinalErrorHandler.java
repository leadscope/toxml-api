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

import javax.xml.stream.XMLStreamReader;

import com.leadscope.stucco.*;

/**
 * A final error handler that throws an exception for every encountered error
 */
public class FinalErrorHandler implements ToxmlErrorHandler {
  public boolean unexpectedTag(XMLStreamReader reader, ToxmlObjectParent parent, String tag) throws ToxmlReaderException {
    throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
        "Unexpected element: " + tag + " in class: " + parent.getClass().getName());
  }

  public boolean valueException(
      XMLStreamReader reader, 
      ToxmlObjectParent parent, 
      String tag,
      PrimitiveToxmlObject obj,
      String value,
      Exception e) throws ToxmlReaderException {
    if (e instanceof ToxmlReaderException) {
      throw (ToxmlReaderException)e;
    }
    else {
      throw new ToxmlReaderException(reader.getLocation().getLineNumber(), e);
    }
  }
}

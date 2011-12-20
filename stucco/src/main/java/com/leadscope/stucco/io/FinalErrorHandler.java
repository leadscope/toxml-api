/**
 * Stucco ToxML API - a Java interface for parsing and creating ToxML documents
 * Copyright (C) 2011 Scott Miller - Leadscope, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.leadscope.stucco.io;

import javax.xml.stream.XMLStreamReader;

import com.leadscope.stucco.*;

/**
 * A final error handler that throws an exception for every encountered error
 */
public class FinalErrorHandler implements ToxmlErrorHandler {
  public boolean unexpectedTag(XMLStreamReader reader, CompositeToxmlObject parent, String tag) throws ToxmlReaderException {
    throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
        "Unexpected element: " + tag + " in class: " + parent.getClass().getName());
  }

  public <T extends ToxmlObject> boolean unexpectedTag(XMLStreamReader reader, ToxmlObjectContainer<T> parent, String tag) throws ToxmlReaderException {
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

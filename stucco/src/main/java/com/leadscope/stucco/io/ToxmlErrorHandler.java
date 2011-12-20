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
 * An interface to an object that can allow a ToxmlReader to recover from
 * an error condition. Or optionally provide the exception message that
 * should be thrown.
 */
public interface ToxmlErrorHandler {
  /**
   * Called when an unexpected tag appeared within a composite toxml object. 
   * If the error handler is able to recover, the reader should be left at the END_ELEMENT of 
   * the invalid tag
   * @param reader the reader with the eventType at START_ELEMENT of the invalid tag
   * @param parent the parent object
   * @param tag the tag that was seen
   * @return true iff this handler was able to recover
   * @throws ToxmlReaderException if the handler decides to generate it's own exception
   */
  public boolean unexpectedTag(
      XMLStreamReader reader, 
      CompositeToxmlObject parent, 
      String tag) throws ToxmlReaderException;
  
  /**
   * Called when an unexpected tag appeared within a container object. If the error handler is
   * able to recover, the reader should be left at the END_ELEMENT of the invalid tag
   * @param reader the reader with the eventType at START_ELEMENT of the invalid tag
   * @param parent the parent object
   * @param tag the tag that was seen
   * @return true iff this handler was able to recover
   * @throws ToxmlReaderException if the handler decides to generate it's own exception
   */
  public <T extends ToxmlObject> boolean unexpectedTag(
      XMLStreamReader reader, 
      ToxmlObjectContainer<T> parent, 
      String tag) throws ToxmlReaderException;
  
  /**
   * Called when an invalid value is encountered on a primitive type
   * @param reader the reader with the eventType at END_ELEMENT of the invalid tag
   * @param parent the parent of the object
   * @param tag the tag for the invalid object
   * @param obj the object with the issue
   * @param value the value that was tried to be set
   * @param exception the exception that was thrown
   * @return true iff the exception was handled and th
   * @throws ToxmlReaderException
   */
  public boolean valueException(
      XMLStreamReader reader, 
      ToxmlObjectParent parent, 
      String tag,
      PrimitiveToxmlObject obj,
      String value,
      Exception exception) throws ToxmlReaderException;
  
}

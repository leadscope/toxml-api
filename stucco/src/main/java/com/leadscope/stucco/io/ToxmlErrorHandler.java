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

import com.leadscope.stucco.PrimitiveToxmlObject;
import com.leadscope.stucco.ToxmlObjectParent;

import javax.xml.stream.XMLStreamReader;

/**
 * An interface to an object that can allow a ToxmlReader to recover from
 * some error conditions - or optionally provide an exception message that
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
   * @return true iff this handler was able to recover - this method will also have parsed to
   * the matching END_ELEMENT of the invalid tag 
   * @throws ToxmlReaderException if the handler decides to generate it's own exception
   */
  boolean unexpectedTag(
      XMLStreamReader reader, 
      ToxmlObjectParent parent, 
      String tag) throws ToxmlReaderException;
    
  /**
   * Called when an invalid value is encountered on a primitive type. This method should never
   * perform any additional parsing on the reader
   * @param reader the reader with the eventType at END_ELEMENT of the invalid tag
   * @param parent the parent of the object
   * @param tag the tag for the invalid object
   * @param obj the object with the issue
   * @param value the value that was tried to be set
   * @param exception the exception that was thrown
   * @return true iff the exception was handled
   * @throws ToxmlReaderException
   */
  boolean valueException(
      XMLStreamReader reader, 
      ToxmlObjectParent parent, 
      String tag,
      PrimitiveToxmlObject obj,
      String value,
      Exception exception) throws ToxmlReaderException;
  
}

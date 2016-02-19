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

import java.io.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import com.leadscope.stucco.ToxmlObject;

/**
 * A collection of static methods that simplify the use of the ToxmlReader object.
 */
public class ToxmlParser {
  /**
   * Parses a list of toxml objects - expects a root node with the child objects appearing
   * as elements underneath
   * @param file the xml file to parse
   * @param toxmlClass the class of child objects to parse
   * @param handler the handler to process the objects
   * @param errorHandlers optional error handlers
   */
  public static <T extends ToxmlObject> void parseList(
      File file, 
      Class<T> toxmlClass, 
      ToxmlHandler<T> handler,
      ToxmlErrorHandler... errorHandlers) throws Exception {
    FileInputStream fis = new FileInputStream(file);
    Exception exception = null;
    try {
      parseList(createReader(fis), toxmlClass, handler, errorHandlers);
    }
    finally {
      try { fis.close(); } catch (Exception e) { 
        if (exception != null) {
          throw exception;
        }
        else {
          throw e;
        }
      }
    }
  }

  /**
   * Parses a list of toxml objects - expects a root node with the child objects appearing
   * as elements underneath
   * @param xml the xml string to parse
   * @param toxmlClass the class of child objects to parse
   * @param handler the handler to process the objects
   * @param errorHandlers optional error handlers
   */
  public static <T extends ToxmlObject> void parseList(
      String xml, 
      Class<T> toxmlClass, 
      ToxmlHandler<T> handler,
      ToxmlErrorHandler... errorHandlers) throws Exception {
    parseList(createReader(xml), toxmlClass, handler, errorHandlers);
  }

  /**
   * Parses a list of toxml objects - expects a root node with the child objects appearing
   * as elements underneath
   * @param input the input stream to read from
   * @param toxmlClass the class of child objects to parse
   * @param handler the handler to process the objects
   * @param errorHandlers optional error handlers
   */
  public static <T extends ToxmlObject> void parseList(
      InputStream input, 
      Class<T> toxmlClass, 
      ToxmlHandler<T> handler,
      ToxmlErrorHandler... errorHandlers) throws Exception {
    parseList(createReader(input), toxmlClass, handler, errorHandlers);
  }

  /**
   * Parses a list of toxml objects - expects a root node with the child objects appearing
   * as elements underneath
   * @param reader the xml reader to parse from
   * @param toxmlClass the class of child objects to parse
   * @param handler the handler to process the objects
   * @param errorHandlers optional error handlers
   */
  public static <T extends ToxmlObject> void parseList(
      XMLStreamReader reader, 
      Class<T> toxmlClass, 
      ToxmlHandler<T> handler,
      ToxmlErrorHandler... errorHandlers) throws Exception {
    reader.nextTag();
    
    int eventType = reader.nextTag();
    while (eventType != XMLStreamReader.END_ELEMENT) {
      if (eventType == XMLStreamReader.START_ELEMENT) {
        try {
          String tagName = reader.getLocalName();
          T obj = parseInternal(reader, toxmlClass, errorHandlers);
          handler.handle(obj);
          if (reader.getEventType() != XMLStreamReader.END_ELEMENT) {
            throw new RuntimeException("Not at end element after parsing: " + obj.getClass().getName());
          }
          else if (!tagName.equals(reader.getLocalName())) {
            throw new RuntimeException("Not at matching end element after parsing: " + obj.getClass().getName() + 
                  " got: " + reader.getLocalName() + "  expected: " + tagName);
          }
        }
        catch (ToxmlReaderException tre) {
          throw tre;
        }
        catch (Throwable t) {
          throw new ToxmlReaderException(reader.getLocation().getLineNumber(), t);                          
        }
      }
      
      eventType = reader.nextTag();
    }
  }
  
  /**
   * Parses the given class from the source xml string
   * @param xml the xml string to parse
   * @param toxmlClass the class of the object to parse
   * @param errorHandlers optional error handlers
   */
  public static <T extends ToxmlObject> T parse(
      String xml, 
      Class<T> toxmlClass,
      ToxmlErrorHandler... errorHandlers) throws Exception {
    return parse(createReader(xml), toxmlClass, errorHandlers);    
  }

  /**
   * Parses the given class from the source reader
   * @param input the input to read from
   * @param toxmlClass the class of the object to parse
   * @param errorHandlers optional error handlers
   */
  public static <T extends ToxmlObject> T parse(
      InputStream input, 
      Class<T> toxmlClass,
      ToxmlErrorHandler... errorHandlers) throws Exception {
    return parse(createReader(input), toxmlClass, errorHandlers);    
  }

  /**
   * Parses the given class from the source reader
   * @param reader the reader to read from
   * @param toxmlClass the class of the object to parse
   * @param errorHandlers optional error handlers
   */
  public static <T extends ToxmlObject> T parse(
      XMLStreamReader reader, 
      Class<T> toxmlClass,
      ToxmlErrorHandler... errorHandlers) throws Exception {
    reader.nextTag();
    return parseInternal(reader, toxmlClass, errorHandlers);    
  }

  /**
   * Parses the given class of object from the stream
   * @param reader the xml stream just after the start tag of the element
   * @param toxmlClass the class of object to parse
   * @param errorHandlers optional error handlers
   */
  public static <T extends ToxmlObject> T parseInternal(
      XMLStreamReader reader, 
      Class<T> toxmlClass,
      ToxmlErrorHandler... errorHandlers) throws Exception {
    ToxmlReader toxReader = new ToxmlReader(reader);
    for (ToxmlErrorHandler errorHandler : errorHandlers) {
      toxReader.addErrorHandler(errorHandler);
    }
    T obj = toxmlClass.newInstance();
    obj.accept(toxReader);
    return obj;
  }
  
  /**
   * Creates a reader with the appropriate parsing properties set
   * @param xml the string of xml
   * @return the reader for the xml string
   */
  public static XMLStreamReader createReader(String xml) throws Exception {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(xml));
    return reader;
  }

  /**
   * Creates a reader with the appropriate parsing properties set
   * @param input the input stream
   * @return the reader for the xml string
   */
  public static XMLStreamReader createReader(InputStream input) throws Exception {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    XMLStreamReader reader = factory.createXMLStreamReader(input);
    return reader;
  }

}

/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.io;

import java.io.*;
import java.util.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import com.leadscope.stucco.*;
import com.leadscope.stucco.StringValue;

/**
 * Parses an arbitrary toxml object from an xml source
 */
public class ToxmlReader implements ToxmlVisitor<ToxmlObject>, ToxmlXmlConstants {  
  private XMLStreamReader reader;

  /**
   * Parses a list of toxml objects - expects a root node with the child objects appearing
   * as elements underneath
   * @param file the xml file to parse
   * @param toxmlClass the class of child objects to parse
   * @param handler the handler to process the objects
   */
  public static <T extends ToxmlObject> void parseList(
      File file, 
      Class<T> toxmlClass, 
      ToxmlHandler<T> handler) throws Exception {
    FileInputStream fis = new FileInputStream(file);
    try {
      parseList(createReader(fis), toxmlClass, handler);
    }
    finally {
      try { fis.close(); } catch (Exception e) { 
        // don't mask exception during read
      }
    }
  }

  /**
   * Parses a list of toxml objects - expects a root node with the child objects appearing
   * as elements underneath
   * @param xml the xml string to parse
   * @param toxmlClass the class of child objects to parse
   * @param handler the handler to process the objects
   */
  public static <T extends ToxmlObject> void parseList(
      String xml, 
      Class<T> toxmlClass, 
      ToxmlHandler<T> handler) throws Exception {
    parseList(createReader(xml), toxmlClass, handler);
  }

  /**
   * Parses a list of toxml objects - expects a root node with the child objects appearing
   * as elements underneath
   * @param input the input stream to read from
   * @param toxmlClass the class of child objects to parse
   * @param handler the handler to process the objects
   */
  public static <T extends ToxmlObject> void parseList(
      InputStream input, 
      Class<T> toxmlClass, 
      ToxmlHandler<T> handler) throws Exception {
    parseList(createReader(input), toxmlClass, handler);
  }

  /**
   * Parses a list of toxml objects - expects a root node with the child objects appearing
   * as elements underneath
   * @param reader the xml reader to parse from
   * @param toxmlClass the class of child objects to parse
   * @param handler the handler to process the objects
   */
  public static <T extends ToxmlObject> void parseList(
      XMLStreamReader reader, 
      Class<T> toxmlClass, 
      ToxmlHandler<T> handler) throws Exception {
    reader.nextTag();
    
    int eventType = reader.nextTag();
    while (eventType != XMLStreamReader.END_ELEMENT) {
      if (eventType == XMLStreamReader.START_ELEMENT) {
        try {
          String tagName = reader.getLocalName();
          T obj = parseInternal(reader, toxmlClass);
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
   */
  public static <T extends ToxmlObject> T parse(String xml, Class<T> toxmlClass) throws Exception {
    return parse(createReader(xml), 
        toxmlClass);    
  }

  /**
   * Parses the given class from the source reader
   * @param input the input to read from
   * @param toxmlClass the class of the object to parse
   */
  public static <T extends ToxmlObject> T parse(InputStream input, Class<T> toxmlClass) throws Exception {
    return parse(XMLInputFactory.newInstance().createXMLStreamReader(input), toxmlClass);    
  }

  /**
   * Parses the given class from the source reader
   * @param reader the reader to read from
   * @param toxmlClass the class of the object to parse
   */
  public static <T extends ToxmlObject> T parse(XMLStreamReader reader, Class<T> toxmlClass) throws Exception {
    reader.nextTag();
    return parseInternal(reader, toxmlClass);    
  }

  /**
   * Parses the given class of object from the stream
   * @param reader the xml stream just after the start tag of the element
   * @param toxmlClass the class of object to parse
   */
  public static <T extends ToxmlObject> T parseInternal(
      XMLStreamReader reader, 
      Class<T> toxmlClass) throws Exception {
    ToxmlReader toxReader = new ToxmlReader(reader);
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

  public ToxmlReader(XMLStreamReader reader) {
    this.reader = reader;
  }

  public CompositeToxmlObject visit(CompositeToxmlObject obj) throws Exception {
    String expectedEndTag = reader.getLocalName();
    parseAbstractAttributes(obj);
       
    Set<String> seenTags = new HashSet<String>();
    int eventType = reader.nextTag();
    while (eventType != XMLStreamReader.END_ELEMENT) {
      if (eventType == XMLStreamReader.START_ELEMENT) {
        String tagName = reader.getLocalName();
        if (tagName != null) {                
          if (!seenTags.contains(tagName)) {
            seenTags.add(tagName);
          }
          else {
            ToxmlObject oldObj = obj.getChild(tagName);
            throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
                "Repeat of element: " + tagName + " in class: " + obj.getClass().getName() +
                " Old object [" + oldObj + "]");
          }
          
          Class<? extends ToxmlObject> childClass = obj.getChildClass(tagName);
          if (childClass != null) {
            try {                                    
              ToxmlObject childObj = childClass.newInstance();
              childObj.accept(this);
              obj.setChild(tagName, childObj);
              if (reader.getEventType() != XMLStreamReader.END_ELEMENT) {
                throw new RuntimeException("Not at end element after parsing: " + obj.getClass().getName());
              }
              else if (!tagName.equals(reader.getLocalName())) {
                throw new RuntimeException("Not at matching end element after parsing: " + obj.getClass().getName() + 
                      " got: " + reader.getLocalName() + "  expected: " + tagName);
              }
            } 
            catch (InstantiationException ie) {
              throw new RuntimeException("Could not instantiate toxml object: " + 
                                         childClass.getName(),
                                         ie);
            }
            catch (IllegalAccessException iae) {
              throw new RuntimeException("Could not access toxml class: " + 
                                         childClass.getName(),
                                         iae);
            }
            catch (ToxmlReaderException tre) {
              throw tre;
            }
            catch (Throwable t) {
              throw new ToxmlReaderException(reader.getLocation().getLineNumber(), t);                
            }
          }
          else {
            throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
                "Unexpected element: " + tagName + " in class: " + 
                obj.getClass().getName());
          }
        }
      }      

      eventType = reader.nextTag();
    }
    
    checkEndTag(expectedEndTag);
     
    return obj;
  }

  public <T extends ToxmlObject> ToxmlObjectList<T> visit(ToxmlObjectList<T> obj) throws Exception {
    return (ToxmlObjectList<T>)parseContainer(obj);
  }

  public <T extends HashableToxmlObject> ToxmlObjectSet<T> visit(ToxmlObjectSet<T> obj) throws Exception {
    return (ToxmlObjectSet<T>)parseContainer(obj);
  }

  private <T extends ToxmlObject> ToxmlObjectContainer<T> parseContainer(ToxmlObjectContainer<T> obj) throws Exception {
    String expectedEndTag = reader.getLocalName();
    parseAbstractAttributes(obj);
    
    int eventType = reader.nextTag();
    while (eventType != XMLStreamReader.END_ELEMENT) {
      if (eventType == XMLStreamReader.START_ELEMENT) {
        String tagName = reader.getName().getLocalPart();
        
        if (tagName.equals(obj.getChildTag())) {
          Class<T> childClass = obj.getChildClass();
          try {
            T childObj = childClass.newInstance();
            childObj.accept(this);
            obj.addChild(childObj);
            if (reader.getEventType() != XMLStreamReader.END_ELEMENT) {
              throw new RuntimeException("Not at end element after parsing: " + obj.getClass().getName());
            }
            else if (!tagName.equals(reader.getLocalName())) {
              throw new RuntimeException("Not at matching end element after parsing: " + obj.getClass().getName() + 
                    " got: " + reader.getLocalName() + "  expected: " + tagName);
            }
          }
          catch (InstantiationException ie) {
            throw new RuntimeException("Could not instantiate toxml object: " +
                                       childClass.getName(),
                                       ie);
          }
          catch (IllegalAccessException iae) {
            throw new RuntimeException("Could not access toxml class: " +
                                       childClass.getName(),
                                       iae);
          }
          catch (ToxmlReaderException tre) {
            throw tre;
          }
          catch (Throwable t) {
            throw new ToxmlReaderException(reader.getLocation().getLineNumber(), t);                
          }
        }
        else {
          throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
                  "Unexpected element: " + tagName + " by class: " + obj.getClass().getName());
        }
      }
      
      eventType = reader.nextTag();
    }
        
    checkEndTag(expectedEndTag);
    
    return obj;    
  }
    
  private void checkEndTag(String expectedEndTag) {
    if (reader.getEventType() != XMLStreamReader.END_ELEMENT) {
      throw new ToxmlReaderException(
          reader.getLocation().getLineNumber(),
          "Expected end tag: " + expectedEndTag + " but got non-end element event: " + reader.getEventType());
    }
    if (!expectedEndTag.equals(reader.getLocalName())) {
      throw new ToxmlReaderException(
          reader.getLocation().getLineNumber(),
          "Expected end tag: " + expectedEndTag + " but got: " + reader.getLocalName());
    }
  }
  
  public ToxmlObject visit(StringValue obj) throws Exception {
    parseAbstractAttributes(obj);    
    obj.setValue(reader.getElementText());
    return obj;
  }

  public ToxmlObject visit(CDataValue obj) throws Exception {
    parseAbstractAttributes(obj);    
    obj.setValue(reader.getElementText());
    return obj;
  }

  public ToxmlObject visit(AbstractToxmlEnumeratedType obj) throws Exception {
    parseAbstractAttributes(obj);    
    obj.setValue(reader.getElementText());
    return obj;
  }
  
  public ToxmlObject visit(BooleanValue obj) throws Exception {
    parseAbstractAttributes(obj);
    obj.setValue(reader.getElementText());
    return obj;
  }

  public ToxmlObject visit(DateValue obj) throws Exception {
    parseAbstractAttributes(obj);
    obj.setValue(reader.getElementText());
    return obj;
  }

  public ToxmlObject visit(FloatValue obj) throws Exception {
    parseAbstractAttributes(obj);
    obj.setValue(reader.getElementText());
    return obj;
  }

  public ToxmlObject visit(IntegerValue obj) throws Exception {
    parseAbstractAttributes(obj);
    obj.setValue(reader.getElementText());
    return obj;
  }

  public ToxmlObject visit(IntegerArray obj) throws Exception {
    parseAbstractAttributes(obj);
    obj.setValue(reader.getElementText());
    return obj;
  }
  
  public ToxmlObject visit(Units obj) throws Exception {
    parseAbstractAttributes(obj);
    obj.setValue(reader.getElementText());
    return obj;
  }
  
  public ToxmlObject visit(TypedValue obj) throws Exception {
    parseAbstractAttributes(obj);
    obj.setType(reader.getAttributeValue(null, TYPE_ATTRIBUTE));
    obj.setValue(reader.getElementText());
    return obj;
  }
  
  public ToxmlObject visit(InexactValue obj) throws Exception {
    parseAbstractAttributes(obj);
    
    String qualifierString = reader.getAttributeValue(null, QUALIFIER_ATTRIBUTE);
    if (qualifierString != null && qualifierString.length() > 0) {
      parseQualifiedInexactValue(qualifierString, obj);
    }    
    else {
      parseUnqualifiedInexactValue(obj);
    }
    
    return obj;
  }

  public ToxmlObject visit(IntegerRange obj) throws Exception {
    parseAbstractAttributes(obj);    
    parseIntegerRange(obj);    
    return obj;
  }

  private void parseQualifiedInexactValue(String qualifierString, InexactValue obj) throws Exception {
    int qualifier = 0;    
    if (qualifierString.equals(GREATER_ATTRIBUTE)) {
      qualifier = 1;
    }
    else if (qualifierString.equals(LESS_ATTRIBUTE)) {
      qualifier = -1;
    }
    else if (qualifierString.equals(UNKNOWN_ATTRIBUTE)) {
      obj.setValue(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
    }
    else {
      throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
                               "Unknown qualifier: " + qualifierString);
    }

    String valueString = reader.getElementText();
    try {
      float value = Float.parseFloat(valueString);         
      if (qualifier > 0) {
        obj.setValue(value, Float.POSITIVE_INFINITY);
      }
      else {
        obj.setValue(Float.NEGATIVE_INFINITY, value);
      }
    }
    catch (NumberFormatException nfe) {
      throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
          "Invalid number string: " + valueString);
    }      
  }
  
  private void parseUnqualifiedInexactValue(InexactValue obj) throws Exception {
    String valueString = parseStringBeforeTag();
    if (valueString != null) {
      try {
        float value = Float.parseFloat(valueString);
        obj.setValue(value);
      }
      catch (NumberFormatException nfe) {
        throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
            "Invalid number string: " + valueString);
      }        
    }
    else if (isStart(LOW_VALUE_TAG)) {
      String lowValueString = reader.getElementText();
      float lowValue = Float.parseFloat(lowValueString);
      checkEndTag(LOW_VALUE_TAG);
      
      reader.nextTag();
      
      if (isStart(HIGH_VALUE_TAG)) {
        String highValueString = reader.getElementText();
        float highValue = Float.parseFloat(highValueString);
        obj.setValue(lowValue, highValue);
      }
      else {
        throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
                                 "Expected either a text value or LowValue and HighValue elements");
      }  

      checkEndTag(HIGH_VALUE_TAG);
      
      reader.nextTag();
    }
    else {
      throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
                               "Expected either a text value or LowValue and HighValue elements");
    }
  }

  private void parseIntegerRange(IntegerRange obj) throws Exception {
    String valueString = parseStringBeforeTag();
    
    if (valueString != null) {
      try {
        int value = Integer.parseInt(valueString);
        obj.setValue(value);
      }
      catch (NumberFormatException nfe) {
        throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
            "Invalid number string: " + valueString);
      }        
    }
    else if (isStart(LOW_VALUE_TAG)) {
      String lowValueString = reader.getElementText();
      int lowValue = Integer.parseInt(lowValueString);
      checkEndTag(LOW_VALUE_TAG);
            
      reader.nextTag();
      
      if (isStart(HIGH_VALUE_TAG)) {
        String highValueString = reader.getElementText();
        int highValue = Integer.parseInt(highValueString);
        obj.setValue(lowValue, highValue);
      }
      else {
        throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
                                 "Expected either a text value or LowValue and HighValue elements");
      }  

      checkEndTag(HIGH_VALUE_TAG);
      
      reader.nextTag();
    }
    else {
      throw new ToxmlReaderException(reader.getLocation().getLineNumber(),
                               "Expected either a text value or LowValue and HighValue elements");
    }
  }

  private void parseAbstractAttributes(ToxmlObject obj) {
    try {
      String sourceList = reader.getAttributeValue(namespace, SOURCES_ATTRIBUTE);
      if (sourceList != null) {
        obj.setSources(Arrays.asList(sourceList.split(",")));
      }
      obj.setDiffStatus(DiffStatus.parse(reader.getAttributeValue(namespace, DIFF_ATTRIBUTE)));
      obj.setOriginalConflictValue(reader.getAttributeValue(namespace, ORIGINAL_CONFLICT_ATTRIBUTE));
    }
    catch (Exception e) {
      throw new ToxmlReaderException(reader.getLocation().getLineNumber(), e);
    }
  }

  /**
   * @param tag the tag to check for
   * @return true if the parser is at the start of the given tag
   */
  public boolean isStart(String tag) {
    return reader.getEventType() == XMLStreamReader.START_ELEMENT &&
        reader.getLocalName().equals(tag);
  }
  
  /**
   * Reads characters until the next start or end tag
   * @return the trimmed text before the start or end - null if empty
   */
  private String parseStringBeforeTag() throws Exception {
    int eventType = reader.next();
    
    StringBuilder characters = new StringBuilder();
    while (eventType != XMLStreamReader.START_ELEMENT &&
           eventType != XMLStreamReader.END_ELEMENT) {
      if (eventType == XMLStreamReader.CHARACTERS) {
        characters.append(reader.getText());
      }
      eventType = reader.next();
    }
    
    String valueString = characters.toString().trim();
    if (valueString.length() == 0) {
      return null;
    }
    else {
      return valueString;
    }
  }
}

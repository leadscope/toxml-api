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

import java.util.*;

import javax.xml.stream.XMLStreamReader;

import com.leadscope.stucco.*;
import com.leadscope.stucco.StringValue;

/**
 * Parses an arbitrary toxml object from an xml source. To use, construct an
 * XMLStreamReader, process the initial tag events to get to the START_ELEMENT event
 * of the toxml object you wish to parse. Pass a new instance of that object to
 * the visit method on this reader. This reader will recursively parse all child
 * objects contained within.
 * 
 * @see ToxmlParser for simplified methods and specific use cases
 */
public class ToxmlReader implements ToxmlVisitor<ToxmlObject>, ToxmlXmlConstants {
  private XMLStreamReader reader;
  private List<ToxmlErrorHandler> errorHandlers = new ArrayList<ToxmlErrorHandler>();
  private List<String> tagStack = new ArrayList<String>();
  private List<ToxmlObjectParent> parentStack = new ArrayList<ToxmlObjectParent>();
  
  /**
   * Constructs a reader which will begin parsing as new instances of toxml objects
   * are visited.
   * @param reader the reader from which the toxml objects will be parsed - this
   * reader should be at the START_ELEMENT event of the object being visited
   */
  public ToxmlReader(XMLStreamReader reader) {
    this.reader = reader;
    errorHandlers.add(new FinalErrorHandler());
  }

  /**
   * Adds an error handler. The handlers are called in reverse order of addition
   * @param handler the handler to add
   */
  public void addErrorHandler(ToxmlErrorHandler handler) {
    errorHandlers.add(handler);
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
            ToxmlObject childObj = parseChild(obj, tagName, childClass);
            obj.setChild(tagName, childObj);
          }
          else {
            handleUnexpectedTag(obj, tagName);
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
          T childObj = parseChild(obj, tagName, childClass);
          obj.addChild(childObj);
        }
        else {
          handleUnexpectedTag(obj, tagName);
        }
      }
      
      eventType = reader.nextTag();
    }
        
    checkEndTag(expectedEndTag);
    
    return obj;    
  }
    
  private <T extends ToxmlObject> T parseChild(ToxmlObjectParent parent, String tagName, Class<T> childClass) throws Exception {
    pushChildTag(parent, tagName);
    try {                                    
      T childObj = childClass.newInstance();
      childObj.accept(this);      
      if (reader.getEventType() != XMLStreamReader.END_ELEMENT) {
        throw new RuntimeException("Not at end element after parsing: " + parent.getClass().getName());
      }
      else if (!tagName.equals(reader.getLocalName())) {
        throw new RuntimeException("Not at matching end element after parsing: " + parent.getClass().getName() + 
              " got: " + reader.getLocalName() + "  expected: " + tagName);
      }
      return childObj;
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
    finally {
      popChildTag();
    }
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
    String text = reader.getElementText();
    try {
      obj.setValue(text);
    }
    catch (Exception e) {
      handleValueException(getTopParent(), getTopTag(), obj, text, e);
    }
    return obj;
  }

  public ToxmlObject visit(CDataValue obj) throws Exception {
    parseAbstractAttributes(obj);
    String text = reader.getElementText();
    try {
      obj.setValue(text);
    }
    catch (Exception e) {
      handleValueException(getTopParent(), getTopTag(), obj, text, e);
    }
    return obj;
  }

  public ToxmlObject visit(AbstractToxmlEnumeratedType obj) throws Exception {
    parseAbstractAttributes(obj);
    String text = reader.getElementText();
    try {
      obj.setValue(text);
    }
    catch (Exception e) {
      handleValueException(getTopParent(), getTopTag(), obj, text, e);
    }
    return obj;
  }
  
  public ToxmlObject visit(BooleanValue obj) throws Exception {
    parseAbstractAttributes(obj);
    String text = reader.getElementText();
    try {
      obj.setValue(text);
    }
    catch (Exception e) {
      handleValueException(getTopParent(), getTopTag(), obj, text, e);
    }
    return obj;
  }

  public ToxmlObject visit(DateValue obj) throws Exception {
    parseAbstractAttributes(obj);
    String text = reader.getElementText();
    try {
      obj.setValue(text);
    }
    catch (Exception e) {
      handleValueException(getTopParent(), getTopTag(), obj, text, e);
    }
    return obj;
  }

  public ToxmlObject visit(FloatValue obj) throws Exception {
    parseAbstractAttributes(obj);
    String text = reader.getElementText();
    try {
      obj.setValue(text);
    }
    catch (Exception e) {
      handleValueException(getTopParent(), getTopTag(), obj, text, e);
    }
    return obj;
  }

  public ToxmlObject visit(IntegerValue obj) throws Exception {
    parseAbstractAttributes(obj);
    String text = reader.getElementText();
    try {
      obj.setValue(text);
    }
    catch (Exception e) {
      handleValueException(getTopParent(), getTopTag(), obj, text, e);
    }
    return obj;    
  }

  public ToxmlObject visit(IntegerArray obj) throws Exception {
    parseAbstractAttributes(obj);
    String text = reader.getElementText();
    try {
      obj.setValue(text);
    }
    catch (Exception e) {
      handleValueException(getTopParent(), getTopTag(), obj, text, e);
    }
    return obj;
  }
  
  public ToxmlObject visit(Units obj) throws Exception {
    parseAbstractAttributes(obj);
    String text = reader.getElementText();
    try {
      obj.setValue(text);
    }
    catch (Exception e) {
      handleValueException(getTopParent(), getTopTag(), obj, text, e);
    }
    return obj;
  }
  
  public ToxmlObject visit(TypedValue obj) throws Exception {
    parseAbstractAttributes(obj);
    String type = reader.getAttributeValue(null, TYPE_ATTRIBUTE);
    try {
      obj.setType(type);
    }
    catch (Exception e) {
      handleValueException(getTopParent(), getTopTag(), obj, type, e);
    }
    
    String text = reader.getElementText();
    try {
      obj.setValue(text);
    }
    catch (Exception e) {
      handleValueException(getTopParent(), getTopTag(), obj, text, e);
    }
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
  
  private void handleUnexpectedTag(CompositeToxmlObject parent, String tag) {
    for (int i = errorHandlers.size()-1; i >= 0; i--) {
      if (errorHandlers.get(i).unexpectedTag(reader, parent, tag)) {
        return;
      }
    }
  }
  
  private <T extends ToxmlObject> void handleUnexpectedTag(ToxmlObjectContainer<T> parent, String tag) {
    for (int i = errorHandlers.size()-1; i >= 0; i--) {
      if (errorHandlers.get(i).unexpectedTag(reader, parent, tag)) {
        return;
      }
    }
  }
  
  private void handleValueException(
      ToxmlObjectParent parent, 
      String tag,
      PrimitiveToxmlObject obj,
      String value,
      Exception e) {
    for (int i = errorHandlers.size()-1; i >= 0; i--) {
      if (errorHandlers.get(i).valueException(reader, parent, tag, obj, value, e)) {
        return;
      }
    }
  }

  private void pushChildTag(ToxmlObjectParent parent, String tag) {
    parentStack.add(parent);
    tagStack.add(tag);
  }
  
  private void popChildTag() {
    parentStack.remove(parentStack.size()-1);
    tagStack.remove(tagStack.size()-1);
  }

  private String getTopTag() {
    int count = tagStack.size(); 
    if (count > 0) {  
      return tagStack.get(count-1);
    }
    else {
      return null;
    }    
  }
  
  private ToxmlObjectParent getTopParent() {
    int count = parentStack.size();
    if (count > 0) {
      return parentStack.get(count-1);
    }
    else {
      return null;
    }
  }
}

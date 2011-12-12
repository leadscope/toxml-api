/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.io;

import java.io.*;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import com.leadscope.stucco.*;
import com.leadscope.stucco.StringValue;
import com.leadscope.stucco.util.StringUtil;

//import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;

/**
 * Writes toxml objects to an xml stream. After constructing the writer, use the visit
 * methods to write objects to the stream. Be sure to call close() when finished writing.
 */
public class ToxmlWriter implements ToxmlVisitor<ToxmlObject>, ToxmlXmlConstants {
  private XMLStreamWriter writer;
    
  /**
   * Writes the obj to a string of containing the xml
   * @param rootTag the root tag to use for the document
   * @param obj the object to serialize
   * @return the string containing the xml
   * @throws Exception
   */  
  public static String toString(String rootTag, ToxmlObject obj) throws Exception {
    StringWriter sw = new StringWriter();
    ToxmlWriter writer = new ToxmlWriter(
        rootTag,
        XMLOutputFactory.newInstance().createXMLStreamWriter(sw));
    obj.accept(writer);
    writer.close();
    return sw.toString();
  }
  
  /**
   * Writes a single toxml object to the output stream. Closes the output stream
   * when done 
   * @param rootTag the root tag to use for the document
   * @param obj the object to serialize
   * @param os the output stream to write to - this will be closed by this method
   * @throws Exception if something goes awry
   */
  public static void write(String rootTag, ToxmlObject obj, OutputStream os) throws Exception {
    ToxmlWriter writer = new ToxmlWriter(
        rootTag,
        XMLOutputFactory.newInstance().createXMLStreamWriter(os));
    Exception exception = null;
    try {      
      obj.accept(writer);
    }
    catch (Exception e) {
      exception = e;
    }
    finally {
      try { writer.close(); } catch (Exception e) {
        if (exception == null) {
          throw e;
        }
      }
      if (exception != null) {
        throw exception;
      }
    }
  }

  /**
   * Writes the streamed objects to the given file. 
   * @param rootTag the root document tag under which the objects will be written
   * @param childTag the tag with which each child will be written 
   * @param iterable the stream of objects to write
   * @param file the file to write to
   * @throws Exception if something goes awry
   */
  public static void write(      
      String rootTag, 
      String childTag,
      Iterable<? extends ToxmlObject> iterable,
      File file) throws Exception {
    write(rootTag, childTag, iterable, new FileOutputStream(file));
  }
  
  /**
   * Writes the streamed objects to the given output stream. This method will also close the
   * output stream
   * @param rootTag the root tag under which the objects will be written
   * @param childTag the tag with which each child will be written 
   * @param iterable the stream of objects to write
   * @param os the output stream to write to - will be closed by this method when complete
   * @throws Exception if something goes awry
   */
  public static void write(
      String rootTag, 
      String childTag,
      Iterable<? extends ToxmlObject> iterable, 
      OutputStream os) throws Exception {
    ToxmlWriter writer = new ToxmlWriter(rootTag, os);
    Exception exception = null;
    try {      
      for (ToxmlObject obj : iterable) {
        writer.write(childTag, obj);
      }
    }
    catch (Exception e) {
      exception = e;
    }
    finally {
      try { writer.close(); } catch (Exception e) {
        if (exception == null) {
          throw e;
        }
      }
      if (exception != null) {
        throw exception;
      }
    }
  }
  
  public ToxmlWriter(String rootTag, OutputStream os) throws Exception {
    this(rootTag, XMLOutputFactory.newInstance().createXMLStreamWriter(os));
  }
  
  public ToxmlWriter(String rootTag, XMLStreamWriter writer) throws Exception {
    // this.writer = new IndentingXMLStreamWriter(writer);
    this.writer = writer;
    this.writer.writeStartDocument("UTF-8", "1.0");
    this.writer.writeStartElement(rootTag);
  }

  public void close() throws Exception {
    writer.writeEndElement();
    writer.writeEndDocument();
    writer.close();
  }
  
  /**
   * Writes the given object to the writer under the given tag
   * @param tag the tag under which the object will be written
   * @param obj the object to serialize
   */
  public void write(String tag, ToxmlObject obj) throws Exception {
    writer.writeStartElement(tag);
    obj.accept(this);
    writer.writeEndElement();
  }
  
  public ToxmlObject visit(CompositeToxmlObject obj) throws Exception {
    writeAbstractAttributes(obj);
    for (String tag : obj.getChildTags()) {
      ToxmlObject child = obj.getChild(tag);
      if (child != null && !child.isEmpty()) {
        writer.writeStartElement(tag);
        child.accept(this);
        writer.writeEndElement();
      }
    }
    return obj;
  }

  public <T extends ToxmlObject> ToxmlObject visit(ToxmlObjectList<T> obj) throws Exception {
    return visit((ToxmlObjectContainer<T>)obj);
  }

  public <T extends HashableToxmlObject> ToxmlObject visit(ToxmlObjectSet<T> obj) throws Exception {
    return visit((ToxmlObjectContainer<T>)obj);
  }

  private <T extends ToxmlObject> ToxmlObject visit(ToxmlObjectContainer<T> obj) throws Exception {
    writeAbstractAttributes(obj);
    for (T child : obj.getValues()) {
      if (!child.isEmpty()) {
        writer.writeStartElement(obj.getChildTag());
        child.accept(this);
        writer.writeEndElement();
      }
    }
    return obj;    
  }
  
  public ToxmlObject visit(StringValue obj) throws Exception {
    writeAbstractAttributes(obj);
    write(obj.getValue());
    return obj;
  }

  public ToxmlObject visit(CDataValue obj) throws Exception {
    writeAbstractAttributes(obj);
    writer.writeCData(obj.getValue());
    return obj;
  }

  public ToxmlObject visit(BooleanValue obj) throws Exception {
    writeAbstractAttributes(obj);
    write(String.valueOf(obj.getValue()));
    return obj;
  }

  public ToxmlObject visit(DateValue obj) throws Exception {
    writeAbstractAttributes(obj);
    write(obj.toString());
    return obj;
  }

  public ToxmlObject visit(FloatValue obj) throws Exception {
    writeAbstractAttributes(obj);
    write(obj.toString());
    return obj;
  }

  public ToxmlObject visit(IntegerValue obj) throws Exception {
    writeAbstractAttributes(obj);
    write(obj.toString());
    return obj;
  }

  public ToxmlObject visit(IntegerRange obj) throws Exception {
    writeAbstractAttributes(obj);
    if (obj.isRange()) {
      writeText(LOW_VALUE_TAG, String.valueOf(obj.getLowValue()));
      writeText(HIGH_VALUE_TAG, String.valueOf(obj.getHighValue()));
    }
    else {
      write(String.valueOf(obj.getIntValue()));
    }
    return obj;
  }

  public ToxmlObject visit(IntegerArray obj) throws Exception {
    writeAbstractAttributes(obj);
    write(obj.toString());
    return obj;
  }

  public ToxmlObject visit(InexactValue obj) throws Exception {
    writeAbstractAttributes(obj);
    if (obj.isRange()) {
      if (Float.isInfinite(obj.getLowValue()) &&
          Float.isInfinite(obj.getHighValue())) {
        writer.writeAttribute(QUALIFIER_ATTRIBUTE, UNKNOWN_ATTRIBUTE);
      }
      else if (Float.isInfinite(obj.getLowValue())) {
        writer.writeAttribute(QUALIFIER_ATTRIBUTE, LESS_ATTRIBUTE);
        write(StringUtil.formatProperty(obj.getHighValue()));
      }
      else if (Float.isInfinite(obj.getHighValue())) {
        writer.writeAttribute("qualifer", GREATER_ATTRIBUTE);
        write(StringUtil.formatProperty(obj.getLowValue()));
      }
      else {
        writeText(LOW_VALUE_TAG, String.valueOf(obj.getLowValue()));
        writeText(HIGH_VALUE_TAG, String.valueOf(obj.getHighValue()));
      }
    }
    else {
      write(String.valueOf(obj.getFloatValue()));
    }
    return obj;
  }

  public ToxmlObject visit(Units obj) throws Exception {
    writeAbstractAttributes(obj);
    write(obj.toString());
    return obj;
  }

  public ToxmlObject visit(TypedValue obj) throws Exception {
    writeAbstractAttributes(obj);
    writer.writeAttribute(TYPE_ATTRIBUTE, obj.getType());
    write(obj.getValue());
    return obj;
  }

  public ToxmlObject visit(AbstractToxmlEnumeratedType obj) throws Exception {
    writeAbstractAttributes(obj);
    write(obj.toString());
    return obj;
  }
  
  private void write(String value) throws Exception {
    writer.writeCharacters(value);
  }
  
  private void writeText(String element, String value) throws Exception {
    writer.writeStartElement(element);
    writer.writeCharacters(value);
    writer.writeEndElement();
  }
  
  private void writeAbstractAttributes(ToxmlObject obj) throws Exception {
    String sourceList = StringUtil.join(obj.getSources(), ",");
    if (sourceList.trim().length() > 0) {
      writer.writeAttribute(SOURCES_ATTRIBUTE, sourceList);
    }
    DiffStatus diffStatus = obj.getDiffStatus();
    if (diffStatus != null) {
      writer.writeAttribute(DIFF_ATTRIBUTE, diffStatus.toString());
    }
    String conflictValue = obj.getOriginalConflictValue();
    if (conflictValue != null) {
      writer.writeAttribute(ORIGINAL_CONFLICT_ATTRIBUTE, conflictValue);
    }
  }
}

/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.io;

import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import com.leadscope.stucco.*;
import com.leadscope.stucco.StringValue;
import com.leadscope.stucco.util.StringUtil;

//import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;

/**
 * Writes toxml objects to an xml stream
 */
public class ToxmlWriter implements ToxmlVisitor<ToxmlObject>, ToxmlXmlConstants {
  private XMLStreamWriter writer;
    
  public static String toString(String rootTag, ToxmlObject obj) throws Exception {
    StringWriter sw = new StringWriter();
    ToxmlWriter writer = new ToxmlWriter(
        rootTag,
        XMLOutputFactory.newInstance().createXMLStreamWriter(sw));
    obj.accept(writer);
    writer.close();
    return sw.toString();
  }
  
  public static void write(String rootTag, ToxmlObject obj, OutputStream os) throws Exception {
    ToxmlWriter writer = new ToxmlWriter(
        rootTag,
        XMLOutputFactory.newInstance().createXMLStreamWriter(os));
    obj.accept(writer);
    writer.close();
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

/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

/**
 * A visitor that can process a toxml hierarchy. Every mutually exclusive interface
 * or class that extends ToxmlObject should have a visit method defined below.
 */
public interface ToxmlVisitor<V> {

  public V visit(CompositeToxmlObject obj) throws Exception;
  
  public <T extends ToxmlObject> V visit(ToxmlObjectList<T> obj) throws Exception;
  
  public <T extends HashableToxmlObject> V visit(ToxmlObjectSet<T> obj) throws Exception;
  
  public V visit(StringValue obj) throws Exception;  
  public V visit(CDataValue obj) throws Exception;  
  public V visit(BooleanValue obj) throws Exception;
  public V visit(DateValue obj) throws Exception;
  public V visit(FloatValue obj) throws Exception;
  public V visit(IntegerValue obj) throws Exception;
  public V visit(IntegerRange obj) throws Exception;
  public V visit(IntegerArray obj) throws Exception;
  public V visit(InexactValue obj) throws Exception;
  public V visit(Units obj) throws Exception;
  public V visit(TypedValue obj) throws Exception;
  public V visit(AbstractToxmlEnumeratedType obj) throws Exception;
}

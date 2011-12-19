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

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
package com.leadscope.stucco;

/**
 * A visitor that can process a toxml hierarchy. Every mutually exclusive interface
 * or class that extends ToxmlObject should have a visit method defined below.
 */
public interface ToxmlVisitor<V> {

  V visit(CompositeToxmlObject obj) throws Exception;
  
  <T extends ToxmlObject> V visit(ToxmlObjectList<T> obj) throws Exception;
  
  <T extends HashableToxmlObject> V visit(ToxmlObjectSet<T> obj) throws Exception;
  
  V visit(StringValue obj) throws Exception;
  V visit(CDataValue obj) throws Exception;
  V visit(BooleanValue obj) throws Exception;
  V visit(DateValue obj) throws Exception;
  V visit(FloatValue obj) throws Exception;
  V visit(IntegerValue obj) throws Exception;
  V visit(IntegerRange obj) throws Exception;
  V visit(IntegerArray obj) throws Exception;
  V visit(InexactValue obj) throws Exception;
  V visit(Units obj) throws Exception;
  V visit(TypedValue obj) throws Exception;
  V visit(AbstractToxmlEnumeratedType obj) throws Exception;
}

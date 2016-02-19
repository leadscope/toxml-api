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

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import com.leadscope.stucco.ToxmlObject;

/**
 * A streamable source of toxml objects from a file. The source file should contain
 * a root element that in turn contains an arbitrary number of child elements. Each
 * child element should contain the specified type of toxml object.
 * <pre>
 * &lt;Compounds&gt;
 *   &lt;Compound&gt;
 *     &lt;Ids&gt;&lt;Id type=&quot;reg&quot;&gt;LS-12345&lt;/Id&gt;&lt;/Ids&gt;
 *   &lt;/Compound&gt;
 * &lt;/Compounds&gt;
 * </pre>
 * 
 * Could be parsed with: 
 *   new ToxmlFileSource&lt;CompoundRecord&gt;(file, CompoundRecord.class);
 */
public class ToxmlFileSource<T extends ToxmlObject> implements Iterable<T> {
  private File file;
  private Class<T> toxmlClass;
  
  /**
   * Creates an Iterable source for a toxml file
   * @param file the file to read from - should contain a root element containing an
   * arbitrary number of child elements that contain the given class of toxml object
   * @param toxmlClass the class of toxml object that should appear in each child
   * element of the root
   */
  public ToxmlFileSource(File file, Class<T> toxmlClass) {
    this.file = file;
    this.toxmlClass = toxmlClass;
  }

  /**
   * Opens the file and returns an iterator - once the iteration is complete the underlying
   * file will be closed. If you need to close the file earlier, call close. The
   * underlying file is also closed if an exception occurs during iteration.
   * @return an iterator over the source
   * @exception RuntimeException if something goes wrong with parsing
   */
  public ToxmlIterator iterator() {
    try {
      return new ToxmlIterator();
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
      
  public class ToxmlIterator implements Iterator<T> {
    private XMLStreamReader reader;
    private T nextObj;
    private boolean closed;
    
    public ToxmlIterator() throws Exception {
      reader = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(file));
      reader.nextTag(); // root element
      parseNextObj();
    }
    
    public boolean hasNext() {
      return nextObj != null;
    }

    public T next() {
      if (nextObj == null) {
        throw new NoSuchElementException();
      }
      T thisObj = nextObj;
      parseNextObj();
      return thisObj;
    }

    /**
     * Not available
     */
    public void remove() {
      throw new RuntimeException("Cannot remove from from ToxmlFileSource");
    }    
    
    /**
     * Call if the source needs to be closed before the iterator completes. Not
     * necessary to call if the iterator is used to completion. Idempotent in any
     * case
     */
    public void close() throws Exception {
      if (!closed) {
        reader.close();
        closed = true;
      }
    }
    
    private void parseNextObj() {
      try {
        int eventType = reader.nextTag(); // next element
        if (eventType == XMLStreamReader.START_ELEMENT) {
          nextObj = ToxmlParser.parseInternal(reader, toxmlClass);
        }
        else {
          nextObj = null;
          close();
        }
      }
      catch (Throwable t) {
        try { reader.close(); } catch (Throwable t2) { }
        if (t instanceof RuntimeException) {
          throw (RuntimeException)t;
        }
        else {
          throw new RuntimeException(t);
        }
      }
    }
  }
}

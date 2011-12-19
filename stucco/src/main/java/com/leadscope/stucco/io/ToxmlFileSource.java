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

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import com.leadscope.stucco.ToxmlObject;

/**
 * A streamable source of toxml objects from a file
 */
public class ToxmlFileSource<T extends ToxmlObject> implements Iterable<T> {
  private File file;
  private Class<T> toxmlClass;
  
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
     * Call if the source needs to be closed before the iterator completes
     */
    public void close() throws Exception {
      reader.close();
    }
    
    private void parseNextObj() {
      try {
        int eventType = reader.nextTag(); // next element
        if (eventType == XMLStreamReader.START_ELEMENT) {
          nextObj = ToxmlReader.parseInternal(reader, toxmlClass);
        }
        else {
          nextObj = null;
          reader.close();
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

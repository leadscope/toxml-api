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

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import com.leadscope.stucco.PrimitiveToxmlObject;
import com.leadscope.stucco.ToxmlObjectParent;

/**
 * Handler that allows additional tags to exist and simply skips them. 
 * It collects the line numbers and tags of skipped elements.
 */
public class IgnoreAdditionalTagsHandler implements ToxmlErrorHandler {
  private List<SkippedTag> skippedTags = new ArrayList<SkippedTag>();
  
  public boolean unexpectedTag(
      XMLStreamReader reader, 
      ToxmlObjectParent parent, 
      String tag) throws ToxmlReaderException {
    
    skippedTags.add(new SkippedTag(tag, reader.getLocation().getLineNumber()));
    try {
      int depth = 0;
      int eventType = reader.next();
      while (eventType != XMLStreamReader.END_DOCUMENT) {        
        if (eventType == XMLStreamReader.END_ELEMENT) {
          if (depth == 0) {
            return true;
          }
          else {
            depth--;
          }
        }
        else if (eventType == XMLStreamReader.START_ELEMENT) {
          depth++;
        }
        eventType = reader.next();
      }
      if (eventType == XMLStreamReader.END_DOCUMENT) {
        throw new RuntimeException("Unexpected end of document before finding end tag for: " + tag);
      }
      return true;
    }
    catch (Throwable t) {
      throw new ToxmlReaderException(reader.getLocation().getLineNumber(), t);
    }
  }

  public boolean valueException(
      XMLStreamReader reader,
      ToxmlObjectParent parent, 
      String tag, 
      PrimitiveToxmlObject obj,
      String value, 
      Exception exception) throws ToxmlReaderException {
    return false;
  }

  public List<SkippedTag> getSkippedTags() {
    return skippedTags;
  }
  
  public static class SkippedTag {
    private String tag;
    private int lineNumber;
    public SkippedTag(String tag, int lineNumber) {
      this.tag = tag;
      this.lineNumber = lineNumber;
    }
    public String toString() {
      return "Skipped " + tag + " at line: " + lineNumber;
    }
  }
}

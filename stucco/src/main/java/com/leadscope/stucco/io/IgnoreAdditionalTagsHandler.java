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

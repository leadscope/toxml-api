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

import com.leadscope.stucco.PrimitiveToxmlObject;
import com.leadscope.stucco.ToxmlObjectParent;

import javax.xml.stream.XMLStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IgnoreLegacyTagsHandler extends IgnoreAdditionalTagsHandler {
  private Set<String> legacyTags = new HashSet<>(Arrays.asList(
     "InvalidMolecularFormulas", "AdditionalMolecularFormulas"
  ));

  @Override
  public boolean unexpectedTag(
          XMLStreamReader reader,
          ToxmlObjectParent parent,
          String tag) throws ToxmlReaderException {
    if (legacyTags.contains(tag)) {
      return super.unexpectedTag(reader, parent, tag);
    }
    else {
      return false;
    }
  }

  @Override
  public boolean valueException(XMLStreamReader reader, ToxmlObjectParent parent, String tag, PrimitiveToxmlObject obj, String value, Exception exception) throws ToxmlReaderException {
    return false;
  }
}

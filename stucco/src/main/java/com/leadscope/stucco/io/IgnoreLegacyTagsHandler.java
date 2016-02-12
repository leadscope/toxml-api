/**
 * Copyright 2016 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
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

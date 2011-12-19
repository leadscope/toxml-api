/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.apps;

import java.io.File;

import com.leadscope.stucco.io.ToxmlHandler;
import com.leadscope.stucco.io.ToxmlReader;
import com.leadscope.stucco.model.CompoundRecord;

/**
 * A simple app that reads a toxml file and prints out id information
 * to the stdout
 */
public class ToxmlValidator {
  private static void usageExit() {
    System.out.println("usage: toxml_validator <input file>");
    System.exit(1);
  }

  private static class Handler implements ToxmlHandler<CompoundRecord> {
    public void handle(CompoundRecord cr) throws Exception {
      if (cr.getIds().size() >= 1) {
        System.out.println("Parsed: " + cr.getIds().get(0).toDisplayableString());
      }
      else if (cr.getNames().size() >= 1) {
        System.out.println("Parsed: " + cr.getNames().get(0).toDisplayableString());
      }
      else {
        System.out.println("Parses anonymous compound");
      }
    }    
  }
  
  /**
   * @param args file
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      usageExit();
    }
    
    File file = new File(args[0]);
    try {
      ToxmlReader.parseList(file, CompoundRecord.class, new Handler());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}

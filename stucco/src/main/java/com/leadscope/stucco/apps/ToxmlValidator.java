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
package com.leadscope.stucco.apps;

import java.io.File;

import com.leadscope.stucco.io.ToxmlHandler;
import com.leadscope.stucco.io.ToxmlParser;
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
      ToxmlParser.parseList(file, CompoundRecord.class, new Handler());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}

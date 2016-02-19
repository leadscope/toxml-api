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
package com.leadscope.stucco.apps;

import com.leadscope.stucco.io.IgnoreAdditionalTagsHandler;
import com.leadscope.stucco.io.ToxmlHandler;
import com.leadscope.stucco.io.ToxmlParser;
import com.leadscope.stucco.model.CompoundRecord;

import java.io.File;

/**
 * A simple app that reads a toxml file and prints out id information
 * to the stdout
 */
public class ToxmlValidator {
  private static void usageExit() {
    System.out.println("usage: toxml_validator [--allow-additional-tags] <input file>");
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
    if (args.length < 1 || args.length > 2) {
      usageExit();
    }

    File file = null;
    boolean allowAdditionalTags = false;
    if (args.length == 2) {
      if (args[0].equals("--allow-additional-tags")) {
        allowAdditionalTags = true;
        file = new File(args[1]);
      }
      else {
        usageExit();
      }
    }
    else {
      file = new File(args[0]);
    }

    try {
      if (allowAdditionalTags) {
        IgnoreAdditionalTagsHandler ignoreHandler = new IgnoreAdditionalTagsHandler();
        ToxmlParser.parseList(file, CompoundRecord.class, new Handler(), ignoreHandler);
        ignoreHandler.getSkippedTags().stream()
                .forEachOrdered(System.out::println);
      }
      else {
        ToxmlParser.parseList(file, CompoundRecord.class, new Handler());
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}

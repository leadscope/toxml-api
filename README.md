ToxML API
=========

Provides an implementation of the ToxML specification. There are two parts to the
project, the main stucco module and the stucco-builder-plugin module, which is used
to generate the concrete model objects from the current specification.

Installation
------------
To create API jar file:

  mvn install

The jar file will available be at: stucco/target/stucco-<version>.jar


Using the API
-------------
The com.leadscope.stucco.model package contains the concerte object model. The main
root object is the CompoundRecord which will hold information about a single compound
(including any number of toxicity studies performed with that compound). However,
any object in the model package can be created and used standalone.

To parse ToxML documents, use the ToxmlReader class, e.g.:

    import com.leadscope.stucco.io.ToxmlReader
    import com.leadscope.stucco.model.CompoundRecord
    ...

    ToxmlReader.parseList(
        new File("src/test/resources/fdagenetox-test.xml"),
        CompoundRecord.class,
        new ToxmlHandler<CompoundRecord>() {
          public void handle(CompoundRecord obj) {
            System.out.println(obj.getIds().get(0).getValue());
          }
        });

To write a ToxML document, use the ToxmlWriter class, e.g.:

    import com.leadscope.stucco.io.ToxmlFileSource
    import com.leadscope.stucco.io.ToxmlWriter
    ...

    ToxmlWriter.write("Compounds", "Compound",
        new ToxmlFileSource<CompoundRecord>(inputFile, CompoundRecord.class),
        outputFile);


stucco-builder-plugin
---------------------
This module provides the generate-sources task for the main stucco module. Given a
toxml_specification.xml file (exported from the toxml.lhasalimited.org/release site),
it will construct concerte classes to implement the specification.

stucco
------
The main module contains all of the abstract classes and interfaces and will ultimately
generate the final library. The src/source-generation/toxml_specification.xml is used
to consruct the concrete classes via the stucco-builder-plugin module during the
generate-sources phase.


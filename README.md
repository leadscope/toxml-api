ToxML API
=========

For the upcoming update of the ToxML specification, the ToxML foundation has moved
towards opening the standard, and allowing external contributions. To this end
a community site has been created that allows the viewing and modification of
the working draft of the specification (http://toxml.lhasalimited.org/working-draft).
As part of this site, the specification can be downloaded in a concise XML format
(toxml_specification.xml). This project takes that specification and generates
a Java API that provides a data model, a parser, and a serializer.

There are two parts to the project, the main stucco module and the
stucco-builder-plugin module, which is used to generate the concrete model objects
from the current specification.

Installation
------------
To create API jar file:

  mvn install

The jar file will available be at: stucco/target/stucco-<version>.jar

To create the ToxML validation app:

  mvn package appassembler:assemble

The app directory will be available at: target/toxml-api-<version>. To run the ToxML validator:

  target/toxml-api-<version>/bin/toxml_validator <input toxml file>


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

    import com.leadscope.stucco.io.ToxmlWriter
    import com.leadscope.stucco.model.CompoundRecord
    ...

    Compound cr = new CompoundRecord();
    cr.getIds().add(new TypedValue("cas", "123-45-5"));

    ToxmlWriter.write("Compounds", "Compound",
        Arrays.asList(cr),
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


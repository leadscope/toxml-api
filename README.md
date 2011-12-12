ToxML API
=========

Provides an implementation of the ToxML specification. There are two parts to the
project, the main stucco module and the stucco-builder-plugin module, which is used
to generate the concrete model objects from the current specification.


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


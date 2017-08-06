# JavaByteCodeParser
<strong>Java Byte Code Parser using the Java Virtual Machine Specification (Java SE 8 Edition), calculates LCOM4 (Lack of Cohesion of Methods) and CBO (Coupling Between Objects) values</strong>.

<p>Generates DOT files which can be used by Graphviz to view a graphical model of the LCOM4 and CBO of input java .class files provided.</p> 
<p>A DOT file is generated for each java .class file provided, displaying the LCOM4 value and a single CBO DOT file is generated for all java .class files.</p>
<p>Prints all Fields, Methods and LCOM4 values for each java .class file in the terminal and a final CBO value.</p>

#### Only Class Files Version 51.0 and greater are supported, any previous versions will not be parsed.


###### Copyright © Nikhil Gudhka. All Rights Reserved.

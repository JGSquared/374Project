# 374Project

### DESIGN ###

	The design for this project consists of the Visitor and Strategy patterns. Visitors are used by DesignParser and ASM to 
	generate a HashMap that contains all of the properties needed to parse into GVEdit syntax (more on these properties in
	MORE INFO). Each HashMap created by DesignParser is sent to an implementation of IGraphDesign. IGraphDesign is an
	interface for generating proper GVEdit code from a HashMap. Currently our design has one implementation of this
	interface, DotGraphDesign, but more can be added. IGraphDesign handles generating the code and outputting the graph, which
	then completes the program's functionality. 


### CONTRIBUTORS ###

Josh Gayso:

M1:	Created IGraphDesign interface along with one implementation, DotGraphDesign. DotGraphDesign code split approximately 50/50
	with Josh Green. Setup initial DesignParser and visitors.

Josh Green:

M1:	Rewrote visitors to add fields to HashMap rather than building a string. Moved parse functionality from main to a parse 
	method in DesignParser. Implemented about 50% of code in DotGraphDesign.


### INSTRUCTIONS ###

	When running this program, pass any number of Java classes as arguments. A main method should construct a new DesignParser
	and a new IGraphDesign. Main should then call the parse method of DesignParser, passing in the array of Java classes and
	the IGraphDesign. DesignParser will then generate graph code one class at a time using the IGraphDesign passed in. Any
	implementation of IGraphDesign has access to the <Key, Value> pairs described in MORE INFO.
	

### MORE INFO ###

Current <Key, Value> pairs found inside DesignParser HashMap<String, String>:
	
	Key: Value
	
	className: The name of the Java class visited.
	access: String representation of the int access value (i.e. Opcodes.ACC_PUBLIC). Use Integer.parseInt to get the value.
	extends: The name of the Java class that <className> extends, or an empty String if there is none.
	implements: String representation of array of interfaces that <className> implements. Format is [<interface>, <interface>, ...]
	field<i>: i is some int. The value is a colon separated String of a Java class's field as [access]:[name]:[type] where [access] 
			  is as defined in <access>, [name] is the field's name, and [type] is the type of the field.
	method<i>: i is some int. The value is a String representation of a Java class's method as [access]:[name]:[stypes]:[returnType]
			   where [access] is as defined in <access>, [name] is the method name, [stypes] is the method argument types represented
			   as [<type>, <type>, ...], and [returnType] is the method's return Type. 
	

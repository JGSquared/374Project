# 374Project
# Group: Josh Green (greenjm), Josh Gayso (gaysojj)

### DESIGN ###

	The design for this project consists of the Visitor and Strategy patterns. Visitors are used by DesignParser and ASM to 
	generate a HashMap that contains all of the properties needed to parse into GVEdit syntax (more on these properties in
	MORE INFO). Each HashMap created by DesignParser is sent to an implementation of IGraphDesign. IGraphDesign is an
	interface for generating proper GVEdit code from a HashMap. Currently our design has one implementation of this
	interface, DotGraphDesign, but more can be added. IGraphDesign handles generating the code and outputting the graph PNG
	file, which	then completes the program's functionality. 


### CONTRIBUTORS ###

Josh Gayso:

M1:	Created IGraphDesign interface along with one implementation, DotGraphDesign.
	Implemented initializeCode(), generateCode(), addDeclarationCode(), addFieldCode(), and generateGraph() in DotGraphDesign.
	Setup initial DesignParser and visitors. 
	Implemented 50% of tests.
	

Josh Green:

M1:	Rewrote visitors to add fields to HashMap rather than building a string.
	Moved parse functionality from main to a parse method in DesignParser. 
	Implemented addMethodCode(), addExtensionAndImplementsCode(), getAccessSymbol(), and getName() in DotGraphDesign.
	Implemented 50% of tests.
	Created Design UML.
	
M2: Refactored DotGraphDesign, moving all code generators under new abstract class, IGraphCode.
	Implemented new ASM visitors.


### INSTRUCTIONS ###

	When running this program, pass any number of Java classes as arguments. A main method should construct a new DesignParser
	and a new IGraphDesign. Each IGraphDesign will have a List of IGraphCode, which creates GraphViz code from the <Key, Value>
	pairs described in MORE INFO. A default List can be used by calling the useDefault method of IGraphDesign, or a custom list
	can be created one by one. IGraphDesign calls the getCode method of each IGraphCode in the order they are added to the List.
	Main should then call the parse method of DesignParser, passing in the array of Java classes and the IGraphDesign. DesignParser
	will then generate graph code one class at a time using the IGraphDesign passed in. Any implementation of IGraphDesign has 
	access to the <Key, Value> pairs described in MORE INFO. Also, in order to properly generate a png file containing the auto-generated
	graph, the user needs to modify the properties file, located in input_output. The user needs to include the location of the 
	graphviz executable. The user can also choose a different location for the output file including the contents of the graphviz code.
	The user can define a white list in the properties file that will eliminate extraneous classes from being drawn in the graph.
	

### MORE INFO ###

Current <Key, Value> pairs found inside DesignParser HashMap<String, String>:
	
	Key: Value
	
	className: The name of the Java class visited.
	access: String representation of the int access value (i.e. Opcodes.ACC_PUBLIC). Use Integer.parseInt to get the value.
	extends: The name of the Java class that <className> extends, or an empty String if there is none.
	implements: String representation of array of interfaces that <className> implements. Format is [<interface>, <interface>, ...]
	field<i>: i is some int. The value is a colon separated String of a Java class's field as [access]:[name]:[type]:[signature] where
			  [access] is as defined in <access>, [name] is the field's name, [type] is the type of the field, and [signature] is either
			  a String representation of a collection Object or null.
	method<i>: i is some int. The value is a String representation of a Java class's method as [access]:[name]:[stypes]:[returnType]
			   where [access] is as defined in <access>, [name] is the method name, [stypes] is the method argument types represented
			   as [<type>, <type>, ...], and [returnType] is the method's return Type. 
	uses<i>: i is some int. The value is a String representation of a Java class, which the Class [className] uses.
	associated<i>: i is some int. The value is a String representation of a Java class, which the Class [className] is associated with,
				   either through aggregation or composition.

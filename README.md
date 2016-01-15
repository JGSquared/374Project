# 374Project
# Group: Josh Green (greenjm), Josh Gayso (gaysojj)

### DESIGN ###

M1:	The design for this project consists of the Visitor and Strategy patterns. Visitors are used by DesignParser and ASM to 
	generate a HashMap that contains all of the properties needed to parse into GVEdit syntax (more on these properties in
	MORE INFO). Each HashMap created by DesignParser is sent to an implementation of IGraphDesign. IGraphDesign is an
	interface for generating proper GVEdit code from a HashMap. Currently our design has one implementation of this
	interface, DotGraphDesign, but more can be added. IGraphDesign handles generating the code and outputting the graph PNG
	file, which	then completes the program's functionality.
	
M2: The design for this project stuck to the Visitor and Strategy patterns. The major change made to the design was to abstract
	the code which generated GraphViz strings into a new abstract class, GraphCode. In M1, all of this was encapsulated in IGraphDesign,
	meaning any implementation of that interface also needed to implement parsing the HashMap data into usable GraphViz code. Now, they 
	can simply call the getCode method of each GraphCode object given. Outside of this design overhaul, two new ClassVisitors and 
	two MethodVisitors were created, and FileProperties received a whiteList field to better filter out undesired classes from a 
	generated UML.
	
M3: The two major design changes for this milestone were extracting DesignParser as an interface with only one method, parse(), and 
	reorganizing code into packages to better separate code based on use, such as "problem.visitor" and "problem.code". Also, the 
	main method was moved to its own class, App, to make it easier to remember its location rather than having it inside a class
	that served another purpose. Due to our design choices in M2, we were able to create a solution to this problem by implementing
	already existing interfaces or the new IDesignParser interface. In total, we created one new implementation of IDesignParser,
	one new implementation of IGraphDesign, one new ClassVisitor, one new MethodVisitor, and two new implementations of
	AbstractGraphCode. 


### CONTRIBUTORS ###

Josh Gayso:

M1:	Created IGraphDesign interface along with one implementation, DotGraphDesign.
	Implemented initializeCode(), generateCode(), addDeclarationCode(), addFieldCode(), and generateGraph() in DotGraphDesign.
	Setup initial DesignParser and visitors. 
	Implemented 50% of tests.
	
M2: Implemented all new test cases.
	Implemented code to generate Uses and Associated arrows.
	
M3: Implemented new ASM visitors to populate HashMap with Sequence diagram information.
	Implemented new tests.

Josh Green:

M1:	Rewrote visitors to add fields to HashMap rather than building a string.
	Moved parse functionality from main to a parse method in DesignParser. 
	Implemented addMethodCode(), addExtensionAndImplementsCode(), getAccessSymbol(), and getName() in DotGraphDesign.
	Implemented 50% of tests.
	Created Design UML.
	
M2: Refactored DotGraphDesign, moving all code generators under new abstract class, IGraphCode.
	Implemented new ASM visitors.
	
M3: Implemented new codeGetters to generate SDEdit code.
	Updated README


### INSTRUCTIONS ###

	When running this program, pass any number of Java classes as arguments. A main method should construct a new IDesignParser
	and a new IGraphDesign. Each IGraphDesign will have a List of IGraphCode, which creates GraphViz code from the <Key, Value>
	pairs described in MORE INFO. A default List can be used by calling the useDefault method of IGraphDesign, or a custom list
	can be created one by one. IGraphDesign calls the getCode method of each IGraphCode in the order they are added to the List.
	Main should then call the parse method of DesignParser, passing in the array of Java classes and the IGraphDesign. IDesignParser
	will then generate graph code one class at a time using the IGraphDesign passed in. Any implementation of IGraphDesign has 
	access to the <Key, Value> pairs described in MORE INFO. Also, in order to properly generate a png file containing the auto-generated
	graph, the user needs to modify the properties file, located in input_output. The user needs to include the location of the 
	graphviz executable. The user can also choose a different location for the output file including the contents of the graphviz code.
	The user can define a white list in the properties file that will eliminate extraneous classes from being drawn in the graph.
	
	The only changes in instruction in order to create a sequence diagram are a change in command line arguments and a change in
	the output. To run this program to generate an SDEdit code file, you should pass command line arguments in the format <className>,<method> <i>.
	<className> is the path to a Java class, <method> is the name of a method within that class without the parameters (i.e. 
	"methodName(String s)" should just be "methodName"), and <i> is how many levels to go down in the method calls. <i> is optional,
	and will default to 5 if left blank. The current implementation of IGraphDesign and IDesignParser that handle creating sequence
	diagrams are SequenceGraphDesign and MethodDesignParser, respectively. The <Key, Value> pairs that should be expected in this
	implementation are defined in MORE INFO. Lastly, this implementation will not generate a PNG file. Rather, it will only 
	generate a code file specified in fileIn of properties.txt. fileIn should have the extension ".sd".
	

### MORE INFO ###

Current <Key, Value> pairs found inside DesignParser HashMap<String, String>:
	
	Key: Value for ClassDesignParser (UML)
	
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
				   
	Key: Value for MethodDesignParser (Sequence)
	
	sequenceNode<i>: i is an int representing the order in which this sequenceNode is used. the value is a repesentation of a node
					 in a sequence diagram as [className]:[hidden] where [className] is the node's class and [hidden] has either the
					 value "hidden" or "nonhidden", depending on the node's initial state.
	sequenceMethod<i>: i is an int representing the order in which this method is called. The value is a representation of the method
					   call as [caller]:[callee]:[method]:[args]

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
	
M4: Our overall design did not undergo many changes during this milestone. AbstractGraphCode became an interface, and all of its default
	helper methods were moved to a new class, Helpers, and each of the methods was made static. A new interface, IPatternDetector, was
	created, and a single implementation, SingletonPatternDetector, takes our parsedCode HashMap to determine if the given class uses
	the Singleton pattern.
	
M5: The design of our tool changed a fair amount to solve the issue of handling patterns that involved multiple classes. Our 
	solution was to push all pattern detection until after the GraphViz code had been generated, sending a list of each class's parsedCode.
	To handle adding to the StringBuilder after the fact, we use our own special syntax within the code in order to inject proper
	labels and colors into the generated code. Constants.java holds all of the special syntax tags used, and any new tags will be
	added here. Lastly, FileProperties was transformed into a Singleton to ensure there was no difference in the properties found
	between our classes that require those properties.

M6: The design of our tool for this milestone evolved slightly to make it easier for detectors to find and replace our special
	syntax. Previously, our DotGraphDesign constructed a single large String containing all of the code generated for each class.
	Now, each time the addGraphCode method is called, DotGraphDesign maps that class to its code. This map is sent to our detectors,
	and now knowing the class name makes it quick to find the correct piece of code and inject the proper labels and colors. All of 
	these string are combined after the detectors have run to generate the full graph. Outside of this change, our design stayed
	consistent, and we only added one new class, CompositePatternDetector, to solve the problem for this milestone.
	
M7: This milestone saw one of the most substantial changes to our design yet. In the past, we were generating Strings for each class
	before running our pattern detectors, and our detectors would use copious amounts of String parsing to place the correct labels
	and colors into their proper place. This design made it near impossible to separate our program into distinct phases, because each
	step required arguments to be passed in from a previous step. This entire process was redesigned using a combination of the Command,
	Visitor, and Singleton patterns. The Command pattern is used to run the phases in sequence, where each phase is responsible for 
	creating and executing any classes it needs to run (See INSTRUCTIONS for information about phases). We use two Singletons, one for
	maintaining the runtime properties of the session, as determined by the config file, and another to maintain the data structures
	that represent a Class object (IClass, see MORE INFO). Finally, the visitor pattern is employed at the end step of the core code's
	execution. Our visitors allow us to traverse our data structures and generate the GraphViz code without relying on any other phases. 


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

M4: Implemented new design detector.
	Implemented new test cases.
	
M5: Implemented Adapter detector.
	Implemented new tests.
	
M6: Implemented Composite detector.
	Implemented new tests.
	
M7: Implemented majority of GUI code.
	Implemented Properties.
	

Josh Green:

M1:	Rewrote visitors to add fields to HashMap rather than building a string.
	Moved parse functionality from main to a parse method in DesignParser. 
	Implemented addMethodCode(), addExtensionAndImplementsCode(), getAccessSymbol(), and getName() in DotGraphDesign.
	Implemented 50% of tests.
	Created Design UML.
	
M2: Refactored DotGraphDesign, moving all code generators under new abstract class, IGraphCode.
	Implemented new ASM visitors.
	
M3: Implemented new codeGetters to generate SDEdit code.
	Created Sequence diagrams.
	
M4: Implemented new design detector.
	Updated documentation, including UML and README.
	
M5: Implemented Decorator detector.
	Updated documentation, including UML and README.
	
M6: Implemented Composite detector.
	Updated documentation, including UML and README.
	
M7: Implemented majority of refactor.
	Implemented ClassStorage Singleton.


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
	the output. To run this program to generate an SDEdit code file, you should pass command line arguments in the format
	<className>,<method>,<argTypes> <i>. <className> is the path to a Java class, and <method> is the name of a method within that
	class without the parameters (i.e. "methodName(String s)" should just be "methodName"). <argTypes> is a comma separated list
	of the <method>'s qualified argument types (i.e. List is java.util.List) in the order that they appear in the method declaration,
	and <i> is how many levels to go down in the method	calls. <i> is optional, and will default to 5 if left blank. The current
	implementation of IGraphDesign and IDesignParser that handle creating sequence diagrams are SequenceGraphDesign and
	MethodDesignParser, respectively. The <Key, Value> pairs that should be expected in this implementation are defined in MORE INFO.
	Lastly, this implementation will not generate a PNG file. Rather, it will only generate a code file specified in fileIn of
	properties.txt. fileIn should have the extension ".sd".
	
	In Milestone 5, DotGraphDesign must have IPatternDetectors registered, much like IGraphCode. Calling useDefaultPatternDetectors()
	will use all of our default implementations of IPatternDetector. 
	
	HOW-TO: Specify execution order of phases
	Specifying the execution order of phases can be done in two simple steps. First, you should create or edit
	a new txt file with the format described in MORE INFO. This file should have a property called Phases. Just
	set the value of that property to a comma separated list of phase names you wish to execute in the order you
	want them to execute. Next, you will need to add the phases to a PhaseRunner class (most likely in the main
	method). Just create a PhaseRunner, and call addPhase, passing in the name of the phase, and an IPhase
	instance that should be executed for that name. If you have added every phase correctly, calling the run
	method of PhaseRunner will ensure that the phases defined in the config execute in the proper order you
	defined.
	
	HOW-TO: Set pattern specific settings
	For any pattern specific setting, you will need to add a custom key, value pair to the config, and create a
	new implementation of IPhase that can be executed as described above. To provide as much flexibility as 
	possible to the user, we expect each IPhase to instantiate the classes it will use. Thus, if you define a 
	custom property, you can retrieve it from within your IPhase by calling ConfigProperties.getInstance().getProperty(<your-property>). Then, having access to the parameter you defined,
	you can use it as an argument to any class that accepts it. Again, this is up to the IPhase itself to ensure
	the parameter is handled properly.
	

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
	associated<i>: i is some int. The value is a String representation of a Java class, wh,ich the Class [className] is associated with,
				   either through aggregation or composition.
				   
	Key: Value for MethodDesignParser (Sequence)
	
	sequenceNode<i>: i is an int representing the order in which this sequenceNode is used. the value is a repesentation of a node
					 in a sequence diagram as [className], where [className] is the node's class.
	sequenceMethod<i>: i is an int representing the order in which this method is called. The value is a representation of the method
					   call as [caller]:[callee]:[method]:[args]
					  
					  
Proper format for a config file:

	For a config file to be read properly by our program, it must follow the format outlined by the Java
	Properties class. This means that each property must be defined on its own line, and follow the following
	format: "<key-name>: <value>". Normally, each value will be read in as a String, but there are five required
	properties for our config files, and failure to provide any of them could lead to errors. The keys and value
	formats are:
	
	Input-Folder: A path to a directory
	Input-Classes: A comma separated list of the full package for a Java	class (i.e. List should be
		java.util.List)
	Output-Directory: a path to a directory
	Dot-Path: path to the file 'dot.exe'
	Phases: Comma separated list of phase names
	
	Any other property key,value pairs can be specified in the same file, and will be stored in ConfigProperties
	as a String value, which can be retrieved by calling getProperty with the key.

Explanation of data structures:

	To ease the process of generating code, we store a List of IClass, each of which contains Lists for IField,
	IMethod, and IArrow. The fields contained in each can be seen by checking the interfaces or the specific
	implementations (All of which can be found in problem.graph.component). To ensure that any class can access
	these structures without a need to pass the List around, we store our data structures in a Singleton, 
	ClassStorage, which provides all necessary methods for storing and retrieving the data. 



HOW TO RUN FIERY DRAGONS:
1) Download and install the JDK 22 Development Kit 22: https://www.oracle.com/my/java/technologies/downloads/#jdk22-windows
2) Download and install JavaFx version 22.0.1 SDK https://gluonhq.com/products/javafx/
3) Download the FieryDragonsGame.jar FILE
3) Run the jar file using Java(TM) Platform SE binary

FUNCTIONALITY CREATED
1) Setup up the initial game board
2) Flipping the dragon cards

HOW TO BUILD THE EXECUTABLE
1) Create a new module and in the Public Static void main program call the main method from the main module of the javafx project
2) Go to File -> Project Structure. Then go to the Artifact tab and press the "+" button.
3) Then press JAR -> from module with dependencies
4) Then at the Main class choose the newly created main class. Then Press ok
5) At the Output Layout. Press the "+" button then choose File
6) Find the extracted JavaFx folder then go into the Bin file and choose all the files that ends with .dll
7) Click Apply then Ok
8) Go to Build -> Build Artifact
9) Press Build and you will see the .jar file be created in a newly created directory call "out"
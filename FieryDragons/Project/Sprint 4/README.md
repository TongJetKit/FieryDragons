HOW TO RUN FIERY DRAGONS:
1) Download and install the JDK 22 Development Kit 22: https://www.oracle.com/my/java/technologies/downloads/#jdk22-windows
2) Download and install JavaFx version 22.0.1 SDK https://gluonhq.com/products/javafx/
3) Download the FieryDragonsGame.jar FILE
3) Run the jar file using Java(TM) Platform SE binary


HOW TO BUILD THE EXECUTABLE
1) Make sure you have installed JDK 22 and JavaFx version 22.0.1
2) Create a new module and in the Public Static void main program call the main method from the main module of the javafx project
3) Go to File -> Project Structure. Then go to the Artifact tab and press the "+" button.
4) Then press JAR -> from module with dependencies
5) Then at the Main class choose the newly created main class. Then Press ok
6) At the Output Layout. Press the "+" button then choose File
7) Find the extracted JavaFx folder then go into the Bin file and choose all the files that ends with .dll
8) Click Apply then Ok
9) Go to Build -> Build Artifact
10) Press Build and you will see the .jar executable file be created in a newly created directory call "out"
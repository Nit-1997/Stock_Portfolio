##Readme for Stocks Assignment 7
<hr>

###How to Run JAR / Code from intelliJ
To run the JAR file, place it in a directory with any files you plan on loading into the program. Then run the command:

```java -jar Stocks.jar```

In order to run the graphical user interface, rather than the terminal interface, also pass the argument "GUI" like so:

```java -jar Stocks.jar GUI```

To run application through IntelliJ : 
    
Run the "Stock Runner" class for terminal
    
For GUI View, edit Configuration and add "GUI" in program arguments.

###Parts Completed :-
- Rebalancing a portfolio on a specific date.
- Adding the rebalance feature in the command line UI.
- Adding the rebalance feature in the GUI.

###Changes made to old code :-

Model : 
   - Added 2 new functions getStocksOnDate and reBalance in the Model interface.
   - Added their implementation in ModelImpl class.
   - Added tests for modelImpl for rebalancing scenarios.

Controller
   - for terminal controller
     - created a new function "rebalance" that takes portfolio name and date from user and displays the stocks available on that date and takes weights from user for each stock available.
   - for GUI : 
     - added 2 new functions : getStockNamesForReBalancing, reBalance in Features interface to get stocks available in a portfolio on a date and then to rebalance portfolio
     - added implementation of these functions in "Controller" class

View:
   -  for terminal view : added 8 new functions in StockView interface and their implemetations in StockViewer class to basically print the error message and stocks and other information.
   - for GUI view : 
     - created new JPanel,Map of {stock,weights}, Jbuttons and JPanels for rebalance panel
     - Created 2 new functions : 
     - reBalanceCaller : which contains inputValidation and data collection for rebalance function.
     - reBalance : which checks the weights are in correct format and then calls the rebalance function of features which in turn calls model's rebalance function.


<hr>

<h5>Contributors:</h5> 

Nitin Bhat
<br>
Arush Aggarwal

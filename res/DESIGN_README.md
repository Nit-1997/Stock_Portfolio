# Assignment 4: Stocks (Part 1)
## Overall Design

### Controller
The controller is fed the view, an input, and a model when created in the main method. Once that occurs, go is called and execution starts. There are several methods within the controller (which can be seen in the class diagram) that support execution in order to reduce repeated code. The controller calls these methods from a main switch block that takes the user's input from the specified inputStream.

### View
The view is responsible for displaying the data in the specified printStream, which is passed to the view when it is created. Every method within in the view works with just a String input (or collection of Strings), and simply outputs a specified format to the printStream.

### Model
The model contains the builk of the logic behind simulating the stock program. From the previous iteration of this project we have implemented a facade, being the model class. The controller deals only with this class, which handles the other smaller parts of the model. The model itself utilizes the composition design technique, containing several smaller parts of the model: the API for getting data, the portfolio class for storing portfolio data, the fileHandler for reading and writing data, and the stock class for holding the stock data.

The model has a list of portfolio objects, which can be added to by the controller when it receives input from the user to do so. Once a stock ticker is specified, the controller can tell the model to validate the ticker using the API (keeping the API seperate from the controller). The model calls the constructor for the flexible and inflexible portfolios, with the arguments that the controller passes to it. All the constructors are called from **within the model**, and are as such abstracted away from the controller. It simply calls methods within the model whenever it needs to perform any of these functions.

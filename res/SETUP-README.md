# Assignment 4: Stocks (Part 1)
## Setup

### Running the JAR
To run the JAR file, place it in a directory with any files you plan on loading into the program. Then run the command:

java -jar Stocks.jar

In order to run the graphical user interface, rather than the terminal interface, also pass the argument "GUI" like so:

java -jar Stocks.jar GUI

### Input file formatting
The format for a inflexible portfolio file to load it into the program is a .JSON file extension, with the following style:

    {
        "Ticker1":123,
        "Ticker2":456
    }

Where Ticker1 and Ticker2 are the stock symbols for the stocks in the portfolio, and the values 123 and 456 are the number of each of those stocks owned. When prompted for a filename to read, please include the .JSON file extension. To load a file, enter L on the main screen and enter the name of the file to be loaded (include the .JSON) extension. Once it is loaded, the program will prompt you to enter the name of the portfolio. Now the portfolio is loaded and can be viewed the same way a created portfolio can be. For the GUI, there is a load tab, with a file selector for you to choose the JSON with your portfolio in it. A popup will confirm that it was loaded properly, and you should be able to select it then.

Flexible portfolios work in the same way, but the formatting is slightly different, with each transaction recorded as a new entry in the JSON.

### Creating a new portfolio
#### Terminal
To create a new portfolio from the main menu, press the N key then enter, and when prompted enter the portfolio name. Next, you will be asked to enter a ticker, and the number of stocks to buy. These will be added to the portfolio. You can continue for as many stocks as you would like (within limits mentioned later), and when done enter the D key. The portfolio will now be created and you should see a screen confirming this. Once a portfolio is created, it is automatically saved to a JSON file in the same directory as the JAR file.

To view the portfolio from the main screen, enter the V key. The program will prompt you to select which portfolio to view, simply type the number corresponding to this and press enter. You will then be prompted for a date in the format YYYY-MM-DD. Enter a date in this format, and press enter. Finally the portfolio will display the data for your selected portfolio for the given date, including the total value and the individual value of the stocks owned.

#### GUI
To create a new portfolio in the GUI, select the _new portfolio_ button in the top right of the screen. A box will prompt you to enter a name for the portfolio, and then click ok. A popup will confirm the creation, or show any errors that occur.

To select the portfolio, simply choose it from the drop down on the top of the screen.

To view the portfolio, you can select either _view portfolio_ or _performance_ from the tabs on the left. Performance is the tab for the graph showing the performance over an interval, and viewing the portfolio will show you the composition on the date you enter.

### Program limitations
The stocks program supports any ticker supported by the alphaVantage API, as it is used to retreive the stock data. As such, the program will wait for the API to respond, and this will occasionally take a moment. Currently saving and loading portfolios only supports direct buy/sell transactions. Any strategies are not persisted.
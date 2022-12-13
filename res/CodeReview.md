## Design Critique
- GUI code could have been modularized , so that we could abstract the common code for panels that is currently being repeated. one suggestion would be to create different classes for each different panel and a different helper class to validate user, so that it is easy to traverse through the main GUI view class and add new component.
- Lot of processing post file reads / write is happening in the controller invalidating the MVC principles , this could have been avoided by restricting parsing/processing to model which helps when we change the type of view from text to GUI etc.
- We observed a lot of typecasting done in the codebase to find the portfolio type i.e flexible or inflexible. A better approach would have been to create 2 model gateways one for flexible portfolios , one for inflexible portfolios and use composition to abstract common code into abstract classes and keep code specific to that portfolio type implementation in it's own class. This would have cleaned up the flow and isolated the code for different portfolio types in their own modules satifying the Open for extention and closed for modification principles.


## Implementation Critique

- User has to manually load files of previously created portfolios in every session. Automatic loading of portfolio could have been implemented. For example, loading a list of names of all the JSONs in data directory and then using the filename user inputs to read and load the actual file.
- Handling of value on weekend. Application does not give ```value of portfolio``` on a weekend , which should not be the case. Not allowing Buying/Selling on a weekend is justified but the value of a portfolio on a Saturday would be the same as value of the portfolio on Friday, so as a User I should be able to see it.
- Command line UI is not intuitive to use. Could have distinguished inflexible and flexible portfolios into their own menus with their specific features. For ex : right now if you try some features of a flexible portfolios on inflexible ones they are not valid but it shows in the UI which confuses the User. This also leads to some unexpected crashes. Also there is no option to exit or go back to previous menu in the command line UI.
- While taking inputs for creating portfolios , the user is asked to give tickers as inputs and ```D``` is the command to stop taking inputs. This becomes an issue if I want to create a portfolio which has ```Dominion Energy Inc``` whose ticker symbol is ```D```, Rather than using a char like ```D``` we could have used something like an Integer or some char that does not correspond to an actual Ticker symbol.
- Some methods like getPerformance() in the model are too long, they could have been broken down into smaller chunks.
- No validation is done on creating a portfolio with same name as the existing persisted portfolio. Rather it rewrites the JSON files of the old persisted portfolio. Which could have been avoided by adding a simple check to fetch all existing persisted portfolios.


## Documentation Critique
- Most of the codebase has excellent documentation which helped us understand the codebase really fast and understand the design to a great degree.


## Design/Code Strength

- Good Isolation of file handling operations into an interface, makes it extensible for adding further capabilities.
- Used Structural Design pattern like ```Fascade``` to make their model contain only publicly available methods accessible to the controller.
- Codebase is using ```composition technique``` in multiple places to reuse existing code which helps reduce redundancy.
- There is an attempt to use ```callback design pattern```  in the GUI controller which is great , Although for complex feature implementation there could have been separate class to make the code more manageable but the idea of using a callback design pattern for this use-case is great.
- Codebase's gui controller is independent of the view as there is no use of ActionListeners in the Gui controller making it independent of the swing dependencies.

## Design/Code limitations

- Graphical representation of performance is not working as per expectations. It just shows 1 line.
- There is no support for multiple DCA strategies. All these features could have been supported if the code owners persisted the dca plans for a portfolio.
- One time investment is not getting persisted in portfolio.(sends the error handling message after clicking submit finally, should have been on add.
-  Buying stocks before portfolio creation date should have be restricted as the portfolio won't exist on that date. This could have been achieved by persisting the portfolio creation date.
- Fractional shares not supported, This limits the application from adding this feature in the future and code would need to be changed for most of the functions as the number of stocks is represented by int everywhere in the codebase.
- Validations for loading a portfolio from a file are missing , for example If I edit a portfolio JSON with incorrect ticker, invalid date , invalid qty. The program tries to load it and eventually crashes. This could have handled by doing validation while loading the portfolio.


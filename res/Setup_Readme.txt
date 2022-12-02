================================================
Stock-App : Set-up Readme
================================================

------------------------------------------------
Folder Structure Required to run the JAR
------------------------------------------------

    assignment.zip :  unzip it.

    assignment
         |_ assignment.jar
         |_ stock
              |_ stock_list.csv ( contains list of stock symbols supported by the application)

    After the jar is running , the Program creates 2 more directories in Runtime :-

    - portfolios :- This stores all the newly created portfolios / if using manual files ,
                    create this repo manually then add those csv files in it.
           |_ flex :-  portfolios directory consists of flex directory that keeps track of the flexible portfolios, if you wish to create one manually create the csv here.
    - stock_data :- This stores the historic API data dump for the stock to be added dynamically into a csv file


===============================================
How To Run Program
===============================================

1.) Unzip the submission file
2.) You should see the stock folder with stock_list.csv file containing all the stock’s ticker symbol we support.
3.) In cmd/terminal enter java -jar assignment.jar
4.) make sure you maintain the folder structure as mentioned above.

===============================================
How to Run Tests
===============================================
For the tests to work properly your folder structure should look like this (testingArtifacts and portfolios/flex/flexUserTest2.csv are in res folder of submission) :-
         |_ src
         |_ test
         |_ testingArtifacts (contains dumps required to run tests)
         |_ portfolios/flex/flexUserTest2.csv (contains seed portfolois required for the tests to run correctly , NOTE: application works even without this only tests will fail as they need this to validate certain scenarios)
1.) In order to run the tests we require testingArtifacts and portfolios/flex/flexUserTest2.csv folder at the root level to dump all the testing data.
2.) You can go to the test and then run all tests and that should trigger the Junit for all the tests.

=========================================================
External dependencies required to run the application
=========================================================

jcommon-1.0.0.jar
Documentation
https://www.jfree.org/jcommon/

jfreechart-1.0.1.jar
Documentation
https://www.jfree.org/jfreechart/

Please make sure you have these 2 jars configured in your project to run the application.



===============================================
Getting Started
===============================================

-----------------------------------------------
Inflexible Portfolios
-----------------------------------------------

create portfolio with 2/3 different stocks using CLI.

         - In the welcome menu press 2 to create Inflexible Portfolios
         - Enter the name of the Portfolio you want to create e.g tech
         - If the portfolio with this name already exists you will get a prompt to enter other name or press
           0 to go back to previous menu
         - If you enter a new portfolio name it will display all available ticker symbols for the stocks
   - Enter the valid ticker symbol or press 0 to go back to main menu
         - If you enter an invalid ticker symbol , you get a validation prompt and are asked to enter the
           valid ticker symbol or press 0 to go back to main menu.
         - If you enter the correct valid ticker symbol it will ask you to enter number of stocks
           of this type.
   - Enter a valid Integer , if not entered you get a prompt to re-enter or go back to main menu using 0.
   - Once a valid quantity is entered you get a prompt to add more stocks using y/n
   - If you press n your portfolio gets saved and you are redirected to welcome menu.
   - If you press y , you are taken to the same add/remove stock menu.
   - Do this 3 times to add 3 stocks / 2 times to add 2 stocks
   - If you press 2 option in the add/remove/save menu you are asked the number of stocks you want to remove
   - If you press 3 option then your portfolio gets saved to a csv file in the Portfolios directory.


create portfolio with 2/3 different stocks using Files :-
    We allow user to create their own Portfolio CSV files and feed it to our system.
    In that case the csv file should look like this :-
    <ticker_symbol,BuyPrice,Quantity,Date>
    eg: NVDA,134.970001,12.0,2022-11-01
   - All the query functionality works as it it.
   - Program validates the file user manually creates and in case the file does not follow the
     constraints of program , it tells user to recreate it or use another portfolio.

How to query portfolios:-
          1. In the main menu press 2 to view portfolios
          2. List of portfolios is displayed
          3. Press 1 to get details of the portfolio eg tech
          4. Enter the valid name from the display else you get the validation prompt with option
             go back to previous menu by pressing 0.
          5. Press 1 to get summary
          6. Press 2 to get detailed view of the portfolio.
          7. Press 3 to get current value of the portfolio.
          8. Press 4 to get the portfolio detailed view by date , you are asked to input date
                   - If you enter a valid date you get the detailed view of the portfolio on that date.
                   - If you enter invalid date you get validaiton prompt and are asked to enter correct date.
          9. Press 5 to get the portfolio value by date , you are asked to input date
                   - If you enter a valid date you get the value of the portfolio on that date.
                   - If you enter invalid date you get validaiton prompt and are asked to enter correct date.
          10. Press 6 to return to the list of portfolios
          11. Press 7 to return to main menu.


------------------------------------------
Flexible Portfolios
------------------------------------------

.........................................................
create portfolio with 3 different stocks using CLI.
.........................................................

         - In the welcome menu press 1 to create Flexible Portfolios
         - Press 1 to Add new Portfolio
         - Enter the name of the Portfolio you want to create e.g tech
         - If the portfolio with this name already exists you will get a prompt to enter other name or press
           0 to go back to previous menu
         - If you enter a new portfolio name it will display all available ticker symbols for the stocks ( i.e NASDAQ 100)
   - Enter the valid ticker symbol or press 0 to go back to main menu
         - If you enter an invalid ticker symbol , you get a validation prompt and are asked to enter the
           valid ticker symbol or press 0 to go back to main menu.
         - If you enter the correct valid ticker symbol it will ask you to enter number of stocks
           of this type.
   - Enter a valid Integer , if not entered you get a prompt to re-enter or go back to main menu using 0.
   - Once a valid quantity is entered you get a prompt to add the transaction date, enter a valid date else a validaiton prompt occurs.
   - After adding in a valid date a prompt to add more stocks using y/n
   - If you press n your portfolio gets saved and you are redirected to welcome menu.
   - If you press y , you are taken to the same add/remove stock menu.
   - Do this 3 times to add 3 stocks / 2 times to add 2 stocks
   - If you press 2 option in the add/remove/save menu you are asked the number of stocks you want to remove
   - If you press 3 option then your portfolio gets saved to a csv file in the Portfolios directory.

............................................................
create portfolio with 3 different stocks using Files.
............................................................

    We allow user to create their own Portfolio CSV files and feed it to our system.
    In that case the csv file should look like this :-
    <Ticker_Symbol,Price,Quantity,Date,Commision_Fee>
    eg: NVDA,134.970001,12.0,2022-11-01,2.2
    Negative Quantity signifies Sell transactions.
   - All the query functionality works as it it.
   - Program validates the file user manually creates and in case the file does not follow the constraints of program , it tells user to recreate it or use another portfolio.

............................................................
How to query portfolios:-
............................................................

          1. In the flex portfolio main menu press 2 to view portfolios
          2. List of portfolios is displayed
          3. Press 1 to get details of the portfolio eg tech
          4. Enter the valid name from the display else you get the validation prompt with option
             go back to previous menu by pressing 0.
          5. Press 1 to get summary
          6. Press 2 to get Current of the portfolio.
          7. Press 3 to get Historic value of the portfolio.
          8. Press 4 To add stocks
          9. Press 5 to sell stocks
          10. Press 6 to get cost basis on a date.
          11. Press 7 to print performance graph between 2 valid date.
-- Summary
     - Enter a valid Date for summary
     -  Stock composition on that date is displayed
-- Current Value
     - Portfolio Value on that date is displayed
-- Historic Value
    - Enter a valid Date
    - Portfolio Value on that date is displayed
-- Add Stocks
        similar to adding stocks in the begining
-- Sell Stocks
        - Displays the current state of the portfolio
        - select  the ticker
        - Enter transaction date (make sure you refer the last transaction date , no sell/buy is allowed before last transaction date) , commision fee
 -- Cost Basis
        - Enter a valid date
        - Cost basis is displayed on that date
 -- Print Performance Graphs
        - Enter Valid start date
        - Enter Valid end date
        - Graph gets generated as per the appropriate scale

===============
How to use GUI
===============

- In the command line Select 2 to open the GUI

----------------
create portfolio
----------------
Select create new portfolio button on top left pane or the main menu.
select create normal portfolio button
Enter the requried details , put stock details , click add more stocks
The previously added stocks will be displayed in the blue box.
Hit submit to create the portfolio.

------------------------------------
create portfolio with a DCA strategy
------------------------------------
Select create new portfolio button on top left pane or the main menu.
select create normal portfolio button
Enter the requried details , endDate is optional if you do not add it then the DCA strategy is ongoing.
Add stock vs weight for each stock and make sure your weights add upto 100.
Hit submit to create the portfolio using this DCA strategy.

-----------------
List portfolios
-----------------
Click the list portfolio button on the left pane.
Select the portfolio you want to work with from the menu.
Select the desired feature from the menu.

------------
Get value
------------
asks you the date and fetches the value on that date for this portfolio.

----------------
Get cost basis
----------------
asks you the date and fetches the value on that date for this portfolio.

----------------
Get Composition
----------------
asks you the date and fetches the composition on that date for this portfolio.

-----------------
Purchase stocks
-----------------
asks stock name , qty , date , commision fee
hit purchase stocks to add it to the portfolio.

-------------
Sell stocks
-------------
asks you the date and fetches the composition on that date for this portfolio.
now add the stock name , qty , commision fee and hit sell stocks to make the transaction.

--------------------
Invest in portfolio
--------------------
asks you date of investment , amount to be invested , commision fee and stock compostion that you want based on weights.
Hit invest to make the transaction.
Hit invest to create the DCA plan on existing portfolio.

------------------
Performance Graph
------------------
asks you start and end date for plot and plots the graph (Note: start date should be after portfolio creation date)
Invest in portfolio through DCA plan on existing portfolio
Enter the requried details , endDate is optional if you do not add it then the DCA strategy is ongoing.
Add stock vs weight for each stock and make sure your weights add upto 100.
Hit invest to create the DCA plan on existing portfolio.


==========================================
Program Limitations
==========================================

- Our application only supports data from 1st January 2010 - till current date

- Our application only supports 100 ticker symbols from NASDAQ 100 as specified in the stock_list.csv file namely :-

      AAPL
      ABNB
      ADBE
      ADI
      ADP
      ADSK
      AEP
      ALGN
      AMAT
      AMD
      AMGN
      AMZN
      ANSS
      ASML
      ATVI
      AVGO
      AZN
      BIDU
      BIIB
      BKNG
      CDNS
      CEG
      CHTR
      CMCSA
      COST
      CPRT
      CRWD
      CSCO
      CSX
      CTAS
      CTSH
      DDOG
      DLTR
      DOCU
      DXCM
      EA
      EBAY
      EXC
      FAST
      FISV
      FTNT
      GILD
      GOOG
      GOOGL
      HON
      IDXX
      ILMN
      INTC
      INTU
      ISRG
      JD
      KDP
      KHC
      KLAC
      LCID
      LRCX
      LULU
      MAR
      MCHP
      MDLZ
      MELI
      META
      MNST
      MRNA
      MRVL
      MSFT
      MTCH
      MU
      NFLX
      NTES
      NVDA
      NXPI
      ODFL
      OKTA
      ORLY
      PANW
      PAYX
      PCAR
      PDD
      PEP
      PYPL
      QCOM
      REGN
      ROST
      SBUX
      SGEN
      SIRI
      SNPS
      SPLK
      SWKS
      TEAM
      TMUS
      TSLA
      TXN
      VRSK
      VRSN
      VRTX
      WBA
      WDAY
      XEL
      ZM
      ZS
- Our application is dependent on alpha vantage historic api to fetch historic data.

- If the user enters the data on which the stock exchange is closed the buyPrice becomes the last traded price
and buyDate becomes the last traded date

- User created .csv files should be created in portfolios directory , if it does not exist create it.

- User creted file should comply to this given types :-

- Inflex Portfolios
<string , double , double , valid_date>
<ticker , price , qty , transaction_date>

- Flex Portfolios
<string , double , double , valid_date , double>
<ticker , price , qty , transaction_date , commision_fee>
Incase of flex portfolis negative qty means sell transaction and positive means buy transactions.
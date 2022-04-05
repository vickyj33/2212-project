# CS2212 CryptTrader Project
Program that allows users to login use a program that trades crypto currenceies.
This includes creating trading brokers, assigning coins of interest, and trading strategies.
Prices for coins are fetched from CoinGecko API and brokers use strategies to perform trades.
Information regarding the trades are output in visuals on the user interface.

##Running the program
- Import project as a Java Maven Project
- Specify Java compiler to be 1.7
- Main program is under cryptoTrader.mainApp > MainApp.java

##logging in
Information regarding users is found in the users.txt file <br />
Guest user can use username: guest password: guest1

##Notes
Each trading broker can only perform one trade at a time. This means each row in the table must have a unique broker name. This aligns with the project description where it says if the broker name already exists, a message is displayed and the broker is not added.
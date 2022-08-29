#CRYPTO WALLET PERFORMANCE

##Objective
Your assignment is to implement a Java program that given a collection of crypto assets
with their positions, it must retrieve, concurrently, their latest prices from the Coincap
API and return the updated total financial value of the wallet with performance data.

###Input
CSV file representing the wallet with columns symbol, quantity, price
###Output
Print a line with
total={},best_asset={},best_performance={},worst_asset={},worst_performance=
{}

##Execute Project

###Required environment, tools and languages:
- Java 11+
- maven 3.6.3

###Build Project
mvn clean install

###Tests
mvn test

###Run Project
mvn spring-boot:run

###Configure Assets
The cryptoAssets.csv file could be change for more tests.

####For Production
src/main/resources/cryptoAssets.csv
symbol,quantity,price
BTC,0.12345,37870.5058
ETH,4.89532,2004.9774

####For Tests
src/test/resources/cryptoAssets.csv

symbol,quantity,price
BTC,0.12345,37870.5058
ETH,4.89532,2004.9774
XEM,0.12345,37870.5058
NEAR,0.12345,37870.5058
STX,0.12345,37870.5058
BSV,0.12345,37870.5058
BCH,0.12345,37870.5058
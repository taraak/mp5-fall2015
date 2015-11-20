CPEN 221 / Fall 2015 / Machine Problem 5

Restaurants, Queries and Statistical Learning
===

This machine problem is designed to allow you to explore multiple aspects of software construction:
+ managing complex ADTs
+ query parsing and execution
+ multithreading and the client-server pattern

In addition to these aspects, the problem also touches upon rudimentary methods for statistical inference and learning.

### Background

For this machine problem, you will work with an excerpt from the Yelp Academic Dataset. Specifically, you will work with data (in [JSON](https://en.wikipedia.org/wiki/JSON) format) on restaurants, and this data includes information about some restaurants, reviews of the restaurants, and user information (for those contributing reviews).

You will use the dataset to create and maintain a simple in-memory database with restaurants, users and reviews.

### Part I: In-Memory Database Server and Queries

Create an abstract datatype `RestaurantDB` that represents the data from the given dataset. An important operation that we would like to perform on such a datatype is a search that retrieves a set of restaurants given a query.

A query can be based on a combination of restaurant names, neighbourhood, categories, rating and price level. 

For example:
```
in("Telegraph Ave") && (category("Chinese") || category("Italian")) && price(1..2)
```
represents the query that should return a list of all Chinese or Italian restaurants in the Telegraph Avenue neighbourhood that are in the price range [1,2].

#### Grammar

The grammar for the query language is:
```
<orExpr> ::= <andExpr>(<or><andExpr>)*
<andExpr> ::= <atom>(<and><atom>)*
<atom> ::= <in>|<category>|<rating>|<price>|<name>|<LParen><orExpr><RParen>
<or> ::= "||"
<and> ::= "&&"
<in> ::= "in" <LParen><string><RParen>
<category> ::= "category" <LParen><string><RParen>
<name> ::= "name" <LParen><string><RParen>
<rating> ::= "by" <LParen><range><RParen>
<price> ::= "by" <LParen><range><RParen>
<range> ::= <LParen>[1-5]..[1-5]<RParen>
<LParen> ::= "("
<RParen> ::= ")"
```
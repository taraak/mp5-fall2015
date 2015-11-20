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

Implement a **multithreaded server** that can be launched from the command line using four arguments in this order:
+ a port number;
+ the name of a file that contains the restaurant details in JSON format;
+ the name of a file that contains user reviews in JSON format;
+ and the name of a file with user details in JSON format.

This server should be capable of accepting queries from many clients and executing those queries concurrently. The clients communicate with the server by sending a query (a `String`) and they receive, in JSON format, a list of restaurants that satisfies the query.

The server should also be capable of responding to two other types of client requests: 
1. `randomReview("Restaurant Name")`. To this request, the server should respond by providing a random review (in JSON format) for the restaurant that matches the provided name. If more than one restaurant matches the name then any restaurant that satisfies the match can be selected.
2. `getRestaurant("businessId")`. To this request, the server should respond with the restaurant details in JSON format for the restaurant that has the provided business identifier.

For both these requests, a suitable JSON formatted string should be returned if there is no valid response.

The server should be implemented using a class named `RestaurantDBServer`.

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
<rating> ::= "rating" <LParen><range><RParen>
<price> ::= "price" <LParen><range><RParen>
<range> ::= <LParen>[1-5]..[1-5]<RParen>
<LParen> ::= "("
<RParen> ::= ")"
```

> One of the limitations of this grammar is that if we want restaurants at the price level 2 then the query will look like `price(2..2)` and this can be improved upon but we will leave this as it is for this machine problem.

### Part II: Statistical Learning

> Look at the skeleton code in the package for `statlearning`.

In this part of the machine problem you will implement two approaches to statistical machine learning: one is an instance of unsupervised learning and the second is an instance of supervised learning. Statistical learning is an exciting area for computing today!

#### k-means Clustering

Suppose you are given a set of (x, y) coordinates, you may sometimes want to group the points into _k_ clusters such that no point is closer to a point in a cluster other than the one to which it is assigned. In the case of restaurants, this approach may allow us to group restaurants that are in the same neighbourhood even without knowing anything about the neighbourhoods in a city. _A similar approach is used to group similar products on online shopping services such as Amazon._

The k-means algorithm finds k centroids within a dataset that each correspond to a cluster of inputs. To do so, k-means clustering begins by choosing k centroids at random, then alternates between the following two steps:

1. Group the restaurants into clusters, where each cluster contains all restaurants that are closest to the same centroid.
2. Compute a new centroid (average position) for each non-empty cluster.

This [visualization](http://tech.nitoyon.com/en/blog/2013/11/07/k-means/) is a good way to understand how the algorithm works.

For the k-means clustering algorithm, you should implement a method that returns a `List` of `Set`s: each `Set` representing a cluster of restaurants. You should also implement a method that converts such a `List` to JSON format as illustrated by the JSON file `voronoi.json` in the directory `visualize`.

You can run the provided visualization method using `python` (Python 3) and the visualization is called a [Voronoi tesselation](https://en.wikipedia.org/wiki/Voronoi_diagram).

> Instructions on how to launch the visualization

#### Least Squares Regression

As an instance of supervised learning, you will implement an algorithm for predicting the rating that a user may give to a restaurant.

By analyzing a user's past ratings, we can try to predict what rating the user might give to a new restaurant. 

To predict ratings, you will implement simple least-squares linear regression, a widely used statistical method that approximates a relationship between some input feature (such as price) and an output value (the rating) with a line. The algorithm takes a sequence of input-output pairs and computes the slope and intercept of the line that minimizes the mean of the squared difference between the line and the outputs.

Implement the `getPredictor` method, which takes a user and a feature function (as well as the `RestaurantDB`), and returns a _function_ that predicts the users ratings as well as a regression quality estimate (`r_squared`).

One method of computing these values is by calculating the sums of squares, S<sub>xx</sub>, S<sub>yy</sub>, and S<sub>xy</sub>:

+ S<sub>xx</sub> = Σ<sub>i</sub> (x<sub>i</sub> - mean(x))<sup>2</sup>
+ S<sub>yy</sub> = Σ<sub>i</sub> (y<sub>i</sub> - mean(y))<sup>2</sup>
+ S<sub>xy</sub> = Σ<sub>i</sub> (x<sub>i</sub> - mean(x))(y<sub>i</sub> - mean(y))

After calculating the sums of squares, the regression coefficients and R<sup>2</sup> (`r_squared`) are defined as follows:

+ b = S<sub>xy</sub> / S<sub>xx</sub>
+ a = mean(y) - b * mean(x)
+ R<sup>2</sup> = S<sub>xy</sub><sup>2</sup> / (S<sub>xx</sub> S<sub>yy</sub>)

Also implement the `getBestPredictor` method that takes a user and a list of feature functions and returns the _best_ feature function (the one that results in the highest R<sup>2</sup> value). 

In this machine problem, we will use interfaces to pass and return functions but we could have also considered using [lambdas that Java 8 supports](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html).
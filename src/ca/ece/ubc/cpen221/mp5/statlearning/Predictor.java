package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public class Predictor implements MP5Function {
    Double a;
    Double b;
    Double r_squared;
    MP5Function FF;
    
    //represents a line y= ax+b
    public Predictor(Double a, Double b, Double r_squared, MP5Function FF){
        this.a = a;
        this.b = b;
        this.r_squared = r_squared;
        this.FF = FF;
    }

    @Override
    public double f(Restaurant yelpRestaurant, RestaurantDB db) {
        Double feature = FF.f(yelpRestaurant, db);
        Double ratingGuess = a*feature + b;
        if (ratingGuess < 0.0 ) ratingGuess = 0.0;
        if (ratingGuess > 5.0) ratingGuess = 5.0;
        return ratingGuess;
    }
     public Double getA(){
         return a;
     }
     
     public Double getB(){
         return b;
     }
     
     public Double getR(){
         return r_squared;
     }
     
     public MP5Function getFF(){
         return FF;
     }

}

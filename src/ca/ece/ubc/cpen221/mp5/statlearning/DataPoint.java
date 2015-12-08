package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;

public class DataPoint {
    Double rating;
    Double feature;
    
    public DataPoint (Double rating, Double feature){
        this.rating = rating;
        this.feature = feature;
    }
    
    public Double getFeature(){
        return this.feature;
    }
    
    public Double getRating(){
        return this.rating;
    }
    
    //Y = rating
    public static Double sumYY(Set<DataPoint> points){
        Double sum = 0.0;
        DataPoint avgPoint = avgPointVal(points);
        for(DataPoint point : points){
            sum += Math.pow((point.getRating()-avgPoint.getRating()), 2);
        }
        return sum;
    }
    
    //X = feature
    public static Double sumXX(Set<DataPoint> points){
        Double sum = 0.0;
        DataPoint avgPoint = avgPointVal(points);
        for(DataPoint point : points){
            sum += Math.pow((point.getFeature()-avgPoint.getFeature()), 2);
        }
        return sum;
    }
    
    public static Double sumXY(Set<DataPoint> points){
        Double sum = 0.0;
        DataPoint avgPoint = avgPointVal(points);
        for(DataPoint point : points){
            sum += (point.getRating()-avgPoint.getRating())*(point.getFeature()-avgPoint.getFeature());
        }
        return sum;
    }
    
    public static DataPoint avgPointVal(Set<DataPoint> points){
        Double sumFeature = 0.0;
        Double sumRating = 0.0;
        
        for(DataPoint point : points){
            sumFeature += point.getFeature();
            sumRating += point.getRating();          
        }
        return new DataPoint((sumRating / points.size()),(sumFeature / points.size()));
    }

}

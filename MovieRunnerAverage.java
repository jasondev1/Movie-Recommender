
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerAverage {
    public void printAverageRatings() {
        SecondRatings myRatings = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        System.out.println("Number of movies is " + myRatings.getMovieSize());
        System.out.println("Number of raters is " + myRatings.getRaterSize());

        //myRatings.getAverageByID("1798709", 1);
        myRatings.getAverageRatings(3);
        System.out.println(myRatings.getTitle("01168646"));

        //--------------------------------

        //This method will print a list of movies and their average rating, if they have a specified
        //number of ratings. These will sort by lowest rating to highest rating 

        ArrayList<Rating> testRatingList = myRatings.getAverageRatings(3);

        int ratingListLength = testRatingList.size();

        Rating[] testRatingArray = new Rating[ratingListLength];

        //Loop over testRating and add each Rating to the testRatingArray

        for (Rating rs : testRatingList) {
            // process each item in turn 
            testRatingArray[testRatingList.indexOf(rs)] = rs;
        } 

        //Then loop over the array X amount of times (length) to sort the positions based on average
        int loopNumber = testRatingArray.length;
        for (int k = 0; k < loopNumber; k++) {
            for (int i = 1; i < testRatingArray.length; i++) {

                double currRating = testRatingArray[i].getValue();
                //System.out.println("currRating is " + currRating);
                double currRatingPrev = testRatingArray[i-1].getValue();
                //System.out.println("currRatingPrev is " + currRatingPrev);

                if (currRating < currRatingPrev) {
                    Rating currRatRating = testRatingArray[i];
                    Rating currRatRatingPrev = testRatingArray[i-1];

                    testRatingArray[i] = currRatRatingPrev;
                    testRatingArray[i-1] = currRatRating;

                }

                if (currRating > currRatingPrev) {
                    Rating currRatRating = testRatingArray[i];
                    Rating currRatRatingPrev = testRatingArray[i-1];

                    testRatingArray[i] = currRatRatingPrev;
                    testRatingArray[i-1] = currRatRating;
                    i--;
                }

            }
        }

        //Print the array to test 
        System.out.println("");
        for (int i = 0; i < testRatingArray.length; i++) {

            String currIDMovie = testRatingArray[i].getItem();
            String currTitles = myRatings.getTitle(currIDMovie);
            System.out.println(testRatingArray[i].toString() + " " + currTitles);

        }

    }

    public void getAverageRatingOneMovie() {
        //This method should print out the rating average for a specific movie 
        SecondRatings myRatings = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        String movieTitle = "The Godfather";
        String currID = myRatings.getID(movieTitle);
        double movieRating = myRatings.getAverageByID(currID,1);
        System.out.println("Movie average for " + movieTitle + " is " + movieRating);
    }

    
    
    
}

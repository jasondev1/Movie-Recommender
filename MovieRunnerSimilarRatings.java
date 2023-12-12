
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class MovieRunnerSimilarRatings {
    public Rating[] printAverageRatings(int minNum) {
        //Create a FourthRatings object and print the number of raters
        FourthRatings myRatings = new FourthRatings();
        RaterDatabase.initialize("data/ratedmoviesfull.csv");
        System.out.println("Number of raters is " + RaterDatabase.size());

        //Inititialize the movie database and print the size
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        System.out.println("Number of movies is " + MovieDatabase.size());

        //Call getAverageRatings with a minimal number of rater to return an Arraylist
        ArrayList<Rating> averageTestRatings = myRatings.getAverageRatings(minNum);

        //--------------------------------
        //This method will print a list of movies and their average rating, if they have a specified
        //number of ratings. These will sort by lowest rating to highest rating 

        ArrayList<Rating> testRatingList = myRatings.getAverageRatings(minNum);

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
        //Total number of movies rated 
        System.out.println("found " + testRatingArray.length + " movies");
        System.out.println("");
        for (int i = 0; i < testRatingArray.length; i++) {

            String currIDMovie = testRatingArray[i].getItem();
            String currTitles = MovieDatabase.getTitle(currIDMovie);
            System.out.println(testRatingArray[i].toString() + " " + currTitles);

        }
        System.out.println("");

        return testRatingArray;
    }

    
    public void printAverageRatingsByYearAfterAndGenre() {
        FourthRatings tr = new FourthRatings();
        AllFilters af = new AllFilters();
        af.addFilter(new YearAfterFilter(1995));
        af.addFilter(new GenreFilter("Crime"));
        ArrayList<Rating> testRatList = tr.getAverageRatingsByFilter(1, af);
        //printAverageRatings(1);
    }

    public void printAverageRatingsByYearAfterAndGenres() {
        printAverageRatings(1);
    }

    
    public void printSimilarRatings() {
        FourthRatings tr = new FourthRatings();
        RaterDatabase.initialize("ratings_short");
        MovieDatabase.initialize("ratedmovies_short");

        ArrayList<Rating> testRatingList = tr.getSimilarRatings("65", 20, 5); 
        for (Rating s : testRatingList) {
            // Print the rating  
            s.toString();
            System.out.println(MovieDatabase.getMovie(s.getItem()));
        }

    }

    
    public void printSimilarRatingsByGenre() {
        FourthRatings tr = new FourthRatings();
        RaterDatabase.initialize("ratings_short");
        MovieDatabase.initialize("ratedmovies_short");

        GenreFilter genreFilt = new GenreFilter("Action");
        ArrayList<Rating> testRatingList = tr.getSimilarRatingsByFilter("65", 20, 5, genreFilt); 
        for (Rating s : testRatingList) {
            // Print the rating  
            s.toString();
            System.out.println(MovieDatabase.getTitle(s.getItem()) + " - " + MovieDatabase.getGenres(s.getItem()));
        }

    }

    
    public void printSimilarRatingsByDirector() {
        FourthRatings tr = new FourthRatings();
        RaterDatabase.initialize("ratings_short");
        MovieDatabase.initialize("ratedmovies_short");

        DirectorsFilter ff = new DirectorsFilter("Clint Eastwood,Sydney Pollack,David Cronenberg,Oliver Stone");
        ff.setTraining("Clint Eastwood,Sydney Pollack,David Cronenberg,Oliver Stone");
        ArrayList<Rating> testRatingList = tr.getSimilarRatingsByFilter("1034", 10, 3, ff); 
        for (Rating s : testRatingList) {
            // Print the rating  
            s.toString();
            System.out.println(MovieDatabase.getTitle(s.getItem()) + " - " + MovieDatabase.getDirector(s.getItem()));
        }

    }

    
    public void printSimilarRatingsByGenreAndMinutes() {

        FourthRatings tr = new FourthRatings();
        RaterDatabase.initialize("ratings_short");
        MovieDatabase.initialize("ratedmovies_short");

        AllFilters af = new AllFilters();
        af.addFilter(new MinutesFilter(100,200));
        af.addFilter(new GenreFilter("Adventure"));
        ArrayList<Rating> testRatingList = tr.getSimilarRatingsByFilter("65", 10, 5, af); 
        for (Rating s : testRatingList) {
            // Print the rating  
            s.toString();
            System.out.println(MovieDatabase.getTitle(s.getItem()) + " - " + " minutes: " + MovieDatabase.getMinutes(s.getItem())
                + " similarity rating: " + s.getValue());
            System.out.println("Genres: " + MovieDatabase.getGenres(s.getItem()));
        }

    }

    
    public void printSimilarRatingsByYearAfterAndMinutes() {

        FourthRatings tr = new FourthRatings();
        RaterDatabase.initialize("ratings_short");
        MovieDatabase.initialize("ratedmovies_short");

        AllFilters af = new AllFilters();
        af.addFilter(new MinutesFilter(80,100));
        af.addFilter(new YearAfterFilter(2000));
        ArrayList<Rating> testRatingList = tr.getSimilarRatingsByFilter("65", 10, 5, af); 
        for (Rating s : testRatingList) {
            // Print the rating  
            s.toString();
            System.out.println(MovieDatabase.getTitle(s.getItem()) + " - " + " year: " + MovieDatabase.getYear(s.getItem())
                + " minutes: " + MovieDatabase.getMinutes(s.getItem())
                + " similarity rating: " + s.getValue());
        }

    }
    
    
}

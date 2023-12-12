
/**
 * Write a description of MovieRunnerWIthFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerWithFilters {
    public Rating[] printAverageRatings(int minNum) {
        //Create a ThirdRatings object and print the number of raters
        ThirdRatings myRatings = new ThirdRatings("ratings.csv");
        System.out.println("Number of raters is " + myRatings.getRaterSize());

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

        /*
        //Use a years filter to calculate the number of movies in the databse that have at least a min ratings and 
        //came out in a particular year 
        YearAfterFilter yearFilt = new YearAfterFilter(1972);
        ArrayList<String> listByYears = MovieDatabase.filterBy(yearFilt);
        System.out.println("\nFiltered by year: \n");
        //Loop over the rated list and only print the movies that came out a particular year 
        for (int i = 0; i < testRatingArray.length; i++) {

        String currIDMovie = testRatingArray[i].getItem();
        String currTitles = MovieDatabase.getTitle(currIDMovie);
        int currMovieYear = MovieDatabase.getYear(currIDMovie);
        if (listByYears.contains(currIDMovie)) {

        System.out.println(testRatingArray[i].toString() + " " + currMovieYear + " " + currTitles);

        }

        }
         */
        return testRatingArray;
    }

    public void printAverageRatingsByYear() {
        ThirdRatings tr = new ThirdRatings();
        YearAfterFilter yearFilt = new YearAfterFilter(2000);
        ArrayList<Rating> testRatList = tr.getAverageRatingsByFilter(1, yearFilt);

    }

    public void printAverageRatingsByGenre() {
        ThirdRatings tr = new ThirdRatings();
        GenreFilter genreFilt = new GenreFilter("Crime");
        ArrayList<Rating> testRatList = tr.getAverageRatingsByFilter(1, genreFilt);

    }

    public void printAverageRatingsByMinutes() {
        ThirdRatings tr = new ThirdRatings();
        MinutesFilter minFilt = new MinutesFilter(110,170);
        ArrayList<Rating> testRatList = tr.getAverageRatingsByFilter(1, minFilt);

    }

    public void printAverageRatingsByDirectors() {
        ThirdRatings tr = new ThirdRatings();
        DirectorsFilter ff = new DirectorsFilter("Charles Chaplin,Michael Mann,Spike Jonze");
        ff.setTraining("Charles Chaplin,Michael Mann,Spike Jonze");
        ArrayList<Rating> testRatList = tr.getAverageRatingsByFilter(1, ff);

    }

    public void printAverageRatingsByYearAfterAndGenre() {
        ThirdRatings tr = new ThirdRatings();
        AllFilters af = new AllFilters();
        af.addFilter(new YearAfterFilter(1995));
        af.addFilter(new GenreFilter("Crime"));
        ArrayList<Rating> testRatList = tr.getAverageRatingsByFilter(1, af);
    }

    public void printAverageRatingsByDirectorsAndMinutes() {
        ThirdRatings tr = new ThirdRatings();
        AllFilters af = new AllFilters();
        af.addFilter(new MinutesFilter(30,170));

        DirectorsFilter ff = new DirectorsFilter("Spike Jonze,Michael Mann,Charles Chaplin,Francis Ford Coppola");
        ff.setTraining("Spike Jonze,Michael Mann,Charles Chaplin,Francis Ford Coppola");
        af.addFilter(ff);

        ArrayList<Rating> testRatList = tr.getAverageRatingsByFilter(1, af);

    }
    
    
    
}

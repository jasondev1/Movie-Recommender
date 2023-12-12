
/**
 * Write a description of ThirdRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class ThirdRatings {
    //private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    public ThirdRatings(String ratingsfile) {
        FirstRatings myRatings = new FirstRatings();
        myRaters = myRatings.loadRater(ratingsfile);
    }

    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }

    public int getRaterSize() {
        return myRaters.size();
    }

    public double getAverageByID(String id, int minimalRaters) {
        //This method returns a double representing the average movie rating for this ID, 
        //if there are at least minimalRaters for this ID

        double averageForId = 0.0;
        //Loop over the raters, and collect all the ratings for the target in an ArrayList

        ArrayList<Double> ratingList = new ArrayList<Double>();

        for (Rater rr : myRaters) {
            // process each item in turn 
            //System.out.println(rr.getID());
            //System.out.println(rr.getItemsRated());

            double currRating = rr.getRating(id);

            if (currRating != -1) {
                ratingList.add(currRating);
                //System.out.println("new raing added");

            }
        } 

        //Call size on the ratingList ArrayList and compare it to the minimalRaters number
        int listSize = ratingList.size();
        //System.out.println("listSize is " + listSize);
        //If the size is smaller, then return 0.0
        if (listSize < minimalRaters) {
            //System.out.println("we broke ");
            return 0.0;
        }

        //If the size is equal or larger, loop through the new ArrayList of ratings and add 
        //them to a variable, then divide the variable by number of ratings

        double addedTogether = 0.0;
        if (listSize >= minimalRaters) {

            for (Double d : ratingList) {
                addedTogether = addedTogether + d;

            } 

            averageForId = addedTogether/ratingList.size();

        }

        //return the result 
        //System.out.println("averageForId is " + averageForId);
        return averageForId;

    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        //This method should find the average rating for every movie that has been rated by at least 
        //minimalRaters raters. 

        ArrayList<Rating> averageRatList = new ArrayList<Rating>();

        //Store all move IDs in an ArrayList from the MovieDatabase
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());

        //Loop over the movies list and compile a list of all the movie IDs with their ratings    
        for (String sd : movies) {

            //Call getAverageByID(String id, int minimalRaters) to get the average for each ID
            double currMovieAverage = getAverageByID(sd,minimalRaters);

            //If it returns a number besides 0.0, create a new rating object using this number 
            if (currMovieAverage != 0.0) {
                //Create new Rating and add the rating object to the averageRatList ArrayList 
                Rating currRating = new Rating (sd, currMovieAverage); 
                averageRatList.add(currRating);

            }

        }

        //Return the list 
        /*
        for (Rating r : averageRatList) {
        // process each item in turn 
        System.out.println(r.toString());
        }

         */
        return averageRatList;

    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        //This method will return an ArrayList of Ratings of all moves that fit the passed criteria

        //This Arraylist will store the filtered Ratings 
        ArrayList<Rating> filteredRatings = new ArrayList<Rating>();

        //This ArrayList will store the IDs that are returned by the filter 
        ArrayList<String> listByYears = MovieDatabase.filterBy(filterCriteria);

        //This Array will store the rated list by minumum raters 
        MovieRunnerWithFilters mf = new MovieRunnerWithFilters();
        Rating[] currRatings = mf.printAverageRatings(1);

        //Loop over the rated list Array and only add the movies that came out a particular year to the filteredRatings

        for (int i = 0; i < currRatings.length; i++) {

            String currIDMovie = currRatings[i].getItem();
            String currTitles = MovieDatabase.getTitle(currIDMovie);
            int currMovieYear = MovieDatabase.getYear(currIDMovie);
            if (listByYears.contains(currIDMovie)) {

                System.out.println(currRatings[i].toString() + " " + currMovieYear + " " + currTitles);
                System.out.println("Genres: " + MovieDatabase.getGenres(currIDMovie));
                System.out.println("Time: " + MovieDatabase.getMinutes(currIDMovie));
                System.out.println("Director(s): " + MovieDatabase.getDirector(currIDMovie) + "\n");
                filteredRatings.add(currRatings[i]);
            }

        }
        System.out.println("\nfound " + filteredRatings.size() + " movies filtered");
        return filteredRatings;

    }

    
}

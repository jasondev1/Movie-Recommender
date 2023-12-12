
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings myRatings = new FirstRatings();
        myMovies = myRatings.loadMovies(moviefile);
        myRaters = myRatings.loadRater(ratingsfile);
    }

    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public int getMovieSize() {
        return myMovies.size();
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
        System.out.println("averageForId is " + averageForId);
        return averageForId;

    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        //This method should find the average rating for every movie that has been rated by at least 
        //minimalRaters raters. 

        ArrayList<Rating> averageRatList = new ArrayList<Rating>();

        //Loop over the movie list and compile a list of all the movie IDs

        for (Movie ss : myMovies) {
            // process each item in turn 
            String currID = ss.getID();

            //Call getAverageByID(String id, int minimalRaters) to get the average for each ID

            double currMovieAverage = getAverageByID(currID,minimalRaters);

            //If it returns a number besides 0.0, create a new rating object using this number 

            if (currMovieAverage != 0.0) {
                //Create new Rating and add the rating object to the averageRatList ArrayList 
                Rating currRating = new Rating (currID, currMovieAverage); 
                averageRatList.add(currRating);

            }

        } 
        //Return the list 
        for (Movie s : myMovies) {
            // process each item in turn 
            System.out.println(s);
        }

        for (Rating r : averageRatList) {
            // process each item in turn 
            System.out.println(r.toString());
        }

        return averageRatList;

    }

    
    public String getTitle(String id) {
        //Returns the movie title when passed a movie ID
        String currTitle = "";
        String currID = "";
        for (Movie sm : myMovies) {
            //Get the current title 
            currTitle = sm.getTitle();
            currID = sm.getID();
            if (currID.equals(id)) {
                return currTitle;
            }
        }

        return "Title not found";

    }

    public String getID(String title) {
        //Returns an ID when passed a movie title 
        String currTitles = "";
        String currIDs = "";
        for (Movie sh : myMovies) {
            //Get the current title 
            currTitles = sh.getTitle();
            currIDs = sh.getID();
            if (currTitles.equals(title)) {
                return currIDs;
            }
        }

        return "Title not found";

    }

    
    
    
}

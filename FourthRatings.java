
/**
 * Write a description of FourthRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FourthRatings {
    public double getAverageByID(String id, int minimalRaters) {
        //This method returns a double representing the average movie rating for this ID, 
        //if there are at least minimalRaters for this ID

        double averageForId = 0.0;
        ArrayList<Double> ratingList = new ArrayList<Double>();

        //Get the size of the database
        ArrayList<Rater> raterList = RaterDatabase.getRaters();

        //Loop over the raters in the database and collect all the ratings for the target in an ArrayList
        for (int i = 0; i < raterList.size(); i++) {

            double currRating = raterList.get(i).getRating(id);

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

    private double dotProduct(Rater me, Rater r) {
        //This method should convert a rating scale to -5 to 5 and return the dot product of ratings they both rated 
        double dotProd = 0.0;
        //Call ArrayList<String> getItemsRated() to get a list of the movie IDs for the Rater me
        ArrayList<String> myRaterListIDs = me.getItemsRated();

        //Loop over the myRaterListIDs list and call getRating() on me and r ratings

        ArrayList<Double> myRatingNumbers = new ArrayList<Double>();
        ArrayList<Double> rRatingNumbers = new ArrayList<Double>();

        for (int i = 0; i < myRaterListIDs.size(); i++) {
            //Call getRating(String item) to get my current rating
            double currMyRating = me.getRating(myRaterListIDs.get(i));
            //Call getRating(String item) to get r current rating
            double currRRating = r.getRating(myRaterListIDs.get(i));

            //If both have values, subtract 5 from each value, multiple them, and add them to dotProd
            if(currMyRating != -1 && currRRating != -1) {

                currMyRating = currMyRating - 5;
                currRRating = currRRating - 5;

                double multipliedNumbers = currMyRating * currRRating;

                dotProd = dotProd + multipliedNumbers;

            }

        }

        return dotProd;

    }

    private ArrayList<Rating> getSimilarities(String id) {
        //This method computers a similarity rating for each rater in the RaterDatabase (except the rater with the ID
        //given by the parameter) to see how similar they are to the Rater whos ID if the parameter

        ArrayList<Rating> list = new ArrayList<Rating>();

        Rater me = RaterDatabase.getRater(id);

        for(Rater r : RaterDatabase.getRaters()) {
            //Add dot_product(r,me) to list if r != me
            //Calcualte the dot product of rater me and rater r
            double currDotProd = dotProduct(r,me);

            if (currDotProd >= 0 && r != me) {
                list.add(new Rating(r.getID(), currDotProd));
            }

        }
        Collections.sort(list, Collections.reverseOrder());
        return list;

    }

    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        //This method should return an ArrayList of type Rating, of movies and their weighted average ratings using only
        //the top numSimilarRaters with positive ratings and including only those movies that have at least 
        //minimalRaters ratings from those most similar raters. String id represents the rater to compare to. 

        ArrayList<Rating> list = new ArrayList<Rating>();

        //Create a rater object for the id pasted in the parameter
        Rater me = RaterDatabase.getRater(id);

        //Create a list of similar raters to me 
        ArrayList<Rating> mySimRaterList = getSimilarities(id);

        //Loop over the movies in the moviedatabase 
        //Store all move IDs in an ArrayList from the MovieDatabase
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());

        //Loop over the movies list and compile a list of all the movie IDs with their ratings    
        for (String sd : movies) {

            double currMovieWeight = 0.0;
            double currMovieFinalWeight = 0.0;
            int numberOfRatersRated = 0;

            //Loop over the list of close raters and see if they rated this movie 
            for (int i = 0; i < numSimilarRaters; i++) {
                //Get the current Rater from the mySimRaterList
                Rater currRater = RaterDatabase.getRater(mySimRaterList.get(i).getItem());

                //Get the rating from this rater for the current movie
                double raterRating = currRater.getRating(sd);

                //If there is a rating, then mutliple the rating by the closeness weight and add to MovieWeight

                if (raterRating != -1) {

                    //Get the weight
                    double currRaterWeight = mySimRaterList.get(i).getValue();

                    //Multiple weight times rating 
                    double multipledNumber = currRaterWeight * raterRating;

                    //Add the multipled number to the MovieWeight
                    currMovieWeight = currMovieWeight + multipledNumber;
                    //System.out.println("rater rating is " + raterRating);
                    //System.out.println("currMovieWeight is " + currMovieWeight);
                    
                    //Add one to numberOfRatersRated
                    numberOfRatersRated = numberOfRatersRated + 1;

                }

            }

            //Take the currMovieWeight and divide it by number of raters to get the final weight 
            currMovieFinalWeight = currMovieWeight / numberOfRatersRated;
            //System.out.println("final movieweight is " + currMovieFinalWeight);

            //System.out.println("numberOfRatersRated is " + numberOfRatersRated + " and minimalsRaters is " + minimalRaters);
            //Add the movie and final weight to the ArrayList if minimalsRaters is satisfied 
            if (numberOfRatersRated >= minimalRaters) {
                list.add(new Rating(sd,currMovieFinalWeight));
            }
        }

        Collections.sort(list, Collections.reverseOrder());
        //System.out.println(list);
        return list;

    }

    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {

        //This method should return an ArrayList of type Rating, of movies and their weighted average ratings using only
        //the top numSimilarRaters with positive ratings and including only those movies that have at least 
        //minimalRaters ratings from those most similar raters. This method will only access and rate movies
        //that match the filter criteria. String id represents the rater to compare to. 

        ArrayList<Rating> list = new ArrayList<Rating>();

        //Create a rater object for the id pasted in the parameter
        Rater me = RaterDatabase.getRater(id);

        //Create a list of similar raters to me 
        ArrayList<Rating> mySimRaterList = getSimilarities(id);

        //Loop over the movies in the moviedatabase 
        //Store all move IDs in an ArrayList from the MovieDatabase
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);

        //Loop over the movies list and compile a list of all the movie IDs with their ratings    
        for (String sd : movies) {

            double currMovieWeight = 0.0;
            double currMovieFinalWeight = 0.0;
            int numberOfRatersRated = 0;

            //Loop over the list of close raters and see if they rated this movie 
            for (int i = 0; i < numSimilarRaters; i++) {
                //Get the current Rater from the mySimRaterList
                Rater currRater = RaterDatabase.getRater(mySimRaterList.get(i).getItem());

                //Get the rating from this rater for the current movie
                double raterRating = currRater.getRating(sd);

                //If there is a rating, then mutliple the rating by the closeness weight and add to MovieWeight

                if (raterRating != -1) {

                    //Get the weight
                    double currRaterWeight = mySimRaterList.get(i).getValue();

                    //Multiple weight times rating 
                    double multipledNumber = currRaterWeight * raterRating;

                    //Add the multipled number to the MovieWeight
                    currMovieWeight = currMovieWeight + multipledNumber;

                    //Add one to numberOfRatersRated
                    numberOfRatersRated = numberOfRatersRated + 1;

                }

            }

            //Take the currMovieWeight and divide it by number of raters to get the final weight 
            currMovieFinalWeight = currMovieWeight / numberOfRatersRated;

            //Add the movie and final weight to the ArrayList if minimalsRaters is satisfied 
            if (numberOfRatersRated >= minimalRaters) {
                list.add(new Rating(sd,currMovieFinalWeight));
            }
        }

        Collections.sort(list, Collections.reverseOrder());
        return list;

    }
    
    
    
    
    
    
}

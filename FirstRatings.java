
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class FirstRatings {

    public ArrayList<Movie> loadMovies (String filename) {
        //This method should process every record from the CSV file whose name is filename, a file of movie information, 
        //and return an ArrayList of type Movie with all of the movie data from the file.

        ArrayList<Movie> movieList = new ArrayList<Movie>();
        FileResource fr = new FileResource();
        CSVParser csvp = fr.getCSVParser();

        for(CSVRecord rec : csvp) {

            //Loop over each line and put all the data into a variable
            String id = rec.get("id");
            String title = rec.get("title");
            String year = rec.get("year");
            String genres = rec.get("genre");
            String director = rec.get("director");
            String country = rec.get("country");
            String poster = rec.get("poster");
            String minutesS = rec.get("minutes");
            int minutes = Integer.parseInt(minutesS.trim());

            //Put all the varibales into a Movie object 
            Movie currMovie = new Movie (id, title, year, genres, director,country, poster, minutes);

            //Add the current Movie to the ArrayList movieList
            movieList.add(currMovie);

        } 

        //Return the complete movieList    
        return movieList;

    }

    public void testLoadMovies () {

        ArrayList movieList = loadMovies ("ratedmovies_short.csv");

        //Loop over the movieList and print each one
        for (int i = 0; i < movieList.size(); i++) {
            // print the current movie 
            System.out.println(movieList.get(i));
        }    
        //Print the number of movies 
        System.out.println("total amount of movies is " + movieList.size());

        //--------------------------------

        //Determine how many movies include the Comedy genre
        int totalComedy = 0;

        //Loop over the movieList and determine how many movies include the Comedy genre
        for (int i = 0; i < movieList.size(); i++) {
            // get the genre
            Movie currMovie = (Movie)movieList.get(i);
            String currGenres = currMovie.getGenres();
            int currIndex = currGenres.indexOf("Comedy");

            //See if the current Movie contains the genre 
            if (currIndex != -1) {

                totalComedy = totalComedy + 1;

            }
        }  

        System.out.println("total with Comedy genre are " + totalComedy);

        //--------------------------------

        //Determine how many movies are greater than 150 minutes in length

        int totalAbove = 0;

        //Loop over the movieList and determine how many movies include the Comedy genre
        for (int i = 0; i < movieList.size(); i++) {
            // get the genre
            Movie currMovie = (Movie)movieList.get(i);
            int currMinutes = currMovie.getMinutes();

            //See if the current Movie contains the genre 
            if (currMinutes > 150) {

                totalAbove = totalAbove + 1;

            }
        }  

        System.out.println("total Movies more than 150 minutes are " + totalAbove);

        //--------------------------------
        //Determine the maximum number of movies by any director, and who the directors are that directed that 
        //many movies.

        //Create a HashMap to store the director names (key) and number of occurances (value)
        HashMap<String,Integer> directorMap = new HashMap<String,Integer>();

        //Loop over the movieList and get a complete list of all directors, including duplicates 
        for (int i = 0; i < movieList.size(); i++) {
            // get the director names
            Movie currMovie = (Movie)movieList.get(i);
            String currDirectors = currMovie.getDirector();
            String[] currDirectorsList = currDirectors.split(", ", 0);

            //See if the current Director is in the HashMap, add if not, increment if so 
            for (int j = 0; j < currDirectorsList.length; j++) {

                //System.out.println("current director is " + currDirectorsList[j]);

                //If the director is in the HashMap, increment value by 1
                if (directorMap.containsKey(currDirectorsList[j])) {

                    directorMap.put(currDirectorsList[j],directorMap.get(currDirectorsList[j])+1);

                }

                //If the director is not in the HashMap, add it with a value of 1
                if (!directorMap.containsKey(currDirectorsList[j])) {

                    directorMap.put(currDirectorsList[j],1);

                }

            }  

        }

        //Loop over the HashMap and get the highest value 
        int highestValue = 0;
        for (String s : directorMap.keySet()) {
            // process each key in turn 
            System.out.println("Director: " + s + " Number of movies: " + directorMap.get(s));
            if (directorMap.get(s) > highestValue) {
                highestValue = directorMap.get(s);

            }

        } 

        System.out.println("Highest value is " + highestValue);

        //Loop over and print the director with the highest value 
        for (String s : directorMap.keySet()) {
            // process each key in turn 

            if (directorMap.get(s) == highestValue) {
                System.out.println("Director with the most movies is : " + s 
                    + " has made " + directorMap.get(s) + " movies.");
            }

        } 

    }

    public ArrayList<Rater> loadRater (String filename) {
        //This method should process every record from the CSV file whos name is filename, 
        //a file of raters and their ratings, and return an ArrayList of type Rater with 
        //all the rater data from the file. 

        ArrayList<Rater> raterList = new ArrayList<Rater>();
        System.out.println("please load ratings file");
        FileResource fr = new FileResource();
        CSVParser csvp = fr.getCSVParser();

        for(CSVRecord rec : csvp) {

            //Store the data from the CSV line into variables
            String raterID = rec.get("rater_id");
            String movieID = rec.get("movie_id");
            double ratingD = Double.parseDouble(rec.get("rating"));

            //Create a temp Rater using the CSV data
            Rater currRater = new EfficientRater (raterID);

            //Loop through raterList and get the ID of each rater and see if it matches this 
            //current one 
            boolean exists = false;
            int raterIndex = 0;
            for (Rater r : raterList) {
                // process each item in turn 
                String rList = r.getID();

                raterIndex = raterList.indexOf(r);
                if (rList.equals(raterID) ) {
                    exists = true;

                }

            } 

            //If the rater already exists 
            if (exists == true) {
                //only add the current rating to the exting rater
                raterList.get(raterIndex).addRating(movieID,ratingD);

            }

            //if the rater ID does not exist in the raterList 
            if (exists == false) {
                //add the new rater, along with the current rating to raterList
                Rater currRater1 = new EfficientRater (raterID);
                currRater1.addRating(movieID,ratingD);
                raterList.add(currRater1);
            }

        } 
   
        return raterList;

    }

    public void testLoadRaters () {

        //This method has several smaller functions to print out and test 

        ArrayList<Rater> testList = loadRater("ratings_short.csv");

        //Prints out the raterList for testing 

        for (Rater s : testList) {
            // process each item in turn 
            System.out.println(s.getID() + " " + s.getItemsRated());

        }

        //Prints out the total number of raters
        System.out.println("total number of raters is " + testList.size());

        //-------------------------
        //This method will find the number of ratings for a particular rater you specify
        //Declare the ID number of the rater you are searching for 
        int findRaterID = 2;

        //Generate the index of the rater in question
        int indexOfRater = findRaterID - 1;

        //Store the rater into a variable 
        Rater currRater = testList.get(indexOfRater);

        //Put the Rater's rating list into an ArrayList 
        ArrayList<String> raterList = currRater.getItemsRated();

        //Print the size of the list 
        System.out.println("The number of ratings for rater " + findRaterID + " is " 
            + raterList.size());

        //---------------------------------
        //This method will find the maximum number of rating by any rater. Return the 
        //rater ID along with how many ratings. Also include how many raters had the max.

        //Loop through the whole list and get the max size first
        int maxSize = 0;
        for (Rater r : testList) {
            // Get the current rater's list 
            ArrayList<String> currRaterList = r.getItemsRated();
            int currRaterListSize = currRaterList.size();

            //See if the rater's list is larger than maxSize. If so, set maxSize
            if (currRaterListSize > maxSize) {

                maxSize = currRaterListSize;

            }

        }

        //Loop through the whole list and save all raters that equal max size 
        int maxRaters = 0;
        ArrayList<Rater> raterListMax = new ArrayList<Rater>();

        for (Rater r : testList) {
            // Get the current rater's list 
            ArrayList<String> currRaterList = r.getItemsRated();
            int currRaterListSize = currRaterList.size();

            //See if the rater's list is larger than maxSize. If so, set maxSize
            if (currRaterListSize == maxSize) {

                maxRaters = maxRaters + 1;
                raterListMax.add(r);
            }

        }

        //Print out the total number of raters that equal the max and the rater ID(s)

        System.out.println("total number of raters with the maximum size ratings are " 
            + maxRaters);
        for (Rater r : raterListMax) {
            // Print out the current rater 
            System.out.println("rater with max rating of " + maxSize + " is " + "raterID " 
                + r.getID());

        }

        //--------------------------------
        //This method will find the number of ratings a particular movie has 
        int numOfMovieRaters = 0;
        String targetMovie = "0790636";

        //Loop over the rater list and see if the movie was rated, if so, 
        //increment numOfMovieRaters

        for (Rater g : testList) {
            // process each item in turn 
            ArrayList<String> currRaterLists = g.getItemsRated();

            //See if the targetMovie is in the rater's movie list
            //If so, increment the numOfMovieRaters

            if (currRaterLists.contains(targetMovie)) {
                numOfMovieRaters = numOfMovieRaters + 1;

            }

        }

        //Print the number of raters 
        System.out.println("total number of raters for movie " +  targetMovie + " is " +
            numOfMovieRaters + " raters");

        //-------------------------------------
        //This method will determine how many differnt movies have been rated by all raters
        //Loop through the  rater list and add each new movie to the movieList ArrayList
        ArrayList<String> movieListN = new ArrayList<String>();

        for (Rater g : testList) {
            // Store the current rater's rating list
            ArrayList<String> currRaterListM = g.getItemsRated();

            //Loop over ther rater's list and save the movie if its not in movieList
            for (int i = 0; i < currRaterListM.size(); i++) {

                String currMovie = currRaterListM.get(i);

                if (!movieListN.contains(currMovie)) {
                    movieListN.add(currMovie);
                }

            }

        }

        //Then call the size of the ArrayList and print it 
        int movieListSize = movieListN.size();
        System.out.println("Total number of movies rated by raters is " + movieListSize);

    }

}

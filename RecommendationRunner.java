
/**
 * Write a description of RecommendationRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;


public class RecommendationRunner implements Recommender{
    @Override
    public ArrayList<String> getItemsToRate () {
        //This method returns a list of movie IDs that will be used to look up the movies in the MovieDatabase and present them 
        //to users to rate. The movies returned in the list will be displayed on a web page, so the number you choose may affect 
        //how long the page takes to load and how willing users are to rate the movies.  For example, 10-20 should be fine, 50 
        //or more would be too many. There are no restrictions on the method you use to generate this list of movies: the most 
        //recent movies, movies from a specific genre, randomly chosen movies, or simply your favorite movies. The ratings for 
        //these movies will make the profile for a new Rater that will be used to compare to for finding recommendations.

        //This Arraylist will store the list of movieID that will be used as recommandations and presented to the user
        ArrayList<String> itemsToRate = new ArrayList<String>();

        //Create a list of movies to be rated 
        ArrayList<String>movieList = MovieDatabase.filterBy(new TrueFilter());

        //Loop over the movie list and randomly present them with 10 movies 
        for (int i = 0; i < 10; i++) {
            //Generate the random number
            Random myRand = new Random();
            int myRandNum = myRand.nextInt(movieList.size());

            //Add the random movie ID if its not already in the itemsToRate list 
            if (!itemsToRate.contains(movieList.get(i))) {
                itemsToRate.add(movieList.get(i));

            }
        }

        return itemsToRate;

    }

    @Override
    public void printRecommendationsFor (String webRaterID) {
        //This method returns nothing, but prints out an HTML table of the movies recommended for the given rater.
        //The HTML printed will be displayed on a web page, so the number you choose to display may affect how long the page 
        //takes to load.  For example, you may want to limit the number printed to only the top 20-50 movies recommended or 
        //to movies not rater by the given rater.

        //You may also include CSS styling for your table using the &lt;style&gt; tag before you print the table.  There are no 
        //restrictions on which movies you print, what order you print them in, or what information you include about each movie. 

        //@param webRaterID the ID of a new Rater that has been already added to the RaterDatabase with ratings for the movies 
        //returned by the method getItemsToRate

        //Initialize the movieDatabase and raterDatabase
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");

        //Create the new rater object and get the rating list 
        FourthRatings webRaterF = new FourthRatings();
        ArrayList<Rating> ratingList = webRaterF.getSimilarRatings(webRaterID, 20, 5);

        //Loop over the 
        ArrayList<Rating> movieID = new ArrayList<>();
        if (ratingList.size() != 0) {
            ArrayList<String> moviesToRate = getItemsToRate();
            int count = 0;
            for (int i = 0; movieID.size() + count != ratingList.size() && movieID.size() < 10; i++) {
                if (!moviesToRate.contains(ratingList.get(i).getItem())) {
                    movieID.add(ratingList.get(i));

                } else {
                    count = count + 1;
                }
            }

        }
        else {
            System.out.println("<h2>There are no movies recommended based on your ratings!</h2>");

        }

        //Print out the top of the table 
        System.out.println("<h2>Movies to watch based off your ratings</h2>");
        System.out.println("<table id = \"rater\">");
        System.out.println("<tr>");
        System.out.println("<th>Rank</th>");
        System.out.println("<th>Poster</th>");
        System.out.println("<th>Title & Rating</th>");
        System.out.println("<th>Genre</th>");
        System.out.println("<th>Country</th>");
        System.out.println("</tr>");

        //Loop over the recommended movie list and display the results
        int rank = 1;
        for (Rating i : movieID) {
            System.out.println("<tr><td>" + rank + "</td>" +

                "<td><img src = \"" + MovieDatabase.getPoster(i.getItem()) + "\" width=\"60\" height=\"80\"></td> " +
                "<td>" + MovieDatabase.getYear(i.getItem()) + "&ensp;&ensp; <a href=\"https://www.imdb.com/title/tt" +
                i.getItem() + "\">" + MovieDatabase.getTitle(i.getItem()) + "</a><br><div class = \"rating\">&starf; &ensp;&ensp;&ensp;"
                + String.format("%.1f", i.getValue()) + "/10</td>" +
                "<td>" + MovieDatabase.getGenres(i.getItem()) + "</td>" +
                "<td>" + MovieDatabase.getCountry(i.getItem()) + "</td>" +
                "</tr> ");
            rank++;
        }

        System.out.println("</table>");

    }

 
    
}


/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class DirectorsFilter implements Filter{

    private String myDirectors;
    private String[] myDirectorsArray;

    public DirectorsFilter(String directors) {
        myDirectors = directors;
    }

    public void setTraining(String text){
        myDirectorsArray = myDirectors.split(",");
        System.out.println("myText is " + Arrays.toString(myDirectorsArray));
    }

    @Override
    public boolean satisfies(String id) {
        String currDirectorsList = MovieDatabase.getDirector(id);

        //Loop over the directors array and see if each director is in the currDirectorsList, if so, return true

        for (int i = 0; i < myDirectorsArray.length; i++) {

            int indexFound = currDirectorsList.indexOf(myDirectorsArray[i]);
            if (indexFound != -1) {
                return true;
            }
        }
        return false;
    }

}

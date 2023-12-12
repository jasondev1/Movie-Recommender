
/**
 * Write a description of GenreFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GenreFilter implements Filter{

    private String myGenre;

    public GenreFilter(String genre) {
        myGenre = genre;
    }

    @Override
    public boolean satisfies(String id) {
        int indexFound = MovieDatabase.getGenres(id).indexOf(myGenre);
        if (indexFound != -1) {
            return true;
        }
        return false;
    }

}

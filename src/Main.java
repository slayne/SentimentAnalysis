import java.io.IOException;

/**
 * Created by David on 26/04/2015.
 */
public class Main {

    public static void main(String [] arg) throws IOException, InterruptedException {

        TwitterManager tm = new TwitterManager();
        tm.performQuery("Bieber");
    }
}

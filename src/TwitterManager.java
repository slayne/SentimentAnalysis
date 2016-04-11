import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by David on 26/04/2015.
 */
public class TwitterManager {
    SentimentClassifier sentClassifier;
    int LIMIT= 10000; //the number of retrieved tweets
    ConfigurationBuilder cb;
    Twitter twitter;
    public TwitterManager() {
        cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey("");
        cb.setOAuthConsumerSecret("");
        cb.setOAuthAccessToken("");
        cb.setOAuthAccessTokenSecret("");
        twitter = new TwitterFactory(cb.build()).getInstance();
        sentClassifier = new SentimentClassifier();
    }
    public void performQuery(String inQuery) throws InterruptedException, IOException {
        Query query = new Query(inQuery);
        query.setLang("fr");
        query.setCount(10000);
        try {
            int count=0;
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("train/neu", true)));
            QueryResult r;
            do {
                r = twitter.search(query);
                ArrayList ts= (ArrayList) r.getTweets();
                for (int i = 0; i < ts.size() && count < LIMIT; i++) {





                    count++;
                    Status t = (Status) ts.get(i);

                    String text = t.getText();
                    System.out.println("Text: " + text);

                    String name = t.getUser().getScreenName();
                    System.out.println("User: " + name);
                    String sent = sentClassifier.classify(t.getText());
                    System.out.println("Sentiment: " + sent);
                    System.out.println("");

                    //writer.println(text);

                }
            } while ((query = r.nextQuery()) != null && count < LIMIT);
            writer.close();
        }
        catch (TwitterException te) {
            System.out.println("Couldn't connect: " + te);
        }
    }
}

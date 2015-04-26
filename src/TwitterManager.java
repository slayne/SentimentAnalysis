import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by David on 26/04/2015.
 */
public class TwitterManager {
    SentimentClassifier sentClassifier;
    int LIMIT= 500; //the number of retrieved tweets
    ConfigurationBuilder cb;
    Twitter twitter;
    public TwitterManager() {
        cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey("QXrRShnqmYZMK2pKVTxH5A3S3");
        cb.setOAuthConsumerSecret("O6FK1YByRtJovAAEtBlU2uImBDwvYl9Qt46EWdFsCcG2Ol4CFt");
        cb.setOAuthAccessToken("51033667-O27xiEvITurg3QzqCsJ5OnpGzvPun05X73aopqgfM");
        cb.setOAuthAccessTokenSecret("np5AAhKIWfOk8up3rYlopRgrbvNENGZrBeqTnvT9laFcQ");
        twitter = new TwitterFactory(cb.build()).getInstance();
        sentClassifier = new SentimentClassifier();
    }
    public void performQuery(String inQuery) throws InterruptedException, IOException {
        Query query = new Query(inQuery);
        query.setCount(100);
        try {
            int count=0;
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
                }
            } while ((query = r.nextQuery()) != null && count < LIMIT);
        }
        catch (TwitterException te) {
            System.out.println("Couldn't connect: " + te);
        }
    }
}

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;

import java.io.File;
import java.io.IOException;

/**
 * Created by David on 26/04/2015.
 */
    public class SentimentClassifier {

    String[] categories;
    LMClassifier classifier;


    public SentimentClassifier() {
        try {
            classifier= (LMClassifier) AbstractExternalizable.readObject(new File("docs/classifier2.txt"));
            categories = classifier.categories();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String classify(String text) {
        ConditionalClassification classification = classifier.classify(text);
        return classification.bestCategory();
    }
}
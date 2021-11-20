package com.projects.Time_Conv;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Toke {
    // public static String text = "Marie was born in Paris.";
    private static String url = "https://gist.githubusercontent.com/valo/c07f8db33d223f57a4cc9c670e1b6050/raw/2f47e8d567aafcaab9ed9cf1b90e21db09a57532/timezones.csv";

    public static void testToke(String text) throws IOException, InterruptedException {
        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,ssplit,pos");
        // example of how to customize the PTBTokenizer (these are just random example
        // settings!!)
        props.setProperty("tokenize.options", "splitHyphenated=false,americanize=false");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument doc = new CoreDocument(text);
        // annotate
        pipeline.annotate(doc);
        // display tokens
        for (CoreLabel tok : doc.tokens()) {
            System.out.println(String.format("%s\t%d\t%d\t%s\t%s", tok.word(), tok.beginPosition(), tok.endPosition(),
                    tok.word(), tok.tag()));
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());
    }
}

package com.projects.Time_Conv;

import java.io.IOException;
import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class Toke {
    // public static String text = "Marie was born in Paris.";
    // private static String url =
    // "https://gist.githubusercontent.com/valo/c07f8db33d223f57a4cc9c670e1b6050/raw/2f47e8d567aafcaab9ed9cf1b90e21db09a57532/timezones.csv";

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

        // HttpClient client = HttpClient.newHttpClient();
        // HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        // HttpResponse<String> httpResponse = client.send(request,
        // HttpResponse.BodyHandlers.ofString());
        // System.out.println(httpResponse.body());

        SparkSession spark = SparkSession.builder().appName("time_zone_conv").config("spark.master", "local")
                .getOrCreate();

        StructType schema = new StructType().add("Abr", "string").add("Zone", "string").add("Dilate", "string");

        Dataset<Row> df = spark.read().option("mode", "DROPMALFORMED").schema(schema)
                .csv("/home/jmarc/Desktop/Time_Conv/src/main/java/com/projects/Time_Conv/timezones.csv");
        df.createOrReplaceTempView("time_zone");
        Dataset<Row> sqlResult = spark.sql("SELECT Abr,Zone,Dilate" + " FROM time_zone");

        sqlResult.show(); // for testing
    }
}

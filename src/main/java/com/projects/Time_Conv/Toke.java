package com.projects.Time_Conv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Toke {
    private static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a z";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    // public static String text = "Marie was born in Paris.";
    // private static String url =
    // "https://gist.githubusercontent.com/valo/c07f8db33d223f57a4cc9c670e1b6050/raw/2f47e8d567aafcaab9ed9cf1b90e21db09a57532/timezones.csv";

    public static String testToke(String text) throws IOException, InterruptedException {
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
        List<String> hmm = new ArrayList<String>();
        String tim = new String("00:00");
        // display tokens
        for (CoreLabel tok : doc.tokens()) {
            System.out.println(String.format("%s\t%d\t%d\t%s\t%s", tok.word(), tok.beginPosition(), tok.endPosition(),
                    tok.word(), tok.tag()));
            if (tok.tag().contains("NNP") || tok.tag().contains("NN")) {
                hmm.add(tok.word());
            }
            if (tok.tag().contains("CD")) {
                tim = tok.word();
            }
        }
        System.out.println(hmm);
        // HttpClient client = HttpClient.newHttpClient();
        // HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        // HttpResponse<String> httpResponse = client.send(request,
        // HttpResponse.BodyHandlers.ofString());
        // System.out.println(httpResponse.body());
        ZoneId fromTimeZone = ZoneId.of(extracted(hmm.get(0))); // Source timezone
        ZoneId toTimeZone = ZoneId.of(extracted(hmm.get(1))); // Target timezone
        // System.out.println(ZoneId.getAvailableZoneIds() + " " +
        // TimeZone.getTimeZone("PST"));
        LocalDateTime today = LocalDateTime.now(); // Current time

        // Zoned date time at source timezone
        ZonedDateTime currentISTime = today.atZone(fromTimeZone);

        // Zoned date time at target timezone
        ZonedDateTime currentETime = currentISTime.withZoneSameInstant(toTimeZone);

        // Format date time - optional
        System.out.println(formatter.format(currentISTime));
        System.out.println(formatter.format(currentETime));

        return "\nCurrent Time : " + formatter.format(currentISTime) + "\nDilated Time : "
                + formatter.format(currentETime);
    }

    private static String extracted(String test) {
        SparkSession spark = SparkSession.builder().appName("time_zone_conv").config("spark.master", "local")
                .getOrCreate();

        StructType schema = new StructType().add("Abr", "string").add("Zone", "string").add("Dilate", "string");

        Dataset<Row> df = spark.read().option("mode", "DROPMALFORMED").schema(schema)
                .csv("/home/jmarc/Desktop/Time_Conv/src/main/java/com/projects/Time_Conv/timezones.csv");
        df.createOrReplaceTempView("time_zone");
        // String test = new String("AMST");
        Dataset<Row> sqlResult = spark.sql("SELECT Dilate" + " FROM time_zone where Abr = '" + test + "' LIMIT 1");

        sqlResult.show(); // for testing
        List<String> listOne = sqlResult.as(Encoders.STRING()).collectAsList();
        System.out.println(listOne);
        System.out.println(sqlResult);

        String st = new String(listOne.get(0));

        return "GMT" + st;
    }
}

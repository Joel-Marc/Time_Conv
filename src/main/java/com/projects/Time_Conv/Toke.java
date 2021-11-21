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
        List<String> hmm = new ArrayList<String>();
        String tim = new String("00:00");
        // display tokens
        for (CoreLabel tok : doc.tokens()) {
            System.out.println(String.format("%s\t%d\t%d\t%s\t%s", tok.word(), tok.beginPosition(), tok.endPosition(),
                    tok.word(), tok.tag()));
            if (tok.tag().contains("NNP")) {
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
        tim = tim.replace(":", "");
        while (tim.length() <= 4) {
            tim = tim + "0";
        }
        int time = Integer.parseInt(tim);
        int start = extracted(hmm.get(0));
        int end = extracted(hmm.get(1));
        System.out.println(start + " : " + end + " : " + (adsub(start, end)) + " : " + time);

        if (time + (end - start) < 0) {
            System.out.println(adsub(time, adsub(start, end)));
        }

    }

    private static int adsub(int start, int end) {
        int x = 0, y = 0, z = 0, t = 0;
        y = (((int) start / 100) - ((int) end / 100));
        x = start % 100 - end % 100;
        y += (int) x / 60;
        t = (int) y / 24;
        y = y % 24;
        x = x % 60;
        z = t * 10000 + y * 100 + x;
        System.out.println(x + " " + y + " " + z);

        return z;
    }

    private static int extracted(String test) {
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
        int start = new Integer(0);
        if (st.contains("+")) {

            st = st.replace("+", "");
            st = st.replace(":", "");
            if (st.startsWith("0")) {
                st = "0" + st;
            }
            while (st.length() <= 4) {
                st = st + "0";
            }
            System.out.println(st);
            start = Integer.parseInt(st);
            System.out.println(start);
        } else if (st.contains("-")) {

            st = st.replace("-", "");
            st = st.replace(":", "");
            if (!st.startsWith("0")) {
                st = "0" + st;
            }
            while (st.length() <= 4) {
                st = st + "0";
            }
            System.out.println(st);
            start = -Integer.parseInt(st);
            System.out.println(start);
        }
        return start;
    }
}

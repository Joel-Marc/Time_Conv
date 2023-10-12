package com.projects.time_conv;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Toke {
    private static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a z";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private Toke() {
        System.out.println("In private constructor");
    }

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
        String tim = "00:00";
        int flag = 0;
        // display tokens
        for (CoreLabel tok : doc.tokens()) {
            if (tok.tag().contains("NNP") || tok.tag().contains("NN")) {
                if ("am".contains(tok.word().toLowerCase()) || "pm".contains(tok.word().toLowerCase())) {
                    if ("am".contains(tok.word().toLowerCase())) {
                        flag = 1;
                    } else {
                        flag = 2;
                    }
                    continue;
                }
                hmm.add(tok.word().toUpperCase());
            }
            if (tok.tag().contains("CD")) {
                tim = tok.word();
            }
        }
        LocalDateTime today;
        if (!tim.contains("00:00")) {
            tim = tim.replace(":", "");
            if (!tim.startsWith("0") && tim.length() == 1 || tim.length() == 3) {
                tim = "0" + tim;
            }
            while (tim.length() < 4) {
                StringBuilder bld = new StringBuilder();
                bld.append(tim);
                bld.append("0");
            }

            int time = Integer.parseInt(tim);
            if (flag == 2) {
                time += 1200;
            }
            if (time == 2400) {
                time = 0000;
            }
            today = LocalDateTime.of(LocalDate.now(), LocalTime.of(time / 100, time % 100, 00));
        } else {
            today = LocalDateTime.now();
        }

        ZoneId fromTimeZone = ZoneId.of(extrac.extracted(hmm.get(0))); // Source timezone
        ZoneId toTimeZone = ZoneId.of(extrac.extracted(hmm.get(1))); // Target timezone

        // Zoned date time at source timezone
        ZonedDateTime currentISTime = today.atZone(fromTimeZone);

        // Zoned date time at target timezone
        ZonedDateTime currentETime = currentISTime.withZoneSameInstant(toTimeZone);

        return "\nCurrent Time : " + formatter.format(currentISTime) + "\nDilated Time : "
                + formatter.format(currentETime);
    }

}

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

public class extrac {
    public static String extracted(String test) {
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

        String st = listOne.get(0);

        return "GMT" + st;
    }

}

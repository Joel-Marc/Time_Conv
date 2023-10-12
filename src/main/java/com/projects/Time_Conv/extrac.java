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

public class Extrac {
    public static final str =  "string";
    private SparkSession spark;
    
    private Extrac() {
        spark = SparkSession.builder().appName("time_zone_conv").config("spark.master", "local").getOrCreate();
    }

    public static String extracted(String test) {
        StructType schema = new StructType().add("Abr", str).add("Zone", str).add("Dilate", str);

        Dataset<Row> df = this.spark.read().option("mode", "DROPMALFORMED").schema(schema)
                .csv("/home/jmarc/Desktop/Time_Conv/src/main/java/com/projects/Time_Conv/timezones.csv");
        df.createOrReplaceTempView("time_zone");
        Dataset<Row> sqlResult = this.spark.sql("SELECT Dilate" + " FROM time_zone where Abr = '" + test + "' LIMIT 1");

        sqlResult.show(); // for testing
        List<String> listOne = sqlResult.as(Encoders.STRING()).collectAsList();

        String st = listOne.get(0);

        return "GMT" + st;
    }

}

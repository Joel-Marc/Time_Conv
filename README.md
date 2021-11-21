# Time_Conv

- A Web Application made using **Java(Spring Boot)** which gets in a text input in form of a ([time] [from Time Zone] to [Time Zone]) or ([To Time Zone] of [time] [From Time Zone]) Using **Stanford's CoreNLP library** and the inbuilt **Java.time library.**
- Using this input we use **Apache Spark** to Load in a CSV containing the (ABBREVATIONS,TIME ZONE NAME,GMT_OFFSET) to find the GMT offset of the Time zones we want to convert from and to.
- Finally it is displayed using the **thymeleaf** templating engine to Display the output.

## Dependencies

- Apache Spark 3.1.2


## ScreenShots

- 

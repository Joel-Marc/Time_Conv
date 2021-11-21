# Time_Conv

- A Web Application made using **Java(Spring Boot)** which gets in a text input in form of a ([time] [from Time Zone] to [Time Zone]) or ([To Time Zone] of [time] [From Time Zone]) Using **Stanford's CoreNLP library** and the inbuilt **Java.time library.**
- Using this input we use **Apache Spark** to Load in a CSV containing the (ABBREVATIONS,TIME ZONE NAME,GMT_OFFSET) to find the GMT offset of the Time zones we want to convert from and to.
- Finally it is displayed using the **thymeleaf** templating engine to Display the output.

## Dependencies

- Apache Spark 3.1.2


## ScreenShots

- ![image](https://user-images.githubusercontent.com/53477893/142756929-ea715ad7-83ae-4ed1-a346-a693e9a20784.png) ![image](https://user-images.githubusercontent.com/53477893/142756945-ed5389bf-04bd-4ba3-8cf7-ee12881c11c6.png)
- ![image](https://user-images.githubusercontent.com/53477893/142756972-01a9b977-ff82-4f85-a4c1-24571fb814ab.png) ![image](https://user-images.githubusercontent.com/53477893/142756984-4175ca58-f056-4ae8-8219-a625236523e3.png)
 

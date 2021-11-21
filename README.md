# Time_Conv

- A Web Application made using **Java(Spring Boot)** which gets in a text input in form of a ([time] [from Time Zone] to [Time Zone]) or ([To Time Zone] of [time] [From Time Zone]) Using **Stanford's CoreNLP library** and the inbuilt **Java.time library.**
- Using this input we use **Apache Spark** to Load in a CSV containing the (ABBREVATIONS,TIME ZONE NAME,GMT_OFFSET) to find the GMT offset of the Time zones we want to convert from and to.
- Finally it is displayed using the **thymeleaf** templating engine to Display the output.

## Dependencies

- Apache Spark 3.1.2

## ScreenShots

- ![image](https://user-images.githubusercontent.com/53477893/142756929-ea715ad7-83ae-4ed1-a346-a693e9a20784.png) ![image](https://user-images.githubusercontent.com/53477893/142759092-ce35ec17-2c26-411f-819e-afc72173f76c.png)

- ![image](https://user-images.githubusercontent.com/53477893/142756972-01a9b977-ff82-4f85-a4c1-24571fb814ab.png) ![image](https://user-images.githubusercontent.com/53477893/142759110-6a85abdf-830b-4ec0-ba6f-fa7935efae45.png)
- ![image](https://user-images.githubusercontent.com/53477893/142759145-bb2fb4dc-7263-4e32-a0b4-46b6b947f88b.png) ![image](https://user-images.githubusercontent.com/53477893/142759132-0ecc6ef6-7e75-4c30-8684-9a1f0ce56854.png)



## Further Improvements

- To use NER to get the Location and find the appropriate Time Zone.
- To get input in natural language . (Ex : "When the time in New York is 3 pm, what is the time in London? [or] When the time in London is 9 am, what is the time in Tokyo and India?")
  

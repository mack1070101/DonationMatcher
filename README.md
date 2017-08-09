* Copia Test
See wiki for full documentation and writeup

* Problem Statement
** Project Description & Instructions:
The goal of the challenge is to match customer's pickup requests with available non-profit recipients.
At the basic and conceptual level, implementation of this challenge replicates a simplified version of our current algorithm used by the operations team.

** Data Sources
You are given two data sources (Customers and Recipients), both of which are CSV files.
You must be able to correctly parse through the given CSV files and perform appropriate operations to achieve your goal.

Feel free to use any libraries and frameworks to achieve your goal of this challenge.

** Description of Requirements
The distance between a pickup and recipient must be within 10 miles. Date & time is provided for each of the pickup requests and is the earliest promised time. The latest promised time is one hour added to it. Recipients must be open between the earliest and latest promised time. For each pickup-to-recipient(s) matches, sort by the most to least favorable recipients. It is up to you on deciding how you want to define a “favorable recipient.” Both data sources include geo-coordinates, which you will need to use to calculate the distance between them.
Result of matches must be stored as one or more CSV files. It is up to you on how you’d like to structure the CSV file(s).

** Considerations
Break down your implementation into smaller parts by writing them as functions.

Write test cases for each of the functions you implement and perform unit tests as necessary.
It’s crucial that you write enough but useful test cases. Each of your test cases will be reviewed to assess test coverages and thoroughness.

While you are working to implement this challenge, you must document your entire work: your initial thoughts, plans, approach, unit test results, overall result, results analysis, and conclusion. It is highly recommended that your documentation is as thorough as possible.

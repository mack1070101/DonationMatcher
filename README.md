#Project description & instructions:
The goal of the challenge is to match customer's pickup requests with available non-profit recipients.
At the basic and conceptual level, implementation of this challenge replicates a simplified version of our current algorithm used by the operations team.

You are given two data sources (Customers and Recipients), both of which are CSV files.
You must be able to correctly parse through the given CSV files and perform appropriate operations to achieve your goal.

## Requirements
The distance between a pickup and recipient must be within 10 miles. Date & time is provided for each of the pickup requests and is the earliest promised time. The latest promised time is one hour added to it. Recipients must be open between the earliest and latest promised time. For each pickup-to-recipient(s) matches, sort by the most to least favorable recipients. It is up to you on deciding how you want to define a “favorable recipient.” Both data sources include geo-coordinates, which you will need to use to calculate the distance between them.
Result of matches must be stored as one or more CSV files. It is up to you on how you’d like to structure the CSV file(s).

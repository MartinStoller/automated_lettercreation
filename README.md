# automated_lettercreation
This is a tool for a fake car retailer, who wants to modernize her business. The old customer and vehicle data were stored locally as xml files - now they get moved to a MySQL database. 
Also the personalized letters, which she sometimes sends to her customers are now created automatically from that database.

The tool does the following things:
 - It loads xml files of fake customers and vehicles and adds them to a mySQL database.
 - It allows the user to read and edit the database.
 - It automatically creates letters (in German language) for each customer, advertising the currently available cars.

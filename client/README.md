# Client setup and execution

### hotrod-jdg-demo
This application performs following functions:
1. Create connection with the dynamic cache using hotrod-client.
2. Push records in the cache.
3. Read the data using the key
4. Sample code to check the "contains" functionality

To execute the application
````
run the main java class in the code and it should run fine
````

### jdg-cache-producer
This application performs following function:
1. Create connection with the static cache using hotrod-client
2. Pull the records from postgresql database table and push them into cache.
3. Search the records using the key
4. Search records using InfinySpan querying mechanism.

To execute the application
````
The database setup instructions are provided in the README.md file of the project
The setup and execution steps are given inside the README.md of the project.
````

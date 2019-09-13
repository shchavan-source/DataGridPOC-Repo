## Offload your database data into Red Hat JBoss Data Grid made easy

This project is an example showing how to read data from a PostgreSQL database and put the data into a cache within a Red Hat JBoss Data Grid environment.

### Prerequisites

1. Install [Maven](http://maven.apache.org/install.html)
2. Install [Red Hat JBoss Data Grid](http://developers.redhat.com/products/datagrid/hello-world)
3. Install [PostgreSQL](https://wiki.postgresql.org/wiki/Detailed_installation_guides)

Afer install postgresql, login into it using command line or using [Postico](https://eggerapps.at/postico)

### Creating database

Following command creates products database
````
create database products;
````

### Creating Table

Following command creates product table
````
create table product
(
 itemId numeric(10) CONSTRAINT itemid_pk PRIMARY KEY,
 name varchar(50),
 description varchar(1024),
 price decimal(10,2)
);
````

### Creating records in the product table

The code contains csv file to create records into the product table

Use following command to create records into the product table

````
COPY product FROM '<install-dir>/jboss-data-grid/jdg-cache-producer/src/main/java/com/redhat/jdg/producer/db/product-data.csv' ( FORMAT CSV, DELIMITER(','), HEADER(TRUE));

````

### Build application jdg-cache-producer

```bash
$ mvn -s settings.xml clean install
```

### Run application jdg-cache-producer

```bash
$ mvn -s settings.xml exec:java
```



# DataGrid POC

The intension of this POC is to understand the server configurations for Dynamic[File Store] and Static[No storage]

````
Server directory

````
The files under this directory contains the clustered.xml to create 1 node cache and standalone.conf for the parameterised starup of the datagrid.

````
Client directory

````

The JAVA Clients in this directory contains hot-rod client implementation for following use cases:
1. Loading data into dynamic and storing in the file store.
2. Data loading from a custom table in the database and storing in the cache.
3. InfinySpan query implementation for wildcard search.

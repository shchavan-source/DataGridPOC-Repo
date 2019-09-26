# Server Configurations

To start static and dynamic cache place:
1. clustered.xml file in the DataGrid_HOME/standalone/configuration directory.
2. standalone.conf file in DataGrid_HOME/bin directory.
3. module.xml file in the modules/system/layers/base/org/postgresql/main directory. Create the directories if not present.
4. postgresql-42.2.6.jar in the modules/system/layers/base/org/postgresql/main directory.

Following parameters needs to be updated in standalone.conf file

````
JAVA_HOME: The java home directory
jboss.server.data.dir: Data directory to keep the cache data
initial_hosts: all the hosts that need to be in the cluster
dbs.app.instance.name: cache instance name
dbs.app.binding.port-offset: port offset to be added to the port number on which the processes like hotrod, rest, tcp etc will be listening.
````

Use the following command to start the DataGrid node
````
DataGrid_HOME/bin/standalone.sh -c clustered.xml
````

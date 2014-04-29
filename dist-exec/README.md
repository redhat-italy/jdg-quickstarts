JDG-dist-exec
==============

A small project to learn and explore Jboss Data Grid (Infinispan)

Build instructions
==================

To build the code you just need Maven and the JDG repositories installed somewhere (for a simple setup, just download the repositories and install them locally on your disk).
You *won't* need to install the JDG server, as this example is engineered to run in Library mode.

Install the Maven repositories
------------------------------

Installing [JDG 6.2 maven repository](https://access.redhat.com/jbossnetwork/restricted/softwareDetail.html?softwareId=27433&product=data.grid&version=&downloadType=distributions),
which depends by the [EAP 6.1.1 Maven repository](https://access.redhat.com/jbossnetwork/restricted/softwareDetail.html?softwareId=24173&product=appplatform&version=6.1.1&downloadType=distributions), is the first step.

You'll find detailed instructions on how to install the Maven repositories in the JDG 6.2 [Getting Started Guide] (https://access.redhat.com/site/documentation/en-US/Red_Hat_JBoss_Data_Grid/6.2/html-single/Getting_Started_Guide/index.html#chap-Install_and_Use_the_Maven_Repositories)

For your reference, you will find an example settings.xml to copy in your .m2 directory in the example-maven-settings directory.

This Maven settings.xml assumes you have unzipped the repositories in the following locations, so edit it accordingly:

* /opt/jboss-datagrid-6.2.0-maven-repository/
* /opt/jboss-eap-6.1.1.GA-maven-repository

Build the code
--------------

After installing the repositories, you just have to clone the code and run some nodes.
For example to launch four nodes on a single machine just run these commands using different terminals:

```shell
mvn -P run

mvn -P run

mvn -P run

mvn -P run
```

Or on four different machines:

```shell
mvn -P run -Djgroups.bind_addr=ip1

mvn -P run -Djgroups.bind_addr=ip2

mvn -P run -Djgroups.bind_addr=ip3

mvn -P run -Djgroups.bind_addr=ip4
```

Usage
-----

Every node will have its own command line interface "attached", which you can use to play with your Data Grid.
Type 'help' on the command line to show a list of commands:

```shell
get id
     Get an object from the grid.

put id value
     Put an object (id, value) in the grid.

putIfAbsent|putifabsent|pia id value
     Modify an id object with value.

locate id
     Locate an object in the grid.

loadtest
     Load example values in the grid

local
     List all local valuesFromKeys.

primary
     List all local valuesFromKeys for which this node is primary.

clear
     Clear all valuesFromKeys.

info
     Information on cache.

rotate n
     Apply a rotate n on String values with a Distributed Executor

routing
     Print routing table.

help
     List of commands.

exit|quit|q|x
     Exit the shell.
```
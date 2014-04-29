JDG-quickstarts
==============

A small project to learn and explore Jboss Data Grid (Infinispan).
This is the common base project.

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

You can optionally run these commands if you want to build every module
```shell
mvn clean install
```

Run the examples
----------------

To run some nodes, just enter in one of the submodules and execute the correct profile.

For example to launch four nodes on a single machine for the basic playground just run these commands using different terminals:

```shell

cd playground

mvn -P run

mvn -P run

mvn -P run

mvn -P run
```

Or on four different machines:

```shell
cd playground
mvn -P run -Djgroups.bind_addr=ip1

cd playground
mvn -P run -Djgroups.bind_addr=ip2

cd playground
mvn -P run -Djgroups.bind_addr=ip3

cd playground
mvn -P run -Djgroups.bind_addr=ip4
```

Usage
-----

Please refer to every submodule Readme.md for detailed instructions
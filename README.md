JDG-quickstarts
==============

A small project to learn and explore Jboss Data Grid (Infinispan).
This is the root project.

Every module is a standalone project inheriting common features by the 'playground' module: basically a simple Data Grid with small 'business' CLI.

At the moment this quickstart contains these modules:

Playground
----------

Playground consists of a small CLI which is very useful to experiment and learn Infinispan/JDG. Every other modules extends these features adding more commands to the CLI.
See the module Readme file for more details.

Dist-exec
----------

This module adds a 'rotate' command which plays with the strings in the Data Grid. See the module Readme file for more details.


Map-reduce
----------

This module adds a 'count' command which counts the rock groups longer/shorter than a given threshold. See the module Readme file for more details.


Jmx-statistics
--------------

This module is useful if you want to know how to expose via JMX your MBeans. See the module Readme file for more details.


Build instructions
==================

To build the code you just need Maven.

If you want to use the Red Hat supported bits you must also have the JDG maven repositories installed somewhere (for a simple setup, just download the repositories and install them locally on your disk).
See next section for more details on how to reference the local maven repos.

You *won't* need to install the JDG server, as this example is engineered to run in Library mode.

Install the Maven repositories
------------------------------

Please note that if you want to use just the Open Source bits, you can completely skip this step.
If you want to use the Red Hat supported bits, you must install JDG repos and edit the <version.org.infinispan> attribute in POM.xml.

You'll find detailed instructions on how to install the [JDG 6.4.0 maven repository](https://access.redhat.com/jbossnetwork/restricted/softwareDownload.html?softwareId=35213)
 in the JDG 6.4 [Getting Started Guide] (https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_Data_Grid/6.4/html/Getting_Started_Guide/chap-Install_and_Use_the_Maven_Repositories.html)

For your reference, you will find an example settings.xml to copy in your .m2 directory in the example-maven-settings directory.

This Maven settings.xml assumes you have unzipped the repositories in the following locations, so edit it accordingly:

* /opt/jboss-datagrid-maven-repository/

Build the code
--------------

You can optionally run these commands if you want to build every module upfront.

```shell
mvn clean install
```

Run the examples
----------------

To run some nodes, just enter in one of the modules and execute the correct profile.

For example to launch four nodes on a single machine for the basic playground just run these commands using different terminals:

```shell
mvn -P run -Djgroups.bind_addr=localhost

mvn -P run -Djgroups.bind_addr=localhost

mvn -P run -Djgroups.bind_addr=localhost

mvn -P run -Djgroups.bind_addr=localhost
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
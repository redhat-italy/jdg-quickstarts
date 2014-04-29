JDG-jmx-statistics
==============

A small project to learn and explore Jboss Data Grid (Infinispan).
This module adds to the global component registry a new service that handle the creation for a custom MBean

Build instructions
==================

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

Please refer to the parent project Readme.md for more details

Usage
-----

Run a node and use jconsole to monitor the new MBean.
Type 'help' on the command line to show a list of commands, please refer to the 'playground' project Readme.md for more details


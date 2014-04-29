JDG-map-reduce
==============

A small project to learn and explore Jboss Data Grid (Infinispan).
This module adds a simple Map/Reduce task.

Build instructions
==================

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

Please refer to the parent project Readme.md for more details

Usage
-----

Every node will have its own command line interface "attached", which you can use to play with your Data Grid.
Type 'help' on the command line to show a list of commands, please refer to the 'playground' project Readme.md for more details

MAP/Reduce specific commands
----------------------------

```shell
count threshold
     Return how many group's names are shorter/longer than the given threshold
```

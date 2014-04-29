JDG-playground
==============

A small project to learn and explore Jboss Data Grid (Infinispan).
This is the common base project.

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

Every node will have its own command line interface "attached", which you can use to play with your Data Grid.
Type 'help' on the command line to show a list of commands:

```shell
get id
     Get an object from the grid.

put id value
     Put an object (id, value) in the grid.

putIfAbsent|putifabsent|pia id value
     Put an object (id, value) in the grid if not already present

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

routing
     Print routing table.

help
     List of commands.

exit|quit|q|x
     Exit the shell.
```
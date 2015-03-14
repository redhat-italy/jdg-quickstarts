JDG-playground
==============

A small project to learn and explore Jboss Data Grid (Infinispan).
This is the common base project.

Build instructions
==================

For example to launch four nodes on a single machine just run these commands using different terminals:

```shell
mvn -P run -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=localhost

mvn -P run -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=localhost

mvn -P run -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=localhost

mvn -P run -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=localhost
```

Or on four different machines:

```shell
mvn -P run -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=ip1

mvn -P run -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=ip2

mvn -P run -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=ip3

mvn -P run -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=ip4
```

Please refer to the parent project Readme.md for more details

Usage
-----

Every node will have its own command line interface "attached", which you can use to play with your Data Grid.
Type 'help' on the command line to show a list of commands:

```shell

all
     List all valuesFromKeys.

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

replica
    List all local valuesFromKeys for which this node is a replica.
    
routing
     Print routing table.

help
     List of commands.

exit|quit|q|x
     Exit the shell.
```
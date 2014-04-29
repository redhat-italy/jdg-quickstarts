JDG-dist-exec
==============

A small project to learn and explore Jboss Data Grid (Infinispan).
This module adds a simple Distexec (Distributed Execution) task.

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

Distexec specific commands
--------------------------

```shell
rotate n
     Apply a rotate n on String values with a Distributed Executor
```
JDG-dist-exec
==============

A small project to learn and explore Jboss Data Grid (Infinispan).
This module adds a simple Distexec (Distributed Execution) task.

Build instructions
==================

Please refer to the parent project Readme.md

Usage
-----

Every node will have its own command line interface "attached", which you can use to play with your Data Grid.
Type 'help' on the command line to show a list of commands, please refer to the parent project Readme.md for details

Distexec specific commands
--------------------------

```shell
rotate n
     Apply a rotate n on String values with a Distributed Executor
```
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

routing
     Print routing table.

help
     List of commands.

exit|quit|q|x
     Exit the shell.
```

Distexec specific commands
--------------------------

rotate n
     Apply a rotate n on String values with a Distributed Executor
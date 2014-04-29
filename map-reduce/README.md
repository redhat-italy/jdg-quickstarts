JDG-map-reduce
==============

A small project to learn and explore Jboss Data Grid (Infinispan).
This module adds a simple Map/Reduce task.

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

MAP/Reduce specific commands
----------------------------

count limit
     Return how many group's names are shorter and longer than the limit
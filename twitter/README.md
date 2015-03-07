JDG-twitter
==============

A small project to learn and explore Jboss Data Grid (Infinispan).
This module adds Twitter commands to fill the grid with tweets

Build instructions
==================

For example to launch four nodes on a single machine just run these commands using different terminals (or different machines):

```shell
runTwitterExample.sh

runTwitterExample.sh

runTwitterExample.sh

runTwitterExample.sh
```

Please refer to the parent project Readme.md for more details

Usage
-----

Every node will have its own command line interface "attached", which you can use to play with your Data Grid.
Type 'help' on the command line to show a list of commands, please refer to the 'playground' project Readme.md for more details.

Before running the code you have to provide your own Twitter OAuth keys and tokens in the file runTwitterExample.sh

You can generate your own OAuth keys at https://apps.twitter.com

Twitter specific commands
----------------------------

```shell
start <hashtag> <timeout>
		Start feeding the grid with tweets containing the hashtag
stats <hashtag>
		Stats on tweets received with <hashtag>
stop <hashtag>
		Stop feeding the grid with tweets from <hashtag>
```

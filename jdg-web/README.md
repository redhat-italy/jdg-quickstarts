JDG-Web
==============

A small project to learn and explore Jboss Data Grid (Infinispan).
This module start a web interface to visualize distribution on http://localhost:8080

Build instructions
==================

For example to launch four nodes on a single machine just run these commands using different terminals (or on different machines editing the IP):

```shell
mvn jetty:run -Djgroups.bind_addr=localhost
```

If you want to use twitter features just create a property file like this:

```properties
consumer.key=your_key
consumer.secret=your_secret
access.token=your_token
access.token.secret=your_token_secret
```
and set the twitter.config.file as command line argument:

```shell
mvn jetty:run -Djgroups.bind_addr=localhost -Dtwitter.config.file=path_to_file
```

Please refer to the parent project Readme.md for more details

Usage
-----

Every node will have its own command line interface "attached", which you can use to play with your Data Grid.
Type 'help' on the command line to show a list of commands, please refer to the 'playground' project Readme.md for more details.

Before running the Twitter code you have to provide your own Twitter OAuth keys and tokens in the file runTwitterExample.sh

You can generate your own OAuth keys at https://apps.twitter.com


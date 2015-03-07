#!/bin/bash
consumerKey=YOURCONSUMERKEY
consumerSecret=YOURCONSUMERSECRET
token=YOURTOKEN
tokenSecret=YOURTOKENSECRET

mvn -P run -Djgroups.bind_addr=localhost -Dtwitter.consumerKey=$consumerKey -Dtwitter.consumerSecret=$consumerSecret -Dtwitter.token=$token -Dtwitter.tokenSecret=$tokenSecret

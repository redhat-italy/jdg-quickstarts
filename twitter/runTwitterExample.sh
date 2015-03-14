#!/bin/bash
consumerKey=YOURCONSUMERKEY
consumerSecret=YOURCONSUMERSECRET
token=YOURTOKEN
tokenSecret=YOURTOKENSECRET

mvn -P run -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=localhost -Dtwitter.consumerKey=$consumerKey -Dtwitter.consumerSecret=$consumerSecret -Dtwitter.token=$token -Dtwitter.tokenSecret=$tokenSecret

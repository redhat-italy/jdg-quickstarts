#!/bin/bash
mvn -P run -Djava.net.preferIPv4Stack=true -Djgroups.bind_address=localhost -Dplayground.jgroups.configuration=jgroups-tcp.xml

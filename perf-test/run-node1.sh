mvn -P perf-test -Dplayground.levelDB.location=./cache/node1/location -Dplayground.levelDB.expired=./cache/node1/expired -Dplayground.levelDB.writeBehind=true clean install

mvn -P perf-test -Dplayground.levelDB.location=./cache/node2/location -Dplayground.levelDB.expired=./cache/node2/expired -Dplayground.levelDB.writeBehind=true clean install

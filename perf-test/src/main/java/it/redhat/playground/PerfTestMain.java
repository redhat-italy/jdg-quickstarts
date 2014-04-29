package it.redhat.playground;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PerfTestMain {

    public static void main(String[] args) throws IOException {
        new PerfTestConfiguration()
                .configure()
                .start();
    }

    private static Logger log = LoggerFactory.getLogger(PlaygroundMain.class.getName());

}

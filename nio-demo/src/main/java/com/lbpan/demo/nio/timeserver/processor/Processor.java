package com.lbpan.demo.nio.timeserver.processor;

import java.io.IOException;

public interface Processor extends Runnable {

    public void process() throws IOException;
}

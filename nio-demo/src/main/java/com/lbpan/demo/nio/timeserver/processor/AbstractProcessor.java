package com.lbpan.demo.nio.timeserver.processor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public abstract class AbstractProcessor implements Processor {

    protected Selector selector;

    public AbstractProcessor(SelectionKey selectionKey) {
        this.selector = selectionKey.selector();
    }

    @Override
    public void run() {
        try {
            this.process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

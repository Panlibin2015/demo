package com.lbpan.demo.nio.timeserver.processor;

import java.nio.channels.SelectionKey;

public class ProcessorFactory {

    public static Processor processor(SelectionKey selectionKey) {
        boolean valid = selectionKey.isValid();
        if (valid) {
            if (selectionKey.isAcceptable()) {
                return new AcceptorProcessor(selectionKey);
            } else if (selectionKey.isReadable()) {
                return new ReaderProcessor(selectionKey);
            } else if (selectionKey.isWritable()) {
                return new WritableProcessor(selectionKey);
            } else {
                return null;
            }
        }

        return null;
    }

}

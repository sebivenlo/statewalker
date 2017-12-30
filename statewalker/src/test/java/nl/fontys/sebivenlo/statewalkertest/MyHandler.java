/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sebivenlo.statewalkertest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 *
 * @author hom
 */
public class MyHandler extends Handler {

    public String lastMessage;
    public List<Object> lastParams = Collections.EMPTY_LIST;

    @Override
    public void publish( LogRecord record ) {
        lastMessage = record.getMessage();
        System.out.println( "lastMessage = " + lastMessage );
        lastParams = Arrays.asList( record.getParameters() );
        System.out.println( "lastParams = " +lastParams  );
    }

    @Override
    public void flush() {
        lastMessage = "";
        lastParams = Collections.EMPTY_LIST;
    }

    @Override
    public void close() throws SecurityException {
    }

}

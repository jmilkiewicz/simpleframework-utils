package pl.softmil.simpeweb.containers;

import java.io.*;

import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;

public class FlushContainer implements Container {

    @Override
    public void handle(Request req, Response resp) {
        try {
            PrintStream body = resp.getPrintStream();
            body.flush();
            body.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

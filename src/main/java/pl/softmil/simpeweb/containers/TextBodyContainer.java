package pl.softmil.simpeweb.containers;

import java.io.*;

import org.apache.http.HttpHeaders;
import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;

public class TextBodyContainer implements Container {
    private String bodyText = "Hello World";
    private boolean flushAndClose = true;
    private String contentType = "text/plain";

    public TextBodyContainer() {
    }

    public TextBodyContainer withBodyText(String newBodyText) {
        this.bodyText = newBodyText;
        return this;
    }

    public TextBodyContainer withContentType(String newContentType) {
        this.contentType = newContentType;
        return this;
    }

    public TextBodyContainer withFlushingAndClosing(boolean flushAndClose) {
        this.flushAndClose = flushAndClose;
        return this;
    }

    @Override
    public void handle(Request req, Response resp) {
        try {
            PrintStream bodyStream = resp.getPrintStream();
            resp.set(HttpHeaders.CONTENT_TYPE, contentType);
            bodyStream.print(bodyText);
            if (flushAndClose) {
                bodyStream.flush();
                bodyStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

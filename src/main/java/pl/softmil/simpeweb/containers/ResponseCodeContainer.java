package pl.softmil.simpeweb.containers;

import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;

public class ResponseCodeContainer implements Container{
    private final int statusCode;
    private boolean flushAndClose = true;
    
    public ResponseCodeContainer withFlushAndClose(boolean flushAndClose){
        this.flushAndClose = flushAndClose;
        return this;
    }
    
    public ResponseCodeContainer(int statusCode) {
        super();
        this.statusCode = statusCode;
    }

    @Override
    public void handle(Request req, Response resp) {
        try{
        resp.setCode(statusCode);
        if(flushAndClose){
            resp.commit();
            resp.close();
        }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}

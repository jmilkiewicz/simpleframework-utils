package pl.softmil.simpeweb.router;

import java.io.IOException;
import java.util.concurrent.*;

import org.apache.http.*;
import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;

public class SimpleRouter implements Container {
    private ConcurrentMap<String, Container> handlers = new ConcurrentHashMap<String, Container>();

    @Override
    public void handle(Request req, Response resp) {        
        Container handler = getHandler(req.getPath());
        if (handler == null) {
            signalNonHandlerFound(resp);
        } else {
            handler.handle(req, resp);
        }
    }

    private void signalNonHandlerFound(Response resp) {
        try {
            resp.setCode(HttpStatus.SC_NOT_FOUND);
            resp.commit();
            resp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Container getHandler(Path path) {
        for (String handlerPath : handlers.keySet()) {
            if (handlerPath.equals(path.getPath())) {
                return handlers.get(handlerPath);
            }
        }
        return null;
    }

    public SimpleRouter addHandler(String discriminator, Container handler) {
        handlers.putIfAbsent(discriminator, handler);
        return this;
    }
}

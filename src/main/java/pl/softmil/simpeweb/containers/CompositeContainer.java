package pl.softmil.simpeweb.containers;

import java.util.*;

import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;

import pl.softmil.simpeweb.interceptor.response.ResponseInterceptor;

public class CompositeContainer implements Container {
    public List<ResponseInterceptor> responseInterceptors = new LinkedList<ResponseInterceptor>();
    private List<Container> containers = new LinkedList<Container>();

    public CompositeContainer withContainer(Container container){
        containers.add(container);
        return this;
    }
    
    public CompositeContainer withResponseInterceptor(ResponseInterceptor responseInterceptor){
        responseInterceptors.add(responseInterceptor);
        return this;
    }
    
    @Override
    public void handle(Request req, Response resp) {
        runResponseInterceptors(resp);
        runContainers(req, resp);

    }

    private void runContainers(Request req, Response resp) {
        for (Container container : containers) {
            container.handle(req, resp);
        }
    }

    private void runResponseInterceptors(Response resp) {
        for (ResponseInterceptor responseInterceptor : responseInterceptors) {
            responseInterceptor.intercept(resp);
        }

    }
}

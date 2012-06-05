package pl.softmil.simpeweb.interceptor.response;

import java.util.*;

import org.simpleframework.http.Response;

public class SetHeadersInterceptor implements ResponseInterceptor {
    private Map<String, String> headers = new HashMap<String, String>();
    
    public SetHeadersInterceptor(String name, String value) {
        headers.put(name, value);
    }

    public SetHeadersInterceptor with(String name, String value) {
        headers.put(name, value);
        return this;
    }
    
    @Override
    public void intercept(Response response) {
        for (String headerName : headers.keySet()) {
            response.add(headerName, headers.get(headerName));
        }
    }

}

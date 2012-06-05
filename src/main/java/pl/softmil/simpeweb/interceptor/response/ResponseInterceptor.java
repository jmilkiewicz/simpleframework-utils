package pl.softmil.simpeweb.interceptor.response;

import org.simpleframework.http.Response;

public interface ResponseInterceptor{
    void intercept(Response response);
}

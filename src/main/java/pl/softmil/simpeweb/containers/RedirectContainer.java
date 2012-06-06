package pl.softmil.simpeweb.containers;

import java.io.IOException;
import java.net.*;

import org.apache.http.*;
import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;

public class RedirectContainer implements Container {
    private final String redirectTo;
    private int redirectCode = HttpStatus.SC_MOVED_TEMPORARILY;
    
    public RedirectContainer(String redirectTo) {
        this.redirectTo = redirectTo;
    }
    
    public RedirectContainer withCustomerRedirectCode(int newRedirectCode){
        this.redirectCode = newRedirectCode;
        return this;
    }
    
    public int getRedirectCode() {
        return redirectCode;
    }

    @Override
    public void handle(Request req, Response resp) {
        try {
            resp.setCode(redirectCode);
            //String redirectTargetUrl = getRedirectToFullUrl(req);
            String redirectTargetUrl = redirectTo;
            resp.set(HttpHeaders.LOCATION, redirectTargetUrl);
            resp.commit();
            resp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRedirectToFullUrl(Request req)
            throws MalformedURLException {
        String result = null;
        if (redirectTo.startsWith("http")) {
            result = redirectTo;
        } else {
            result = buildAbsoluteFromRelative(req);
        }
        return result;
    }

    private String buildAbsoluteFromRelative(Request req)
            throws MalformedURLException {
        Address address = req.getAddress();
        String scheme = address.getScheme();
        String domain = address.getDomain();
        if (domain == null) {
            domain = "";
        }
        try {
            URL url = new URL("http", "localhost", address.getPort(), domain
                    + redirectTo, null);
            return url.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

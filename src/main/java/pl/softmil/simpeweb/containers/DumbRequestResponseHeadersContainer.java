package pl.softmil.simpeweb.containers;

import java.util.*;

import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;
import org.slf4j.*;

public class DumbRequestResponseHeadersContainer implements Container {
    private static final Logger Log = LoggerFactory
            .getLogger(DumbRequestResponseHeadersContainer.class);

    @Override
    public void handle(Request req, Response resp) {
        dumpRequestHeaders(req);
        dumpResponseHeaders(resp);
    }

    private void dumpRequestHeaders(Request req) {
        Log.info("REQUEST HEADERS:");
        List<String> names = req.getNames();
        for (String string : names) {
            List<String> values = req.getValues(string);
            logHeader(string, values);
        }
        Log.info("===========================");
    }

    private void dumpResponseHeaders(Response resp) {
        Log.info("RESPONSE HEADERS:");
        List<String> names = resp.getNames();
        for (String string : names) {
            List<String> values = resp.getValues(string);
            logHeader(string, values);
        }
        Log.info("===========================");
    }

    private void logHeader(String headerName, List<String> values) {
        Log.info(headerName
                + " : "
                + Arrays.deepToString(values.toArray(new String[values.size()])));
    }

}

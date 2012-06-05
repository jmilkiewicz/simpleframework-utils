package pl.softmil.simpeweb.containers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import helper.IOHelper;

import java.io.*;
import java.net.URL;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.*;

import pl.softmil.simpeweb.interceptor.response.SetHeadersInterceptor;
import pl.softmil.simpeweb.junit.StartStopSimpleWebServerRule;
import pl.softmil.simpeweb.router.SimpleRouter;

import com.google.common.base.Charsets;

public class RedirectContainerTest {
    private SimpleRouter simpleRouter = new SimpleRouter();
    
    @Rule
    public StartStopSimpleWebServerRule startStopSimpleWebServerRule = new StartStopSimpleWebServerRule(
            32331, simpleRouter);
    
    @Test
    public void testFollowsRedirection() throws IOException {
        simpleRouter.addHandler("/red", new RedirectContainer("/foo"))
                .addHandler("/foo", new TextBodyContainer().withBodyText("aa"));
        
        URL url = new URL("http://localhost:32331/red");
        String content = getContentAsString((InputStream) url.getContent());
        
        assertThat("invalid content string", content, is("aa"));
    }
    
    @Test
    public void testHttpClientHasRedirectResponseHeaders() throws Exception {
        simpleRouter.addHandler(
                "/my",
                new CompositeContainer().withContainer(new FlushContainer())
                        .withResponseInterceptor(
                                new SetHeadersInterceptor("foo", "my")));
        simpleRouter.addHandler(
                "/redirect",
                new CompositeContainer().withContainer(
                        new RedirectContainer("http://localhost:32331/my"))
                        .withResponseInterceptor(
                                new SetHeadersInterceptor("foo", "red")));

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(new HttpGet(
                "http://localhost:32331/redirect"));

        String contentAsString = getContentAsString(response.getEntity()
                .getContent());
        assertThat(contentAsString, notNullValue());
        assertSingleHeaderValue(response, "foo", "my");
    }

    private void assertSingleHeaderValue(HttpResponse response,
            String headerName, String headerValue) {
        Header[] headers = response.getHeaders(headerName);
        assertThat(headers, notNullValue());
        assertThat(headers.length, equalTo(1));
        assertThat(headers[0].getValue(), equalTo(headerValue));
    }
    
    private String getContentAsString(InputStream content) throws IOException {
        return IOHelper.getContentAsString(content, Charsets.UTF_8);
    }

}

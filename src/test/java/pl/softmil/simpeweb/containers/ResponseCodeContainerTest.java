package pl.softmil.simpeweb.containers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.*;

import pl.softmil.simpeweb.junit.StartStopSimpleWebServerRule;

public class ResponseCodeContainerTest {
    private ResponseCodeContainer responseCodeContainer = new ResponseCodeContainer(403);

    @Rule
    public StartStopSimpleWebServerRule startStopSimpleWebServerRule = new StartStopSimpleWebServerRule(
            32331, responseCodeContainer);

    @Test(timeout=1000)
    public void testReturnsRecorderStatusCode() throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(new HttpGet(
                "http://localhost:32331/"));
        
        assertThat(new Integer(response.getStatusLine().getStatusCode()), equalTo(403));
    }
}

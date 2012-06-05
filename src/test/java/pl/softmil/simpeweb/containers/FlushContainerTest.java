package pl.softmil.simpeweb.containers;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.*;

import pl.softmil.simpeweb.junit.StartStopSimpleWebServerRule;

public class FlushContainerTest {
    @Rule
    public StartStopSimpleWebServerRule startStopSimpleWebServerRule = new StartStopSimpleWebServerRule(
            32331, new FlushContainer());

    @Test(timeout = 1000)
    public void testName() throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.execute(new HttpGet("http://localhost:32331"));
    }

}

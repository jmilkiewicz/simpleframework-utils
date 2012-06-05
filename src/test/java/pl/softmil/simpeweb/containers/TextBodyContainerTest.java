package pl.softmil.simpeweb.containers;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import helper.IOHelper;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.*;

import pl.softmil.simpeweb.junit.StartStopSimpleWebServerRule;

import com.google.common.base.Charsets;

public class TextBodyContainerTest {
    private CompositeContainer compositeContainer = new CompositeContainer();
    @Rule
    public StartStopSimpleWebServerRule startStopSimpleWebServerRule = new StartStopSimpleWebServerRule(
            32331, compositeContainer);

    @Test
    public void shouldReturnRecorderBodyAndContentType() throws Exception {
        String bodyText = "<html><head></head></html>";
        String contentType = "text/html; charset=UTF-8";
        compositeContainer.withContainer(new TextBodyContainer().withBodyText(
                bodyText).withContentType(contentType));

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(new HttpGet(
                "http://localhost:32331"));

        assertThat(IOHelper.getContentAsString(response.getEntity()
                .getContent(), Charsets.UTF_8), equalTo(bodyText));
        assertThat(
                response.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue(),
                equalTo(contentType));
    }

}

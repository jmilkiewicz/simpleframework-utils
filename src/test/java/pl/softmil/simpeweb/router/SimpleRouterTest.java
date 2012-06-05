package pl.softmil.simpeweb.router;

import java.io.FileNotFoundException;
import java.net.URL;

import org.junit.*;
import org.junit.rules.ExpectedException;

import pl.softmil.simpeweb.containers.TextBodyContainer;
import pl.softmil.simpeweb.junit.StartStopSimpleWebServerRule;

public class SimpleRouterTest {
    private SimpleRouter simpleRouter = new SimpleRouter();

    @Rule
    public StartStopSimpleWebServerRule server = new StartStopSimpleWebServerRule(
            12234, simpleRouter);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testThrownsNotFoundWhenAccessingNotHandledPath() throws Exception {
        simpleRouter.addHandler("my", new TextBodyContainer());
        thrown.expect(FileNotFoundException.class);
        URL url = new URL("http://localhost:12234/dupa");
        url.getContent();
    }
    

}

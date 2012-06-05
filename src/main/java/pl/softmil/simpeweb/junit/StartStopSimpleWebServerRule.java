package pl.softmil.simpeweb.junit;

import java.io.IOException;
import java.net.*;

import org.junit.rules.ExternalResource;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.*;

public class StartStopSimpleWebServerRule extends ExternalResource{
    private final int port;
    private final Container handler;
    private Connection connection;

    public StartStopSimpleWebServerRule(int port, Container handler) {
        super();
        this.port = port;
        this.handler = handler;
    }

    protected void before() throws Throwable {
        connection = new SocketConnection(handler);
        SocketAddress address = new InetSocketAddress(port);
        connection.connect(address);
    }

    protected void after() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

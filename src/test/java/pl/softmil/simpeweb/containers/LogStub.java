package pl.softmil.simpeweb.containers;

import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

import java.util.*;

import org.hamcrest.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.*;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

public class LogStub extends TestWatcher {

    public enum LogLevel {
        TRACE(Level.TRACE), DEBUG(Level.DEBUG), INFO(Level.INFO), WARN(
                Level.WARN), ERROR(Level.ERROR);

        Level internalLevel;

        private LogLevel(Level level) {
            this.internalLevel = level;
        }
    }

    private final ListAppender<ILoggingEvent> listAppender = new ListAppender<ILoggingEvent>();

    private final LoggerContext lc = (LoggerContext) LoggerFactory
            .getILoggerFactory();

    private final Vector<Class> loggingSources = new Vector<Class>();

    private LogLevel level = LogLevel.TRACE;

    @Override
    protected void starting(Description description) {
        before();
    }

    @Override
    protected void finished(Description description) {
        after();
    }

    public void before() {
        resetLoggingContext();
        for (Class logSource : loggingSources) {
            addAppenderToType(logSource);
        }
        listAppender.start();
    }

    public void after() {
        listAppender.stop();
        resetLoggingContext();
    }

    public void record(LogLevel level) {
        this.level = level;
    }

    public void recordLoggingForObject(Object sut) {
        Class type = sut.getClass();
        recordLoggingForType(type);
    }

    public <T> void recordLoggingForType(Class<T> type) {
        loggingSources.add(type);
        addAppenderToType(type);
    }

    public void assertLoggingEntryMatches(Matcher<? super String> loggingStatement) {
        List<ILoggingEvent> list = listAppender.list;
        assertThat(list, Matchers.<ILoggingEvent>hasItem(hasProperty("formattedMessage", loggingStatement)));
    }

    public int size() {
        return listAppender.list.size();
    }

    private <T> void addAppenderToType(Class<T> type) {
        Logger logger = (Logger) LoggerFactory.getLogger(type);
        logger.addAppender(listAppender);
        logger.setLevel(level.internalLevel);
    }

    private void resetLoggingContext() {
        lc.reset();
    }
}
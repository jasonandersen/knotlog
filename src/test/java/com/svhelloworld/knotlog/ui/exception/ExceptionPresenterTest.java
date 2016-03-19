package com.svhelloworld.knotlog.ui.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.svhelloworld.knotlog.exceptions.ExceptionHandler;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Testing the {@link ExceptionPresenter} class.
 */
public class ExceptionPresenterTest extends BaseIntegrationTest {

    @Autowired
    private ExceptionPresenter presenter;

    @Autowired
    private ExceptionHandler handler;

    @Before
    public void setup() {
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    @After
    public void tearDown() {
        presenter.clearException();
    }

    @Test
    public void testDI() {
        assertNotNull(presenter);
    }

    @Test
    public void testExceptionCaught() throws InterruptedException {
        assertNull(presenter.getCurrentException());
        throwBackgroundException();
        assertNotNull(presenter.getCurrentException());
    }

    @Test
    public void testDisplayExceptionIsTrue() throws InterruptedException {
        assertFalse(presenter.getDisplayException());
        throwBackgroundException();
        assertTrue(presenter.getDisplayException());
    }

    @Test
    public void testGetHeader() throws InterruptedException {
        throwBackgroundException();
        assertEquals("An unexpected error happened.", presenter.getHeaderText());
    }

    @Test
    public void testGetExceptionDescription() throws InterruptedException {
        throwBackgroundException();
        assertEquals("OH. SHIT. BALLS.\nCheck the log files for further details.", presenter.getExceptionDescription());
    }

    @Test
    public void testStackTrace() throws InterruptedException {
        throwBackgroundException();
        assertNotNull(presenter.getStackTrace());
        assertFalse(presenter.getStackTrace().equals(""));
    }

    private void throwBackgroundException() throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                throw new UnsupportedOperationException("OH. SHIT. BALLS.");
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        Thread.sleep(100);
    }

}

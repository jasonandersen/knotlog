package com.svhelloworld.knotlog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Provides access to any lifecycle-managed beans in the dependency injection container.
 */
public class Context {

    private static Logger log = LoggerFactory.getLogger(Context.class);

    private static ClassPathXmlApplicationContext context;

    /**
     * Private constructor.
     */
    private Context() {
        //no instantiation for you!
    }

    /**
     * Returns a bean from the application context of a specified type
     * @param type
     * @return
     */
    public static <T> T getBean(Class<T> type) {
        return getContext().getBean(type);
    }

    /**
     * Shut down the application context.
     */
    public static void shutdown() {
        log.warn("shutting down application context");
        if (context != null) {
            context.close();
            context = null;
        }
    }

    /**
     * @return lazy loaded application context
     */
    private static ClassPathXmlApplicationContext getContext() {
        if (context == null) {
            initContext();
        }
        return context;
    }

    /**
     * Initialize application context
     */
    private static void initContext() {
        log.info("initializing application context");
        context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
    }

}

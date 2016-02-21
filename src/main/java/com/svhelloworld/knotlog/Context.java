package com.svhelloworld.knotlog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Singleton holding reference to a Spring {@link ApplicationContext} instance.
 */
public class Context {

    private static Logger log = LoggerFactory.getLogger(Context.class);

    private static ClassPathXmlApplicationContext context;

    private Context() {
        //no instantiation for you!
    }

    /**
     * Returns a bean from the application context of a specified type
     * @param type
     * @return
     */
    public static <T> T getBean(Class<T> type) {
        if (context == null) {
            initContext();
        }
        return context.getBean(type);
    }

    /**
     * Shut down the application context.
     */
    public static void shutdown() {
        log.warn("shutting down application context");
        if (context != null) {
            context.close();
        }
    }

    /**
     * Initialize application context
     */
    private static void initContext() {
        log.info("initializing application context");
        context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
    }

}

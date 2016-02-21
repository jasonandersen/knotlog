package com.svhelloworld.knotlog;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Singleton holding reference to a Spring {@link ApplicationContext} instance.
 */
public class Context {

    private static ApplicationContext context;

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
     * Initialize application context
     */
    private static void initContext() {
        context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
    }

}

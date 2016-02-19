package com.svhelloworld.knotlog;

import org.springframework.context.ApplicationContext;

/**
 * Singleton holding reference to a Spring {@link ApplicationContext} instance.
 */
public class Context {

    private static ApplicationContext context;

    private Context() {
        //no instantiation for you!
    }

    /**
     * Set an instance of the application context.
     * @param newContext
     */
    public static void setContext(ApplicationContext newContext) {
        context = newContext;
    }

    /**
     * Returns a bean from the application context of a specified type
     * @param type
     * @return
     */
    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

}

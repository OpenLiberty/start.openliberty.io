package io.openliberty.website.starter;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class NLS {
    
    public static ResourceBundle messages = ResourceBundle.getBundle("io.openliberty.website.starter.metadata.nls.resources.Messages");

    public static void loadBundle(Locale locale) {
    	messages = ResourceBundle.getBundle("io.openliberty.website.starter.metadata.nls.resources.Messages", locale);
    }
   
    public static String getMessage(String key, Object... args) {
        String message = messages.getString(key);
        return args.length == 0 ? message : MessageFormat.format(message, args);
    }
    
    public static ResourceBundle getMessages() {
        return messages;
    }
}
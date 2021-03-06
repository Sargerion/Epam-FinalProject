package edu.epam.project.model.util.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class MailBuilder used to loads mail properties.
 * @author Sargerion.
 */
class MailBuilder {

    private static final Logger logger = LogManager.getLogger();
    private static final String PROPERTIES_PATH = "/property/mail.properties";
    private static final Properties properties = new Properties();

    MailBuilder() {
        try (InputStream input = MailBuilder.class.getResourceAsStream(PROPERTIES_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
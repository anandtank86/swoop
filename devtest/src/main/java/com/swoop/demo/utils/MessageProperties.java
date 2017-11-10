/*
 * Copyright (c) 2017, WestJet (Swoop-ULCC) Inc
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.swoop.demo.utils;

import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author zo061e
 */
public class MessageProperties {

    static Logger log = LogManager.getLogger();

    private static MessageProperties instance = null;
    private Properties properties = null;

    /**
     *
     */
    public static final String MESSAGE_PROPERTIES_FILE = "messages.properties";

    /**
     *
     */
    protected MessageProperties() {
        try {
            properties = PropertiesUtil.getConfig(MESSAGE_PROPERTIES_FILE);
        } catch (Exception e) {
            // Throw an exception here
        }
    }

    /**
     * Return the singleton instance of the properties object
     *
     * @return instance
     */
    public static synchronized MessageProperties getInstance() {
        if (instance == null) {
            instance = new MessageProperties();
        }

        return instance;
    }

    /**
     * Return the property value for the given key
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String value = null;

        if (properties != null) {
            value = properties.getProperty(key);

            if (value == null) {
                log.error("Value not found for key - " + key);
                return null;
            } else {
                value = value.trim();
            }
        } else {
            log.error("Message properties file is not loaded");
        }

        return value;
    }
}

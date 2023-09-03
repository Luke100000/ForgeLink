package com.pequla.forgelink.utils;

import com.mojang.logging.LogUtils;
import lombok.Getter;
import org.slf4j.Logger;

import java.io.*;
import java.util.Properties;

@Getter
public class ConfigService {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static ConfigService instance;
    private String webhookUrl;
    private String guildId;
    private Boolean printServerEvents;
    private Boolean printDeathEvents;
    private Boolean printLoginEvents;
    private Boolean printAdvancementEvents;

    private ConfigService() {
        File config = new File("discord.properties");
        Properties prop = new Properties();
        if (!config.exists()) {
            try (OutputStream output = new FileOutputStream(config)) {
                prop.setProperty("discord.webhook", "<url-goes-here>");
                prop.setProperty("discord.guild", "797899107124510731");
                prop.setProperty("printServerEvents", "false");
                prop.setProperty("printDeathEvents", "false");
                prop.setProperty("printLoginEvents", "false");
                prop.setProperty("printAdvancementEvents", "false");
                prop.store(output, "ForgeLink configuration file");
            } catch (IOException e) {
                LOGGER.error("Failed to generate the configuration file");
                LOGGER.error(e.getMessage(), e);
            }
        }
        try (InputStream input = new FileInputStream(config)) {
            prop.load(input);
            this.webhookUrl = prop.getProperty("discord.webhook");
            this.guildId = prop.getProperty("discord.guild");
            this.printServerEvents = prop.getProperty("printServerEvents").equals("true");
            this.printDeathEvents = prop.getProperty("printDeathEvents").equals("true");
            this.printLoginEvents = prop.getProperty("printLoginEvents").equals("true");
            this.printAdvancementEvents = prop.getProperty("printAdvancementEvents").equals("true");
        } catch (IOException e) {
            LOGGER.error("Failed to read properties");
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static ConfigService getInstance() {
        if (instance == null) instance = new ConfigService();
        return instance;
    }
}

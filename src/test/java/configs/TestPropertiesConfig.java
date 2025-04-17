package configs;

import org.aeonbits.owner.Config;

import constants.Constants;

@Config.Sources({
    "classpath:${env}.properties",
    "classpath:default.properties"
})
public interface TestPropertiesConfig extends Config {

    @Key("baseUrl")
    @DefaultValue(Constants.BASE_URL)
    String getBaseUrl();

    @Key("username")
    String getUsername();

    @Key("password")
    String getPassword();
}

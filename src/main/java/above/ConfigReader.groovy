package above

import java.util.regex.Pattern

/**
 *      File /configuration.properties Reader
 */
class ConfigReader {

    private static Properties configFile = new Properties()

    static {
        FileInputStream file = new FileInputStream(System.getProperty("user.dir")
                                                    .replace('\\', '/')
                                                    .split(Pattern.quote("/src/"))[0] + "/configuration.properties")
        configFile.load(file)
        file.close()
    }

    synchronized static String get(String keyName) {
        return configFile.getProperty(keyName)
    }

    synchronized static Map getConfig() {
        return (Map) configFile
    }

}

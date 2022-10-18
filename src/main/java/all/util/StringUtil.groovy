package all.util

import above.RunWeb
import org.apache.commons.lang.StringEscapeUtils

/**
 *     Utility methods for Strings
 * @vdiachuk
 *     Generate, check
 */
class StringUtil {
    /**
     *     Random String with Length
     *     includeSpaces - boolean
     */
    synchronized static String randomString(int length, boolean includeSpaces = false) {
        RunWeb r = run()
        try {
            Random rd = new Random();
            String result = "";
            String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            if (includeSpaces) {
                abc += ' ';
            }
            (1..length).each {
                result += abc.charAt(rd.nextInt(abc.length()));
            }
            r.logDebug("Got Random string:$result")
            return result
        } catch (Exception e) {
            r.log "Can not generate random string length:$length. $e"
            return null
        }
    }

    /**
     *     Random String that contains only Digits, with Length
     *
     */
    synchronized static String getRandomDigits(int lenght, String digitsList = "0123456789") {
        RunWeb r = run()
        try {
            String result = "";
            Random rd = new Random();
            for (int i = 0; i < lenght; i++) {
                result += digitsList.charAt(rd.nextInt(digitsList.length()));
            }
            return result
        } catch (Exception e) {
            r.log "Can not generate random string with Digits:$lenght. $e"
            return null
        }
    }

    /**
     *     Check if string contains only digits
     *
     */
    static boolean isStringContainsOnlyDigits(String findIn) {
        if (!findIn) {
            return false
        }
        for (char letter in findIn) {
            if (letter < '0' || letter > '9') {
                return false
            }
        }
        return true
    }

    /**
     *     Check if string contains any letter from second string
     *
     */
    synchronized static boolean isStringContainsLetterFromOtherString(String findIn, String lettersToFind) {
        for (int i = 0; i < lettersToFind.length(); i++) {
            String letter = lettersToFind.charAt(i);
            if (findIn.contains(letter)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Formats a string with a map input
     * @param String to format with $keys
     * @param Map binding of values for each key [key: value]
     * @return Newly formatted string
     */
    static String formatWithMap(String stringToFormat, Map binding) {
        def engine = new groovy.text.SimpleTemplateEngine()
        def stringTemplate
        def errorMessage = null
        try {
            def template = engine.createTemplate(stringToFormat.stripIndent()).make(binding)
            stringTemplate = template.toString()
        } catch (e) {
            errorMessage = "$e\n\nBinding:\n$binding.\n\nString:\n$stringToFormat"
            stringTemplate = null
        }
        try {
            RunWeb r = run()
            if (!errorMessage) {
                r.logDebug "Created formatted string:\n" + stringTemplate
            } else {
                r.log(errorMessage, r.console_color_red)
            }
        } catch(ignored) {}
        return stringTemplate
    }

    /**
     * Combines {@link #formatWithMap} and {@link #fixQuotedNulls} for accurate SQL formatting.
     * @param Query to format with $keys
     * @param Map binding of values for each key [key: value]
     * @return Newly formatted string
     */
    static String formatQuery(String queryToFormat, Map binding) {
        return fixQuotedNulls(formatWithMap(queryToFormat, binding))
    }

    /**
     * Check is Given String contains only lower case letters/chars
     */
    synchronized static Boolean isStringContainsOnlyLowerCase(String stringToCheck) {
        formatQuery('', [:])
        return stringToCheck.every() { it.toLowerCase() == it }
    }

    synchronized static Boolean isStringContainsLetters(String stringToCheck) {
        return stringToCheck.find(/[a-zA-z]/)
    }

    /**
     * Find Line in Text that contains certain text
     * @return Line number
     * -1 if not exists, or one of elements null
     */
    synchronized static Integer findLineInText(String textToSearchIn, String searchingFor) {
        if ((!textToSearchIn) || (!searchingFor)) {
            return -1
        }
        def linesList = textToSearchIn.readLines()
        return linesList.findIndexOf { it.contains(searchingFor) }
    }

    /**
     * Fixes quoted 'null' to NULL for SQL queries. Case insensitive and quote type agnostic
     * Additional lookbehind is to account for long strings that use ''null'' inside of them
     * @param input
     * @return string with all types of 'null' replaced with NULL
     */
    def static fixQuotedNulls(String input) {
        return input.replaceAll(/(?i)(?<=[^'])["']null["']/, 'NULL')
    }

    /**
     * Returns a UUID without dashes as a string
     * @return example: 8e1b3ad486cd445dbadd1e5f7d145d83
     */
    static String randomUUID() {
        def uuid = UUID.randomUUID()
        def mostSig = uuid.getMostSignificantBits()
        def leastSig = uuid.getLeastSignificantBits()
        return (uuidDigits(mostSig >> 32, 8) +
                uuidDigits(mostSig >> 16, 4) +
                uuidDigits(mostSig, 4) +
                uuidDigits(leastSig >> 48, 4) +
                uuidDigits(leastSig, 12))
    }

    /**
     * Helper function for randomUUID. Copied from UUID, but original method is private
     * @param val
     * @param digits
     * @return
     */
    private static String uuidDigits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
}

package all

/**
 *      Text Tools
 */
class Text {


    /** Encode Base64 */
    synchronized static String encodeBase64(String text) {
        return text.bytes.encodeBase64().toString()
    }


    /** Decode Base64 */
    synchronized static String decodeBase64(String text) {
        return new String(text.decodeBase64())
    }

}

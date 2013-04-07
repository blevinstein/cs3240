import java.util.*;
import java.util.regex.*;
import java.util.zip.*;

/*
 * Message format:
 * {0x:y&y:z|asdf1234}
 * 0 is the sequence number (valid values: 0 and 1)
 * then there is a list of key-value pairs (or just tokens, which are treated as keys corresponding to empty values)
 * then a checksum
 */

public class Message {
    private int seqNum;
    private HashMap<String, String> map;

    // construct empty message
    public Message() {
        map = new HashMap<String, String>();
    }

    private int checksumOf(String s) {
        int cs = 0;
        for(int i=0; i<s.length(); i++)
            cs += s.charAt(i);
        return ~cs;
    }

    // construct the original message from a string
    public Message(String s) {
        map = new HashMap<String, String>();
        Pattern p = new Pattern("^\{(\d)(.+)\|(.+)\}$");
        Matcher m = p.matcher(s);
        if(!m.matches() || m.groupCount() < 3)
            return; // TODO: error here
        // get sequence number
        seqNum = Integer.parseInt(m.group(0));
        // calculate and compare checksum
        int checksum = checksumOf(m.group(0) + m.group(1));
        int messageChecksum = Integer.parseInt(m.group(2));
        if(checksum != messageChecksum)
            return; // TODO: error here
        // parse body
        String trimmed = m.group(1);
        String[] split = trimmed.split("&");
        for(int i=0; i<split.length; i++) {
            int colon = split[i].indexOf(":");
            if(colon == -1)
                map.put(split[i], "");
            else
                map.put(split[i].substring(0,colon), split[i].substring(colon+1));
        }
    }

    // return the underlying HashMap
    public HashMap<String, String> getMap() {
        return map;
    }

    // return the sequence number
    public int getSeqNum() {
        return seqNum;
    }

    // serialize message for transmission
    public String toString() {
        StringBuffer b = new StringBuffer("{");
        Iterator<String> keys = map.keySet().iterator();
        while(keys.hasNext()) {
            String k = keys.next();
            String v = map.get(k);
            if(v.length() > 0) {
                b.append(k);
                b.append(':');
                b.append(v);
            } else {
                b.append(k);
            }
            if(keys.hasNext())
                b.append('&');
        }
        return b.toString();
    }
}

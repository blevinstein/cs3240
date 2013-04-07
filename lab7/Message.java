import java.util.*;

public class Message {
    private HashMap<String, String> pairs;

    // construct empty message
    public Message() {
        pairs = new HashMap<String, String>();
    }

    // construct the original message from a string
    public Message(String s) {
        pairs = new HashMap<String, String>();
        if(s.charAt(0) != '{' || s.charAt(s.length()-1) != '}')
            return;
            // TODO: throw error here?
        String trimmed = s.substring(1,s.length()-1);
        String[] split = trimmed.split("&");
        for(int i=0; i<split.length; i++) {
            int colon = split[i].indexOf(":");
            if(colon == -1)
                add(split[i], "");
            else
                add(split[i].substring(0,colon), split[i].substring(colon+1));
        }
    }

    // return the underlying HashMap
    public HashMap<String, String> getPairs() {
        return pairs;
    }

    // add a key-value pair
    public void add(String key,String value) {
        pairs.put(key, value);
    }

    // serialize message for transmission
    public String toString() {
        StringBuffer b = new StringBuffer("{");
        Iterator<String> keys = pairs.keySet().iterator();
        while(keys.hasNext()) {
            String k = keys.next();
            String v = pairs.get(k);
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

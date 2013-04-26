package control.comm;
import java.util.*;

/**
 * Class for representing a message, which can be sent to or received from the base station.
 * 
 * Serialized Message format:
 * {0x:y&y:z|1234}
 * 0 is the sequence number (valid values: 0 and 1)
 * followed by a series of key:value pairs separated by ampersands (&)
 * followed by a bar (|) and a checksum
 */
public class Message {
    private int seqNum;                 // the sequence number of the message
    public ArrayList<String[]> pairs;   // the key-value pairs contained in the message
    

    /**
     * Constructs an empty message with the given sequence number.
     * @param seqNum The sequence number of the message.
     */
    public Message(int seqNum) {
        pairs = new ArrayList<String[]>();
        this.seqNum = seqNum;
    }

    /**
     * Internal method for calculating the "checksum" of a string.
     * @param s The string to calculate a checksum for.
     */
    private int checksumOf(String s) {
        int cs = 0;
        for(int i=0; i<s.length(); i++)
            cs += s.charAt(i);
        return ~cs;
    }

    /**
     * Constructs a message from a serialized representation received from the base station.
     * @param s The serialized representation of the message.
     */
    public Message(String s) {
        // check for correct delimiters
        assert(s.charAt(0) == '{' && s.charAt(s.length()-1) == '}');
        // parse sequence number
        seqNum = Integer.parseInt(s.charAt(1)+"");
        // parse body of message
        pairs = new ArrayList<String[]>();
        // parse and compare checksum
        int bar = s.indexOf('|');
        assert(bar != -1);
        String pairString = s.substring(2,bar);
        int checksum = checksumOf(seqNum + pairString);
        int messageChecksum = Integer.parseInt(s.substring(bar+1,s.length()-1));
        assert(checksum == messageChecksum);
        // parse pairs from body
        StringTokenizer st = new StringTokenizer(pairString, "&");
        while(st.hasMoreTokens()) {
            String pair = st.nextToken();
            int colon = pair.indexOf(':');
            if(colon == -1)
                pairs.add(new String[]{pair, null});
            else
                pairs.add(new String[]{pair.substring(0, colon), pair.substring(colon+1)});
        }
    }

    /**
     * @return The underlying ArrayList of key-value pairs which constitute the body of the message.
     */
    public ArrayList<String[]> getMap() {
        return pairs;
    }

    /**
     * @return The sequence number of the message.
     */
    public int getSeqNum() {
        return seqNum;
    }

    /**
     * Serializes the message into a format which can be sent to the base station.
     * @return A String containing the serialized representation of the message.
     */
    public String toString() {
        StringBuffer b = new StringBuffer();
        Iterator<String[]> pairsIterator= pairs.iterator();
        while(pairsIterator.hasNext()) {
            String[] pair = pairsIterator.next();
            String k = pair[0];
            String v = pair[1];
            if(v != null) {
                b.append(k);
                b.append(':');
                b.append(v);
            } else {
                b.append(k);
            }
            if(pairsIterator.hasNext())
                b.append('&');
        }
        int checksum = checksumOf(seqNum + b.toString());
        b.append("|" + checksum + "}");
        String fullmessage = "{" + seqNum + b.toString();
        return fullmessage;
    }
    
    public void setSeqNum(int seqNum) {
    	this.seqNum = seqNum;
    }
}


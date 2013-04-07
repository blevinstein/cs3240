import java.util.*;

/*
 * Message format:
 * {0x:y&y:z|asdf1234}
 * 0 is the sequence number (valid values: 0 and 1)
 * then there is a list of key-value pairs (or just tokens, which are treated as keys corresponding to empty values)
 * then a checksum
 */

public class Message {
    private int seqNum;
    public ArrayList<String[]> pairs;
    

    // construct empty message
    public Message(int seqNum) {
        pairs = new ArrayList<String[]>();
        this.seqNum = seqNum;
    }

    private int checksumOf(String s) {
        int cs = 0;
        for(int i=0; i<s.length(); i++)
            cs += s.charAt(i);
        return ~cs;
    }

    // construct the original message from a string
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

    // return the underlying HashMap
    public ArrayList<String[]> getMap() {
        return pairs;
    }

    // return the sequence number
    public int getSeqNum() {
        return seqNum;
    }

    // serialize message for transmission
    public String toString() {
        StringBuffer b = new StringBuffer("{");
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
        int checksum = checksumOf(b.toString());
        b.append('|' + checksum + '}');
        String fullmessage = "{" + seqNum + b.toString();
        return fullmessage;
    }
}


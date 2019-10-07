package replaceUtil;

import java.util.Comparator;

import org.eclipse.jetty.io.ByteArrayBuffer.CaseInsensitive;

public class MapKeyComparator implements Comparator<String>{
	@Override
	public int compare(String s1, String s2) {
		int len1 = s1.length();
		int len2 = s2.length();
	    if (len1<len2) {
	    	return 1;
	    }else if(len1>len2) {
	    	return -1;
	    }else {
	    	return s2.compareTo(s1);
	    }
	}

}

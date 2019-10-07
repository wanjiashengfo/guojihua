package replaceUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.UUID;

public class writeProperty {


	public static void writeProperty(LinkedHashSet<String> llist,Map map,String type) throws IOException {
		// TODO Auto-generated method stub
	    File file =new File("D:\\internation.properties");
	    int i = 0;
		Writer out =new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(out);
	    for (String s : llist) {
	    	i++;
	    	UUID uuid = UUID.randomUUID() ;
	    	String keyName = null;
	    	if ("java".equals(type)) {
	    		keyName = "internation.java.char"+i;
	    	}else if ("js".equals(type)) {
	    		keyName = "internation.javaScript.char"+i;
	    	}else if("html".equals(type)) {
	    		keyName = "internation.html.char"+i;
	    	}
	    			
	    	map.put(s, keyName);
	    	bw.write(keyName +" = "+s);
	    	bw.newLine();
	    	bw.flush();
	    }
		
	    bw.close();
	}

}

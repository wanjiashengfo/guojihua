package replaceUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Modify {
	private String path;
	private final String target;
	private final String newContent;

	public  Modify(String path, String target, String newContent) {
		// 操作目录。从该目录开始。该文件目录下及其所有子目录的文件都将被替换。
		this.path = path;
		// target:需要被替换、改写的内容。
		this.target = target;
		// newContent:需要新写入的内容。
		this.newContent = newContent;

		operation();
	}

	private void operation() {
		File file = new File(path);
		opeationDirectory(file);
	}

	public void opeationDirectory(File dir) {

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory())
				// 如果是目录，则递归。
				opeationDirectory(f);
			if (f.isFile())
				operationFile(f);
		}
	}

	public void operationFile(File file) {

		try {
			InputStream is = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			String filename = file.getName();
			// tmpfile为缓存文件，代码运行完毕后此文件将重命名为源文件名字。
			File tmpfile = new File(file.getParentFile().getAbsolutePath()
					+ "\\" + filename + ".tmp");

			BufferedWriter writer = new BufferedWriter(new FileWriter(tmpfile));

			boolean flag = false;
			String str = null;
			while (true) {
				str = reader.readLine();

				if (str == null)
					break;

				if (str.contains(target)) {
					str = str.replace(target, newContent);
					writer.write(str + "\n");

					flag = true;
				} else
					writer.write(str + "\n");
			}

			is.close();

			writer.flush();
			writer.close();

			if (flag) {
				file.delete();
				tmpfile.renameTo(new File(file.getAbsolutePath()));
			} else
				tmpfile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//代码测试：假设有一个test文件夹，test文件夹下含有若干文件或者若干子目录，子目录下可能也含有若干文件或者若干子目录（意味着可以递归操作）。
		//把test目录下以及所有子目录下（如果有）中文件含有"hi"的字符串行替换成新的"hello,world!"字符串行。
		String filePath = "D:\\Users\\liuchenxi\\git\\GLIMS718\\src\\com\\bgi\\genebank\\glims\\core\\product\\seqData\\action\\DipseqDataAction.java";
//		new Modify(filePath, "区域编码", "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww!");
//		autoReplace(filePath, "区域编码", "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww!");
	}

	
	 public static boolean autoReplace(String filePath, String oldstr, String newStr) {
		 if(filePath.contains("flowAddConfig.html")) {
			 System.out.println();
		 }
		    boolean change = false;
			File file = new File(filePath);
			Long fileLength = file.length();
			byte[] fileContext = new byte[fileLength.intValue()];
			FileInputStream in = null;
			PrintWriter out = null;
			try {
				in = new FileInputStream(filePath);
				in.read(fileContext);
				// 避免出现中文乱码
				String str = new String(fileContext, "utf-8");
				String newStrCon = str.replace(oldstr, newStr);
				out = new PrintWriter(filePath);
				out.write(newStrCon);
				if (!str.equals(newStrCon)){
					change = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					out.flush();
					out.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return change;
	 
		}

	public static boolean modify(String filePath, Map<String,String> map,String type) {
		// TODO Auto-generated method stub
	
		Map<String, String> resMap = sortMapByKey(map);
		boolean change = false;
		for(Map.Entry<String, String> entry : resMap.entrySet()){
		    String mapKey = entry.getKey();
		    String mapValue = entry.getValue();
		    boolean  c = false;
			if ("java".equals(type)) {
				 c = autoReplace(filePath,"\""+mapKey+"\"","MessageUtils.getMessageUtils().getMessage(\""+mapValue+"\")");
	    	}else if ("js".equals(type)) {
	    		 c = autoReplace(filePath,"\'"+mapKey+"\'","$.i18n.prop(\""+mapValue+"\")");
	    		 c = autoReplace(filePath,"\""+mapKey+"\"","$.i18n.prop(\""+mapValue+"\")");
	    		 c = autoReplace(filePath,">"+mapKey+"<",">'+$.i18n.prop(\""+mapValue+"\")+          '<");
	    		 c = autoReplace(filePath,"\'"+mapKey+"<","$.i18n.prop(\""+mapValue+"\")+'<");
	    		 c = autoReplace(filePath,">"+mapKey+"\'",">'+$.i18n.prop(\""+mapValue+"\")");
	    		 c = autoReplace(filePath,">"+mapKey+"\"",">\"+$.i18n.prop(\""+mapValue+"\")");
	    		 updateWrongReplace(filePath);
	    	}else if("html".equals(type)) {
	    		 c = autoReplace(filePath,"\""+mapKey+"\"","\"<@spring.message \""+mapValue+"\"/>\"");
	    		 c = autoReplace(filePath,"\'"+mapKey+"\'","\'<@spring.message \""+mapValue+"\"/>\'");
//	    		 c = autoReplace(filePath,">"+mapKey+"<","><@spring.message \""+mapValue+"\"/><");
//	    		 c = autoReplace(filePath,">"+mapKey+"\n","><@spring.message \""+mapValue+"\"/>\n");
//	    		 c = autoReplace(filePath,">"+mapKey+"\r\n","><@spring.message \""+mapValue+"\"/>\r\n");
//	    		 c = autoReplace(filePath,"\t"+mapKey+"<","\t<@spring.message \""+mapValue+"\"/><");
//	    		 c = autoReplace(filePath,"\t"+mapKey+"\n","\t<@spring.message \""+mapValue+"\"/>\n");
//	    		 c = autoReplace(filePath,"\t"+mapKey+"\r\n","\t<@spring.message \""+mapValue+"\"/>\r\n");
	    		  autoReplace(filePath,mapKey+"\n","<@spring.message \""+mapValue+"\"/>\n");
	    		  autoReplace(filePath,mapKey+"\r\n","<@spring.message \""+mapValue+"\"/>\r\n");
	    		  autoReplace(filePath,mapKey+"<","<@spring.message \""+mapValue+"\"/><");
	    	}
		   
		    if(c) {
		    	change = true;
		    }
		    System.out.println(filePath);
		}
		for (Map.Entry<String, String> entry : resMap.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		return change;
	}
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
		sortMap.putAll(map);
		return sortMap;
	}

	private static void updateWrongReplace(String filePath) {
		// TODO Auto-generated method stub
		File file = new File(filePath);
		Long fileLength = file.length();
		byte[] fileContext = new byte[fileLength.intValue()];
		FileInputStream in = null;
		PrintWriter out = null;
		String lineTxt = null;
		List<File> tempList = Scan.scanFilesWithRecursion(filePath,"");
		try {
			in = new FileInputStream(filePath);
			in.read(fileContext);
			// 避免出现中文乱码
			String str = new String(fileContext, "utf-8");
			String publicClass = null;
			String packageLine = null;
			if(file.isFile() && file.exists()) {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				
				while ((lineTxt = br.readLine()) != null) {
					if((lineTxt.endsWith(">\";")||lineTxt.endsWith(">\" +")||lineTxt.endsWith(">\")")||lineTxt.endsWith("n\" +")||lineTxt.endsWith(">\");")||lineTxt.endsWith(">\"+"))&&lineTxt.contains("'+$.i18n.prop")) {
						String newStrCon = lineTxt.replace("'+$.i18n.prop", "\"+$.i18n.prop");
						newStrCon = newStrCon.replace(")+          '<", ")+          \"<");
						str = str.replace(lineTxt, newStrCon);
					}
				}
			}
		
			out = new PrintWriter(filePath);
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addAutowired(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		if(filePath.contains("TaskHandlerBase.java")) {
			System.out.println("ppppp");
		}
		File file = new File(filePath);
		Long fileLength = file.length();
		byte[] fileContext = new byte[fileLength.intValue()];
		FileInputStream in = null;
		PrintWriter out = null;
		String lineTxt = null;
		List<File> tempList = Scan.scanFilesWithRecursion(filePath,"");
		try {
			in = new FileInputStream(filePath);
			in.read(fileContext);
			// 避免出现中文乱码
			String str = new String(fileContext, "utf-8");
			String publicClass = null;
			String packageLine = null;
//			String autowiredLine = "import org.springframework.beans.factory.annotation.Autowired;";
			if(file.isFile() && file.exists()) {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				
				while ((lineTxt = br.readLine()) != null) {
					if(lineTxt.startsWith("package ")) {
						packageLine = lineTxt;
					}
					
//					if(lineTxt.contains(" class ")) {
//						publicClass = lineTxt;
//					}
				}
			}
//			String newStrCon = str.replace(publicClass, publicClass+"\r\n    @Autowired\r\n    private MessageUtils messageUtils;");
//			if(!str.contains(autowiredLine)) {
//				newStrCon = newStrCon.replace(packageLine, packageLine+"\r\n"+autowiredLine);
//			}
			String newStrCon = str.replace(packageLine, packageLine+"\r\n \r\nimport com.bgi.lims.common.utils.MessageUtils; ");
			if(filePath.contains("TaskHandlerBase.java")) {
				System.out.println("ppppp");
			}
			out = new PrintWriter(filePath);
			out.write(newStrCon);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}

	public static void modifyTheLeft(String filePath, Map<String,String> map, String type) {
		// TODO Auto-generated method stub
		for(Map.Entry<String, String> entry : map.entrySet()){
		    String mapKey = entry.getKey();
		    String mapValue = entry.getValue();
		    if("html".equals(type)) {
	    		  autoReplace(filePath,mapKey+"\n","<@spring.message \""+mapValue+"\"/>\n");
	    		  autoReplace(filePath,mapKey+"\r\n","<@spring.message \""+mapValue+"\"/>\r\n");
	    		  autoReplace(filePath,mapKey+"<","<@spring.message \""+mapValue+"\"/><");
	    	}
		   
		  
		    System.out.println(filePath);
		}
	}



	
}

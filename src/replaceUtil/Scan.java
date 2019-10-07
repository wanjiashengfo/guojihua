package replaceUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Scan {
	private static ArrayList<File> scanFiles = new ArrayList<File>();

	/**linkedList实现**/
	private static LinkedList<File> queueFiles = new LinkedList<File>();
	
	public static void main(String[] args) throws IOException {
//		String filePath = "D:\\Users\\liuchenxi\\git\\bgi-ai-lims20190916\\src\\main\\java\\com\\bgi\\lims\\common\\exception\\BGIExceptionHandler.java";
//		Modify.addAutowired(filePath);
		replaceJava();
	}
	
	
	public  static  void  replaceJava() throws IOException{
        Map map =new HashMap<String,String >();
	    //读取目录HashSet
		LinkedHashSet<String> llist =  new LinkedHashSet<String>();
	    String filePath = "D:\\Users\\liuchenxi\\git\\bgi-ai-lims20190916\\src\\main\\java\\com\\bgi";
//	    String filePath = "D:\\Users\\liuchenxi\\git\\bgi-ai-lims20190916\\src\\main\\java\\com\\bgi\\lims\\common\\aspect";
	    List<File> tempList = Scan.scanFilesWithRecursion(filePath,"");
	    FindAllWords.findJava(llist, filePath);//把需要替换的中文句子找出来，放到llist里面，并去重复

	    writeProperty.writeProperty(llist,map,"java");//把llist里面的句子生成键值对，放到一个map中，并生成对应的properties文件
	    for (int i = 0; i < tempList.size(); i++) {
	        if (tempList.get(i).isFile()) {
	        	boolean change = false;
	            File tempFile = tempList.get(i);
	            String fileName = tempFile.getName();
	            int index = fileName.lastIndexOf('.');
	            String ext = fileName.substring(index+1, fileName.length());
	            if("java".equals(ext)) {
	            	change = Modify.modify(tempList.get(i).getAbsolutePath(),map,"java");
	            }
	            if(change) {
	            	Modify.addAutowired(tempList.get(i).getAbsolutePath());
	            }
	        }
	    }
	    System.out.println(";;;;");
	}
	public  static  void  replaceJavaScript() throws IOException{
		Map map =new HashMap<String,String >();
		//读取目录HashSet
		LinkedHashSet<String> llist =  new LinkedHashSet<String>();
		String filePath = "D:\\Users\\liuchenxi\\git\\bgi-ai-lims20190916\\src\\main\\resources\\static\\js\\modules";
		
		List<File> tempList = scanFilesWithRecursion(filePath,"");
		FindAllWords.findJavaScript(llist, filePath);
		System.out.println("该目录下对象个数："+tempList.size());
		 writeProperty.writeProperty(llist,map,"js");
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).isFile()) {
				
				File tempFile = tempList.get(i);
				String fileName = tempFile.getName();
				int index = fileName.lastIndexOf('.');
				String ext = fileName.substring(index+1, fileName.length());
				if("js".equals(ext)) {
//					readAllJAVASCRIPT(tempList.get(i).getAbsolutePath(),llist);
					Modify.modify(tempList.get(i).getAbsolutePath(),map,"js");
				}
				
			}
		}
		System.out.println(";;;;");
	}
	public  static  void  replaceHtml() throws IOException{
		Map map =new HashMap<String,String >();
		//读取目录HashSet
		LinkedHashSet<String> llist =  new LinkedHashSet<String>();
		String filePath = "D:\\Users\\liuchenxi\\git\\bgi-ai-lims20190916\\src\\main\\resources\\views";
		
		List<File> tempList = scanFilesWithRecursion(filePath,"");
		FindAllWords.findHtml(llist, filePath);
		System.out.println("该目录下对象个数："+tempList.size());
		writeProperty.writeProperty(llist,map,"html");
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).isFile()) {
				
				File tempFile = tempList.get(i);
				String fileName = tempFile.getName();
				int index = fileName.lastIndexOf('.');
				String ext = fileName.substring(index+1, fileName.length());
				if("html".equals(ext)) {
					Modify.modify(tempList.get(i).getAbsolutePath(),map,"html");
				}
			}
		}
		
		System.out.println(";;;;");
	}
	
	public static List<File> scanFilesWithRecursion(String folderPath,String exclude) {
		
		ArrayList<String> dirctorys = new ArrayList<String>();
		File directory = new File(folderPath);
	
			if(directory.isDirectory()){
				File [] filelist = directory.listFiles();
				for(int i = 0; i < filelist.length; i ++){
				/**如果当前是文件夹，进入递归扫描文件夹**/
					if(filelist[i].isDirectory()&&(!exclude.equals(filelist[i].getAbsolutePath()) )){
					dirctorys.add(filelist[i].getAbsolutePath());
					/**递归扫描下面的文件夹**/
						scanFilesWithRecursion(filelist[i].getAbsolutePath(),exclude);
					}
					/**非文件夹**/
					else{
					scanFiles.add(filelist[i]);
					}
		        }
		   }
		return scanFiles;
    }
	

	public static String readAllJAVA(String filePath,LinkedHashSet<String> llist) {
		String lineAll = null;
		String lineTxt = null;
		int i = 1;
		try {
			File file = new File(filePath);
			if(file.isFile() && file.exists()) {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				while ((lineTxt = br.readLine()) != null) {
					if (CharUtil.isChineseByREG(lineTxt)){
						lineTxt = lineTxt.trim();
						if(lineTxt.contains("\"")&&(!lineTxt.contains("*"))&&(!lineTxt.contains("//"))&&(!lineTxt.contains("@"))) {
							String[] strLines = lineTxt.split("\"");
							for(String s:strLines) {
								if (CharUtil.isChineseByREG(s)) {
									llist.add(s);
									System.out.println(s);  
								}
								
							}
						}
					}
					
				}
				
			}
		}catch(Exception e) {
			
		}finally {
			
		}
		return lineTxt;
	}
	public static String readAllHTML(String filePath,LinkedHashSet<String> llist) {
		String lineAll = null;
		String lineTxt = null;
		int i = 1;
		try {
			File file = new File(filePath);
			if(file.isFile() && file.exists()) {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				while ((lineTxt = br.readLine()) != null) {
					if (CharUtil.isChineseByREG(lineTxt)){
						lineTxt = lineTxt.trim();
						if((!lineTxt.contains("<!--"))&&(!lineTxt.contains("//"))&&(!lineTxt.startsWith("/*"))&&(!lineTxt.startsWith("+"))) {
							System.out.println("-----------");  
							System.out.println(lineTxt);  
							String[] strLines = lineTxt.split("\"|\'|>|<");
							for(String s:strLines) {
								if (CharUtil.isChineseByREG(s)) {
									llist.add(s);
									System.out.println(s);  
								}
								
							}
						}
					}
					
				}
				
			}
		}catch(Exception e) {
			
		}finally {
			
		}
		return lineTxt;
	}
	public static String readAllJAVASCRIPT(String filePath,LinkedHashSet<String> llist) {
		String lineAll = null;
		String lineTxt = null;
		int i = 1;
		try {
			File file = new File(filePath);
			if(file.isFile() && file.exists()) {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				while ((lineTxt = br.readLine()) != null) {
					
					if (CharUtil.isChineseByREG(lineTxt)){
						lineTxt = lineTxt.trim();
						if((!lineTxt.contains("*"))&&(!lineTxt.contains("//"))) {
							if(true) {
							String[] strLines = lineTxt.split("\"|\'|<|>");
							for(String s:strLines) {
								if (CharUtil.isChineseByREG(s)) {
									llist.add(s);
									System.out.println(lineTxt);  
									System.out.println(s);  
									
								}
							}
						}
					}
				}
					
			}
			}
		}
		catch(Exception e) {
			
		}finally {
			
		}
		return lineTxt;
	}
}


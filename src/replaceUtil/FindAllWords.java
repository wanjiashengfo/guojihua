package replaceUtil;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;

public class FindAllWords {
	public static void main(String[] args) throws Exception {
//		LinkedHashSet<String> llist =  new LinkedHashSet<String>();
//		findJava(llist,"D:\\Users\\liuchenxi\\git\\bgi-ai-lims0912\\src\\main\\java\\com\\bgi");
//		findJavaScript(llist, "D:\\Users\\liuchenxi\\git\\bgi-ai-lims0912\\src\\main\\resources\\static\\js\\modules");
//		findHtml(llist,"D:\\Users\\liuchenxi\\git\\bgi-ai-lims0912\\src\\main\\resources\\views");
//		findJava(llist,"D:\\Users\\liuchenxi\\git\\bgi-ai-lims20190916\\src\\main\\java\\com\\bgi");
//		findJavaScript(llist, "D:\\Users\\liuchenxi\\git\\bgi-ai-lims20190916\\src\\main\\resources\\static\\js\\modules");
//		findHtml(llist,"D:\\Users\\liuchenxi\\git\\bgi-ai-lims20190916\\src\\main\\resources\\views");
//		Excel.writeExcel(llist,  "D:\\tiquwords.xls");
//		System.exit(0);
		
		LinkedHashSet<String> llist =  new LinkedHashSet<String>();
		findJava(llist,"D:\\Users\\Administrator.SD-20190824BKFS\\git\\banzidonghua\\bgi-ai-lims0912\\src");
	}
	public static void findJava(LinkedHashSet<String> llist, String filePath) {
		// TODO Auto-generated method stub
//		 String filePath = "D:\\Users\\liuchenxi\\git\\bgi-ai-lims0912\\src\\main\\java\\com\\bgi";
		   List<File> tempList = Scan.scanFilesWithRecursion(filePath,"");
		    System.out.println("该目录下对象个数："+tempList.size());
		    for (int i = 0; i < tempList.size(); i++) {
		        if (tempList.get(i).isFile()) {

		            File tempFile = tempList.get(i);
		            String fileName = tempFile.getName();
		            int index = fileName.lastIndexOf('.');
		            String ext = fileName.substring(index+1, fileName.length());
		            if("java".equals(ext)) {
		            	Scan.readAllJAVA(tempList.get(i).getAbsolutePath(),llist);
		            }
		            
		        }
		    }
	}
	public static void findHtml(LinkedHashSet<String> llist,String filePath) {
		// TODO Auto-generated method stub
//		String filePath = "D:\\Users\\liuchenxi\\git\\bgi-ai-lims0912\\src\\main\\resources\\views";
		List<File> tempList = Scan.scanFilesWithRecursion(filePath,"");
		System.out.println("该目录下对象个数："+tempList.size());
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).isFile()) {
				
				File tempFile = tempList.get(i);
				String fileName = tempFile.getName();
				int index = fileName.lastIndexOf('.');
				String ext = fileName.substring(index+1, fileName.length());
				if("html".equals(ext)) {
					Scan.readAllHTML(tempList.get(i).getAbsolutePath(),llist);
				}
				
			}
		}
	}
	public static void findJavaScript(LinkedHashSet<String> llist,String filePath) {
		// TODO Auto-generated method stub
//		String filePath = "D:\\Users\\liuchenxi\\git\\bgi-ai-lims0912\\src\\main\\resources\\static\\js\\modules";
		List<File> tempList = Scan.scanFilesWithRecursion(filePath,"");
		System.out.println("该目录下对象个数："+tempList.size());
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).isFile()) {
				
				File tempFile = tempList.get(i);
				String fileName = tempFile.getName();
				int index = fileName.lastIndexOf('.');
				String ext = fileName.substring(index+1, fileName.length());
				if("js".equals(ext)) {
					Scan.readAllJAVASCRIPT(tempList.get(i).getAbsolutePath(),llist);
				}
				
			}
		}
	}
}

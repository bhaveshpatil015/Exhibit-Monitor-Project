import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
	static Scanner sc = new Scanner(System.in);
	private static int noofFiles=0;
	//private static Set<String> fileNames = new HashSet<String>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("D:/File/Demo");
		if(f.isDirectory()) {
			String[] nameofFiles = f.list();
			setNoofFiles(nameofFiles.length);
			File[] fileList = f.listFiles();
			for(File name: fileList) {
				MonitorConstants.fileNames.add(name.getName());
			}
			
			while(true) {
				File directory = new File("D:/File/Demo");
					String[] newNameofFiles = directory.list();
					int newNoofFiles = newNameofFiles.length;
					//System.out.println("while: "+newNoofFiles);
					//File[] newFileList = directory.listFiles();
					if(newNoofFiles > getNoofFiles()) {
						setNoofFiles(newNoofFiles);
						//System.out.println("In while if : "+Main.getNoofFiles());
						for(String newFile: newNameofFiles) {
								if(!MonitorConstants.fileNames.contains(newFile)) {
									//fileList = directory.listFiles();
									MonitorConstants.fileNames.add(newFile);
									File fileDir = new File("D:/File/Demo/"+newFile);
									WorkerThreadClass worker = new WorkerThreadClass(fileDir);
									worker.start();				
									try {
										Thread.sleep(15000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
						}
						
					}else {
						System.out.println("File not arrived");
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}	
				}
			}
		}
	}
	public static int getNoofFiles() {
		return noofFiles;
	}
	public static void setNoofFiles(int noofFiles) {
		Main.noofFiles = noofFiles;
	}

}

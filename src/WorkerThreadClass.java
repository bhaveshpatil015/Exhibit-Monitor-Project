import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

//import org.apache.commons.io.FileUtils;

public class WorkerThreadClass extends Thread {
	File file;
	static {
		Properties props = new Properties();
	}

	public WorkerThreadClass(File file) {
		super();
		this.file=file;
	}
	public void run() {
		System.out.println("New file "+file.getName()+" is arrived..");
		//System.out.println(file.getName());
		
		try {
			synchronized(this) {
			if(isInTime(file) && isValid(file) && isDuplicate(file)) {
				System.out.println("This file passed 3 conditions successfully, now it is ready for process..");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ProcessorThreadClass processor = new ProcessorThreadClass(file);
				processor.start();
			}else{
				file.delete();
				MonitorConstants.fileNames.remove(file.getName());
				Main.setNoofFiles(Main.getNoofFiles() - 1);
				System.out.println("File deleted..");
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean isDuplicate(File file2) throws FileNotFoundException,IOException {
		// TODO Auto-generated method stub
		System.out.println("3rd check-->Checking the file  is duplicate or not..");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String incomingFileName = file2.getName();
		//System.out.println(incomingFileName);
		File incommingFile = new File("D:/File/Demo/"+incomingFileName);
		//System.out.println(incommingFile);
		File f = new File("D:/File/Demo");
		File[] fileList = f.listFiles();
		for(File file: fileList) {
			//System.out.println("file-->"+file);
			//System.out.println("incommingFile-->"+incommingFile);
			if(!file.equals(incommingFile)) {
				//System.out.println(file+" == "+incommingFile);
				
				if(isEqual(file.toPath(), incommingFile.toPath())) {
					System.out.println("This File "+incommingFile.getName()+" is Duplicate..");
					return false;
				}
				/*
				if(areEqual(file, incommingFile)) {
					System.out.println("This File "+incommingFile.getName()+" is Duplicate..");
					return false;
				}*/
			}
			
		}
		System.out.println("File is not duplicate");
		return true;
	}
	
	private static boolean isValid(File file2) throws FileNotFoundException, IOException {
		System.out.println("2nd check-->Checking the file name  is valid or not..");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String incomingFileName = file2.getName();
		Properties props = new Properties();
		props.load(new FileReader("fileNameTime.properties"));
		Set fileNameTimeStampPairs = props.entrySet();
		for(Object pair: fileNameTimeStampPairs) {
			//System.out.println("Name of file--> "+((Map.Entry)pair).getKey());
			if(incomingFileName.equals(((Map.Entry)pair).getKey())){
				System.out.println("File Name is Valid..");
				return true;
			}
		}
		
		//String expectedTime = props.getProperty(incomingFileName);
		//System.out.println(expectedTime);
		System.out.println("File Name is not Valid..");		
		return false;
	}
	@SuppressWarnings("deprecation")
	private boolean isInTime(File file2) throws FileNotFoundException, IOException, NumberFormatException {
		System.out.println("1st check-->Checking the file is in time or not..");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long incomingFileTime = file2.lastModified();
		String incomingFileName = file2.getName();
		//System.out.println(incomingFileName);
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(incomingFileTime);
		Date date = cal.getTime();
		int incomingFileHours = date.getHours();
		int incomingFileMinutes = date.getMinutes();
		//System.out.println(incomingFileHours);
		//System.out.println(incomingFileMinutes);
		//System.out.println(incomingFileMinutes);
		Properties props = new Properties();
		props.load(new FileReader("fileNameTime.properties"));
		String expectedTime = props.getProperty(incomingFileName);
		String[] hoursAndMinutes = expectedTime.split(":", 2);
		int hours = Integer.parseInt(hoursAndMinutes[0]);
		int minutes = Integer.parseInt(hoursAndMinutes[1]);
		if(incomingFileHours <= hours && incomingFileMinutes <= minutes) {
			System.out.println("File is in time..");
			return true;
		}
		System.out.println("File is late..");
		return false;
	}
	
	private boolean isEqual(Path firstFile, Path secondFile) {
        try {
            if (Files.size(firstFile) != Files.size(secondFile)) {
                return false;
            }
 
            byte[] first = Files.readAllBytes(firstFile);
            byte[] second = Files.readAllBytes(secondFile);
            //System.out.println(first);
            //System.out.println(second);
            return Arrays.equals(first, second);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
	
}

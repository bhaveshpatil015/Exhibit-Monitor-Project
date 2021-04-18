import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ProcessorThreadClass extends Thread {
	File file;


	public ProcessorThreadClass(File file) {
		super();
		this.file=file;
	}
	public void run() {
		try {
			if(isInFormat(file)) {
				System.out.println("Thank you for processing your file: "+file.getName());
			}else {
				System.out.println("Some lines are not matching with the format.\n That is appended in error.txt file successfully..");
			}
		} catch (IOException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isInFormat(File file2) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		boolean valueForoReturn = false;
		int[] maxLength = new int[MonitorConstants.sizeOfFields];
		int index=0;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();	
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc;
		doc = docBuilder.parse("format.xml");
		Node rootElement = doc.getDocumentElement();
		
		//System.out.println(rootElement.getNodeName());
		
		/*NamedNodeMap attMap = rootElement.getAttributes();
		for(int j=0; j< attMap.getLength(); j++) {
			Node attNode = attMap.item(j);
			String attName = attNode.getNodeName();
			String attValue = attNode.getNodeValue();
			System.out.println(attName+" = "+attValue);
		}*/
		
		NodeList childElement = rootElement.getChildNodes();
		for(int i=0; i<childElement.getLength(); i++) {
			Node child = childElement.item(i);
			if(child.getNodeType()==Node.ELEMENT_NODE) {
				String childNodeName = child.getNodeName();
				//System.out.println("Child Name:"+childNodeName);
				NodeList childOfChiledElement = child.getChildNodes();
				for(int k=0; k<childOfChiledElement.getLength(); k++) {
					Node childOFChild = childOfChiledElement.item(k);
					if(childOFChild.getNodeType()==Node.ELEMENT_NODE) {
						String childOfChildNodeName = childOFChild.getNodeName();
						//System.out.println("Child Name: "+childOfChildNodeName);
						NamedNodeMap attMapR = childOFChild.getAttributes();
						for(int j=0; j< attMapR.getLength(); j++) {
							Node attNode = attMapR.item(j);
							String attName = attNode.getNodeName();
							String attValue = attNode.getNodeValue();
							
							if(attName.equals("maxlength")) {
								maxLength[index++]=Integer.parseInt(attValue);
								//System.out.println(attName+" = "+attValue);
							}
						}
					}
				}
				//NamedNodeMap attMapR = child.getAttributes();
				//for(int j=0; j< attMapR.getLength(); j++) {
				//	Node attNode = attMapR.item(j);
				//	String attName = attNode.getNodeName();
				//	String attValue = attNode.getNodeValue();
				//	System.out.println(attName+" = "+attValue);
				}
		}
		
		String incomingFileName = file.getName();
		File f = new File("D:/File/Demo/"+incomingFileName);
		FileReader fr;
		int lineCount=0;
		fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line=null;
		while((line = br.readLine()) != null) {
			lineCount++;
			String[] fieldValues = line.split(", ",3);
			if(fieldValues.length == MonitorConstants.sizeOfFields) {
				if(fieldValues[0].length()<= maxLength[0] && fieldValues[1].length()<= maxLength[1] && fieldValues[2].length()<= maxLength[2]) {
					valueForoReturn=true;
					File newFile = new File("D:/File/out.txt");
					FileWriter fw = new FileWriter(newFile, true);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.append("Record: " +line+"\n");
					bw.close();
					fw.close();
					
				}else {
					System.out.println("Field values exceeds its limits..");
					writeInErrorTxtFile(file2, lineCount, line);
					valueForoReturn=false;
					
				}	
			}else {
				System.out.println("File contains less then 3 fields..");
				writeInErrorTxtFile(file2, lineCount, line);
				valueForoReturn=false;
			}
			}
			br.close();
			fr.close();
		//File newFile = new File("D:/File/newDemo.txt");
		//FileWriter fw = new FileWriter(newFile);
		//BufferedWriter bw = new BufferedWriter(fw);
		
			//bw.write(line+"\n");
		//bw.close();
		//fw.close();
		return valueForoReturn;
	}
	private void writeInErrorTxtFile(File file, int lineCount, String line) throws IOException {
		// TODO Auto-generated method stub
		File newFile = new File("D:/File/error.txt");
		FileWriter fw = new FileWriter(newFile, true);
		BufferedWriter bw = new BufferedWriter(fw);
		String data = "File name: "+file.getName()+", Line no: "+lineCount+", Data: "+line+"\n";
		bw.append(data);
		bw.close();
		fw.close();
		
	}
}


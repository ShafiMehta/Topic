package resources.utilFiles;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.testng.Reporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mercury.qualitycenter.otaclient.ClassFactory;
import com.mercury.qualitycenter.otaclient.IAttachment;
import com.mercury.qualitycenter.otaclient.IAttachmentFactory;
import com.mercury.qualitycenter.otaclient.IBug;
import com.mercury.qualitycenter.otaclient.IBugFactory;
import com.mercury.qualitycenter.otaclient.IExtendedStorage;
import com.mercury.qualitycenter.otaclient.ITDConnection;



@SuppressWarnings("unused")
public class DefectDetailsDriver {

	public static String Project = "Web2.0";
	public static String DetectedBy = "chphilip";
	public static String Status = "New";
	public static String Summary = "";
	public static String AssignedTo = "";
	public static String Severity = ""; 
	public static String StepsToReproduce="";

	public static DocumentBuilderFactory documentBuilderFactory;
	public static DocumentBuilder documentBuilder;
	public static Document document;
	public static FileOutputStream isod = null;
	public static File xmlFile = null;	
	public static String errorDetail_Name = null;
	public static Element root = null;
	public static boolean value = false;



	public static DocumentBuilderFactory documentBuilderFactory1;
	public static DocumentBuilder documentBuilder1;
	public static Document document1;
	public static FileOutputStream isod1 = null;
	public static File xmlFile1 = null;	
	public static String errorDetail_Name1 = null;
	public static Element root1 = null;
	public static boolean value1 = false;










	/*public static void RaiseDefect(ArrayList<String> DefectDetails,String ScreenshotPath){

		
		Calendar now = Calendar.getInstance();
		String currentDate = (now.get(Calendar.MONTH)+1) + "/" + now.get(Calendar.DATE) + "/" + now.get(Calendar.YEAR);
		System.out.println("Current Date ="+currentDate);
		
		
		Summary = DefectDetails.get(0);
		StepsToReproduce = DefectDetails.get(1);
		//AssignedTo = DefectDetails.get(2);
		AssignedTo ="mnalawad";
		Severity = DefectDetails.get(3);

		ITDConnection itdc= ClassFactory.createTDConnection();    

		System.out.println(itdc.connected());   
		itdc.initConnectionEx(Retrieve_EnvVar.RetrieveQCDetails("QC_Path"));
		System.out.println(itdc.connected());    
		itdc.connectProjectEx(Retrieve_EnvVar.RetrieveQCDetails("QC_Project"), Retrieve_EnvVar.RetrieveQCDetails("QC_Domain"), Retrieve_EnvVar.RetrieveQCDetails("QC_UserName"), "*Kerala231"); 
		System.out.println(itdc.connected());
		System.out.println("Logged into QC");
		IBugFactory bugFactory = (IBugFactory) itdc.bugFactory().queryInterface(IBugFactory.class);
		IBug Big1 = (bugFactory.addItem(null)).queryInterface(IBug.class);
	
		Big1.field("BG_DETECTED_BY",Retrieve_EnvVar.RetrieveQCDetails("QC_DetectedBy"));		//ReportedBy
		Big1.field("BG_DETECTION_DATE",currentDate);	//ReporterDate
		Big1.field("BG_DETECTION_VERSION",Retrieve_EnvVar.RetrieveQCDetails("QC_Environment"));	//Environment	
		//Big1.field("BG_PRIORITY","P5");					//Priority
		Big1.field("BG_PROJECT",Retrieve_EnvVar.RetrieveQCDetails("QC_Track"));				//Track
		Big1.field("BG_RESPONSIBLE",Retrieve_EnvVar.RetrieveQCDetails("QC_AssignedTo"));		//AssignedTo
		Big1.field("BG_SEVERITY",Severity);					//Severity
		Big1.field("BG_USER_01",Retrieve_EnvVar.RetrieveQCDetails("QC_Release"));				//Release
		Big1.field("BG_USER_02",Retrieve_EnvVar.RetrieveQCDetails("QC_Type"));					//Type
		Big1.field("BG_USER_06",Retrieve_EnvVar.RetrieveQCDetails("QC_Status"));				//Status
		Big1.field("BG_USER_07",Retrieve_EnvVar.RetrieveQCDetails("QC_BusinessFlow"));				//Business Flow
		Big1.field("BG_USER_29",Retrieve_EnvVar.RetrieveQCDetails("QC_PLCPhaseDetected"));					//PLC Phase detected
		Big1.field("BG_DESCRIPTION",StepsToReproduce);	//Description
		Big1.field("BG_SUMMARY",Summary);				//Summary
		//Big1.field("BG_USER_04",DueDate);				//Due Date

			Big1.field("BG_USER_15",Retrieve_EnvVar.RetrieveQCDetails("QC_ReporterTrack"));			//Reporter Track
		Big1.field("BG_USER_43",Retrieve_EnvVar.RetrieveQCDetails("QC_Project"));				//Project
		Big1.field("BG_USER_80",Retrieve_EnvVar.RetrieveQCDetails("QC_TesterType"));			//Tester Type
		Big1.field("BG_USER_27",Retrieve_EnvVar.RetrieveQCDetails("QC_ReportingFlow"));//Reporting Flow
		 

		IAttachmentFactory attachfac = Big1.attachments().queryInterface(IAttachmentFactory.class);
		IAttachment attach = attachfac.addItem(ScreenshotPath).queryInterface(IAttachment.class);
		IExtendedStorage attachStorage =  attach.attachmentStorage().queryInterface(IExtendedStorage.class);
		attachStorage.clientPath(Object_Definition_Library.getEnvVar("SecondaryExecutionResultPath"));
		attachStorage.save(ScreenshotPath, true);
		attach.post();
		attach.refresh();
		System.out.println("Attached File Name is "+attach.fileName());


		Big1.post();
		Big1.refresh();
		System.out.println("Bug is successfullly posted");
		System.out.println("Defect ID= "+Big1.field("BG_BUG_ID"));


	}*/
	
	
	public static void RaiseDefect(){



		ArrayList<String> DefectNamesArray = DefectDetailsDriver.RetrieveDefectFromXML();
		ArrayList<String> DefectDetailsArray = new ArrayList<String>();
		ArrayList<String> DefectScreenshot =  DefectDetailsDriver.RetreiveScreenShotpathFromTestDefectXML();
		String DefectScreenshotPath = "";
		ITDConnection itdc= ClassFactory.createTDConnection();    
		System.out.println(itdc.connected());   
		itdc.initConnectionEx(Retrieve_EnvVar.RetrieveQCDetails("QC_Path"));
		System.out.println(itdc.connected());    
		//itdc.connectProjectEx(Retrieve_EnvVar.RetrieveQCDetails("QC_Project"), Retrieve_EnvVar.RetrieveQCDetails("QC_Domain"), Retrieve_EnvVar.RetrieveQCDetails("QC_UserName"), "*Kerala231"); 
		itdc.connectProjectEx(Retrieve_EnvVar.RetrieveQCDetails("QC_Project"), Retrieve_EnvVar.RetrieveQCDetails("QC_Domain"), Retrieve_EnvVar.RetrieveQCDetails("QC_UserName"), "Voicemail5");
		System.out.println(itdc.connected());
		System.out.println("Logged into QC");
		IBugFactory bugFactory = (IBugFactory) itdc.bugFactory().queryInterface(IBugFactory.class);
		IBug Big1 = (bugFactory.addItem(null)).queryInterface(IBug.class);
		//set1
		/*Big1.project(Project);
		Big1.detectedBy(DetectedBy);
		Big1.status(Status);
		Big1.summary(Summary);    
		Big1.assignedTo(AssignedTo);*/

		for(int i=0;i<DefectNamesArray.size();i++){

			DefectDetailsArray = DefectDetailsDriver.RequestDetailsFromDefectDetailsHubXML(DefectNamesArray.get(i));
			DefectScreenshotPath = DefectScreenshot.get(i);

			//=======Defect logging
			Summary = DefectDetailsArray.get(0);
			StepsToReproduce = DefectDetailsArray.get(1);
			Severity = DefectDetailsArray.get(3);
			
			
			
			
			
			
			
			
			

			Big1.field("BG_DETECTED_BY",Retrieve_EnvVar.RetrieveQCDetails("QC_DetectedBy"));		//ReportedBy
			//Big1.field("BG_DETECTION_DATE","3/27/2013");	//ReporterDate
			Big1.field("BG_DETECTION_VERSION",Retrieve_EnvVar.RetrieveQCDetails("QC_Environment"));	//Environment	
			//Big1.field("BG_PRIORITY","P5");					//Priority
			Big1.field("BG_PROJECT",Retrieve_EnvVar.RetrieveQCDetails("QC_Track"));				//Track
			Big1.field("BG_RESPONSIBLE",Retrieve_EnvVar.RetrieveQCDetails("QC_AssignedTo"));		//AssignedTo
			Big1.field("BG_SEVERITY",Severity);					//Severity
			Big1.field("BG_USER_01",Retrieve_EnvVar.RetrieveQCDetails("QC_Release"));				//Release
			Big1.field("BG_USER_02",Retrieve_EnvVar.RetrieveQCDetails("QC_Type"));					//Type
			Big1.field("BG_USER_06",Retrieve_EnvVar.RetrieveQCDetails("QC_Status"));				//Status
			Big1.field("BG_USER_07",Retrieve_EnvVar.RetrieveQCDetails("QC_BusinessFlow"));				//Business Flow
			Big1.field("BG_USER_29",Retrieve_EnvVar.RetrieveQCDetails("QC_PLCPhaseDetected"));					//PLC Phase detected
			//String BGDesc = Summary + "Tested in Browser :" + brw;
			Big1.field("BG_DESCRIPTION",StepsToReproduce);	//Description
			Big1.field("BG_SUMMARY",Summary);				//Summary
			//Big1.field("BG_USER_04",DueDate);				//Due Date

			/*	Big1.field("BG_USER_15",Retrieve_EnvVar.RetrieveQCDetails("QC_ReporterTrack"));			//Reporter Track
			Big1.field("BG_USER_43",Retrieve_EnvVar.RetrieveQCDetails("QC_Project"));				//Project
			Big1.field("BG_USER_80",Retrieve_EnvVar.RetrieveQCDetails("QC_TesterType"));			//Tester Type
			Big1.field("BG_USER_27",Retrieve_EnvVar.RetrieveQCDetails("QC_ReportingFlow"));//Reporting Flow
			 */

			System.out.println(DefectScreenshotPath);
			String FileName = "";
			String BasePath = "";
			String[] temp;
			temp = DefectScreenshotPath.split("\\\\");

			FileName  = temp[7];
			BasePath = temp[0]+"\\"+temp[1]+"\\"+temp[2]+"\\"+temp[3]+"\\"+temp[4]+"\\"+temp[5]+"\\"+temp[6]+"\\";
			
			
			
			
			
			IAttachmentFactory attachfac = Big1.attachments().queryInterface(IAttachmentFactory.class);
			IAttachment attach = attachfac.addItem(FileName).queryInterface(IAttachment.class);
			IExtendedStorage attachStorage =  attach.attachmentStorage().queryInterface(IExtendedStorage.class);
			attachStorage.clientPath(BasePath);
			attachStorage.save(FileName, true);
			attach.post();
			attach.refresh();
			System.out.println("Attached File Name is "+attach.fileName());

			Big1.post();
			Big1.refresh();
			System.out.println("Bug is successfullly posted");
			System.out.println("Defect ID= "+Big1.field("BG_BUG_ID"));
			System.out.println("DEFECT SCREENSHOT = "+DefectScreenshot.get(i));
			
			Reporter.log("************* Defect"+i+" details *************");
			Reporter.log("Defect ID : "+Big1.field("BG_BUG_ID"));
			Reporter.log("Summary : "+Summary);
			Reporter.log("Steps to reproduce : "+StepsToReproduce);
			Reporter.log("Detected by : "+Retrieve_EnvVar.RetrieveQCDetails("QC_DetectedBy"));
			Reporter.log("Environment : "+Retrieve_EnvVar.RetrieveQCDetails("QC_Environment"));
			Reporter.log("Track : "+Retrieve_EnvVar.RetrieveQCDetails("QC_Track"));
			Reporter.log("Assigned To : "+Retrieve_EnvVar.RetrieveQCDetails("QC_AssignedTo"));
			Reporter.log("Release : "+Retrieve_EnvVar.RetrieveQCDetails("QC_Release"));
			Reporter.log("Type : "+Retrieve_EnvVar.RetrieveQCDetails("QC_Type"));
			Reporter.log("Status : "+Retrieve_EnvVar.RetrieveQCDetails("QC_Status"));
			Reporter.log("Business Flow : "+Retrieve_EnvVar.RetrieveQCDetails("QC_BusinessFlow"));
			Reporter.log("PLC Phase detected : "+Retrieve_EnvVar.RetrieveQCDetails("QC_PLCPhaseDetected"));
			Reporter.log("************************************************");
			Reporter.log("");
			
			
		}

		


	}






	//<Method Name>DeleteTestDefectXML</Method Name>
	//<Description>Deletes Test Defect XML</Description>
	//<Arguments></Arguments>
	//<Author>Chris Thomson Philip</Author>
	//<Version>1.0</Version>	

	public static void DeleteTestDefectXML()
	{	
		try{

			File file = new File(Object_Definition_Library.getEnvVar("TestDefectXmlLocation"));

			if(file.delete()){
				System.out.println(file.getName() + " is deleted!");
			}else{
				System.out.println("Delete operation is failed.");
			}

		}catch(Exception e){

			e.printStackTrace();

		}

	}

	public static void AppendDefectDetailsXML( String error_Code, String test_details, String path_details) throws TransformerException {

		try {
			//Creating an empty XML Document	 
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.newDocument();

			File f = new File(Object_Definition_Library.getEnvVar("TestDefectXmlLocation"));	            
			if(f.exists()){
				value = true;
				System.out.println("File found. Appending data to the XML");

				//If file exists call the append function	      	  			
				appendToXML(error_Code,test_details,path_details);	      	  			
			} else{
				value = false;
				System.out.println("File not found. Creating a new XML");	            	

				//Generate a new XML file
				generateXML();

				Element suiteElement = document.createElement("suite");
				document.appendChild(suiteElement);

				Element testElement = document.createElement("defect");
				suiteElement.appendChild(testElement);
				testElement.setAttribute("name", error_Code);

				Element classesElement = document.createElement("classes");
				testElement.appendChild(classesElement);

				Element itemId = document.createElement("class");
				classesElement.appendChild(itemId);
				itemId.setAttribute("name",test_details);	 
				itemId.setAttribute("path",path_details);	         

				//Stream Result
				transformerFac();	                      
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}  
	}

	public static void appendToXML(String error_Code, String test_details, String path_details){

		try{             
			parseXML();

			//Search for existing error name
			Element link = null;   
			NodeList list = document.getElementsByTagName("defect");

			outermostloop:	            	
				if(true){
					for( int i = 0; i < list.getLength(); i++ ) {
						link = (Element) list.item(i);
						errorDetail_Name = link.getAttribute("name");
						if(error_Code.equals(errorDetail_Name)){
							System.out.println("Matching");
							System.out.println("Appending data to the existing error tag!!!");
							Element classes1 = (Element) link.getFirstChild();
							Element newEle = document.createElement("class");	            
							newEle.setAttribute("name", test_details);
							newEle.setAttribute("path",path_details);
							classes1.appendChild(newEle);

							//Write into file
							transformerFac();
							break outermostloop;
						} 
					}

					Element server = document.createElement("defect");
					server.setAttribute("name", error_Code);
					root.appendChild(server);

					Element port = document.createElement("classes");
					server.appendChild(port);

					Element class_1 = document.createElement("class");
					class_1.setAttribute("name",test_details);	 
					class_1.setAttribute("path",path_details);
					port.appendChild(class_1);

					//Write into file
					transformerFac();
				}
		}catch(Exception e){
			e.printStackTrace();
		}	    		    	
	}

	public static void generateXML(){
		try{
			//create XML file    		
			xmlFile = new File(Object_Definition_Library.getEnvVar("TestDefectXmlLocation"));
			xmlFile.createNewFile();
			isod = new FileOutputStream(xmlFile);
			isod.flush();
			isod.close();
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	public static void parseXML(){

		try {
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(Object_Definition_Library.getEnvVar("TestDefectXmlLocation"));
			root = document.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void transformerFac(){
		try{	            
			DOMSource source = new DOMSource(document);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StreamResult result = new StreamResult(Object_Definition_Library.getEnvVar("TestDefectXmlLocation"));
			transformer.transform(source, result);	            
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public static ArrayList<String> RetrieveDefectFromXML(){

		ArrayList<String> DefectNamesArray = new ArrayList<String>();

		try{             
			parseXML();

			//Search for existing error name
			Element link = null;   
			NodeList list = document.getElementsByTagName("defect");
			System.out.println("No of defects is : "+list.getLength());

			//outermostloop:	            	

			for( int i = 0; i < list.getLength(); i++ ) {
				link = (Element) list.item(i);
				errorDetail_Name = link.getAttribute("name");
				DefectNamesArray.add(errorDetail_Name);

				System.out.println("Defect names are : "+errorDetail_Name);

				//Write into file
				transformerFac();
				//	break outermostloop;
			} 


			//Write into file
			transformerFac();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return DefectNamesArray;	    		    	
	}




	
	public static ArrayList<String> RetreiveScreenShotpathFromTestDefectXML() {

		ArrayList<String> DefectScreenshotArray = new ArrayList<String>();

		try{             
			parseXML();


			String Screenshotpath = "";
			// Count No of Test Cases under a defect
			Element countTC,countTC1 = null;
			NodeList Classes = root.getElementsByTagName("classes");
			for(int i=0;i<Classes.getLength();i++){
				countTC = (Element) Classes.item(i);
				NodeList Class = countTC.getElementsByTagName("class");
				for(int j=0;j<Class.getLength();j++){
					Element	SingleClass = (Element) Class.item(j);
				/*Node classesTag =  countTC.getFirstChild();
				Element classTag = (Element) countTC.getFirstChild();*/
				Screenshotpath = SingleClass.getAttribute("path");
				// = classesTag.
				break;
				}
					//Element classTag = (Element) classesTag.getFirstChild();
					
					DefectScreenshotArray.add(Screenshotpath);

				}				
					
			transformerFac();
		}catch(Exception e){
			e.printStackTrace();
		}
		return DefectScreenshotArray;

	}





	public static ArrayList<String> RequestDetailsFromDefectDetailsHubXML(String errorCode){


		DocumentBuilderFactory documentBuilderFactory1;
		DocumentBuilder documentBuilder1;
		Document document1;
		/*FileOutputStream isod1 = null;
		File xmlFile1 = null;	*/
		String errorDetail_Name1 = null;
		Element root1 = null;
		boolean value1 = false;

		ArrayList<String> DefectDetailsArray = new ArrayList<String>();

		try{             
			documentBuilderFactory1 = DocumentBuilderFactory.newInstance();
			documentBuilder1 = documentBuilderFactory1.newDocumentBuilder();
			document1 = documentBuilder1.parse(Object_Definition_Library.getEnvVar("DefectDetailsHubXmlLocation"));
			root1 = document1.getDocumentElement();

			//Search for existing error name
			Element link = null; 
			NodeList list = document1.getElementsByTagName("defect");



			for( int i = 0; i < list.getLength(); i++ ) {
				link = (Element) list.item(i);
				errorDetail_Name1 = link.getAttribute("name");

				if(errorCode.equals(errorDetail_Name1)){
					System.out.println("Matching");
					System.out.println("Retreving defect data from the existing error tag!!!");


					//	for (int temp = 0; temp < list.getLength(); temp++) {
					Node nNode1 =list.item(i);
					if (nNode1.getNodeType() == Node.ELEMENT_NODE) {		
						Element eElement = (Element) nNode1;
						String Summary = getTagValue("Summary", eElement);
						String StepsToReproduce = getTagValue("StepsToReproduce", eElement);
						String AssignedTo = getTagValue("AssignedTo", eElement);
						String Severity = getTagValue("Severity", eElement);
						DefectDetailsArray.add(Summary);
						DefectDetailsArray.add(StepsToReproduce);
						DefectDetailsArray.add(AssignedTo);
						DefectDetailsArray.add(Severity);


					}
				}
				//	}
				//Write into file
				DOMSource source = new DOMSource(document1);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				StreamResult result = new StreamResult(Object_Definition_Library.getEnvVar("DefectDetailsHubXmlLocation"));
				transformer.transform(source, result);	 

			} 

			//Write into file
			DOMSource source = new DOMSource(document1);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StreamResult result = new StreamResult(Object_Definition_Library.getEnvVar("DefectDetailsHubXmlLocation"));
			transformer.transform(source, result);	 

		}catch(Exception e){
			e.printStackTrace();
		}	   

		return DefectDetailsArray;
	}


	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}


	//<Method Name>DeleteExecutionStatusXML</Method Name>
	//<Description>Find systime in specified format.</Description>
	//<Arguments>selenium,Object</Arguments>
	//<Author>Chris Thomson Philip</Author>
	//<Version>1.0</Version>	

	public static void DeleteFailedTestXML()
	{	
		try{

			File file = new File(Object_Definition_Library.getEnvVar("FailedTestXmlLocation"));

			if(file.delete()){
				System.out.println(file.getName() + " is deleted!");
			}else{
				System.out.println("Delete operation is failed.");
			}

		}catch(Exception e){

			e.printStackTrace();

		}

	}

	//<Method Name>CreateFailedTestXMLFile</Method Name>
	//<Description>Find systime in specified format.</Description>
	//<Arguments>selenium,Object</Arguments>
	//<Author>Chris Thomson Philip</Author>
	//<Version>1.0</Version>
	public static void CreateFailedTestXMLFile() {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("suite");
			rootElement.setAttribute("name", "Suite");
			rootElement.setAttribute("parallel", "false");
			doc.appendChild(rootElement);

			// staff elements
			Element staff = doc.createElement("test");
			staff.setAttribute("name", "Re-execution");
			rootElement.appendChild(staff);


			// firstname elements
			Element classes = doc.createElement("classes");
			//firstname.appendChild(doc.createTextNode("Partner360"));
			staff.appendChild(classes);



			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(Object_Definition_Library.getEnvVar("FailedTestXmlLocation")));








			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}	


	//<Method Name>AppendFailedTestXML</Method Name>
	//<Description>Find systime in specified format.</Description>
	//<Arguments>selenium,Object</Arguments>
	//<Author>Chris Thomson Philip</Author>
	//<Version>1.0</Version>




	/*public static void AppendFailedTestXML(String ClassName) throws TransformerException, SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(Object_Definition_Library.getEnvVar("FailedTestXmlLocation"));

		Node ExecutionStatus = doc.getElementsByTagName("classes").item(0);
		NodeList list = ExecutionStatus.getChildNodes();
		int len = list.getLength();
		Element teststep = doc.createElement("class");
		teststep.setAttribute("name", ClassName);
		ExecutionStatus.appendChild(teststep);
		System.out.println("Created");




		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		String abc = Object_Definition_Library.getEnvVar("FailedTestXmlLocation");
		StreamResult result = new StreamResult(new File(abc));
		transformer.transform(source, result);



		//=======================


	}
	 */



	/*public static void AppendFailedTestXML(String TestCaseName){

		try{             
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(Object_Definition_Library.getEnvVar("FailedTestXmlLocation"));

			//Search for existing error name
			Element link = null; 
			Element link1 = null; 
			NodeList list = doc.getElementsByTagName("class");
			String error_Code=TestCaseName;
			outermostloop:	            	
				if(true){
					for( int i = 0; i < list.getLength(); i++ ) {
						link = (Element) list.item(i);
						link1 = (Element) list.item(i+1);
						//String errorDetail_Name = link.getNodeName();
						String errorDetail_Name = link.getAttribute("name");
						if(error_Code.equals(errorDetail_Name)){
							System.out.println("Matching");
							System.out.println("Appending data to the existing failed xml tag!!!");
							//Element classes1 = (Element) link.getFirstChild();
							Element newEle = doc.createElement("class");	            
							newEle.setAttribute("name", TestCaseName);
							link.appendChild(newEle);



							//Write into file
							TransformerFactory transformerFactory = TransformerFactory.newInstance();
							Transformer transformer = transformerFactory.newTransformer();
							DOMSource source = new DOMSource(doc);
							String abc = Object_Definition_Library.getEnvVar("FailedTestXmlLocation");
							StreamResult result = new StreamResult(new File(abc));
							transformer.transform(source, result);
							//break outermostloop;
						} 
					}

					Element server = doc.createElement("test");
					server.setAttribute("name", error_Code);
					root.appendChild(server);

					Element port = doc.createElement("classes");
					server.appendChild(port);

					Element class_1 = doc.createElement("class");
					class_1.setAttribute("name",TestCaseName);	 
					port.appendChild(class_1);

					//Write into file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					String abc = Object_Definition_Library.getEnvVar("FailedTestXmlLocation");
					StreamResult result = new StreamResult(new File(abc));
					transformer.transform(source, result);
				}
		}catch(Exception e){
			e.printStackTrace();
		}	    		    	
	}*/

	//=====================================================================================================



	public static void AppendFailedTestXML(String TCaseName){

		String TestCaseName = "TestScript."+TCaseName;


		try {
			//Creating an empty XML Document	 
			documentBuilderFactory1 = DocumentBuilderFactory.newInstance();
			documentBuilder1 = documentBuilderFactory1.newDocumentBuilder();
			document1 = documentBuilder1.newDocument();

			File f = new File(Object_Definition_Library.getEnvVar("FailedTestXmlLocation"));	            
			if(f.exists()){
				value = true;
				System.out.println("File found. Appending data to the XML");

				//If file exists call the append function	      	  			
				appendToFailedXML(TestCaseName);	      	  			
			} else{
				value1 = false;
				System.out.println("File not found. Creating a new XML");	            	

				//Generate a new XML file
				generateFailedXML();

				Element suiteElement = document1.createElement("suite");
				suiteElement.setAttribute("name", "Suite");
				suiteElement.setAttribute("parallel", "false");
				document1.appendChild(suiteElement);

				Element testElement = document1.createElement("test");
				suiteElement.appendChild(testElement);
				testElement.setAttribute("name", TestCaseName);

				Element classesElement = document1.createElement("classes");
				testElement.appendChild(classesElement);

				Element itemId = document1.createElement("class");
				classesElement.appendChild(itemId);
				itemId.setAttribute("name",TestCaseName);	 

				//Stream Result
				transformerFailedXMLFac();	                      
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}  
	}

	public static void appendToFailedXML(String TestCaseName){

		try{             
			parseFailedXML();

			//Search for existing error name
			Element link = null;   
			NodeList list = document1.getElementsByTagName("test");

			outermostloop:	            	
				if(true){
					for( int i = 0; i < list.getLength(); i++ ) {
						link = (Element) list.item(i);
						errorDetail_Name1 = link.getAttribute("name");
						if(TestCaseName.equals(errorDetail_Name1)){
							System.out.println("Matching");
							System.out.println("Appending data to the existing error tag!!!");
							Element classes1 = (Element) link.getFirstChild();
							Element newEle = document1.createElement("class");	            
							newEle.setAttribute("name", TestCaseName);

							classes1.appendChild(newEle);

							//Write into file
							transformerFac();
							break outermostloop;
						} 
					}

					Element server = document1.createElement("test");
					server.setAttribute("name", TestCaseName);
					root1.appendChild(server);

					Element port = document1.createElement("classes");
					server.appendChild(port);

					Element class_1 = document1.createElement("class");
					class_1.setAttribute("name",TestCaseName);	 

					port.appendChild(class_1);

					//Write into file
					transformerFailedXMLFac();
				}
		}catch(Exception e){
			e.printStackTrace();
		}	    		    	
	}

	public static void generateFailedXML(){
		try{
			//create XML file    		
			xmlFile1 = new File(Object_Definition_Library.getEnvVar("FailedTestXmlLocation"));
			xmlFile1.createNewFile();
			isod1 = new FileOutputStream(xmlFile1);
			isod1.flush();
			isod1.close();
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	public static void parseFailedXML(){

		try {
			documentBuilderFactory1 = DocumentBuilderFactory.newInstance();
			documentBuilder1 = documentBuilderFactory1.newDocumentBuilder();
			document1 = documentBuilder1.parse(Object_Definition_Library.getEnvVar("FailedTestXmlLocation"));
			root1 = document1.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void transformerFailedXMLFac(){
		try{	            
			DOMSource source = new DOMSource(document1);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StreamResult result = new StreamResult(Object_Definition_Library.getEnvVar("FailedTestXmlLocation"));
			transformer.transform(source, result);	            
		}catch(Exception e){
			e.printStackTrace();
		}
	}






	public static String RetreieveQCParameters(String TagValue){
		String HTMLGlobalParameters=null;
		try {

			File fXmlFile = new File(Object_Definition_Library.getEnvVar("QCDetailsXMLLocation"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("QC_Details");


			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					HTMLGlobalParameters = getTagValue(TagValue, eElement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		return HTMLGlobalParameters;

	}



	public static String getQCDetails(String tag) {
		String tag_Value = RetreieveQCParameters(tag);
		//System.out.println("Tag Value ="+tag_Value);		
		return tag_Value;
	}



















}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
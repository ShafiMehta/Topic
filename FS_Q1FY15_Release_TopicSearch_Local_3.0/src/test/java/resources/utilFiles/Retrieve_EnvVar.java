package resources.utilFiles;

import java.io.File;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Retrieve_EnvVar {
	
	public static String ReportGlobalParameters(String TagValue){
		String HTMLGlobalParameters=null;
		try {

			File fXmlFile = new File("src//test//java//resources//configFiles//EnvVar.xml");
			//File fXmlFile = new File(".//EnvVar.xml");
			//System.out.println("Absolute: "+fXmlFile.getAbsolutePath());
			//System.out.println("Canonical: "+fXmlFile.getCanonicalPath());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("EnvVariables");
			//System.out.println("-----------------------");

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

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
		}

	
	public static String RetrieveDefectDetails(String ErrCode, String TagValue){
		String HTMLGlobalParameters=null;
		try {

			File fXmlFile = new File("C://Automation//FS_Q1FY15_Release_TopicSearch_3.0//src//test//java//Defects.xml");
			//File fXmlFile = new File(".//EnvVar.xml");
			//System.out.println("Absolute: "+fXmlFile.getAbsolutePath());
			//System.out.println("Canonical: "+fXmlFile.getCanonicalPath());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName(ErrCode);
			//System.out.println("-----------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//	System.out.println("ReportHeadLine : " + getTagValue("ReportHeadLine", eElement));
					//	System.out.println("ApplicationName : " + getTagValue("ApplicationName", eElement));
					//	System.out.println("Release : " + getTagValue("Release", eElement));
					//HTMLGlobalParameters = getTagValue("UI3", eElement)+";"+getTagValue("UI5", eElement);
					HTMLGlobalParameters = getTagValue(TagValue, eElement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return HTMLGlobalParameters;
		
	}

	
	public static String RetrieveQCDetails(String TagValue){
		String HTMLGlobalParameters=null;
		try {

			File fXmlFile = new File("C://Automation//FS_Q1FY15_Release_TopicSearch_3.0//src//test//java//QCDetails.xml");
			//File fXmlFile = new File(".//EnvVar.xml");
			//System.out.println("Absolute: "+fXmlFile.getAbsolutePath());
			//System.out.println("Canonical: "+fXmlFile.getCanonicalPath());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("QC_Details");
			//System.out.println("-----------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//	System.out.println("ReportHeadLine : " + getTagValue("ReportHeadLine", eElement));
					//	System.out.println("ApplicationName : " + getTagValue("ApplicationName", eElement));
					//	System.out.println("Release : " + getTagValue("Release", eElement));
					//HTMLGlobalParameters = getTagValue("UI3", eElement)+";"+getTagValue("UI5", eElement);
					HTMLGlobalParameters = getTagValue(TagValue, eElement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return HTMLGlobalParameters;
		
	}
	
	public static String ReportGlobalParametersPom(String TagValue){
		String HTMLGlobalParameters=null;
		try {

			File fXmlFile = new File("pom.xml");
			//File fXmlFile = new File(".//EnvVar.xml");
			//System.out.println("Absolute: "+fXmlFile.getAbsolutePath());
			//System.out.println("Canonical: "+fXmlFile.getCanonicalPath());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("properties");
			//System.out.println("-----------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//	System.out.println("ReportHeadLine : " + getTagValue("ReportHeadLine", eElement));
					//	System.out.println("ApplicationName : " + getTagValue("ApplicationName", eElement));
					//	System.out.println("Release : " + getTagValue("Release", eElement));
					//HTMLGlobalParameters = getTagValue("UI3", eElement)+";"+getTagValue("UI5", eElement);
					HTMLGlobalParameters = getTagValue(TagValue, eElement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return HTMLGlobalParameters;
		
	}
	
	
	
	
	
}

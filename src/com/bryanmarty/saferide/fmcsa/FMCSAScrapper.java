package com.bryanmarty.saferide.fmcsa;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

import org.apache.commons.lang3.StringEscapeUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class FMCSAScrapper {
	//http://www.fmcsa.dot.gov/safety-security/PCS/Consumers.aspx
	
	public enum VehicleType {
		Motorcoach, Limousine
	}

	private static final String WEB_URL =
		"http://ai.fmcsa.dot.gov/PassengerSearch/SearchResults.aspx?LocationCode=1&LocationValue=" +
			"%s&VehicleType=%s&keyword=%s&Submit=Find+Carriers";  //Zipcode, Vehicle Type, Keyword
	
	private static final String XPATH_SEARCH_QUERY_TABLE_ROWS = "//table[@id='ctl00_ContentPlaceHolder1_gvCarrier']/tbody/tr";

	private static HtmlCleaner htmlCleaner_ = new HtmlCleaner();
	
	public static LinkedList<PassengerCarrier> query(String zipcode, VehicleType vehicleType, String keyword) {
		try {
			if(keyword == null) {
				keyword = "";
			}
			String urlString = String.format(WEB_URL, zipcode, vehicleType.toString(),keyword);
			URL url = new URL(urlString);
			
			CleanerProperties props = htmlCleaner_.getProperties();
			props.setAllowHtmlInsideAttributes(true);
			props.setAllowMultiWordAttributes(true);
			props.setRecognizeUnicodeChars(true);
			props.setOmitComments(true);
			props.setTranslateSpecialEntities(true);
			
			
			URLConnection connection = url.openConnection();
			
			TagNode node;
			node = htmlCleaner_.clean(new InputStreamReader(connection.getInputStream()));
			
			Object[] row_nodes = node.evaluateXPath(XPATH_SEARCH_QUERY_TABLE_ROWS);
			LinkedList<PassengerCarrier> carrierList = new LinkedList<PassengerCarrier>();
			for(Object n : row_nodes) {
				PassengerCarrier c = processRow((TagNode)n);
				if(c != null) {
					c.setVehicleType(vehicleType);
					carrierList.add(c);
				}
			}

			return carrierList;

		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (XPatherException xe) {
			xe.printStackTrace();
		}
		return null;
		
	}
	
	private static PassengerCarrier processRow(TagNode tr) {
		try {
			TagNode[] td_list = tr.getAllElements(false);
			if(!validateRow(td_list)) {
				return null;
			}
			//First TD
			TagNode carrierLinkNode = (TagNode) td_list[0].getChildren().get(0);
			URL carrierLink = new URL("http://ai.fmcsa.dot.gov/PassengerSearch/" + StringEscapeUtils.unescapeHtml4(carrierLinkNode.getAttributeByName("href")));
			
			String carrierName = carrierLinkNode.getChildren().iterator().next().toString().trim();
			carrierName = StringEscapeUtils.unescapeHtml4(carrierName);
			
			if(carrierName.contentEquals("Passenger Carrier Name")) {
				return null;
			}
			
			//Second TD
			String carrierLocation = ((TagNode) td_list[1]).getChildren().iterator().next().toString().trim();
			carrierLocation = StringEscapeUtils.unescapeHtml4(carrierLocation);
			
			//Fourth TD
			String allowedToOperate = ((TagNode) td_list[3]).getChildren().iterator().next().toString().trim();
			
			PassengerCarrier carrier = new PassengerCarrier();
			carrier.setName(carrierName);
			carrier.setPrincipalLocation(carrierLocation);
			carrier.setAllowedToOperate(allowedToOperate.contentEquals("Yes"));
			carrier.setDetailPage(carrierLink);
			
			return carrier;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static boolean validateRow(TagNode[] td) {
		return (td.length == 4);
	}
	
	public static PassengerCarrier getDetails(PassengerCarrier passengerCarrier) {
		try {
			if(passengerCarrier.getDetailPage() == null) {
				return passengerCarrier;
			}
			
			URL detailPage = passengerCarrier.getDetailPage();
			
			CleanerProperties props = htmlCleaner_.getProperties();
			props.setAllowHtmlInsideAttributes(true);
			props.setAllowMultiWordAttributes(true);
			props.setRecognizeUnicodeChars(true);
			props.setOmitComments(true);
			props.setTranslateSpecialEntities(true);
			
			URLConnection connection = detailPage.openConnection();
			
			TagNode node;
			System.out.println(detailPage.toString());
			node = htmlCleaner_.clean(new InputStreamReader(connection.getInputStream()));
			
			TagNode table = node.findElementByAttValue("id","ctl00_ContentPlaceHolder1_Table2",true,false);
			if(table == null ) {
				System.out.println("Null");
				return passengerCarrier;
			}
			
			
			TagNode companyNameNode =  table.findElementByAttValue("id","ctl00_ContentPlaceHolder1_lblTblCarrierName", true,false);
			
			TagNode dotNumberNode = table.findElementByAttValue("id","ctl00_ContentPlaceHolder1_lblTblDOTNumber", true, false);
			System.out.println("DOT:" + dotNumberNode.getText());
			
			
			
			
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
}

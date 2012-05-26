package com.bryanmarty.saferide.fmcsa;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import android.util.Log;

public class PassengerCarrier {
	//http://www.fmcsa.dot.gov/safety-security/PCS/Consumers.aspx
	
	public enum VehicleType {
		Motorcoach, Limousine
	}

	private static final String WEB_URL =
		"http://ai.fmcsa.dot.gov/PassengerSearch/SearchResults.aspx?LocationCode=1&LocationValue=" +
			"%s&VehicleType=%s&keyword=%s&Submit=Find+Carriers";  //Zipcode, Vehicle Type, Keyword
	
	private static final String XPATH_QUERY = "//table[@id='ctl00_ContentPlaceHolder1_gvCarrier']/tbody/tr";
	
	public static LinkedList<Carrier> query(String zipcode, VehicleType vehicleType, String keyword) {
		try {
			String urlString = String.format(WEB_URL, zipcode, vehicleType.toString(),keyword);
			URL url = new URL(urlString);
			
			HtmlCleaner cleaner = new HtmlCleaner();
			CleanerProperties props = cleaner.getProperties();
			props.setAllowHtmlInsideAttributes(true);
			props.setAllowMultiWordAttributes(true);
			props.setRecognizeUnicodeChars(true);
			props.setOmitComments(true);
			
			URLConnection connection = url.openConnection();
			
			TagNode node;
			node = cleaner.clean(new InputStreamReader(connection.getInputStream()));
			
			System.out.println(node.toString());
			
			Object[] row_nodes = node.evaluateXPath(XPATH_QUERY);
			LinkedList<Carrier> carrierList = new LinkedList<Carrier>();
			for(Object n : row_nodes) {
				Carrier c = processRow((TagNode)n);
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
	
	private static Carrier processRow(TagNode tr) {
		try {
			TagNode[] td_list = tr.getAllElements(false);
			if(!validateRow(td_list)) {
				return null;
			}
			//First TD
			TagNode carrierLinkNode = (TagNode) td_list[0].getChildren().get(0);
			URL carrierLink = new URL("http://ai.fmcsa.dot.gov/PassengerSearch/" + carrierLinkNode.getAttributeByName("href"));
			String dot = "";
			List<NameValuePair> params = URLEncodedUtils.parse(carrierLink.toURI(),"application/x-www-form-urlencoded");
			for(NameValuePair nvp : params) {
				if(nvp.getName().contentEquals("DOT")) {
					dot=nvp.getValue();
				}
			}
			String carrierName = carrierLinkNode.getChildren().iterator().next().toString().trim();
			
			//Second TD
			String carrierLocation = ((TagNode) td_list[1]).getChildren().iterator().next().toString().trim();
			
			//Fourth TD
			String allowedToOperate = ((TagNode) td_list[3]).getChildren().iterator().next().toString().trim();
			
			Carrier carrier = new Carrier();
			carrier.setName(carrierName);
			carrier.setPrincipalLocation(carrierLocation);
			carrier.setAllowedToOperate(allowedToOperate.contentEquals("Yes"));
			carrier.setDOT(dot);
			
			return carrier;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static boolean validateRow(TagNode[] td) {
		return (td.length == 4);
	}
	
}

package webnetmon

import java.util.Date;

class Event {

	String latitude
	String longitude
	String ConnectionType
	String protocol
	Long signal
	Long milliSeconds
	static belongsTo = [device: Device]
    
	Date dateCreated
	Date lastUpdated
	static constraints = {
    }
	
	def icono(){
		if ("EDGE"==protocol){
			return "http://maps.google.com/mapfiles/kml/paddle/blu-blank-lv.png"
		}
		if ("HSDPA"==protocol){
			return "http://maps.google.com/mapfiles/kml/paddle/grn-blank-lv.png"
		}
		if ("NotSubType"==protocol){
			return "http://maps.google.com/mapfiles/kml/paddle/ylw-blank-lv.png"
		}
		if ("EDGS"==protocol){
			return "http://maps.google.com/mapfiles/kml/paddle/wht-blank-lv.png"
		}
		
		return "http://maps.google.com/mapfiles/kml/paddle/wht-stars-lv.png"

	}
	
	//http://maps.google.com/mapfiles/kml/paddle/blu-blank-lv.png
	//http://maps.google.com/mapfiles/kml/paddle/grn-blank-lv.png
	//http://maps.google.com/mapfiles/kml/paddle/ylw-blank-lv.png
	//http://maps.google.com/mapfiles/kml/paddle/wht-blank-lv.png
	//http://maps.google.com/mapfiles/kml/paddle/wht-stars-lv.png
	
}

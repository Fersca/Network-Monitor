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
}

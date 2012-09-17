package webnetmon

class Event {

	String latitude
	String longitude
	String ConnectionType
	String protocol
	Long milliSeconds
	static belongsTo = [device: Device]
    static constraints = {
    }
}

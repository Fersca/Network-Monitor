package webnetmon

class Device {

	String Emai
	Carrier carrier
	static hasMany = [events: Event]

    static constraints = {
    }
}

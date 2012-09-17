package webnetmon

class Carrier {

	String name
	static hasMany = [devices: Device]
    static constraints = {
    }
}

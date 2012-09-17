package webnetmon

import javax.servlet.http.HttpServletResponse;

class EventController {
    static scaffold = true
	
	def evento (){
		
		def emai = params.emai
		def lat = params.lat
		def lon = params.lon
		def signal = new Long(params.sig)
		def carrier = params.carrier
		def type = params.type
		def protocol = params.protocol
		def ms = new Long(params.ms)
		
		Carrier carr = Carrier.findByName(carrier)
		if (!carr){
			carr = new Carrier()
			carr.name = carrier
			if (!carr.save()) println "Error guardando carrier ${carr.errors}"
		}
		
		Device dev = Device.findByEmai(emai)
		if (!dev){
			dev = new Device()
			dev.emai = emai
			dev.carrier = carr
			if (!dev.save()) println "Error guardando device ${dev.errors}"
		}
				
		Event ev = new Event()
		ev.device = dev
		ev.latitude = lat
		ev.longitude = lon
		ev.ConnectionType= type
		ev.protocol = protocol
		ev.milliSeconds = ms
		ev.signal = signal
		if (!ev.save()) println "Error guardando evento ${ev.errors}"	
		render "OK"	
	}
}

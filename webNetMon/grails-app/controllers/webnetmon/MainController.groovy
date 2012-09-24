package webnetmon

class MainController {

    def index() { 
	
		def eventos
		if (params.protocolo){
			eventos = Event.findAllByProtocol(params.protocolo)
		} else {
			eventos = Event.list()
		}
		
		println "canti: "+eventos.size()
		[eventos: eventos]	
		
	}
	
}

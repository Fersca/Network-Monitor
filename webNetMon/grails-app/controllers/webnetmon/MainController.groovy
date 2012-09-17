package webnetmon

class MainController {

    def index() { 
	
		def eventos = Event.list()
		println "canti: "+eventos.size()
		[eventos: eventos]	
	}
	
}

package webnetmon

class GooglePlusTagLib {
	def plusone = { attrs, body ->
		
		println "hola:"+attrs
		
		out << """

   <script type="text/javascript">

      function initialize() {
	      var myLatlng = new google.maps.LatLng(-34.538229071428574, -58.483130114285714);
	      var mapOptions = {
	        zoom: 11,
	        center: myLatlng,
	        mapTypeId: google.maps.MapTypeId.ROADMAP,
	      }
	      var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	      
	      var marker1 = new google.maps.Marker({
	          position: myLatlng,
	          title:"Fernando"
	      });
	      var myLatlng2 = new google.maps.LatLng(-34.538229071420000, -58.583130114280000);
	      var marker2 = new google.maps.Marker({
	          position: myLatlng2,
	          title:"Natalia"
	      });
	      var myLatlng3 = new google.maps.LatLng(-34.538229071420000, -58.683130111080000);
	      var marker3 = new google.maps.Marker({
	          position: myLatlng3,
	          title:"Valeria"
	      });
	      var myLatlng4 = new google.maps.LatLng(-34.538229071420000, -58.783130414280000);
	      var marker4 = new google.maps.Marker({
	          position: myLatlng4,
	          title:"Juan"
	      });
	
	      // To add the marker to the map, call setMap();
	      marker1.setMap(map);
	      marker2.setMap(map);
	      marker3.setMap(map);
	      marker4.setMap(map);
      }

    </script>


"""
	}
}

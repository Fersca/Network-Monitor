<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0; padding: 0 }
      #map_canvas { height: 100% }
    </style>
    <script type="text/javascript"
      src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAmdvU8MC-Dr4cs7SPhIGhFk31ZKQbsfCw&sensor=true">
    </script>

   <script type="text/javascript">

      function initialize() {
	      var myLatlng = new google.maps.LatLng(-34.538229071428574, -58.483130114285714);
	      var mapOptions = {
	        zoom: 11,
	        center: myLatlng,
	        mapTypeId: google.maps.MapTypeId.ROADMAP,
	      }
	      var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
		  
	      <g:each in="${eventos}">
	      
	      var myLatlng${it.id} = new google.maps.LatLng(${it.latitude}, ${it.longitude});
	      var marker${it.id} = new google.maps.Marker({
	          position: myLatlng${it.id},
	          title:"Signal: ${it.signal}, ConnectionType: ${it.connectionType}, Protocol: ${it.protocol}, Speed: ${it.milliSeconds}",
	       	  icon: '${it.icono()}'
	      });
	      marker${it.id}.setMap(map);
	  	  </g:each>
	      	
      }

    </script>

       
  </head>
  <body onload="initialize()">
    
    <form action="">
    	Protocolo
		<select name="protocolo">
		  <option value="">Todos</option>
		  <option value="EDGE">EDGE</option>
		  <option value="EDGS">EDGS</option>
		  <option value="HSDPA">HSDPA</option>
		  <option value="NotSubType">NotSubType</option>
		</select>
		<input type="submit" value="Filtrar">     
	</form>
    
    <div id="map_canvas" style="width:100%; height:100%"></div>
  </body>
</html>
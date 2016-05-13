 var url = 'url_do_streaming';

 var my_media = new PlayStream(url,
 	function (status){

 		if(status === PlayStream.MEDIA_STOPPED){

 		}

 		if(status === PlayStream.MEDIA_STARTING){

 		}

 		if(status === PlayStream.MEDIA_RUNNING){

 		}

 	},

 	function (err) { 
 		alert("Não foi possível iniciar a rádio, tente novamente mais tarde " + err); 
 	}
 );

// Play audio
my_media.play();
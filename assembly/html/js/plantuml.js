function generateUML(data) {
	var r = new createCORSRequest("POST",host+"/uml");
	if (!r) {
	  throw new Error('CORS not supported');
	}
	
	r.onreadystatechange = function () {
	  if (r.readyState != 4 || r.status != 200) return;
	  var s = document.getElementById('plantuml-img');
	  s.setAttribute('src', 'data:image/png;base64, '+r.responseText);
	};
	
	r.send(data);
}

function createCORSRequest(method, url) {
  var xhr = new XMLHttpRequest();
  if ("withCredentials" in xhr) {

    // Check if the XMLHttpRequest object has a "withCredentials" property.
    // "withCredentials" only exists on XMLHTTPRequest2 objects.
    xhr.open(method, url, true);

  } else if (typeof XDomainRequest != "undefined") {

    // Otherwise, check if XDomainRequest.
    // XDomainRequest only exists in IE, and is IE's way of making CORS requests.
    xhr = new XDomainRequest();
    xhr.open(method, url);

  } else {

    // Otherwise, CORS is not supported by the browser.
    xhr = null;

  }
  return xhr;
}


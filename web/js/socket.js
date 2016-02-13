window.DEVELOPMENT_MODE = 'competition';

window.dataDisplay = document.getElementById('data-display');

function convertObjectToLi(object) {
	var element = document.createElement('ul');

	Object.keys(object).forEach(function(key) {
		var e2 = document.createElement('li');
		var value = object[key];

		switch(typeof value) {
			case 'object':
				e2.innerHTML = '<b>' + key + '</b> &mdash;'
				e2.appendChild(convertObjectToLi(value));
				break;
			default:
				e2.innerHTML = '<b>' + key + '</b> &mdash; ' + value;
				break;
		}

		element.appendChild(e2);
	});

	return element;
}

function updateDataDisplay(data) {
	var data = convertObjectToLi(data);

	while(window.dataDisplay.lastChild) {
		window.dataDisplay.removeChild(window.dataDisplay.lastChild);
	}

	window.dataDisplay.appendChild(data);
}

switch(window.DEVELOPMENT_MODE) {
	case 'local-debug':
		window.SOCKET_URI = '0.0.0.0';
		break;

	case 'competition':
	default:
		window.SOCKET_URI = "roboRIO-2503-FRC.local";
		break;
}

function ImageSocket(url, setupCallback) {
	this.url = url;
	this.socket = new WebSocket(url);
	this.setupCallback = setupCallback;

	/* TODO: Implement */
	this.socket.onopen = function() {
		console.debug(arguments);
	};

	this.socket.onmessage = function(messageEvent) {
		var object = JSON.parse(messageEvent.data);
		console.debug(object);

		updateDataDisplay(object);

		if(object['image'])
			document.getElementsByClassName('image')[0].src = object['image'];
	};

	this.socket.onerror = function() {
	};

	this.socket.onclose = function() {
		setTimeout(function() {
			this.setupCallback();
		}.bind(this), 500);
	}.bind(this);

	this.send = function(data) {
		this.socket.send(data);
	}
}

window.setup_image_socket = function() {
	window.image_socket = new ImageSocket('ws://' + window.SOCKET_URI + ':5800/', window.setup_image_socket);
};

window.setup_image_socket();

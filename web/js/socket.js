window.DEVELOPMENT_MODE = 'local-debug';

switch(window.DEVELOPMENT_MODE) {
	case 'local-debug':
		window.SOCKET_URI = '0.0.0.0';
		break;

	case 'competition':
	default:
		window.SOCKET_URI = "roboRIO-2503-frc.local";
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

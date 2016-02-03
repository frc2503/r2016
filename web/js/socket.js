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

function setupSocket(key, uri, messageCallback) {
	window[key] = new Socket(uri, (function() {
		setupSocket(key, uri, messageCallback);
	}), messageCallback);
}

function Socket(url, setupCallback, messageCallback) {
	this.url = url;
	this.socket = new WebSocket(url);
	this.setupCallback = setupCallback;
	this.messageCallback = messageCallback;

	/* TODO: Implement */
	this.socket.onopen = function() {
		console.debug(arguments);
	};

	this.socket.onmessage = function(messageEvent) {
		this.messageCallback(messageEvent);
	};

	this.socket.onerror = function() {
		console.debug(arguments);
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

setupSocket('configuration_socket', 'ws://' + window.SOCKET_URI + ':5800/', function(messageEvent) {
	console.debug(messageEvent.data);
});

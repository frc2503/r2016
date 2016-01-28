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

window.SOCKET = new Socket("ws://" + window.SOCKET_URI + ":5800/");

function Socket(url) {
	this.url = url;
	this.socket = new WebSocket(url);

	/* TODO: Implement */
	this.socket.onopen = null;
	this.socket.onmessage = null;
	this.socket.onerror = null;
	this.socket.onclose = null;

	this.send = function(data) {
		this.socket.send(data);
	}
}

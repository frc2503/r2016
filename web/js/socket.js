/* window.dataDisplay = document.getElementById('data-display');

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

	 function ImageSocket(url, setupCallback) {
	 this.url = url;
	 this.socket = new WebSocket(url);
	 this.setupCallback = setupCallback;

	 /* TODO: Implement
	 this.socket.onopen = function() {
	 console.debug(arguments);
	 };

	 this.socket.onmessage = function(messageEvent) {
	 var object = JSON.parse(messageEvent.data);
	 console.debug(object);

	 updateDataDisplay(object);

	 if(object['vision-image'])
	 document.getElementsByClassName('vision-image')[0].src = object['vision-image'];
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

	 window.setup_image_socket(); */

window.ENVIRONMENT = 'production';

switch(window.ENVIRONMENT) {
	case 'production':
		window.TARGET = 'roboRIO-2503-FRC.local';
		break;

	case 'development':
	default:
		window.TARGET = '0.0.0.0';
		break;
}

function DataSocket(url, setupCallback) {
	this.url = url;
	this.socket = new WebSocket(url);
	this.setupCallback = setupCallback;

	this.socket.onmessage = function(messageEvent) {
		var object = JSON.parse(messageEvent.data);
		console.log(object);
	};

	this.socket.onopen = function() {
	};

	this.socket.onclose = function() {
		setTimeout(function() {
			this.setupCallback();
		}.bind(this), 500);
	}.bind(this);

	this.send = function(data) {
		this.socket.send(data);
	};
}

function MessageSocket(url, setupCallback) {
	this.url = url;
	this.socket = new WebSocket(url);
	this.setupCallback = setupCallback;
	this.id = (Math.floor(Math.random() * 16 * 16 * 16 * 16).toString(16));

	this.socket.onmessage = function(messageEvent) {
		var object = JSON.parse(messageEvent.data);
		console.log(object);

		var textElement = document.createElement("p");
		textElement.setAttribute('type', object['type']);

		var senderSpan = document.createElement("span");
		senderSpan.classList.add('sender');
		senderSpan.innerHTML = object['sender'];
		textElement.appendChild(senderSpan);

		textElement.appendChild(document.createTextNode(': '));

		var messageSpan = document.createElement("span");
		messageSpan.classList.add('message');
		messageSpan.innerHTML = object['message'];
		textElement.appendChild(messageSpan);

		[].slice.call(document.getElementsByClassName('console-output')).forEach(function(outputElement) {
			outputElement.appendChild(textElement);
			outputElement.scrollTop = outputElement.scrollHeight;
		});
	};

	this.socket.onopen = function() {
	};

	this.socket.onclose = function() {
		setTimeout(function() {
			this.setupCallback();
		}.bind(this), 500);
	}.bind(this);

	this.send = function(type, message) {
		var object = {
			sender: 'web-' + this.id,
			type: type,
			message: message
		};

		this.socket.send(JSON.stringify(object));
	};
}

function ImageSocket(url, setupCallback) {
	this.url = url;
	this.socket = new WebSocket(url);
	this.setupCallback = setupCallback;

	this.socket.onmessage = function(messageEvent) {
		var object = JSON.parse(messageEvent.data);

		console.log(Object.keys(object));

		if(object['raw-image'])
			[].slice.call(document.getElementsByClassName('raw-image')).forEach(function(img) { img.src = object['raw-image']});;
	};

	this.socket.onopen = function() {
	};

	this.socket.onclose = function() {
		setTimeout(function() {
			this.setupCallback();
		}.bind(this), 500);
	}.bind(this);
}

window.setup_socket = function(key, callback) {
	window["setup_" + key + "_socket"] = callback;
	window["setup_" + key + "_socket"]();
};

window.setup_socket("data", function(callback) {
	window.data_socket = new DataSocket('ws://' + window.TARGET + ':5800/', window.setup_data_socket);
});

window.setup_socket("message", function(callback) {
	window.message_socket = new MessageSocket('ws://' + window.TARGET + ':5801/', window.setup_message_socket);
});

window.setup_socket("image", function(callback) {
	window.image_socket = new ImageSocket('ws://0.0.0.0:80/', window.setup_image_socket);
})

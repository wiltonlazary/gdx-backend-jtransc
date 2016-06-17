var ipcRenderer = null;

if (typeof require !== 'undefined') {
	var electron = require('electron');
	if (electron) {
		ipcRenderer = electron.ipcRenderer;
		console.log = function() { ipcRenderer.send('console.log', Array.from(arguments)); };
		console.warn = function() { ipcRenderer.send('console.warn', Array.from(arguments)); };
		console.error = function() { ipcRenderer.send('console.error', Array.from(arguments)); };
		window.onerror = function myErrorHandler(errorMsg, url, lineNumber) {
            //alert("Error occured: " + errorMsg);//or any message
            ipcRenderer.send('window.error', Array.from(arguments));
            return false;
        }
	}
}

function downloadBytes(url, callback) {
	var oReq = new XMLHttpRequest();
	oReq.open("GET", url, true);
	oReq.responseType = "arraybuffer";

	oReq.onload = function (oEvent) {
	  var arrayBuffer = oReq.response; // Note: not oReq.responseText
	  if (arrayBuffer) {
	  	var array = new Uint8Array(arrayBuffer);
	  	if (url.match(/(png|jpg)$/)) {
	  		var img = document.createElement('img');
			var arrayBufferView = new Uint8Array( this.response );
			var blob = new Blob( [ arrayBufferView ], { type: "image/jpeg" } );
			var urlCreator = window.URL || window.webkitURL;
			var imageUrl = urlCreator.createObjectURL( blob );
			img.onload = function() {
				callback(array, img);
			};
			img.onerror = function() {
				callback(array, null);
			};
			img.src = imageUrl;

	  	} else {
			callback(array, null);
	  	}
	  } else {
	  	callback(null);
	  }
	};

	oReq.onerror = function (oEvent) {
		callback(null);
	};

	oReq.send(null);
}

function downloadBytesList(urls, callback) {
	urls = Array.from(urls);
	var count = 0;
	var results = new Array(urls.length);

	urls.forEach(function(url, n) {
		downloadBytes(url, function(bytes, image) {
			count++;
			results[n] = { bytes: bytes, image: image };
			if (count >= urls.length) {
				callback(results);
			}
		});
	});
}

var gl; // A global variable for the WebGL context
var GL; // A global variable for the WebGL context
var app = null;
var canvas = null;

var libgdx = function() {
};

libgdx.setTitle = function(title) {
	if (ipcRenderer != null) ipcRenderer.send('window.title', [title]);
};

libgdx.setSize = function(width, height) {
	if (ipcRenderer != null) ipcRenderer.send('window.size', [width, height]);
};

libgdx.show = function() {
	if (ipcRenderer != null) ipcRenderer.send('window.show', []);
};

function updateCanvasSize() {
	if (app == null || gl == null || canvas == null) return;
	var width = window.innerWidth;
	var height = window.innerHeight;
	canvas.width = width;
	canvas.height = height;
	canvas.style.width = width + 'px';
	canvas.style.height = height + 'px';
	app["{% METHOD com.jtransc.media.limelibgdx.LimeApplication:resized:(II)V %}"](width, height);
}

libgdx.initCanvas = function() {
	canvas = document.getElementById('jtransc_canvas');
	if (!canvas) {
		canvas = document.createElement('canvas');
		canvas.id = 'jtransc_canvas';
		canvas.width = '640';
		canvas.height = '480';
		canvas.style.width = '640px';
		canvas.style.height = '480px';
		canvas.innerText = "Your browser doesn't appear to support the canvas element";
		document.body.appendChild(canvas);
		document.body.style.padding = '0';
		document.body.style.margin = '0';
		document.body.style.overflow = 'hidden';
		//console.log(gl);
	}

	GL = gl = null;
	try {
		// Try to grab the standard context. If it fails, fallback to experimental.
		GL = gl = canvas.getContext("webgl") || canvas.getContext("experimental-webgl");
	} catch(e) {
	}

	//gl.clearColor(0.0, 0.0, 0.0, 1.0);
	//gl.clear(gl.COLOR_BUFFER_BIT);
};

libgdx.init = function() {
	libgdx.initCanvas();

	app["{% METHOD com.jtransc.media.limelibgdx.LimeApplication:create %}"]();
	updateCanvasSize();
};

libgdx.initOnce = function() {
	if (!this.initialized) {
		this.initialized = true;
		this.init();
	}
};

libgdx.frame = function() {
	window.requestAnimationFrame(libgdx.frame);

	libgdx.initOnce();

	app["{% METHOD com.jtransc.media.limelibgdx.LimeApplication:render %}"]();
};


libgdx.io = function() {
};

libgdx.io.exists = function(path) {
	return assets[normalizePath(path)] !== undefined;
};

libgdx.io.readBytes = function(path) {
	var asset = assets[normalizePath(path)];
	if (!asset) {
		return null;
		throw "Can't find asset '" + normalizePath(path) + "'";
	}
	var bytes = asset.bytes;
	if (!bytes) {
		return null;
		throw "Can't find asset bytes " + path;
	}
	var out = JA_B.fromTypedArray(bytes);
	//console.log('readBytes:', out);
	return out;
};

function ensureLimeInput() { {% SINIT com.jtransc.media.limelibgdx.LimeInput %} }

// handle event
window.addEventListener("optimizedResize", function() {
	updateCanvasSize();
	if (app != null) app["{% METHOD com.jtransc.media.limelibgdx.LimeApplication:render %}"]();
});

document.addEventListener('keydown', function(event) {
	ensureLimeInput()
	var kc = transformKeyCode(event.keyCode);
	//console.log('keydown', event.keyCode, kc);
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onKeyDown %}(kc, 0);
}, false);

document.addEventListener('keyup', function(event) {
	ensureLimeInput()
	var kc = transformKeyCode(event.keyCode);
	//console.log('keyup', event.keyCode, kc);
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onKeyUp %}(kc, 0);
}, false);

document.addEventListener('mouseup', function(event) {
	ensureLimeInput()
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onMouseUp %}(event.clientX | 0, event.clientY | 0, event.button | 0);
}, false);

document.addEventListener('mousedown', function(event) {
	ensureLimeInput()
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onMouseDown %}(event.clientX | 0, event.clientY | 0, event.button | 0);
}, false);

downloadBytesList(assetsList.map(function(info) { return info.normalizedPath }), function(bytesList) {
	for (var n = 0; n < assetsList.length; n++) {
		assetsList[n].bytes = bytesList[n].bytes;
		assetsList[n].image = bytesList[n].image;
		//console.log(assetsList[n].image);
	}
	//console.log(assetsList);
	//console.log(assets);
	//window.requestAnimationFrame(libgdx.frame);
	setTimeout(function() {
		libgdx.frame();
	}, 0);
});

function _getBufferArray(buffer) {
	return {% SMETHOD java.nio.internal.BufferInternalUtils:getByteBufferByteArray %}(buffer);
}

function _buffer(buffer, size) {
	var array = _getBufferArray(buffer);
	if (!size) size = array.length;
	return new Uint8Array(array.data.buffer);
	//return new Uint8Array(array.data);
}

function _floatBuffer(buffer, size) {
	var array = _getBufferArray(buffer);
	if (!size) size = array.length;
	//return new Uint8Array(array.data);
	//return new Uint8Array(array.data.buffer);
	return new Float32Array(array.data.buffer);
}

function _floatArray(array, offset, count) {
	if (!offset) offset = 0;
	if (!count) count = array.length - offset;
	//return new Float32Array(array.data, offset, count);
	return new Float32Array(array.data.buffer);
}

function __convertPixels(input, output, size) {
	for (var n = 0, m = 0; n < size; n++, m += 4) {
		output[n] = (input[m + 0] << 24) | (input[m + 1] << 16) | (input[m + 2] << 8) | (input[m + 3] << 0);
	}
}

function __decodeImage(path) {
	var asset = assets[normalizePath(path)];
	if (!asset) throw 'Unknown asset ' + path;
	if (!asset.image) throw 'Asset without image ' + path;
	var image = asset.image;
	var canvas = document.createElement('canvas');
	canvas.width = image.width;
	canvas.height = image.height;
	var ctx = canvas.getContext("2d");
	ctx.drawImage(image, 0, 0);

	var idata = ctx.getImageData(0, 0, image.width, image.height);
	var array = new JA_I(image.width * image.height);
	__convertPixels(idata.data, array.data, image.width * image.height);

	return {
		width: image.width,
		height: image.height,
		data: array
	};
}
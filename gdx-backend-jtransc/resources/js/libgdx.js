function downloadBytes(url, callback) {
	var oReq = new XMLHttpRequest();
	oReq.open("GET", url, true);
	oReq.responseType = "arraybuffer";

	oReq.onload = function (oEvent) {
	  var arrayBuffer = oReq.response; // Note: not oReq.responseText
	  if (arrayBuffer) {
		callback(new Uint8Array(arrayBuffer));
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
		downloadBytes(url, function(bytes) {
			count++;
			results[n] = bytes;
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
		//console.log(gl);
	}

	GL = gl = null;
	try {
		// Try to grab the standard context. If it fails, fallback to experimental.
		GL = gl = canvas.getContext("webgl") || canvas.getContext("experimental-webgl");
	} catch(e) {
	}

	updateCanvasSize();

	gl.clearColor(0.0, 0.0, 0.0, 1.0);
	gl.clear(gl.COLOR_BUFFER_BIT);
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
	if (!asset) throw "Can't find asset '" + normalizePath(path) + "'";
	var bytes = asset.bytes;
	if (!bytes) throw "Can't find asset bytes " + path;
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

downloadBytesList(assetsList.map(function(info) { return info.normalizedPath }), function(bytesList) {
	for (var n = 0; n < assetsList.length; n++) assetsList[n].bytes = bytesList[n];
	//console.log(assetsList);
	//console.log(assets);
	//window.requestAnimationFrame(libgdx.frame);
	setTimeout(function() {
		libgdx.frame();
	}, 0);
});

function _buffer(buffer, size) {
	var _buffer = buffer;
	if (_buffer["_bb"]) _buffer = _buffer["_bb"]; // Hack! do this fine
	var jarray = _buffer["{% FIELD java.nio.ByteBuffer:hb %}"];
	if (!size) size = jarray.length;
	return new Uint8Array(jarray.data);
	//return new Uint8Array(jarray.data.buffer, 0, size);
}

function _floatBuffer(buffer, size) {
	var jarray = buffer["{% FIELD java.nio.ByteBuffer:hb %}"];
	if (!size) size = jarray.length;
	return new Uint8Array(jarray.data);
	//return new Float32Array(jarray.data.buffer, 0, size);
}

function _floatArray(array, offset, count) {
	if (!offset) offset = 0;
	if (!count) count = array.length - offset;
	//return new Float32Array(array.data, offset, count);
	return new Float32Array(array.data);
}
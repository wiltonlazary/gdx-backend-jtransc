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
	  	var isPng = url.match(/png$/);
	  	var isJpg = url.match(/jpg$/);
	  	if (isPng || isJpg) {
	  		var img = document.createElement('img');
			var arrayBufferView = new Uint8Array(this.response);
			var blob = new Blob([arrayBufferView], { type: isPng ? "image/png" : "image/jpeg" });
			var urlCreator = window.URL || window.webkitURL;
			var imageUrl = urlCreator.createObjectURL( blob );
			img.onload = function() { callback(array, img); };
			img.onerror = function() { callback(array, null); };
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

	var attributes = {
		premultipliedAlpha: true
	};

	GL = gl = null;
	try {
		// Try to grab the standard context. If it fails, fallback to experimental.
		GL = gl = canvas.getContext("webgl", attributes) || canvas.getContext("experimental-webgl", attributes);
	} catch(e) {
	}

	gl.pixelStorei(gl.UNPACK_PREMULTIPLY_ALPHA_WEBGL, false);
};

libgdx.init = function() {
	libgdx.initCanvas();

	app["{% METHOD com.jtransc.media.limelibgdx.LimeApplication:create %}"]();
	updateCanvasSize();
};

libgdx.initOnce = function() {
	if (this.initialized) return;
	this.initialized = true;
	this.init();
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

/////////////////////////////////////////////////////////////////
// KEYS
/////////////////////////////////////////////////////////////////
document.addEventListener('keydown', function(event) {
	ensureLimeInput();
	var kc = transformKeyCode(event.keyCode);
	//console.log('keydown', event.keyCode, kc);
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onKeyDown %}(kc, 0);
}, false);

document.addEventListener('keyup', function(event) {
	ensureLimeInput();
	var kc = transformKeyCode(event.keyCode);
	//console.log('keyup', event.keyCode, kc);
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onKeyUp %}(kc, 0);
}, false);

/////////////////////////////////////////////////////////////////
// MOUSE
/////////////////////////////////////////////////////////////////
document.addEventListener('mouseup', function(event) {
	ensureLimeInput();
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onMouseUp %}(event.clientX | 0, event.clientY | 0, event.button | 0);
}, false);

document.addEventListener('mousedown', function(event) {
	ensureLimeInput();
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onMouseDown %}(event.clientX | 0, event.clientY | 0, event.button | 0);
}, false);

document.addEventListener('mousemove', function(event) {
	ensureLimeInput();
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onMouseMove %}(event.clientX | 0, event.clientY | 0, event.button | 0);
}, false);

document.addEventListener('wheel', function(event) {
	ensureLimeInput();

	//WheelEvent.deltaX Read only
    //WheelEvent.deltaY Read only
    //WheelEvent.deltaZ Read only
    //WheelEvent.deltaMode Read only
    //DOM_DELTA_PIXEL	0x00	The delta values are specified in pixels.
    //DOM_DELTA_LINE	0x01	The delta values are specified in lines.
    //DOM_DELTA_PAGE	0x02	The delta values are specified in pages

	var mult = 1.0;
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onWheel %}(+event.deltaX * mult, +event.deltaY * mult, +event.deltaZ * mult);
}, false);

/////////////////////////////////////////////////////////////////
// TOUCH
/////////////////////////////////////////////////////////////////

function getTouches(event) {
	var out = [];
	var touches = event.changedTouches;
	for (var n = 0; n < touches.length; n++) {
		var i = touches.item(n);
		out.push({
			//id: (i.identifier|0), // large values on iOS
			id: (n|0),
			x: (i.clientX|0),
			y: (i.clientY|0)
		});
	}
	return out;
}

document.addEventListener('touchstart', function(event) {
	ensureLimeInput();
	var touches = getTouches(event);
	for (var n = 0; n < touches.length; n++) {
		var t = touches[n];
		{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onTouchStart %}(t.id, t.x, t.y);
	}
}, false);

document.addEventListener('touchmove', function(event) {
	ensureLimeInput();
	var touches = getTouches(event);
	for (var n = 0; n < touches.length; n++) {
		var t = touches[n];
		{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onTouchMove %}(t.id, t.x, t.y);
	}
}, false);

document.addEventListener('touchend', function(event) {
	ensureLimeInput();
	var touches = getTouches(event);
	for (var n = 0; n < touches.length; n++) {
		var t = touches[n];
		{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onTouchEnd %}(t.id, t.x, t.y);
	}
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

function _arrayCopyRev(inp) {
	var len = inp.length;
	var out = new Uint8Array(len);
	for (var n = 0; n < len; n += 4) {
		var r = inp[n + 0];
		var g = inp[n + 1];
		var b = inp[n + 2];
		var a = inp[n + 3];
		out[n + 0] = a;
		out[n + 1] = b;
		out[n + 2] = g;
		out[n + 3] = r;
	}
	return out;
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

function __convertPixels(input, output, size, width, height) {
	for (var n = 0, m = 0; n < size; n++, m += 4) {
		var r = input[m + 0] & 0xFF;
		var g = input[m + 1] & 0xFF;
		var b = input[m + 2] & 0xFF;
		var a = input[m + 3] & 0xFF;
		output[n] = (r << 24) | (g << 16) | (b << 8) | (a << 0);
	}
}


function npot(x) {
    if (x < 0) return 0;
    --x;
    x |= x >> 1;
    x |= x >> 2;
    x |= x >> 4;
    x |= x >> 8;
    x |= x >> 16;
    return x + 1;
}

function __decodeImage(path) {
	var asset = assets[normalizePath(path)];
	if (!asset) throw 'Unknown asset ' + path;
	if (!asset.image) throw 'Asset without image ' + path;
	var image = asset.image;
	var canvas = document.createElement('canvas');
	canvas.style.width = canvas.width = npot(image.width);
	canvas.style.height = canvas.height = npot(image.height);

	//canvas.style.width = canvas.width = (image.width);
	//canvas.style.height = canvas.height = (image.height);

	var ctx = canvas.getContext("2d");
	ctx.imageSmoothingEnabled = false;
	ctx.drawImage(image, 0, 0, image.width, image.height);

	var idata = ctx.getImageData(0, 0, image.width, image.height);
	var array = new JA_I(image.width * image.height);
	//array.data = new Int32Array(idata.data.buffer);

	__convertPixels(idata.data, array.data, image.width * image.height, image.width, image.height);

	return {
		width: image.width,
		height: image.height,
		actualWidth: canvas.width,
		actualHeight: canvas.height,
		image: canvas,
		data: array
	};
}

function __decodeImageBytes(jbytes, offset, len, width, height) {
	var canvas = document.createElement('canvas');
	canvas.style.width = canvas.width = npot(width);
	canvas.style.height = canvas.height = npot(height);

	//canvas.style.width = canvas.width = width;
	//canvas.style.height = canvas.height = height;

	var ctx = canvas.getContext("2d");
	ctx.imageSmoothingEnabled = false;

	var img = document.createElement('img');
	var arrayBufferView = new Uint8Array(jbytes.data.buffer, offset, len);
	var blob = new Blob([arrayBufferView], { type: "image/jpeg" });
	var urlCreator = window.URL || window.webkitURL;
	var imageUrl = urlCreator.createObjectURL( blob );
	img.onload = function() {
		ctx.drawImage(img, 0, 0);
		// write data
	};
	img.onerror = function() {
	};
	img.src = imageUrl;

	return {
		width: width,
		height: height,
		image: canvas,
		actualWidth: canvas.width,
		actualHeight: canvas.height,
		data: new JA_I(width * height)
	};

}
// libgdx

// http://paulirish.com/2011/requestanimationframe-for-smart-animating/
// http://my.opera.com/emoller/blog/2011/12/20/requestanimationframe-for-smart-er-animating

// requestAnimationFrame polyfill by Erik Möller. fixes from Paul Irish and Tino Zijdel

// MIT license

(function() {
    var lastTime = 0;
    var vendors = ['ms', 'moz', 'webkit', 'o'];
    for(var x = 0; x < vendors.length && !window.requestAnimationFrame; ++x) {
        window.requestAnimationFrame = window[vendors[x]+'RequestAnimationFrame'];
        window.cancelAnimationFrame = window[vendors[x]+'CancelAnimationFrame']
                                   || window[vendors[x]+'CancelRequestAnimationFrame'];
    }

    if (!window.requestAnimationFrame)
        window.requestAnimationFrame = function(callback, element) {
            var currTime = new Date().getTime();
            var timeToCall = Math.max(0, 16 - (currTime - lastTime));
            var id = window.setTimeout(function() { callback(currTime + timeToCall); },
              timeToCall);
            lastTime = currTime + timeToCall;
            return id;
        };

    if (!window.cancelAnimationFrame)
        window.cancelAnimationFrame = function(id) {
            clearTimeout(id);
        };
}());

;(function() {
    var throttle = function(type, name, obj) {
        obj = obj || window;
        var running = false;
        var func = function() {
            if (running) { return; }
            running = true;
             requestAnimationFrame(function() {
                obj.dispatchEvent(new CustomEvent(name));
                running = false;
            });
        };
        obj.addEventListener(type, func);
    };

    /* init - you can init any event */
    throttle("resize", "optimizedResize");
})();

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
var app = null;

var libgdx = function() {
};

libgdx.initCanvas = function() {
	var canvas = document.getElementById('jtransc_canvas');
	if (!canvas) {
		canvas = document.createElement('canvas');
		canvas.id = 'jtransc_canvas';
		canvas.width = '640';
		canvas.height = '480';
		canvas.style.width = '640px';
		canvas.style.height = '480px';
		canvas.innerText = "Your browser doesn't appear to support the canvas element";
		document.body.appendChild(canvas);
		//console.log(gl);
	}

	gl = null;
	try {
		// Try to grab the standard context. If it fails, fallback to experimental.
		gl = canvas.getContext("webgl") || canvas.getContext("experimental-webgl");
	} catch(e) {
	}

	gl.clearColor(0.0, 0.0, 0.0, 1.0);
	gl.clear(gl.COLOR_BUFFER_BIT);
};

libgdx.init = function() {
	libgdx.initCanvas();

	app["{% METHOD com.jtransc.media.limelibgdx.LimeApplication:create %}"]();
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

var assets = {};
var assetsList = [];

function normalizePath(path) {
	return String(path).replace(/^\/+/, '').replace(/^assets\//, '');
}

function addAsset(path, size) {
	var info = { normalizedPath: normalizePath(path), path: path, size: size };
	assets[normalizePath(path)] = info;
	assetsList.push(info);
}
{% for asset in assetFiles %}
addAsset({{ asset.path|quote }}, {{ asset.size }});
{% end %}

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

// handle event
window.addEventListener("optimizedResize", function() {
    //console.log("Resource conscious resize callback!");
    //app["{% METHOD com.jtransc.media.limelibgdx.LimeApplication:resized:(II)V %}"](width, height);
});

downloadBytesList(assetsList.map(function(info) { return info.normalizedPath }), function(bytesList) {
	for (var n = 0; n < assetsList.length; n++) assetsList[n].bytes = bytesList[n];
	//console.log(assetsList);
	//console.log(assets);
	//window.requestAnimationFrame(libgdx.frame);
	libgdx.frame();
});

function _buffer(buffer, size) {
	console.log(buffer);
	console.log(size);
	throw 'Not implemented _buffer to typed array conversion yet! (TRY to avoid copying)';
}

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
var GL; // A global variable for the WebGL context
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

	GL = gl;

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

function ensureLimeInput() {
	{% SINIT com.jtransc.media.limelibgdx.LimeInput %}
}

//document.addEventListener('mousedown', function(event) {
//	alert('mousedown');
//}, false);

var javascriptKeyCodes = {
	BACKSPACE:	8,
	TAB:	9,
	ENTER:	13,
	SHIFT:	16,
	CTRL:	17,
	ALT:	18,
	PAUSE_BREAK:	19,
	CAPS_LOCK:	20,
	ESCAPE:	27,
	SPACE:	32,
	PAGE_UP:	33,
	PAGE_DOWN:	34,
	END:	35,
	HOME:	36,
	LEFT_ARROW:	37,
	UP_ARROW:	38,
	RIGHT_ARROW:	39,
	DOWN_ARROW:	40,
	INSERT:	45,
	DELETE:	46,
	NUM_0:	48,
	NUM_1:	49,
	NUM_2:	50,
	NUM_3:	51,
	NUM_4:	52,
	NUM_5:	53,
	NUM_6:	54,
	NUM_7:	55,
	NUM_8:	56,
	NUM_9:	57,
	A:	65,
	B:	66,
	C:	67,
	D:	68	 	,
	E:	69,
	F:	70,
	G:	71,
	H:	72,
	I:	73,
	J:	74,
	K:	75,
	L:	76,
	M:	77,
	N:	78,
	O:	79,
	P:	80,
	Q:	81,
	R:	82,
	S:	83,
	T:	84,
	U:	85,
	V:	86,
	W:	87,
	X:	88,
	Y:	89,
	Z:	90,
	LEFT_WINDOW_KEY:	91,
	RIGHT_WINDOW_KEY:	92,
	SELECT_KEY:	93,
	NUMPAD_0:	96,
	NUMPAD_1:	97,
	NUMPAD_2:	98,
	NUMPAD_3:	99,
	NUMPAD_4:	100,
	NUMPAD_5:	101,
	NUMPAD_6:	102,
	NUMPAD_7:	103,
	NUMPAD_8:	104,
	NUMPAD_9:	105,
	MULTIPLY:	106,
	ADD:	107,
	SUBTRACT:	109,
	DECIMAL_POINT: 110,
	DIVIDE:	111,
	F1:	112,
	F2:	113,
	F3:	114,
	F4:	115,
	F5:	116,
	F6:	117,
	F7:	118,
	F8:	119,
	F9:	120,
	F10:	121,
	F11:	122,
	F12:	123,
	NUM_LOCK:	144,
	SCROLL_LOCK:	145,
	SEMI_COLON:	186,
	EQUAL_SIGN:	187,
	COMMA:	188,
	DASH:	189,
	PERIOD:	190,
	FORWARD_SLASH:	191,
	GRAVE_ACCENT:	192,
	OPEN_BRACKET:	219,
	BACK_SLASH:	220,
	CLOSE_BRAKET:	221,
	SINGLE_QUOTE:	222,
};

var gdxKeyCodes = {
	ANY_KEY : -1,
	NUM_0 : 7,
	NUM_1 : 8,
	NUM_2 : 9,
	NUM_3 : 10,
	NUM_4 : 11,
	NUM_5 : 12,
	NUM_6 : 13,
	NUM_7 : 14,
	NUM_8 : 15,
	NUM_9 : 16,

	A : 29,
	B : 30,
	C : 31,
	D : 32,

	ALT_LEFT : 57,
	ALT_RIGHT : 58,
	APOSTROPHE : 75,
	AT : 77,
	BACK : 4,
	BACKSLASH : 73,
	CALL : 5,
	CAMERA : 27,
	CLEAR : 28,
	COMMA : 55,
	DEL : 67,
	BACKSPACE : 67,
	FORWARD_DEL : 112,
	DPAD_CENTER : 23,
	DPAD_DOWN : 20,
	DPAD_LEFT : 21,
	DPAD_RIGHT : 22,
	DPAD_UP : 19,
	CENTER : 23,
	DOWN : 20,
	LEFT : 21,
	RIGHT : 22,
	UP : 19,
	E : 33,
	ENDCALL : 6,
	ENTER : 66,
	ENVELOPE : 65,
	EQUALS : 70,
	EXPLORER : 64,
	F : 34,
	FOCUS : 80,
	G : 35,
	GRAVE : 68,
	H : 36,
	HEADSETHOOK : 79,
	HOME : 3,
	I : 37,
	J : 38,
	K : 39,
	L : 40,
	LEFT_BRACKET : 71,
	M : 41,
	MEDIA_FAST_FORWARD : 90,
	MEDIA_NEXT : 87,
	MEDIA_PLAY_PAUSE : 85,
	MEDIA_PREVIOUS : 88,
	MEDIA_REWIND : 89,
	MEDIA_STOP : 86,
	MENU : 82,
	MINUS : 69,
	MUTE : 91,
	N : 42,
	NOTIFICATION : 83,
	NUM : 78,
	O : 43,
	P : 44,
	PERIOD : 56,
	PLUS : 81,
	POUND : 18,
	POWER : 26,
	Q : 45,
	R : 46,
	RIGHT_BRACKET : 72,
	S : 47,
	SEARCH : 84,
	SEMICOLON : 74,
	SHIFT_LEFT : 59,
	SHIFT_RIGHT : 60,
	SLASH : 76,
	SOFT_LEFT : 1,
	SOFT_RIGHT : 2,
	SPACE : 62,
	STAR : 17,
	SYM : 63,
	T : 48,
	TAB : 61,
	U : 49,
	UNKNOWN : 0,
	V : 50,
	VOLUME_DOWN : 25,
	VOLUME_UP : 24,
	W : 51,
	X : 52,
	Y : 53,
	Z : 54,
	META_ALT_LEFT_ON : 16,
	META_ALT_ON : 2,
	META_ALT_RIGHT_ON : 32,
	META_SHIFT_LEFT_ON : 64,
	META_SHIFT_ON : 1,
	META_SHIFT_RIGHT_ON : 128,
	META_SYM_ON : 4,
	CONTROL_LEFT : 129,
	CONTROL_RIGHT : 130,
	ESCAPE : 131,
	END : 132,
	INSERT : 133,
	PAGE_UP : 92,
	PAGE_DOWN : 93,
	PICTSYMBOLS : 94,
	SWITCH_CHARSET : 95,
	BUTTON_CIRCLE : 255,
	BUTTON_A : 96,
	BUTTON_B : 97,
	BUTTON_C : 98,
	BUTTON_X : 99,
	BUTTON_Y : 100,
	BUTTON_Z : 101,
	BUTTON_L1 : 102,
	BUTTON_R1 : 103,
	BUTTON_L2 : 104,
	BUTTON_R2 : 105,
	BUTTON_THUMBL : 106,
	BUTTON_THUMBR : 107,
	BUTTON_START : 108,
	BUTTON_SELECT : 109,
	BUTTON_MODE : 110,
	NUMPAD_0 : 144,
	NUMPAD_1 : 145,
	NUMPAD_2 : 146,
	NUMPAD_3 : 147,
	NUMPAD_4 : 148,
	NUMPAD_5 : 149,
	NUMPAD_6 : 150,
	NUMPAD_7 : 151,
	NUMPAD_8 : 152,
	NUMPAD_9 : 153,
	COLON : 243,
	F1 : 244,
	F2 : 245,
	F3 : 246,
	F4 : 247,
	F5 : 248,
	F6 : 249,
	F7 : 250,
	F8 : 251,
	F9 : 252,
	F10 : 253,
	F11 : 254,
	F12 : 255
};

var keyCodeTransform = (function() {
	var out = {};
	for (var name in javascriptKeyCodes) {
		out[javascriptKeyCodes[name]] = gdxKeyCodes[name] || 255;
	}
	return out;
})();

function transformKeyCode(keyCode) {
	var result = keyCodeTransform[keyCode];
	return result || 255;
}

document.addEventListener('keydown', function(event) {
	ensureLimeInput()
	var kc = transformKeyCode(event.keyCode);
	console.log('keydown', event.keyCode, kc);
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onKeyDown %}(kc, 0);
}, false);

document.addEventListener('keyup', function(event) {
	ensureLimeInput()
	var kc = transformKeyCode(event.keyCode);
	console.log('keyup', event.keyCode, kc);
	{% SMETHOD com.jtransc.media.limelibgdx.LimeInput:lime_onKeyUp %}(kc, 0);
}, false);

downloadBytesList(assetsList.map(function(info) { return info.normalizedPath }), function(bytesList) {
	for (var n = 0; n < assetsList.length; n++) assetsList[n].bytes = bytesList[n];
	//console.log(assetsList);
	//console.log(assets);
	//window.requestAnimationFrame(libgdx.frame);
	libgdx.frame();
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
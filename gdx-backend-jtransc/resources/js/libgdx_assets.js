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

// List of assets
{% for asset in assetFiles %}
addAsset({{ asset.path|quote }}, {{ asset.size }});
{% end %}

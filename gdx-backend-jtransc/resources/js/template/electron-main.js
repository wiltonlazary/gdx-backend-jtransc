const {app, BrowserWindow} = require('electron');

let mainWindow;

//console.log('electron');

// Quit when all windows are closed.
app.on('window-all-closed', function() {
	if (process.platform != 'darwin') app.quit();
});

// In main process.
const {ipcMain} = require('electron');
ipcMain.on('console.log', (event, arg) => { console.log.apply(console, arg); });
ipcMain.on('console.warn', (event, arg) => { console.warn.apply(console, arg); });
ipcMain.on('console.error', (event, arg) => { console.error.apply(console, arg); });

/*
ipcMain.on('synchronous-message', (event, arg) => {
	console.log(arg);  // prints "ping"
	event.returnValue = 'pong';
});
*/

// This method will be called when Electron has done everything
// initialization and ready for creating browser windows.
app.on('ready', function() {
	// Create the browser window.
	mainWindow = new BrowserWindow({
		title: {{ title|quote }},
		width: {{ initialWidth }},
		height: {{ initialHeight }},
		autoHideMenuBar: true,
		frame: true,
	});

	// and load the index.html of the app.
	mainWindow.loadURL('file://' + __dirname + '/index.html');

	// Emitted when the window is closed.
	mainWindow.on('closed', function() {
		// Dereference the window object, usually you would store windows
		// in an array if your app supports multi windows, this is the time
		// when you should delete the corresponding element.
		mainWindow = null;
	});

	mainWindow.on('unresponsive', function() {
		console.log('Window got unresponsive!');
	});

});

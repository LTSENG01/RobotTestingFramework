import { startClient, stopClient } from "networktables";

const { app, BrowserWindow } = require('electron');

let win;    // global reference

function createWindow () {
    startClient();
    // Create the browser window and load the index.html of the app.
    win = new BrowserWindow({width: 800, height: 600});
    win.loadFile('index.html');
}

app.on('ready', createWindow);

// Quit when all windows are closed.
app.on('window-all-closed', () => {
    stopClient();
    // On macOS it is common for applications and their menu bar
    // to stay active until the user quits explicitly with Cmd + Q
    if (process.platform !== 'darwin') {
        app.quit();
    }
});

app.on('activate', () => {
    // On macOS it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (win === null) {
        createWindow();
    }
});
const { app, BrowserWindow, ipcMain } = require('electron');

/** ---- Electron Stuff ---- **/

let win;    // global reference

function createWindow() {
    // Create the browser window and load the index.html of the app.
    win = new BrowserWindow({width: 800, height: 600});
    win.loadFile('index.html');
}

app.on('ready', createWindow);

// Quit when all windows are closed.
app.on('window-all-closed', () => {
    // Do not quit if on macOS (need to Cmd + Q)
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

ipcMain.on('console', (event, args) => {
   console.log(args);
});
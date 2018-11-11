const ipcRenderer = require('electron').ipcRenderer;
const nt = require('wpilib-nt-client');
const $ = require('jquery');


/** ---- NetworkTables Client ---- **/

const client = new nt.Client();

function startClient(address) {
    ipcRenderer.send('console', "Connecting to the NT client!");
    client.start((isConnected, err) => {
        console.log({isConnected, err});

        if (isConnected) {
            $('#robot-status').text("Connected")
        } else if (!isConnected && err == null) {
            $('#robot-status').text("Disconnected");
        } else {
            $('#robot-status').text("Error Connecting! " + err)
        }

    }, address, 1735);
}

function stopClient() {
    client.stop();
    ipcRenderer.send('console', "Disconnected from robot!");
}

client.addListener((key, val) => {
    ipcRenderer.send('console', { key, val });
});

/*

Network Tables Tree Structure

Root
    - SmartDashboard
        - ...
    - LiveWindow
        - ...
    - Jetson
        - ...
    - Telemetry
        - ...
    - RobotTestingFramework
        - status
            - running: Boolean
            - currentTest: ""
            - instructions: ""
        - availableTests: ["", "", ...]
        - testsToRun: ["", "", ...]
        - finishedTests
            - test1: ["Name", "Result", "Actual", ...]
            - ...

 */

/** ---- UI Elements ---- **/

$(document).ready(() => {

    $('#connect-button').click(() => {
        let address = $('#connect-address').val();
        startClient(address);
    });

    $('#disconnect-button').click(() => {
        stopClient();
    });

});

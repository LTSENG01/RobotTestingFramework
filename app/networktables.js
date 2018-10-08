const nt = require('wpilib-nt-client');
const client = new nt.Client();

const address = '10.17.57.2';

export function startClient() {
    client.start((isConnected, err) => {
        console.log({isConnected, err});
    }, address);
}


export function stopClient() {
    client.stop();
}
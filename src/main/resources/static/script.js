let ws;

function connect() {
    ws = new WebSocket("ws://localhost:8080/chat");
    ws.onmessage = function (e) {
        printMessage(e.data);
    }
    document.getElementById("connectButton").disabled = true;
    document.getElementById("connectButton").value = "Connected";
    document.getElementById("name").disabled = true;
    document.getElementById("disConnectButton").value = "Disconnect";
    document.getElementById("disConnectButton").disabled = false;
}

function disconnect() {
    if (ws != null) {
        ws.close();
    }
    setConnected(false);
    console.log("Websocket is in disconnected state");
}

function setConnected(connected) {
    document.getElementById("connectButton").value = "Connect";
    document.getElementById("connectButton").disabled = connected;
    document.getElementById("disConnectButton").value = "Disconnected";
    document.getElementById("disConnectButton").disabled = !connected;
    document.getElementById("name").disabled = connected;
}

function printMessage(data) {
    let messages = document.getElementById("messages");
    let messageData = JSON.parse(data);
    let newMessage = document.createElement("div");
    newMessage.innerHTML = messageData.name + " : " + messageData.message;
    messages.appendChild(newMessage);
}

function sendToGroupChat() {
    let messageText = document.getElementById("message").value;
    document.getElementById("message").value="";
    let name = document.getElementById("name").value;
    let messageObject = {
        name: name,
        message: messageText
    }
    ws.send(JSON.stringify(messageObject))
}
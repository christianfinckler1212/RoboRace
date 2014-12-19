var WebSocketServer = require('ws').Server
var messageProcessor = require('./messageProcessor.js');
var wss = new WebSocketServer({host: 'localhost', port: 8000});

module.exports = {
  connect: function (datamodel) {
    var connectedClients = datamodel.getConnectedClients();
    console.log(connectedClients);
    wss.on('connection', function(ws) {
        connectedClients.push({
            webSocketConnection: ws,
            id: connectedClients.length
        });
        
        ws.on('message', function incoming(message) {
            var jsonMessage = JSON.parse(message);
            console.log('received: %s', jsonMessage.eventtype);
            var connectedClient = getClientByWsConnection(ws);
            if (jsonMessage.eventtype === "connect") {
              messageProcessor.processConnect(connectedClient, jsonMessage);
            }
            else if (jsonMessage.eventtype === "speed") {
              messageProcessor.processSpeed(connectedClient, jsonMessage);
            }
            else if (jsonMessage.eventtype === "selectRobo") {
              messageProcessor.processRoboSelected(connectedClient, jsonMessage, datamodel);
            }
            else if (jsonMessage.eventtype === "ready") {
              //code  
            } else {
              ws.send(JSON.stringify({eventtype: "error", data: { message: "unknownEventtype"}}));
            }
          for (i in connectedClients) {
              console.log(connectedClients[i]);
          };            
        });
        
        
        
        ws.on('close', function(ws) {
            console.log("Client disconnected ...");
        });
        
        function getClientByWsConnection(ws){
            for (i = 0; i <= connectedClients.length; i++) {
                if (connectedClients[i].webSocketConnection === ws) {
                    return connectedClients[i];
                }
            }
            return null;
        }
    });
  }
};
var processConnect = function(connectedClients, connectedClient, jsonMessage){
        connectedClient.data.name = jsonMessage.data.name;
        connectedClient.data.type = jsonMessage.data.clientType;
        connectedClient.data.ready = jsonMessage.data.ready;
        for(i in connectedClients) {
            var roboDCName = connectedClient.data.name + "(dc)";
            if(connectedClients.length > 1 && connectedClients[i].data.selectedRobo != undefined) {
                if(connectedClients[i].data.selectedRobo.name === roboDCName) {
                    console.log("equal: " + connectedClients[i].data.selectedRobo.name + " / " + roboDCName);
                    var restoreName = connectedClients[i].data.selectedRobo.name;
                    restoreName = restoreName.substring(0, restoreName.length -5);
                    connectedClients[i].data.selectedRobo.name = restoreName;
                    connectedClients[i].data.ready = "true";
                }
            }
        }
        //console.log(connectedClient.type);
    },
    processSpeed = function(roboclient, jsonMessage){
        roboclient.data.speed = jsonMessage.data.speed;
        //console.log(connectedClient.speed);
    },
    processRoboSelected = function(appclient, jsonMessage, datamodel){
        var controlledRobot = datamodel.getClientByName(jsonMessage.data.robo);
        appclient.data.selectedRobo = controlledRobot.data;
        controlledRobot.data.controlledBy = appclient.data.name;
        controlledRobot.webSocketConnection.send(JSON.stringify( {
            eventType: "selectedBy",
            data: {
                playerName: appclient.name 
            }
        }));  
        appclient.webSocketConnection.send(JSON.stringify( {
            eventType: "assignment",
            data: {
               roboName: controlledRobot.name 
           } 
        })); 
    },
    processReady = function(appclient, jsonMessage){
        appclient.data.ready = jsonMessage.data.ready;
    };

module.exports = {
    processConnect: processConnect,
    processSpeed: processSpeed,
    processRoboSelected: processRoboSelected,
    processReady: processReady
};
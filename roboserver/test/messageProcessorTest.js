var assert = require('assert'),
    sinon = require('sinon'),
    messageProccesor = require('../messageProcessor.js');

describe('messageProcessor', function(){
    describe('connect', function(){     
        it('should takeover the values into the client connection', function(){
            // given
            var connectedClient = {"data": {}},
                jsonMessage = {"eventType": "connect", "data": {"clientType": "Test", "name": "TestUnit" }};
            // when
            messageProccesor.processConnect(connectedClient, jsonMessage);
            // then
            assert.equal('TestUnit', connectedClient.data.name);
            assert.equal('Test', connectedClient.data.type);
        })
    })
    describe('speed', function(){
        it('should takeover a speedvalue into the connected client', function(){
            // given
            var connectedClient = {"data": {}},
                jsonMessage = {"eventType": "speed", "data": {"speed": "800"}};
            // when
            messageProccesor.processSpeed(connectedClient, jsonMessage);
            // then
            assert.equal("800", connectedClient.data.speed);
        })
    })
    describe('selectRobo', function(){
        it('should set selected Robo into the connected client', function(){
            // given
            var connectedClient = {"webSocketConnection": {send: sinon.spy()}, "data": {"clientType": "app", "name": "Sascha"}},
                selectedClient = {"webSocketConnection": {send: sinon.spy()}, "data": {"clientType": "robo", "name": "rob0ne"}},
                jsonMessage = { "eventType": "selectRobo", "data": { "robo": "rob0ne"}},
                datamodel = {
                    getClientByName: function(){
                        return selectedClient;
                    }
                };
            // when
            messageProccesor.processRoboSelected(connectedClient, jsonMessage, datamodel);
            // then
            assert.equal("rob0ne", connectedClient.data.selectedRobo.data.name);
            assert(selectedClient.webSocketConnection.send.called);
            assert(connectedClient.webSocketConnection.send.called);
        })
    })
})
process.title = "roboserver";

var express = require('express');
var app = express();
var path = require('path');
var websocket = require('./websocket.js');
var datamodel = require('./datamodel.js');
var routes = require('./routes/index');
var app = express();


websocket.connect(datamodel);

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(function(req,res,next){
    req.datamodel = datamodel;
    next();
});

app.use('/', routes);
app.use(express.static(path.join(__dirname, 'public')));

app.listen(3000);

if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});

module.exports = app;


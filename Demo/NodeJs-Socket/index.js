const app = require('express')();
const http = require('http').createServer(app);
const io = require('socket.io')(http)
const bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: true }));

app.use(bodyParser.json());

app.get('/', (req, res) => {
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', (socket) => {
    console.log('a user connected');
    socket.on('disconnect', () => {
        console.log('a user disconnected');
    });

    socket.on('chat message', (message) => {
        console.log(message);
        io.emit('chat message', message);
    });

});

http.listen(3000, () => {
  console.log('listening on *:3000');
});

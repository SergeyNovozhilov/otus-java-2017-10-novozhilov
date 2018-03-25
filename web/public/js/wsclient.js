var ws = new WebSocket("ws://localhost:9090/app/admin");

init = function () {
    console.log("Init ")
}

ws.onopen = function() {
    console.log("Opened!")
};

ws.onopen = function (event) {
    console.log("JS Connected");
}

ws.onmessage = function (event) {
   var header = '<h2>Cache data:</h2>';
   var list = '';
   document.getElementById('Data').innerHTML = list;

   var data = JSON.parse(event.data);

   list += '<li>hit: ' + data.hit + ' </li>';
   list += '<li>miss: ' + data.miss + ' </li>';
   list += '<li>lifeTime: ' + data.lifeTime + ' </li>';
   list += '<li>idleTime: ' + data.idleTime + ' </li>';
   list += '<li>max: ' + data.max + ' </li>';



   document.getElementById('Data').innerHTML += header;
   document.getElementById('Data').innerHTML += '<ul>' + list + '</ul>';
}


ws.onclose = function (event) {
}

function sendMessage() {

    if (ws === null) {
        console.log("ws === null");
    } else  {
        ws.send("get");
    }
}
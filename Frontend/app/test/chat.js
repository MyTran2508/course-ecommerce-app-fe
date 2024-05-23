const over = require("stompjs").over;
const SockJS = require("sockjs-client");

// Rest of the code...
const url_success = "http://localhost:8080/ws";
const url_test = "http://localhost:8081/ws/courses";
const connect = () => {
  let Sock = new SockJS(url_test);
  stompClient = over(Sock);
  stompClient.connect({}, onConnected, onError);
};

const onConnected = () => {
  console.log("Connected");
};
const onError = (err) => {
  console.log(err);
};

connect();

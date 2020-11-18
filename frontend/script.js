const getButton = document.getElementById('get');
const postButton = document.getElementById('post');

getButton.addEventListener('click', () => {
    fetch('http://localhost:8080/feed/posts')
    .then( (res) => {
        console.log(res.json());
    })
    .catch( (error) => {
        console.log(error);
    });
});

let httpRequest = new XMLHttpRequest(); //第一步：创建需要的对象
let obj = {
    MD5Password: "asdfsdfasdfasdfasdfsdf",
    numOfWorker: "5"
}

httpRequest.onreadystatechange = function () {
    if (httpRequest.readyState == 4 && httpRequest.status == 200) {
        let json = httpRequest.responseText; 
        console.log(json);
    }
};

postButton.addEventListener('click', () => {
  console.log("here");
  httpRequest.open("POST", "http://localhost:8080/feed/post", true); 
  httpRequest.setRequestHeader("Content-type", "application/json"); 
  console.log(typeof(JSON.stringify(obj)));
  httpRequest.send(JSON.stringify(obj)); 
});


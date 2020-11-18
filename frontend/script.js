const getButton = document.getElementById('get');
const postButton = document.getElementById('post');

getButton.addEventListener('click', () => {
    fetch('http://localhost:8080/feed/posts')
    .then( (res) => {
        console.log(res.json());
    })
    .catch( (error) => {
        console.log(error);
    })
});


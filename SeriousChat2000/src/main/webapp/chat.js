
class Chat {
    constructor() {
        this.chat = document.querySelector("#chat");
        this.usr = document.querySelector('#usr');
        this.msg = document.querySelector("#msg");


        this.name = new URL(document.URL).searchParams.get("name");
        //this.loadImage(this.name);

        this.msg.onchange = event => {
            fetch('api/chat/add?name=' + this.name,
                    {
                        method: 'POST',
                        body: JSON.stringify(new Message(this.usr.value, this.msg.value)),
                        headers: {'Content-Type': 'application/json; charset=UTF-8'}
                    })
                    .then(response => {
                        if (response.ok) {
                            return response.json();
                        }

                        throw new Error("Failed to send message " + event.target.value);
                    })
                    .then(message => {
                        this.msg.value = "";
                    })
                    .catch(exception => console.log("Error: " + exception));
        };

        this.worker = new Worker("worker.js");
        this.worker.postMessage({"name": this.name});

        // Stuff to run when Enter is pressed
        this.worker.onmessage = event => {
            this.chat.innerHTML = '';
            let article = document.createElement('article');
            event.data.map(message =>
            {
                var msg = message.text;
                var wordList = msg.split(" ");

                // Look if message contains a URL
                for (let i = 0; i < wordList.length; i++)
                {
                    var word = wordList[i];
                    var firstFourLetters = word.substring(0, 4).toLowerCase();
                    if (firstFourLetters === "http")
                    {
                        // Look if URL contains a picture file extension
                        var jpg = word.search('.jpg');
                        var jpeg = word.search('.jpeg');
                        var png = word.search('.png');
                        if (jpg !== -1 || jpeg !== -1 || png !== -1)
                        {
                            msg = msg.replace(word, '<br><a href="' + word + '" target="_blank"><img src="' + word + '"></a>');
                        }
                    }
                }

                // Print out the chat message
                let chatMessage = document.createElement('p');
                chatMessage.innerHTML = `${message.user}` + ': ' + msg;
                article.appendChild(chatMessage);
            });
            this.chat.appendChild(article);
            this.chat.scrollTop = this.chat.scrollHeight;
        };
    }
}

class Message {
    constructor(user, text) {
        this.user = user;
        this.text = text;
        this.version = new Date();
    }
}

let chat = new Chat();

class ChatRoom
{
    constructor()
    {
        this.chatList = document.querySelector('#chatlist');
        this.load();
    }

    load()
    {
        fetch('api/chat/conversations')
                .then(response =>
                {
                    if (response.ok)
                    {
                        return response.json();
                    }
                    throw new Error('Loading of chat list FAILED.');
                })
                .then(json => this.addChats(json))
                .catch(e => console.log("Error: " + e.message));
    }
    
    addChats(json)
    {
        this.chatList.innerHTML = '';
        for (let i = 0; i < json.length; i++)
        {
            let chatButton = document.createElement('p');
            chatButton.classname = 'button';
            chatButton.innerHTML = 'Chat #' + json[i].id;
            
            let a = document.createElement('a');
            a.href = 'chat.html?name=' + json[i].id;
            a.appendChild(chatButton);
            
            this.chatList.appendChild(a);
        }
    }
}

let chatRoom = new ChatRoom();


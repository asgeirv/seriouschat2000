class ChatRoom
{
    constructor()
    {
        this.chat = document.querySelector("#chat");
        this.load();
    }

    load()
    {
        fetch('api/chat')
                .then(response =>
                {
                    if (response.ok)
                    {
                        return response.json;
                    }
                    throw new Error('Loading of chat list FAILED.');
                }
                )
                .then(json => this.addChats(json))
                .catch(e => console.log("Error: " + e.message));
    }
    
    addChats(json)
    {
        this.chats.innerHTML = '';
        for (let i = 0; i < json.length; i++)
        {
            let chatButton = document.createElement('p');
            chatButton.classname = 'button';
            chatButton.innerHTML = 'Chat ' + json[i].name;
            
            let a = document.createElement('a');
            a.href = 'chat.html?name=' + json[i].name;
            a.appendChild(chatButton);
            
            this.chats.appendChild(a);
        }
    }
}


function getMessages()
{
    fetch('api/chat?name=' + '1')
            .then(response =>
            {
                if (response.ok)
                {
                    return response.json();
                }
                throw new Error("Failed to load message from " + this.name);
            })
            .then(messages =>
            {
                postMessage(messages);
            })
            .catch(e => console.log(e));

    setTimeout("getMessages()", 1000);
}

self.addEventListener("message", event =>
        {
            this.name = event.data.name;
        }, false);

getMessages();
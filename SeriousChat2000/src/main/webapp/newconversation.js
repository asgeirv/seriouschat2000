fetch('api/chat/newconversation')
        .then(response =>
        {
            if (response.ok)
            {
                return response.json();
            }
            throw new Error('Loading of chat list FAILED.');
        });
window.location.replace('index.html');

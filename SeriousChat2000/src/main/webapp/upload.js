let uploadFrame = document.querySelector('#uploadframe');
var url;

function previewFile()
{
    var preview = document.querySelector('#preview');
    var file = document.querySelector('input[type=file]').files[0];
    var reader = new FileReader();

    reader.onloadend = function ()
    {
        preview.src = reader.result;
    };

    if (file)
    {
        url = reader.readAsDataURL(file);
        console.log(url);
    } else
    {
        preview.src = '';
    }
}

function uploadFile ()
{
    
    hideUploadFrame();
}

function showUploadFrame()
{
    uploadFrame.style.visibility = 'visible';
}

function hideUploadFrame()
{
    uploadFrame.style.visibility = 'hidden';
}
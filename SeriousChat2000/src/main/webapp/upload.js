let uploadFrame = document.querySelector('#uploadframe');

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
        reader.readAsDataURL(file);
    } else
    {
        preview.src = '';
    }
}

function showUploadFrame()
{
    uploadFrame.style.visibility = 'visible';
}

function hideUploadFrame()
{
    uploadFrame.style.visibility = 'hidden';
}
$(document).ready(function () {
    $('#problemContent').summernote({
        placeholder: 'Write down your problem here',
        tabsize: 2,
        height: 300,
        codemirror: { // codemirror options
            theme: 'monokai'
        }
    });
});
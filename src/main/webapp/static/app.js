var stompClient = null;
connect();


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        showCodeListTable();
    }
    else {
        hideCodeListTable();
    }
    $("#codes").html("");
}

function showCodeListTable() {
    $("#CodeListTable").show();
}

function hideCodeListTable() {
    $("#CodeListTable").hide();
}

//     <img src="https://mdbootstrap.com/img/Photos/Horizontal/Nature/6-col/img%20(131).jpg" class="img-fluid " alt="smaple image">
//     <div class="mask flex-center">
//     <p class="white-text">Zoom effect</p>
// </div>



function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/codelist', function (codelist) {
            var obj=JSON.parse(codelist.body);
            if(obj.userFind){
                $("#codes").html("");
                obj.codeList.forEach(
                    function (code) {
                        $("#codes").append("<tr id="+code.code_id+"><td>" + code.code_title + "</td><td>"+code.code_author+"</td></tr>");

                        $("#"+code.code_id).click(function () {
                            console.log(code.code_id)//TODO:to code page
                        });
                    }
                );
            }else{
                $("#codes").html("<tr><td>no code aviliable</td></tr>")
            }
        });


        stompClient.send("/app/codelist", {}, JSON.stringify({'username': frame.headers["user-name"]}));
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/codelist", {}, JSON.stringify({'username': $("#name").val()}));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});
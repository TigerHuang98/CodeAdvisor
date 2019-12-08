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

function showCodeListPage() {
    $("#codeListPage").show();
}

function hideCodeListPage() {
    $("#codeListPage").hide();
}

function showCodeDetailPage() {
    $("#codeDetailPage").show();
}

function hideCodeDetailPage() {
    $("#codeDetailPage").hide();
}



function gotoCodeDetails(code_id) {
    hideCodeListPage();
    showCodeDetailPage();
    stompClient.subscribe('/user/codeDetail', function (codeDetail) {
        var obj=JSON.parse(codeDetail.body);
        if(obj.codeFind){
            $("#codeID").html(obj.code.id);
            $("#codeTitle").html(obj.code.title);
            $("#code").html(obj.code.content);
            PR.prettyPrint();
            $("#comments").html("");
            if(obj.commentList.length>0){
                obj.commentList.forEach(
                    function (comment) {
                        $("#comments").append("<tr><td>" + comment.user.username + "</td><td>"+comment.commentContent+"</td></tr>");
                    }
                );
            }else{
                $("#comments").html("<tr id='dummyComment'><td>no comment available</td></tr>")
            }
            codeID=$("#codeID").html();
            stompClient.subscribe('/commentForCode'+codeID, function (realTimeComment) {
                var obj=JSON.parse(realTimeComment.body);
                if(obj.commentSuccess){
                    $("#dummyComment").remove();
                    $("#comments").append("<tr><td>" + obj.comment.user.username + "</td><td>"+obj.comment.commentContent+"</td></tr>");
                }
            });
        }else{
            $("#comments").html("<tr><td>no comment available</td></tr>");//no code
        }
    });
    stompClient.send("/app/codeDetail", {}, JSON.stringify({'codeID': code_id}));
}

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
                            gotoCodeDetails(code.code_id);
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
//TODO:back&unsubscribe
function sendComment() {
    stompClient.send("/app/commentForCode"+codeID, {}, JSON.stringify({'commentToSend': $("#commentToSend").val()}));
    $("#commentToSend").val('');
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#sendComment" ).click(function() { sendComment(); });
});
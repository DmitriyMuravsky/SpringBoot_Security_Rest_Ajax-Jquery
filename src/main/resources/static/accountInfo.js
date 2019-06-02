$(document).ready(function () {
    getUserInfo();
});

function getUserInfo() {
    var roles = [];
    $.ajax({
        type: "GET",
        url: "/user/getInfo/",
        contentType: "application/json",
        success: function (data) {
            var user = JSON.parse(JSON.stringify(data));
            roles = user.roles;
            for (var i in roles) {
                if (roles[i].name === 'admin') {
                   adminUppend();
                   break;
                } else {
                   userUppend();
                }
            } 
            $("#userNameProfile").html('Welcome, ' + user.name);
        },
        error: function (data) {
            console.log(data);
        }
    });
}
function adminUppend() {
    $("#v-pills-tab").append(
        "<a onclick='returnToUsersList()' class=\"nav-link\" data-toggle=\"pill\" id=\"returnUsersList\" href=\"/admin/getUsers\" role=\"tab\" aria-controls=\"v-pills-home\" aria-selected=\"true\">Admin</a> \
        <a class=\"nav-link active\" id='userInfo' data-toggle=\"pill\" href=\"#user_profile\" role=\"tab\" aria-controls=\"v-pills-user\" aria-selected=\"true\">User</a>"
    );
}
function returnToUsersList() {
        $.ajax({
            type: "GET",
            url: "/admin/getUsers",
            contentType: "application/json",
            success: function (data) {
                window.location.replace("/admin/getUsers");
            },
            error: function (data) {
                console.log(data);
            }
        });
}
function userUppend() {
    $("#v-pills-tab").append(
        "<a class=\"nav-link active\" data-toggle=\"pill\" href=\"#user_profile\" role=\"tab\" aria-controls=\"v-pills-home\" aria-selected=\"true\">User</a> \
         <a class=\"nav-link\" id='userInfo' data-toggle=\"pill\" href=\"#nothing\" role=\"tab\" aria-controls=\"v-pills-user\" aria-selected=\"true\">Nothing</a>"
    )
}

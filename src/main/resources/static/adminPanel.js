$(document).ready(function () {

    asignDataToTable();
    getRoles();

    $("#addNewUser").on('click', function () {
        var completeRoles;
        var roleId = [];
        $("#newUser-role option:selected").each(function () {
            roleId.push({id: $(this).attr("data-role-id")});
        });
        completeRoles = JSON.parse(JSON.stringify(roleId));
        var jsonVar = {
            name: $("#InputName").val(),
            login: $("#InputLogin").val(),
            password: $("#InputPassword").val(),
            roles: completeRoles
        };
        $.ajax({
            type: "POST",
            url: "/rest/admin/user",
            data: JSON.stringify(jsonVar),
            contentType: "application/json",
            success: function (data) {
                asignDataToTable();
            },
            error: function (err) {
                console.log(err);
            }
        });
    });

    $("#btnEditUser").on('click', function () {
        var completeRoles;
        var roleId = [];
        $("#userRole option:selected").each(function () {
            roleId.push({id: $(this).attr("data-role-id")});
        });
        completeRoles = JSON.parse(JSON.stringify(roleId));
        var id = $("#userId").val();
        var editUser = {
            name: $("#userName").val(),
            login: $("#userLogin").val(),
            password: $("#userPassword").val(),
            roles: completeRoles
        };
        $.ajax({
            type: "PUT",
            url: "/rest/admin/user/" + id,
            data: JSON.stringify(editUser),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                alert(data);
                window.location.assign("http://localhost:8080/admin/getUsers");
                //asignDataToTable();
            },
            error: function (err) {
                console.log(err);
            }
        });
    });

    $("#v-pills-tab #v-pills-user-tab").on('click', function () {
        $('#v-pills-tab #v-pills-user-tab').removeClass('active');
        $(this).addClass('active');
        $.ajax({
            type: "GET",
            url: "/user/accountInfo",
            contentType: "application/json",
            success: function (data) {
                window.location.replace("/user/accountInfo");
            },
            error: function (data) {
                console.log(data);
            }
        });
    })

    $("#btnCloseEdit").on('click', function () {
        location.reload();
    })
});

function asignDataToTable() {
    $("#bodyListOfUsers").empty();
    $.ajax({
        type: "GET",
        url: "/rest/admin/users",
        contentType: "application/json",
        success: function(data) {
            var users = JSON.parse(JSON.stringify(data));
            for (var i in users) {
                        $("#bodyListOfUsers").append(
                            "<tr> \
                            <th class=\"font-weight-normal\">" + users[i].id + "</th> \
                            <th class=\"font-weight-normal\">"  + getUserRoles(users[i].roles) + "</th> \
                            <th class=\"font-weight-normal\">" + users[i].login + "</th>> \
                            <th class=\"font-weight-normal\">" + users[i].password + "</th> \
                            <th class=\"font-weight-normal\">" + users[i].name + "</th> \
                            <th> <button onclick='openModalEdit(" + users[i].id + ")' type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#edit\">Edit</button> </th> \
                            <th> <button onclick='openModalDelete(" + users[i].id +")' type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" data-target=\"#delete\" data-user-id=" + users[i].id + ">Delete</button> </th> \
                            </tr>"
                        );
            }
        }, 
        error: function (data) {
            console.log(data);
        }
    });
}
function getUserRoles(roles) {
    var userRoles = [];
    for (var i in roles) {
        userRoles[i] = roles[i].name;
    }
    return userRoles;
}
//добавление списка ролей в форму создания пользователей
function getRoles() {
    $.ajax({
        type: "GET",
        url: "/rest/admin/roles",
        contentType: "application/json",
        success: function (data) {
            var roles = JSON.parse(JSON.stringify(data));
            for (var i in roles) {
                $("#newUser-role, #userRole").append(
                    "<option data-role-id=" + roles[i].id + ">" + roles[i].name + "</option>"
                );
            }
        },
        error: function (data) {
            console.log(data);
        }
    });
}
function openModalEdit(id) {
    $.ajax({
        type: "GET",
        url: "/rest/admin/user/" + id,
        contentType: "application/json",
        success: function (data) {
            var user = JSON.parse(JSON.stringify(data));
            var roleName = getUserRoles(user.roles);
            $("#editModalLabel").html('Edit user ' + user.login);
            $("#userId").val(user.id);
            $("#userName").val(user.name);
            $("#userLogin").val(user.login);
            $("#userPassword").val(user.password);
            $("#userRole option").each(function () {
                for (var i in roleName) {
                    if ($(this).text() === roleName[i]) {
                        $(this).prop('selected', true);
                    } else {
                        $(this).click('selected', false);
                    }
                }
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
}

function openModalDelete(id) {
    $.ajax({
        type: "GET",
        url: "/rest/admin/user/" + id,
        contentType: "application/json",
        success: function (data) {
            var user = JSON.parse(JSON.stringify(data));
            $("#deleteModalLabel").html('Are you sure delete: ' + user.login + '?');
        },
        error: function (data) {
            console.log(data)
        }
    });
    $("#btnDeleteUser").on('click', function () {
        $.ajax({
            type: "DELETE",
            url: "/rest/admin/user/" + id,
            contentType: "application/json",
            success: function (data) {
                location.replace("http://localhost:8080/admin/getUsers");
                //asignDataToTable();
            },
            error: function (data) {
                console.log(data)
            }
        });
    })
}

$(document).ready(function () {

    var roleCheckbox = $('#role-checkbox');
    roleCheckbox.click(useRoleChooser);
    var isChecked = false;
    var roleChooserSelect = $('#search-role-chooser');

    function useRoleChooser() {
        if (!isChecked) {
            roleChooserSelect.removeAttr('disabled');
            isChecked = true;
        } else {
            roleChooserSelect.attr('disabled', 'disabled');
            isChecked = false;
        }
        console.log("isChecked = " + isChecked);
    }

    var idField = $('#new-id');
    var emailField = $('#new-email');
    var nameField = $('#new-name');
    var surnameField = $('#new-surname');
    var phoneField = $('#new-phone');
    var groupIdField = $('#new-groupId');

    $('[id $= edit]').click(editUserBtnHandler);

    //Handler for button from users' table
    function editUserBtnHandler() {
        var editBtn = $(this);

        console.log("EDITuser; buttonId = " + editBtn.attr('id'));
        var id = parseInt(editBtn.attr('id'));
        console.log("Id = " + id);
        idField.val(id);
        emailField.val($('#' + id + 'email').text());
        nameField.val($('#' + id + 'name').text());
        surnameField.val($('#' + id + 'lastName').text());
        phoneField.val($('#' + id + 'phone').text());
        groupIdField.val($('#' + id + 'groupId').text());
    }

    $('#editUser').click(editUser);

    //Handler for button from user's update form
    function editUser() {
        console.log("editUser сработала!");
        var id = $('#new-id').val();
        var role = $('#new-role').val();
        var user = {
            loginEmail: $('#new-email').val(),
            name: $('#new-name').val(),
            lastName: $('#new-surname').val(),
            phone: $('#new-phone').val(),
            groupId: $('#new-groupId').val(),
            orderList: []
        };
        console.log("user = " + user.loginEmail + " - " + user.lastName + " - " + user.name + " - " + user.phone + " - ");
        console.log("id = " + id);
        console.log("role = " + role);
        $.ajax({
            url: "../admin/editUser",
            type: "POST",
            data: {
                loginEmail: user.loginEmail,
                name: user.name,
                lastName: user.lastName,
                phone: user.phone,
                groupId: user.groupId,
                id: id,
                role: role
            },
            success: onAjaxSuccess
        });
    }

    function onAjaxSuccess(data) {
        console.log("data.toString() = " + data.toString() + "!");
        var itemsForRewrite = data.split("-..-");
        /*$.each(itemsForRewrite, function(index, item){
         console.log(index + " - " + item);
         });*/
        var id = itemsForRewrite[0];
        $('#' + id + "lastName").text(itemsForRewrite[3]);
        $('#' + id + "name").text(itemsForRewrite[2]);
        $('#' + id + "phone").text(itemsForRewrite[4]);
        $('#' + id + "groupId").text(itemsForRewrite[5]);

        idField.val("");
        emailField.val("");
        nameField.val("");
        surnameField.val("");
        phoneField.val("");
        groupIdField.val("");
    }

    $('[id $= delete]').click(deleteUser);

    function deleteUser() {
        var deleteBtn = $(this);
        console.log("DELETEuser; buttonId = " + deleteBtn.attr('id'));
        var id = parseInt(deleteBtn.attr('id'));
        console.log("digitId = " + id);

        $.post(
            "../admin/deleteUser",
            {id: id},
            onPostSuccess
        );

        function onPostSuccess(data) {
            console.log('data = ' + data);
            $('#' + data + 'delete').parent().parent().remove();
        }
    }

});


<%@page import="uit.pubguru.model.UserModel"%>
<!--@author: Huong Tran-->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="uit.pubguru.controller.CaptchaController" %>
<%@page import="uit.pubguru.controller.UserController" %>
<%@page import="uit.pubguru.constant.PubGuruConst" %>
<%
            String username = (String) session.getAttribute("username");
            if (username == null) {
                 RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                dispatcher.forward(request, response);
            }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Publication Guru</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link href="css/mystyle.css" rel="stylesheet" type="text/css" />
        <script language="javascript" src="javascript/jquery-1.7.1.min.js"></script>
        <script language="javascript" src="javascript/myscripts.js"></script>
    </head>

    <body>
       
            <div class="content">
                  <div class="content_wrapper">
                    <div class="register_wrapper">
                        <%=username%>
                        <form  method="POST" class="c_form" name="changePass_form">
                            <table style="margin: auto;" >
                                <tbody>
                                    <div class="warning" id="changeSuccess">Change password success.</div>
                                    <tr valign="top">
                                        <td class="label">Old password</td>
                                        <td class="content">
                                            <input class="require" type="text" value="" maxlength="100" name="txt_oldPassword" id="txt_oldPassword"/><span class="require_field">*</span>
                                            <div class="warning" id="oldPasswordLenghtWarning">
                                                Password is least 6 character.
                                            </div>
                                            <div class="warning" id="oldPasswordMatchtWarning">
                                                Current password is not match
                                            </div>
                                        </td>
                                    </tr>
                                    <tr valign="top">
                                        <td class="label">
                                            <span id="Countrylabel" class="label2"> </span>New password
                                        </td>
                                        <td class="content">
                                            <input class="require" type="password" value="Vietnam" maxlength="100" name="txt_newPassword" id="txt_newPassword">
                                                <div class="warning requirewarning" id="newPasswordWarning"> Password is leat 6 character.</div>
                                        </td>
                                    </tr>
                                    <tr valign="top">
                                        <td class="label">
                                            Confirm new password
                                        </td>
                                        <td class="content">
                                            <input class="require email" type="password" value="" maxlength="100" name="txt_newPasswordConfirm" id="txt_newPasswordConfirm"/><span class="require_field">*</span>

                                            <div class="warning" id="newPasswordconfirmMatch">The new password is not match.</div>
                                        </td>
                                    </tr>
                                    <tr valign="top">
                                        <td valign="middle" colspan="2" >
                                            <div id="submit_button">
                                                <input id="submitbutton_cancle" name="action" type="submit" value="cancle" class="g_button g_button_submit"/>
                                                <input id="submitbutton_complete" name="action" type="submit" value="complete" class="g_button g_button_submit"/>
                                                <span id="register_loading"></span>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody></table>
                        </form>
                    </div>
                </div><!-- end content_wrapper-->
            </div><!-- end .content -->
        </div><!-- end .wrapper -->
      
    </body>
    <script>
        //////////////
        $("#submitbutton_complete").click(function(){
            var data = $("form[name='changePass_form']").serialize();
            data += "&action=changePass";
            $("#register_loading").html('<a class="tool-24 loading"><a>');
            $.ajax({
                type: "POST",
                url: "UserController",
                data: data,
                success: function(msg){
                    $("#register_loading").html('');
                    if(msg=="1"){
                        $("#changeSuccess").show();
                        return false;
                    }else if(msg=="-1")
                    {$("#oldPasswordMatchtWarning").show();
                    return false;}
                    else
                    {
                      
                    }
                },
                error: function(){
                    alert(data);
                    return false;
                }
            });
            return false;
        });
        ///////////
        $("#submitbutton_cancle").click(function(){
            window.location.replace("login.jsp");
            return;
        });
        ////////////


        function validate(){
            var test = true;
            $(".warning").hide();

            //vaidate fullname
            var newpassword = $("#txt_newPassword").val();
            if(newpassword.length < 6){
                test = false;
                $("#newPasswordWarning").show();

            }

            //vaidate password field
            var txt_pass = $("#txt_newPasswordConfirm").val();
            if(txt_pass.length !=newpassword ){
                test = false;
                $("#newPasswordconfirmMatch").show();
            }
            //validate email

            //vaidate require field
            var arr_inputs = $("form[name='changePass_form']").find("input");
            arr_inputs.each( function(){
                if($(this).hasClass("require") && $(this).val()==""){
                    test = false;
                    var warning = $(this).parent().find(".requirewarning");
                    $(this).parent().find(".requirewarning").show();
                }
            });
            return test;
        }

    </script>
</html>

<%@page import="uit.pubguru.constant.PubGuruConst"%>
<div id="pnlLogin" class="login-input-box">
    <form name="login_frm" id="login_frm" method="POST">
        <div id="login_wrapper">
            <div class="input_login_wrapper">
                <div class="login_form">
                    <div class="login_title" languageid="login_lb_main_Login">Sign in/ <a href="<%=(PubGuruConst.HTTP_SERVER + "/" + "UserController?action=registerform")%>">Sign up</a></div>
                    <td class="content">
                        <div>
                            <div class="login_label" languageid="login_lb_userName">Email </div>
                            <div class="login_input">
                                <input name="txt_User" type="text" id="txt_User" tabindex="1">
                            </div>
                            <div class="warning" id="userWarning" align="center">Enter your email</div>
                        </div>
                    </td>
                    <td class="content">
                        <div>
                            <div class="login_label" languageid="login_lb_passWord">Password</div>
                            <div class="login_input">
                                <input name="txt_Password" type="password" id="txt_Password" tabindex="2"/>
                            </div>
                            <div class="warning" id="passerrorWarning" align="center"> Password is least 6 characters.</div>
                            <div class="warning" id="passWarning" align="center">Enter your password</div>
                        </div>
                    </td>
                    <div class="login_submit"><input id="submit_button" name="submit" type="submit" value="Login" class="g_button g_button_submit "/></div>
                </div>
                <div class="swap_logoSoftware">
                    <div class="login_logo">
                    </div>
                </div>
                    <div class="warning" id="errorWarning" align="center"> <h3><font color ="red"> Login fail ! Your email or password is wrong.</font></h3></div>
            </div>           
           
        </div>
    </form>
</div>
<script>
    //////////////
    $("#submit_button").click(function(){
        if(!validate()){
            return false;
        }
        // $("form[name='confirmedit_form']").submit();
        var data = $("form[name='login_frm']").serialize();
        data += "&action=login&submit=Login";
        $("#register_loading").html('<a class="tool-24 loading"><a>');
        $.ajax({
            type: "POST",
            url: "UserController",
            data: data,
            success: function(msg){              
                $("#register_loading").html('');
                if(msg=="-1"){
                    $("#errorWarning").show();
                    return false;
                }
                else
                    {
                         window.location.replace(msg);
                         return false;
                    }
            },
            error: function(){
                alert(data);
                return false;
            }
        });
        return false;
    });
   
    function validate(){
        var test = true;
        $(".warning").hide();  
        var username = $("#txt_User").val();
        if(username.length ==0){
            test = false;
            $("#userWarning").show();
        }
        //vaidate password field
        var txt_pass = $("#txt_Password").val();
        if(txt_pass.length == 0 ){
            test = false;
            $("#passWarning").show();
        }            
        return test;
    }
</script>

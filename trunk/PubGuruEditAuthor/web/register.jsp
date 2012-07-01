<%@page import="uit.pubguru.constant.PubGuruConst" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="register_wrapper">
    
    
    <form  method="POST" class="register_form" name="register_form">        
        <fieldset>
        <legend>Register </legend>
        <table style="margin: auto;" >
            <tbody>
                <tr valign="top">
                    <td class="label">Full Name</td>
                    <td class="content">
                        <input class="require" type="text" value="" maxlength="100" name="txt_fullname" id="txt_fullname"/>
                        <div class="warning" id="fullNameLenghtWarning">
                            Full name minimum 6 characters.
                        </div>
                    </td>
                </tr>
                <tr valign="top">
                    <td class="label">
                        Country
                    </td>
                    <c:set var="info" value="${requestScope.listCountry}"/>
                    <td class="content">
                        <select class="require" name="country">
                             <option value="VietNam">VietNam</option>
                            <c:if test="${not empty info}">
                                <c:forEach var="rows" items="${info}">
                                    <option value="${rows.country}">${rows.country}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <div class="warning requirewarning" id="countryWarning"> Please enter country.</div>
                    </td>
                </tr>
                <tr valign="top">
                    <td class="label">
                        Email
                    </td>
                    <td class="content">
                        <input class="require email" type="text" value="" maxlength="100" name="txt_email" id="txt_email"/><span class="require_field"></span>
                        <div class="example">
                            e.g. abc@gmail.com.
                        </div>
                        <div class="warning" id="emmailNotvalid">Please enter a valid email.</div>
                        <div class="warning" id="emmailWarningExist">This mail have already exist.</div>
                    </td>
                </tr>
                <tr valign="top">
                    <td class="label">
                        Organization
                    </td>
                    <td class="content">
                        <input class="require org" type="text" value="" maxlength="100" name="txt_org" id="txt_org"/><span class="require_field"></span>
                        <div class="example">
                            e.g. University of Information Technology.
                        </div>

                    </td>
                </tr>
                <tr valign="top">
                    <td class="label">
                        Password
                    </td>
                    <td class="content">
                        <input type="password" value="" class="longblock" maxlength="1000" name="txt_password" id="txt_password"/><span class="require_field"></span>
                        <div class="warning" id="requireWarning">Please enter password.</div>
                        <div class="warning" id="passwordShortWarning">Password is least 6 characters.</div>
                    </td>
                </tr>
                <tr valign="top">
                    <td class="label">
                        Confirm Password
                    </td>
                    <td class="content">
                        <input class="require longblock" type="password" value="" maxlength="1000" name="txt_confirmpass" id="txt_confirmpass"/>
                        <div class="warning" id="confirmPasswordWarning">Confirm password don't match.</div>
                    </td>
                </tr>

                <tr valign="top">
                    <td class="label">
                        Input Code
                    </td>
                    <td class="content">
                        <div id="img_captcha" >
                            <img src=<%=PubGuruConst.HTTP_SERVER + "/CaptchaController" %> />
                        </div>
                        <input class="input_captcha" type="text" id="txt_code" name="txt_code"/>
                        <span class="warning requirewarning " id="codeWarning">Code don't match. </span>
                    </td>
                </tr>

                <tr valign="top">
                    <td valign="top" colspan="2" >
                        <div id="submit_button">
                            <input id="submitbutton_cancle" name="action" type="submit" value="Cancel" class="g_button g_button_submit"/>
                            <input id="submitbutton_complete" name="action" type="submit" value="Complete" class="g_button g_button_submit"/>
                            
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
        </fieldset>
        <div class="push"></div>
        <div id="register_loading" style="text-align: center;"></div>
    </form>
</div>


<script>
    //////////////
    $("#submitbutton_complete").click(function(){
        if(!validate()){
            return false;
        }
        var data = $("form[name='register_form']").serialize();
        data += "&action=register&submit=Complete";     
        $("#register_loading").html('<a class="tool-24 loading"></a>');
        $.ajax({
            type: "POST",
            url: "UserController",
            data: data,
            success: function(msg){                
                $("#register_loading").html('');
                if(msg=="-1"){
                    $("#emmailWarningExist").show();//email exist
                    refreshCapcha();
                }else if(msg=="-2"){// full name too short
                    $("#fullNameLenghtWarning").show();
                }else if(msg=="-3"){
                    $("#passwordShortWarning").show();
                }else if(msg=="0"){
                    $("#codeWarning").show();//captcha don't match
                    refreshCapcha();
                }else if(msg=="1"){
                    window.location.replace("UserController?action=success");
                }
            },
            error: function(){
                alert("Error");
            }
        });
        return false;
    });
    ///////////
    $("#submitbutton_cancle").click(function(){
      
        window.location = "UserController?action=loginform";
        return false;
    });
    ////////////
    function refreshCapcha(){ 
        //$.get("<%=PubGuruConst.HTTP_SERVER + "/CaptchaController" %>"); 
        $("div#img_captcha img").attr("src","<%=PubGuruConst.HTTP_SERVER + "/CaptchaController?" %>" + Math.random());
    }
      
    function validate(){
        var test = true;
        $(".warning").hide();
            
        //vaidate fullname
        var full_name = $("#txt_fullname").val();
        if(full_name.length < 6){
            test = false;
            $("#fullNameLenghtWarning").show();
                
        }
        
        //vaidate password field
        var txt_pass = $("#txt_password").val();
        if(txt_pass.length == 0 ){
            test = false;
            $("#requireWarning").show();
        }else if(txt_pass.length<6){
            test = false;
            $("#passwordShortWarning").show();
        }else if(txt_pass!= $("#txt_confirmpass").val()){
            test = false;
            $("#confirmPasswordWarning").show();
        }
        //validate email
        var txt_email = $("#txt_email").val();
        if(!emailCheck(txt_email)){
            test = false;
            $("#emmailNotvalid").show();
        }
        //vaidate require field
        var arr_inputs = $("form[name='register_form']").find("input");
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

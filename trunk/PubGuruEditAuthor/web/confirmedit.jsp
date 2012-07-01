
<%@page import="uit.pubguru.model.Author"%>
<%@page import="uit.pubguru.constant.PubGuruConst"%>
<%@page import="uit.pubguru.model.Author_EditModel"%>
<%@page import="uit.pubguru.controller.FeedBackController"%>
<!--@author: ThanhIT-->
<%


            String username = (String) session.getAttribute("username");
            String login = (String) session.getAttribute("login");

            String stt = request.getParameter("imp");
            if (stt.equals("0")) // only status ==0, new feedback can ignore a feedback
            {
                stt = "ignore";
            } else {
                stt = "";
            }
            String permission = (String) session.getAttribute("permissionId");
            // check to show checkboxs
            String firstnamedt = (String) request.getAttribute("firstNamedt");
            String firstNameSTT = "";
            if (firstnamedt == null) {
                firstnamedt = "disabled";
            } else {
                firstNameSTT = (String) request.getAttribute("firstNameSTT");
            }
            String lastnamedt = (String) request.getAttribute("lastNamedt");
            String lastNameSTT = "";
            if (lastnamedt == null) {
                lastnamedt = "disabled";
            } else {
                lastNameSTT = (String) request.getAttribute("lastNameSTT");
            }
            String middlenamedt = (String) request.getAttribute("middleNamedt");
            String middleNameSTT = "";
            if (middlenamedt == null) {
                middlenamedt = "disabled";
            } else {
                middleNameSTT = (String) request.getAttribute("middleNameSTT");
            }
            String websitedt = (String) request.getAttribute("websitedt");
            String websiteSTT = "";
            if (websitedt == null) {
                websitedt = "disabled";
            } else {
                websiteSTT = (String) request.getAttribute("websiteSTT");
            }
            String imageurldt = (String) request.getAttribute("imageUrldt");
            String imageUrlSTT = "";
            if (imageurldt == null) {
                imageurldt = "disabled";
            } else {
                imageUrlSTT = (String) request.getAttribute("imageUrlSTT");
            }
            String emaildt = (String) request.getAttribute("emaildt");
            String emailSTT = "";
            if (emaildt == null) {
                emaildt = "disabled";
            } else {
                emailSTT = (String) request.getAttribute("emailSTT");
            }
            String authorID = (String) request.getAttribute("authorID");
            String authorName = (String) request.getAttribute("authorName");
            String photoUrl = (String) request.getAttribute("photoUrl");
            String emailAdderss = (String) request.getAttribute("emailAdderss");
            String website = (String) request.getAttribute("website");
            String firstName = (String) request.getAttribute("firstName");
            String lastName = (String) request.getAttribute("lastName");
            String middleName = (String) request.getAttribute("middleName");
            String authorNameD = (String) request.getAttribute("authorNameD");
            String photoUrlD = (String) request.getAttribute("photoUrlD");
            String emailAdderssD = (String) request.getAttribute("emailAdderssD");
            String websiteD = (String) request.getAttribute("websiteD");
            String firstNameD = (String) request.getAttribute("firstNameD");
            String lastNameD = (String) request.getAttribute("lastNameD");
            String middleNameD = (String) request.getAttribute("middleNameD");
            String comment = (String) request.getAttribute("comment");
           if (login == null) {
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                dispatcher.forward(request, response);
            } else {
                if (login.equals("true")) {
                    if (permission.equals(String.valueOf(PubGuruConst.ADMIN_PERMISSION)) == false) {
                        request.setAttribute("pageName", PubGuruConst.PER_ERror_PAGE);
                        RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                        dispatcher.forward(request, response);
                    }
                } else {
                    request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                    RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                    dispatcher.forward(request, response);

                }
            }

%>
<div>
    <div class="result_info author_card">
        <div class="result_info">
            <div class="author_image">
                <img style="width: 96px; border-width: 0px;"  href="" src="" title="" id=""/>
            </div>
            <div class="author_info">

                <div class="name_univercity">
                    <div class="author_name">
                        <a class="title" href="#"></a>
                    </div>
                    <div class="univercity_name">
                      </div>
                </div>             
                <div class="author_homepage">
                    <img class="paper_icon" src="images/homepage_icon.gif"/>
                    <span>Home page : </span><a class="item_content" href="#"></a><br/><br/>
                </div>
            </div>
        </div>
    </div>
    <!--end .section_header -->
</div>
<form method="POST"  name="confirmedit_form">
    <div  class="confirm_edit_wrapper" >

        <div style="float: left;width: 100%;" >
            <table class="tbinfo" style="width: 100%" cellpadding="0" cellspacing="0">
                <tbody>
                    <tr valign="top">
                       <td class="label"></td>
                        <td id ="title1" class="content1">
                            <span>Old information </span>                            
                        </td>
                        <td id ="title2" class="content2">                           
                            <span> New information</span>
                        </td>
                        <td id ="title3" class="content3">                           
                            <span>Improve</span>
                        </td>
                        
                    </tr>
                    <tr valign="top">
                        <td class="label">First Name</td>
                        <td class="content1">
                           
                            <input readonly type="text" value="<%%>" maxlength="100" name="firstName" id="firstName"/>
                        </td>
                        <td class="content2">                          
                            <input readonly type="text" value="<%=firstName%>" maxlength="100" name="e_firstName" id="e_firstName"/>                            
                        </td>
                        <td class="content3">
                            <input <%=firstnamedt%> <%=firstNameSTT%>  type ="checkbox" name ="chkFirstName" value ="FirstName"  />
                        </td>
                       
                    </tr>
                    <tr valign="top">
                        <td class="label">Middle Name</td>
                        <td class="content1">
                            <input readonly type="text" value="<%%>" maxlength="100" name="middleName" id="middleName"/>
                        </td>
                        <td class="content2">
                            <input readonly type="text" value="<%=middleName%>" maxlength="100" name="e_middleName" id="e_middleName"/>                            
                        </td>
                        <td class="content3">
                            <input  <%=middlenamedt%> <%=middleNameSTT%> type ="checkbox" name ="chkMiddleName" value ="MiddleName"  />
                        </td>

                    </tr>
                    <tr valign="top">
                        <td class="label">Last Name</td>
                        <td class="content1">
                            <input readonly  type="text" value="<%%>" maxlength="100" name="lastName" id="lastName"/>
                        </td>
                        <td class="content2">
                            <input readonly type="text" value="<%=lastName%>" maxlength="100" name="e_lastName" id="e_lastName"/>                            
                        </td>
                        <td class="content3">
                            <input <%=lastnamedt%> <%=lastNameSTT%> type ="checkbox" name ="chkLastName" value ="LastName"/>
                        </td>
                    </tr>
                    <tr valign="top">
                        <td class="label">Homepage URL</td>
                        <td class="content1">
                            <input readonly type="text" value="<%%>" class="" maxlength="1000" name="homepageUrl" id="homepageUrl"/>
                        </td>
                        <td class="content2">
                            <input readonly type="text" value="<%=website%>" class="" maxlength="1000" name="e_homepageUrl" id="e_homepageUrl"/>                            
                        </td>
                        <td class="content3">
                            <input <%=websitedt%> <%=websiteSTT%> type ="checkbox" name ="chkHomePage" value ="HomePage"  />
                        </td>
                    </tr>
                    <tr valign="top">
                        <td align="label" class="label">Photo URL</td>
                        <td class="content1">
                            <input readonly type="text" value="<%%>" class="longblock1" maxlength="1000" name="photoUrl" id="photoUrl"/>
                        </td>
                        <td class="content2">
                            <input readonly type="text" value="<%=photoUrl%>" class="longblock11" maxlength="1000" name="e_photoUrl" id="e_photoUrl"/>                            
                        </td>
                        <td class="content3">
                            <input <%=imageurldt%>  <%=imageUrlSTT%> type ="checkbox" name ="chkPhotoUrl" value ="PhotoUrl"  />
                        </td>
                    </tr>
                    <tr valign="top">
                        <td class="label">Email</td>
                        <td class="content1">
                            <input readonly type="text" value="<%%>" class="longblock1" maxlength="500"  name="txtEmail" id="txtEmail"/>
                        </td>
                        <td class="content2">
                            <input readonly type="text" value="<%=emailAdderss%>" class="longblock1" maxlength="500"  name="e_txtEmail" id="e_txtEmail"/>                            
                        </td>
                        <td class="content3">
                            <input <%=emaildt%> <%=emailSTT%> type ="checkbox" name ="chkEmail" value ="Email"  />
                        </td>
                    </tr>
                    
                    <tr valign="top" style="vertical-align: middle;margin-top: 5px;">
                        <td class="label">Comment</td>
                        <td class="content1"  style=" width: 100%;text-align: center;">
                            <textarea class="longblock1" readonly class="comment" name="comment" id="comment" style="width:50%"><%=comment%>
                            </textarea>
                        </td>
                        
                    </tr>
                    
                    
                    
                </tbody>
            </table>

        </div>

    </div>
    <div class="clear"></div>
    <div valign="top">
        <table>
            <tr valign="top">
                <td valign="top" colspan="2">
                    <div class="form-element nextstep-button" style="margin-top: 10px;margin-left: 125px;">
                        <input id="submitbutton_cancle" name="submitbutton" type="submit" value="Cancle" class="g-button g_button_submit"/>
                        <input id="submitbutton_complete" name="action" type="submit" value="Complete" class="g-button g_button_submit"/>
                        <%if (stt.equals("ignore")) {%>
                        <input id="submitbutton_ignore" name="action" type="submit" value="Ignore"  class="g-button g_button_submit"/>
                        <%}%>
                    </div>
                    <span id="register_loading"></span>
                    <div class="warning" id="error" style="margin-top: 5px;margin-left: 130px;">
                        You didn't choose any info to update!.</div>
                    <div class="warning" id="success" style="margin-top: 10px;margin-left: 130px;">
                        Info is checked is updated success !</div>
                </td>
            </tr>
        </table>
    </div>

</form>  
<script>
    //////////////
    $("#submitbutton_complete").click(function(){
        // $("form[name='confirmedit_form']").submit();
        var data = $("form[name='confirmedit_form']").serialize();
        data += "&action=Complete";
        $("#register_loading").html('<a class="tool-24 loading"><a>');
        $.ajax({
            type: "POST",
            url: "FeedBackController",
            data: data,
            success: function(msg){
                $("#register_loading").html('');
                if(msg=="0"){// full name too short
                    $("#error").show();
                    return false;
                }
                if(msg=="1")
                {
                    $("#success").show();
                    window.location.replace("<%=PubGuruConst.HTTP_SERVER + "/FeedBackController?action=getList&status=" + PubGuruConst.CONFIRM_STATUS + "&page=1"%>");
                }
                if(msg=="-1"){// full name too short
                    alert("Update infor fail!");
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
    ///////////
    $("#submitbutton_cancle").click(function(){
        window.location.replace("<%=PubGuruConst.HTTP_SERVER + "/FeedBackController?action=getList&status=" + PubGuruConst.CONFIRM_STATUS + "&page=1"%>");
        return false;
       
    });
    $("#submitbutton_ignore").click(function(){
        var data ="&action=Ignore";
        $("#register_loading").html('<a class="tool-24 loading"><a>');
        $.ajax({
            type: "POST",
            url: "FeedBackController",
            data: data,
            success: function(msg){
                $("#register_loading").html('');
                if(msg=="1")
                {
                    $("#success").show();
                    window.location.replace("<%=PubGuruConst.HTTP_SERVER + "/FeedBackController?action=getList&status=" + PubGuruConst.CONFIRM_STATUS + "&page=1"%>");
                }
                if(msg=="0"){// full name too short
                    alert("Update infor fail!");
                    return false;
                }
            },
            error: function(){
                alert("Can't send request to server!");
                return false;
            }
        });
        return false;

    });
    $(function(){
        // get data from
        loadInfoAuthor();
        
    });
    
    function loadInfoAuthor(){
        $.ajax({
            type: "POST",
            url: "<%=PubGuruConst.GET_AUTHOR_INFO%>",
            data: {
                action: "getInfo",
                authorID: <%=authorID%>
            },
            dataType: "Json",
            success: function(msg){
                console.log(msg);
                $(".author_name a.title").text(msg.authorName);
                $(".author_homepage a.item_content").text(msg.website);
                //$("#authorID").val(msg.authorID);
                $(".author_image img").attr("src",msg.imageUrl);
                $(".author_image img").attr("href",msg.imageUrl);
                $("#firstName").val(msg.firstName);
                $("#middleName").val(msg.middleName);
                $("#lastName").val(msg.lastName);
                if(msg.emailAddress!=null)
                    $("#txtEmail").val(msg.emailAddress);
                $("#homepageUrl").val(msg.website);
                $("#photoUrl").val(msg.imageUrl);
            },
            error:function(){
                alert("error");
            }
        });
    }
</script>
</html>

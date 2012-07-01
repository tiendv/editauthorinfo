<%@page import="uit.pubguru.model.Author"%>
<%@page import="uit.pubguru.constant.PubGuruConst"%>
<!--@author: Thao Nguyen-->
<%
            String username = (String) session.getAttribute("username");
         //   if (username == null) {
         //       request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
         //      RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
         //      rd.forward(request, response);
         //   }
           
            String authorID = (String) request.getParameter("authorID");

            String permissionID = (String) session.getAttribute("permissionID");
            String authorName = (String) request.getAttribute("authorName");

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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

        <div class="wrapper">
            <div class="content">
                <div class="content_wrapper">
                    <div class="left_wrapper" >
                        <div id="author_list" class="section_wrapper">

                        </div><!--end section wrapper-->                       
                    </div><!-- end left_wrapper-->
                    <div class="center_wrapper">
                        <div class="section_wrapper">
                            <div class="section_header">
                                <div class="order_by_filter">
                                    <div class="dropdownlist">
                                        <!---  <input id="btnShowSelectPnl" type="button" value="Select Another Author" onclick="ShowSelectPnl();" bindornot="true"/>-->
                                        <a href="<%="AuthorController?action=getList"%>">Select Another Author</a>

                                    </div>
                                </div>
                                <div class="result_info author_card">
                                    <div class="result_info">
                                        <div class="author_image">
                                            <img style="width: 96px; border-width: 0px;" alt="" src="" title="" id=""/>
                                        </div>
                                        <c:url var="edit1" value="AuthorEditController">
                                            <c:param name="action" value="edit"/>
                                            <c:param name="authorID" value="<%=authorID%>"/>
                                        </c:url>
                                        <div class="author_info">
                                            <br/>
                                            <div class="name_univercity">
                                                <div class="author_name">
                                                    <a class="title" href="#"></a>
                                                    <a href="<%="AuthorEditController?action=edit&authorID=" + authorID%>">Edit Info </a>
                                                </div>
                                                <br/>
                                                <div class="univercity_name">                                                   
                                                </div>
                                            </div>                                           
                                            <div class="author_homepage">
                                                <img class="paper_icon" src="images/homepage_icon.gif"/>
                                                <span>Home page : </span><a class="item_content" href=""></a><br/>
                                            </div>
                                        </div><!--End author_info-->
                                    </div><!--End result_info-->
                                </div><!--End .result_info author_card-->
                            </div><!--End .section_header-->
                            <div  class="edit_author_box">
                                <form action="AuthorEditController" method="POST">
                                    <input id="authorID" name ="authorID" style="display: none" type="text" value=""/>
                                    <table style="border-left: 1px solid #e5e5e5;">
                                        <tbody>
                                            <tr valign="top">
                                                <td class="label">First Name</td>
                                                <td class="content">
                                                    <input disabled type="text" value="" maxlength="100" name="txt_firstName" id="txt_firstName"/><span> </span>

                                                </td>
                                            </tr>
                                            <tr valign="top">
                                                <td class="label">Middle Name</td>
                                                <td class="content">
                                                    <input  disabled type="text" value="" maxlength="100" name="txt_middleName" id="txt_middleName"/>
                                                    <input type="hidden" value="true" name="oriMiddleName"/>

                                                </td>
                                            </tr>
                                            <tr valign="top">
                                                <td class="label">
                                                    Last Name
                                                </td>
                                                <td class="content">
                                                    <input disabled type="text" value="" maxlength="100" name="txt_lastName" id="txt_lastName"><span></span>
                                                        <input type="hidden" value="true" name="oriLastName"/>

                                                </td>
                                            </tr>
                                            <tr valign="top">
                                                <td class="label">Email</td>
                                                <td class="content">
                                                    <input disabled type="text" value="" class="longblock" maxlength="1000" name="txt_email" id="txt_email"/>

                                                </td>
                                            </tr>
                                            <!--<tr valign="top">
                                                <td class="label">Native Name</td>
                                                <td class="content">
                                                    <input type="text" value="" maxlength="100" name="txt_nativeName" id="txt_nativeName"/>
                                                    <input type="hidden" value="null" name="oriNativeName"/>
                                                    <div class="example">
                                                        e.g. Your name in your native language.
                                                    </div>
                                                    <span class="warning" id="nativeNamewarning">Please enter a valid name. </span>
                                                </td>
                                            </tr>-->
                                            <tr valign="top">
                                                <td class="label">Homepage URL</td>
                                                <td class="content">
                                                    <input disabled type="text" value="" class="longblock" maxlength="1000" name="txt_homepageUrl" id="txt_homepageUrl"/>


                                                </td>
                                            </tr>
                                            <tr valign="top">
                                                <td align="center" class="label">Photo URL</td>
                                                <td class="content">
                                                    <input disabled type="text" value="" class="longblock" maxlength="1000" name="txt_photoUrl" id="txt_photoUrl"/>
                                                </td>
                                            </tr>

                                        </tbody>
                                    </table>
                                </form>
                            </div> <!-- End edit_author_box -->
                            <div class="clear"></div>
                        </div><!--end section_wrapper-->
                        <div id="sidebar">

                        </div><!-- end .sidebar-->

                    </div><!-- end center_content-->
                </div><!-- end content_wrapper-->
            </div><!-- end .content -->
        </div><!-- end .wrapper -->
        <div class="push"></div>
    </body>
    <script>
        $(function(){            
            loadInfoAuthor();
        });
        
        function loadInfoAuthor(){
            $.ajax({
                type: "POST",
                url: "AuthorController",
                data: {
                    action: "getInfo",
                    authorID: <%=authorID%>
                },
                dataType: "Json",
                success: function(msg){
                    $(".author_name a.title").text(msg.authorName);                    
                    $(".author_homepage a.item_content").text(msg.website); 
                    $("#authorID").val(msg.authorID);                    
                    $(".author_image img").attr("src",msg.imageUrl);
                    $("#txt_firstName").val(msg.firstName);
                    $("#txt_middleName").val(msg.middleName);
                    $("#txt_lastName").val(msg.lastName);
                    if(msg.emailAddress!=null)
                        $("#txt_email").val(msg.emailAddress);
                    $("#txt_homepageUrl").val(msg.website);
                    $("#txt_photoUrl").val(msg.imageUrl);
                },
                error:function(){
                    alert("errot");
                }
            });
        }
        
        $("#submit_button_complete").click(function(){             
            if(!validate()){ 
                return false;
            }
        });
        
        function validate(){
            $(".warning").hide();
            var firstName   = $("#txt_firstName").val();
            var middleName  = $("#txt_middleName").val();
            var lastName    = $("#txt_lastName").val();
            var email       = $("#txt_email").val();
            var homepageUrl = $("#txt_homepageUrl").val();
            var photoUrl    = $("#txt_photoUrl").val();
            var test        = true;
            
            if(firstName.length==0){                
                test = false; 
                $("#firstNameNullWarning").show();
            }else if(!nameCheck(firstName)){                
                test = false;
                $("#firstNameWarning").show();
            }
            
            if(middleName.length == 0){
                
            }else if(!nameCheck(middleName)){
                test = false;
                $("#middleNameWarning").show();
            }
            
            if(lastName.length==0){                 
                test = false;
                $("#lastNameNullWarning").show();
            }else if(!nameCheck(lastName)){
                test = false;
                $("#lastNameWarning").show();
            }
            
            if(email.length == 0){
                
            }else if(!emailCheck(email)){                
                test = false;
                $("#emailInvalidWarning").show();
            }
            
            if(homepageUrl.length == 0){
                
            }else if(!urlCheck(homepageUrl)){                
                test = false;
                $("#homepageUrlInvalidWarning").show();
            }
            
            if(photoUrl.length == 0){
                
            }else if(!imageUrlCheck(photoUrl)){                
                test = false;
                $("#photoUrlWarning").show();
            }
            return test;
        }
    </script>
</html>

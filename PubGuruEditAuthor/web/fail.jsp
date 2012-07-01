<%-- 
    Document   : fail.jsp
    Created on : Apr 12, 2012, 9:57:33 AM
    Author     : ThanhIT
--%>
<%@page import="uit.pubguru.constant.PubGuruConst"%>
<%
    String pageResult = (String) request.getAttribute("pageResult");
    System.out.print(pageResult);
   session.setAttribute("pageResult_", pageResult);
    if(pageResult == null)
        pageResult = PubGuruConst.LOGIN_PAGE;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Publication Guru</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link href="css/mystyle.css" rel="stylesheet" type="text/css" />
        <script language="javascript" src="javascript/jquery-1.7.1.min.js"></script>
    </head>
    <body>
        <div class="wrapper">
             <div class="content">
                 <form action ="UserController" method="POST">
                    <div class="content_wrapper">
                        <div class="center_wrapper">

                            <div class="result_message">
                                <span class="message_sucessness"><img src="images/editauthor/success.png"/></span>
                                <div>
                                    <span class="result_title">login fail.</span>
                                </div>
                                <div  style=" margin-right: 321px; margin-top: 50px;">
                                    <input id="submitbutton_cancle" name="action" type="submit" value="Continue" class="g_button g_button_submit" onclick="refresh('index')" />
                                </div>
                               <div class="clear"></div>
                            </div><!-- end content_wrapper-->
                        </div><!-- end .content -->
                </form>
            </div><!-- end .wrapper -->
            
        
    </body>
</html>

<%-- 
    Document   : adminTemplate
    Created on : May 25, 2012, 9:45:49 PM
    Author     : ThanhIT
--%>
<%@page import="uit.pubguru.constant.PubGuruConst"%>
<%
           String username = (String) session.getAttribute("username");
           String controler = (String) session.getAttribute("username");
            String login = (String) session.getAttribute("login");
            String permission = (String) session.getAttribute("permissionId");
          if (login == null) {
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                dispatcher.forward(request, response);
            } else {
                if (login.equals("true")) {
                    if (permission.equals(String.valueOf(PubGuruConst.ADMIN_PERMISSION))) {                         //request.setAttribute("pageName", PubGuruConst.BACKEND_TEMPLATE_PAGE);
                      
                    } else {
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
            
            String pageName = (String) request.getAttribute("pageName");
            if (pageName == null) {
                pageName = "";
            }

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

        <!-- Website Title -->
        <title>WebAdmin | PubGuruEditAuthor</title>

        <!-- Meta data for SEO -->
        <meta name="description" content="">
        <meta name="keywords" content="">

        <!-- Template stylesheet -->
        <link href="css/screen.css" rel="stylesheet" type="text/css" media="all">
        <link href="css/mystyle.css" rel="stylesheet" type="text/css" />
        <!--[if IE]>
	<link href="css/ie.css" rel="stylesheet" type="text/css" media="all">
	<script type="text/javascript" src="js/excanvas.js"></script>
        <![endif]-->

        <!-- Jquery and plugins -->
        <script language="javascript" src="javascript/jquery-1.7.1.min.js"></script>
        <script language="javascript" src="javascript/myscripts.js"></script>
        <script type="text/javascript" src="js/jquery-ui.js"></script>
        <script type="text/javascript" src="js/jquery.img.preload.js"></script>
        <script type="text/javascript" src="js/hint.js"></script>
        <script type="text/javascript" src="js/visualize/jquery.visualize.js"></script>
        <script type="text/javascript" src="js/jwysiwyg/jquery.wysiwyg.js"></script>
        <script type="text/javascript" src="js/fancybox/jquery.fancybox-1.3.0.js"></script>
        <script type="text/javascript" src="js/jquery.tipsy.js"></script>
        <script type="text/javascript" src="js/custom_black.js"></script>
<script>
$(document).ready(function() {
    var link = window.location.href;
    var index = link.indexOf("FeedBackController") ;
    if(index !=-1)
        $("#menu_feedbacks").trigger('click');
    else{
        index = link.indexOf("UserController") ;
        if(index !=-1)
            $("#menu_users").trigger('click');
    }
    
});
</script>
    </head>
    <body>

        <div class="content_wrapper">
            <!-- Begin header -->
            <div id="header">
                <div id="logo">
                    <img src="images/logo.png" alt="logo"/>
                </div>

            </div>
            <!-- End header -->

            <!-- Begin left panel -->
            <a href="javascript:;" id="show_menu">&raquo;</a>
            <div id="left_menu">
                <a href="javascript:;" id="hide_menu">&laquo;</a>
                <ul id="main_menu">
                    <li><a>Home</a></li>
                    <li>
                        <a id="menu_feedbacks" href=""><img src="images/icon_pages.png" alt="Pages"/>Feedback manager</a>
                        <ul>
                            <li><a href="<%=PubGuruConst.HTTP_SERVER + "/FeedBackController?action=getList&status=" + PubGuruConst.ALL_STATUS + "&page=1"%>">All feedbacks</a></li>
                            <li><a href="<%=PubGuruConst.HTTP_SERVER + "/FeedBackController?action=getList&status=" + PubGuruConst.EDITED_STATUS + "&page=1"%>">Improved feedbacks</a></li>
                            <li><a href="<%=PubGuruConst.HTTP_SERVER + "/FeedBackController?action=getList&status=" + PubGuruConst.CONFIRM_STATUS + "&page=1"%>">New feedbacks</a></li>
                            <li><a href="<%=PubGuruConst.HTTP_SERVER + "/FeedBackController?action=getList&status=" + PubGuruConst.IGNORE_STATUS + "&page=1"%>">Ignored feedbacks</a></li>
                        </ul>
                    </li>
                    <li>
                        <a id="menu_users" href="" aria-expanded="true"><img src="images/icon_posts.png" alt="Posts"/>User manager</a>
                        <ul>
                            <li><a href="<%=PubGuruConst.HTTP_SERVER + "/UserController?action=getList&active=2&page=1"%>">All users</a></li>
                            <li><a href="<%=PubGuruConst.HTTP_SERVER + "/UserController?action=getList&active=1&page=1"%>">Actived users</a></li>
                            <li><a href="<%=PubGuruConst.HTTP_SERVER + "/UserController?action=getList&active=0&page=1"%>">Disactived users</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href=""><img src="images/icon_users.png" alt="Users"/>Users</a>
                        <ul>
                            <div><a href="UserController?action=logout">Logout</a></div>
                        </ul>
                    </li>
                </ul>
            </div>
            <!-- End left panel -->


            <!-- Begin content -->
            <div id="content">
                <div class="inner">
                    <jsp:include page="<%=pageName%>"/>
                </div>

                <br class="clear"/><br class="clear"/>

                <!-- Begin footer -->
                <div id="footer">

                </div>
                <!-- End footer -->


            </div>
            <!-- End content -->
        </div>

    </body>
</html>
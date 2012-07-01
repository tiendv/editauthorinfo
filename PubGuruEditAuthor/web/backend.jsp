<%@page import="uit.pubguru.constant.PubGuruConst"%>
<!--@author: ThanhIT-->
<%
            String username = (String) session.getAttribute("username");
            String login = (String) session.getAttribute("login");
            System.out.print("lgin : " + login);

            String permission = (String) session.getAttribute("permissionId");
            System.out.print("per : " + permission);
            if (login == null) {
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                response.sendRedirect(PubGuruConst.FRONT_TEMPLATE_PAGE);
            } else {
                if (login.equals("true")) {
                    if (permission.equals(String.valueOf(PubGuruConst.ADMIN_PERMISSION))) {
                    } else {
                        request.setAttribute("pageName", PubGuruConst.PER_ERror_PAGE);
                        response.sendRedirect(PubGuruConst.FRONT_TEMPLATE_PAGE);
                    }
                } else {
                    request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                    response.sendRedirect(PubGuruConst.FRONT_TEMPLATE_PAGE);
                }
            }

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Publication Guru</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link href="css/mystyle.css" rel="stylesheet" type="text/css" />
        <script language="javascript" src="../javascript/jquery-1.7.1.min.js"></script> 
    </head>

    <body>
        <div class="wrapper">
            <div class="header">
                <div class="menutop">
                    <ol>
                        <li class="headerli"> <a href="/PubGuru">Home</a> </li>
                        <li class="headerli"> <a href="/PubGuru/RankList">Rank List</a> </li>
                        <li class="headerli"> <a href="">Visual Publication Network</a> </li>
                        <li class="headerli"> <a href="">Others</a> </li>
                    </ol>
                </div>
                <div><a href="UserController?action=logout">Logout</a></div>
            </div><!-- end .header -->
            <h1>Admin Manager</h1>
            <div class="content">                
                <div class="searchform">
                    <div class="logo_small"> 
                        <a href="/PubGuru">
                            <img src="images/GuruOfPub_04_small.png" alt="Publication Guru"/>
                        </a>
                    </div>
                    <div class="search-bar-wrapper">
                        <div class="searchwrapper_small">
                            <input id="sk" type="text" class="searchbox_small" name="search" value="" autocomplete="off"/>
                            <input id="btSearch" type="submit" value="" />
                        </div>
                        <div style="float: right; padding-top:5px;"> <a style= "font-size:11px;" href="/PubGuru/search.adv.guru">Advanced Search</a> </div>
                    </div>
                </div><!-- end search form -->

                <div class="content_wrapper">
                    <div class="left_wrapper" style="width: 230px;">
                        <div id="sidebar" class="bImg">
                            <div class="bInner"><span class="bTR"></span><span class="bBL"></span>
                                <ul id="side-nav">
                                    <li class="active">
                                        <a href="#" title="Feedback" class="button">
                                            <strong>
                                                <span class="title">Feedback</span>
                                                <span class="expand expanded"></span>
                                            </strong>
                                        </a>
                                        <ul>
                                            <li class="active"><a href="<%=PubGuruConst.HTTP_SERVER + "/FeedBackController?action=getList&status=" + PubGuruConst.CONFIRM_STATUS + "&page=1"%>" title="Add post">Edit Author</a></li>

                                            <li><a href="#" title="Manage categories">Edit Publication</a></li>
                                            <li><a href="#" title="Manage tags">Edit Author Relation</a></li>
                                        </ul>
                                    </li>
                                    <li class="active">
                                        <a href="#" title="Feedback" class="button">
                                            <strong>

                                                <span class="title">System Manager</span>
                                                <span class="expand expanded"></span>
                                            </strong>
                                        </a>
                                        <ul>
                                            <li class=""><a href="<%=PubGuruConst.HTTP_SERVER + "/UserController?action=getList&active=2&page=1"%>" title="Add post">User Manager</a></li>

                                            <li><a href="#" title="Backup">Backup Manager</a></li>
                                            <li><a href="#" title="Logs">Logs</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div><!-- end left_wrapper-->
                    <div class="center_wrapper">
                        <div class="section_wrapper">
                            <div class="section_header">
                                <div class="order_by_filter">
                                    <div class="dropdownlist">

                                    </div>                            
                                </div>             

                            </div>            
                            <!--end .section_header-->
                        </div>

                        <div class="clear"></div>
                    </div><!--end section_wrapper-->
                    <div id="sidebar">

                    </div><!-- end .sidebar-->

                </div><!-- end center_content-->
            </div><!-- end content_wrapper-->
        </div><!-- end .content -->
        </div><!-- end .wrapper --> 
        <div class="push"></div>
        <div class="footer">
            <center>
                <ol>
                    <li class="footerli"> <a href="">Help</a> | </li>
                    <li class="footerli"> <a href="">FAQ</a> | </li>
                    <li class="footerli"> <a href="">About</a> | </li>
                    <li class="footerli"> <a href="">Contact</a> | </li>
                    <li class="footerli"> <a href="">Privacy Statement</a> <a><strong> - 2011 All rights reserved </strong></a> </li>
                </ol>
            </center>
            <!-- end .footer --></div>
        <script>
            $("#submitbutton_complete").click(function(){
                window.location.replace("result.jsp");
                return;
            });
            $("#submitbutton_cancle").click(function(){
                window.location.replace("index.jsp");
                return;
            });
        </script>
    </body>
</html>

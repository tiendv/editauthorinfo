<%
    String pageName = (String) request.getAttribute("pageName");
    String login = (String) session.getAttribute("login");
    if (pageName == null) {
        pageName = "";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Publication Guru </title>
        <link href="css/style1.css" rel="stylesheet" type="text/css" />
        <link href="css/mystyle1.css" rel="stylesheet" type="text/css" />
        <script language="javascript" src="javascript/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui.js"></script>
        <script language="javascript" src="javascript/myscripts.js"></script>
        
    </head>

    <body>
        <div class="wrapper">
            <div class="header">
                <div class="menutop">
                    <ol>
                        <li class="headerli"> <a href="/PubGuru/">Home</a> </li>
                        <li class="headerli"> <a href="/PubGuru/RankList">Rank List</a> </li>
                        <li class="headerli"> <a href="/PubGuru/">Visual Publication Network</a> </li>
                        <li class="headerli"> <a href="/PubGuru/">Others</a> </li>
                        <li class="headerli"><%if(login!=null)
                            { %>
                              <a href="UserController?action=logout">Logout</a>
                              <%} else { %>
                               <a href="UserController?action=loginform">Login</a>
                               <% }%>
                        </li>
                    </ol>
                </div>
            </div><!-- end .header -->
            <div class="content"> 
                <jsp:include page="<%=pageName%>"/>
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
        </div><!-- end .footer -->
    </body>
</html>

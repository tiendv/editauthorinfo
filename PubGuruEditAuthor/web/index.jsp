<%@page import="uit.pubguru.constant.PubGuruConst"%>
<!--@author: Huong Tran-->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Publication Guru</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
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
                </div><!-- end .header -->
             <form method="POST">
            <div class="content">
                <center>                                  
                 <div style="margin-top: 200px"><a href="<%=PubGuruConst.HTTP_SERVER + "/AuthorController?action=getList"%>"> Edit Author</a></div>
                 <div></div>
                <div><a href="<%=(PubGuruConst.HTTP_SERVER + "/" +PubGuruConst.BACKEND_TEMPLATE_PAGE)%>">Back End</a></div>
                </center>
            </div><!-- end .content -->
            </form>
            <div class = "push"></div>
        </div><!-- end .wrapper -->
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
    </body>
</html>

<%@page import="uit.pubguru.model.FeedBackModel"%>
<%@page import="uit.pubguru.model.Author"%>
<%@page import="uit.pubguru.model.UserModel"%>
<%@page import="uit.pubguru.model.Author_EditModel"%>
<%@page import="uit.pubguru.constant.PubGuruConst"%>
<!--@author: ThanhIT-->
<%
        if(request.getAttribute("ListAuthor")==null)
            {
                 RequestDispatcher dispatcher = request.getRequestDispatcher("/AuthorController?action=getList");
            dispatcher.forward(request, response);
            }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Publication Guru</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
    </head>

    <body>
        <div class="wrapper">
              <div class="content">
                  <form>
                    <c:set var="info" value="${requestScope.ListAuthor}"/>
                    <c:if test="${not empty info}">
                        <table border="1">
                               <tbody>
                                <c:set var="count" value="0"/>
                                <c:forEach var="rows" items="${info}">
                                    <c:set var="count" value="${count +1}"/>
                                    <tr>
                                            <c:url var="edit" value="AuthorEditController">
                                             <c:param name="action" value="info"/>
                                             <c:param name="authorID" value="${rows.author_Id}"/>
                                            </c:url>
                                             <c:set var="id" value="${rows.author_Id}" scope="request"/>
                                             <c:set var="authorName" value="${rows.authorName}" scope="request"/>
                                             <c:set var="imageUrl" value="${rows.imageUrl}" scope="request"/>
                                             <c:set var="website" value="${rows.website}" scope="request"/>
                                           <div class="result_info author_card">
                                                    <div class="result_info">
                                                        <div class="author_image">
                                                            <img style="width: 96px; border-width: 0px;" alt="{{author_name}}" src="<%=String.valueOf(request.getAttribute("imageUrl"))%>" title="" id="">
                                                        </div>
                                                        <div class="author_info">

                                                            <div class="name_univercity">
                                                                <div class="author_name">
                                                                    <a class="title" href="#"><%=String.valueOf(request.getAttribute("authorName"))%></a>
                                                                     <a href="${edit}">View Info </a>
                                                                </div>

                                                                <div class="univercity_name">
                                                                    <br>
                                                                </div>
                                                            </div>
                                                            <div class="author_publication">
                                                                <img class="paper_icon" src="images/paper_icon.png">
                                                                <span>Publication : {{countPub}} </span><br>
                                                                <img class="paper_icon" src="images/paper_icon.png">
                                                                <span> Citations: {{author_citation}}</span>
                                                            </div>
                                                            <div class="author_homepage">
                                                                <img class="paper_icon" src="images/homepage_icon.gif">
                                                                <span>Home page : </span><a class="item_content" href="<%=String.valueOf(request.getAttribute("website"))%>"><%=String.valueOf(request.getAttribute("website"))%></a>
                                                                <br>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                                                
                                         </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </form>
            </div><!-- end .content -->
            <div class = "push"></div>
        </div><!-- end .wrapper -->        
    </body>
</html>

<%@page import="uit.pubguru.model.FeedBackModel"%>
<%@page import="uit.pubguru.model.Author"%>
<%@page import="uit.pubguru.model.UserModel"%>
<%@page import="uit.pubguru.model.Author_EditModel"%>
<%@page import="uit.pubguru.constant.PubGuruConst"%>
<!--@author: ThanhIT-->
<%
            String username = (String) session.getAttribute("username");
            String login = (String) session.getAttribute("login");
            String permission = (String) session.getAttribute("permissionId");
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


            String listname = String.valueOf(request.getParameter("status"));
            if (listname.equals(String.valueOf(PubGuruConst.EDITED_STATUS)))// status  1 : edited
            {
                listname = "Improved Feedbacks";
            } else {
                if (listname.equals(String.valueOf(PubGuruConst.CONFIRM_STATUS))) {
                    listname = "New Feedbacks";
                } else {
                    if (listname.equals(String.valueOf(PubGuruConst.ALL_STATUS))) {
                        listname = "All Feedbacks";
                    } else {
                        listname = "Ignored Feedbacks";
                    }

                }
            }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Begin one column window -->
<div class="onecolumn">    
    <div class="header">
        <span><%=listname%></span>
    </div>
    <br class="clear"/>
    <div class="list_feedback_wrapper">
        <div class="content">
            <c:set var="info" value="${requestScope.ListFeed}"/>
            <c:set var="row" value="${requestScope.Rows}"/>
            <c:url var="getlistsortdateedit" value="FeedBackController">
                <c:param name="action" value="getList"/>
                <c:param name="status" value="<%=(String) request.getAttribute("status_")%>"/>
                <c:param name="sortby" value="dateedit"/>
                <c:param name="page" value="<%=(String) request.getAttribute("page_")%>"/>
                <% String sort1 = (String) request.getAttribute("sort");%>
                <% if (sort1 == null) {%>
                <c:param name="sort" value="desc"/>
                <% } else {
                     if (sort1.equals("desc")) {%>
                <c:param name="sort" value="asc"/>
                <%} else {%>
                <c:param name="sort" value="desc"/>
                <%}
                            }%>
            </c:url>
            <c:url var="getlistsortdateimp" value="FeedBackController">
                <c:param name="action" value="getList"/>
                <c:param name="status" value="<%=(String) request.getAttribute("status_")%>"/>
                <c:param name="sortby" value="dateimp"/>
                <c:param name="page" value="<%=(String) request.getAttribute("page_")%>"/>
                <% String sort2 = (String) request.getAttribute("sort");%>
                <% if (sort2 == null) {%>
                <c:param name="sort" value="desc"/>
                <% } else {
                     if (sort2.equals("desc")) {%>
                <c:param name="sort" value="asc"/>
                <%} else {%>
                <c:param name="sort" value="desc"/>
                <%}
                            }%>
            </c:url>
            <c:if test="${not empty info}">
                <form id="form_data">
                    <table class="data" width="100%" cellpadding="0" cellspacing="0">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <!--  <th>Author Id </th> -->
                                <th>Author Name </th>
                                <th>User Send FeedBack</th>
                                <!-- <th>User Confirm</th> -->
                                <th><a href="${getlistsortdateedit}">Date Send Feedback</a></th>
                                <%if (((String) request.getAttribute("status_")).equals("0")) {%>
                                <th>Date Improve</th>
                                <% } else {%>
                                <th><a href="${getlistsortdateimp}">Date Improve</a></th>
                                <%}%>
                                <th>Status</th>
                                <th></th>

                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="count" value="0"/>
                            <c:forEach var="rows" items="${info}">
                                <c:set var="count" value="${count +1}"/>
                                <tr>

                                    <c:set var="authorEditId" value="${rows.authorEdit_Id}" scope="request"/>
                                    <c:set var="userId" value="${rows.userId}" scope="request"/>
                                    <c:set var="userIdConfirm" value="${rows.userId_Confirm}" scope="request"/>
                                    <c:set var="status" value="${rows.status}" scope="request"/>
                                    <%
                                                String a = String.valueOf(request.getAttribute("authorEditId"));
                                                String status = String.valueOf(request.getAttribute("status"));
                                                if (status.equals(String.valueOf(PubGuruConst.EDITED_STATUS)))// status  1 : edited
                                                {
                                                    status = "Improved";
                                                } else {
                                                    if (status.equals(String.valueOf(PubGuruConst.CONFIRM_STATUS))) {
                                                        status = "New";
                                                    } else {
                                                        status = "Ignored";
                                                    }
                                                }

                                                UserModel user = new UserModel();
                                                Author_EditModel author = new Author_EditModel();
                                                author = author.getAuthor_byId(Integer.parseInt(a));
                                                Author au = new Author();
                                                au = au.getAuthorById(author.getAuthor_Id());
                                                FeedBackModel fee = new FeedBackModel();

                                    %>
                                    <td>${count}.</td>
                                    <!-- <td>
                                    <%//=author.getAuthor_Id()%>
                                </td> -->
                                    <td><%=au.getAuthorName()%></td> <%-- get  author name in  original database --%>
                                    <td><%=user.name_byId(Integer.parseInt(String.valueOf(request.getAttribute("userId"))))%></td> <%-- get email depends on userid--%>
                                   <!-- <td><%//=user.name_byId(Integer.parseInt(String.valueOf(request.getAttribute("userIdConfirm"))))%></td>-->
                                    <td>${rows.dateEdit}</td>
                                    <td>${rows.dateEdit_Confirm}</td>
                                    <td><%=status%></td>
                                    <c:url var="edit" value="FeedBackController">
                                        <c:param name="action" value="Edit"/>
                                        <c:param name="imp" value="1"/>
                                        <c:param name="authorEdit_Id" value="${rows.authorEdit_Id}"/>
                                    </c:url>
                                    <c:url var="edit1" value="FeedBackController">
                                        <c:param name="action" value="Edit"/>
                                        <c:param name="imp" value="0"/>
                                        <c:param name="authorEdit_Id" value="${rows.authorEdit_Id}"/>
                                    </c:url>

                                    <% if (status.equals("New") == false)//
                                    {%>
                                    <td> <a href="${edit}">View</a> </td>
                                    <%} else {%>
                                    <td><a href="${edit1}">View</a>   </td>
                                    <%}%>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </form>        
                <!-- Begin pagination -->
                <div class="pagination"> 
                   
                    <%String sort = (String) request.getAttribute("sort");%>
                    <c:forEach var="i" begin="1" end= "${row}" step="1" varStatus ="status">
                        <c:url var="getlist" value="FeedBackController">
                            <c:param name="action" value="getList"/>
                            <c:param name="status" value="<%=(String) request.getAttribute("status_")%>"/>
                            <%if (sort != null) {%>
                               <c:param name="sortby" value="<%=(String) request.getAttribute("sortby")%>"/>
                            <%}%>
                               <c:param name="page" value="${i}"/>
                            <%if (sort != null) {%>
                               <c:param name="sort" value="<%=sort%>"/>
                            <%}%>
                        </c:url>
                        <td> <a href=${getlist}>${i}</a> </td>
                    </c:forEach>
                   
                </div>
                <!-- End pagination -->   
            </c:if>
        </div>        

    </div>
</div>
<!-- End one column window -->
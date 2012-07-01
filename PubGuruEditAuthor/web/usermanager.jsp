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
           /* if (login == null) {
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                dispatcher.forward(request, response);
            } else {
                if (login.equals("true")) {
                    if (permission.equals(String.valueOf(PubGuruConst.ADMIN_PERMISSION))) {
                        request.setAttribute("pageName", PubGuruConst.PER_ERror_PAGE);
                        RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                        dispatcher.forward(request, response);
                    }
                } else {
                    request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                    RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                    dispatcher.forward(request, response);
                }
            }*/
%>

<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="onecolumn">
    <div class="header">
        <span>List data</span>
    </div>
    <br class="clear"/>
    <div class="content">
        <form id="form_data" action="UserController" method="POST">
            <c:set var="info" value="${requestScope.ListUser}"/>
            <c:set var="row" value="${requestScope.Rows}"/>
            <c:if test="${not empty info}">
                <table  class="data" width="100%" cellpadding="0" cellspacing="0">
                    <thead>
                        <tr>
                               <th>
                                No.
                            </th>
                            <th>Email</th>
                            <th>Full Name</th>
                            <th>Country</th>
                            <th>Date Register</th>
                            <th>Permission</th>
                            <th>organization</th>
                            <th>Actived</th>
                            <th>Enable/Disable</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="count" value="0"/>
                        <c:forEach var="rows" items="${info}">
                            <c:set var="count" value="${count +1}"/>
                            <tr>
                                <c:set var="perId" value="${rows.permissionId}" scope="request"/>
                                <c:set var="actived" value="${rows.actived}" scope="request"/>
                                <%
                                            int per = Integer.parseInt(String.valueOf(request.getAttribute("perId")));
                                            int isActived = Integer.parseInt(String.valueOf(request.getAttribute("actived")));
                                            String userType = (per == PubGuruConst.ADMIN_PERMISSION) ? "Admin" : "User";
                                            String actived = (isActived == PubGuruConst.ACTIVED) ? "Actived" : "";
                                %>
                                <td>${count}.</td>
                                <!--  <td>
                                ${rows.id}
                               </td>-->
                                <td>${rows.emailAddress}</td>
                                <td>${rows.fullName}</td> 
                                <td>${rows.country}</td>
                                <td>${rows.dateRegister}</td>                                
                                <td><%=userType%></td>
                                <td>${rows.organization}</td>
                                <td><%=actived%></td>
                                <c:url var="enable" value="UserController">
                                    <c:param name="action" value="Enable"/>
                                    <c:param name="userId" value="${rows.id}"/>
                                </c:url>
                                <c:url var="disenable" value="UserController">
                                    <c:param name="action" value="Disable"/>
                                    <c:param name="userId" value="${rows.id}"/>
                                </c:url>
                                <% if (per == PubGuruConst.DISENABLE_USER)//
                                            {%>
                                <td> <a href="${enable}">Enable</a> </td>
                                <%} else {%>
                                <td> <a href="${disenable}">Disable</a> </td>
                                <%}%>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <!-- Begin pagination -->
                <div class="pagination">                    
                    <c:forEach var="i" begin="1" end= "${row}" step="1" varStatus ="status">
                        <c:url var="getlist" value="UserController">
                            <c:param name="action" value="getList"/>
                            <c:param name="active" value="2"/>
                            <c:param name="page" value="${i}"/>
                        </c:url>
                        <td> <a class="" href=${getlist}>${i}</a> </td>
                    </c:forEach>                   
                </div>
                <!-- End pagination -->    
            </c:if>

        </form>
    </div>    
</div> 
<!-- End one column window -->
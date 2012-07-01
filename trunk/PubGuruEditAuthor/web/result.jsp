<%@page import="uit.pubguru.constant.PubGuruConst"%>
<%
    String pageResult = (String) request.getAttribute("pageResult");
    session.setAttribute("pageResult_", pageResult);
     String authorId = (String) session.getAttribute("author_ID");
    if (pageResult == null) {
        pageResult = PubGuruConst.RESULT_REGISTER;
    }

%>

                <form action ="" method="POST">

                            <% if (pageResult.equals(PubGuruConst.RESULT_REGISTER)) {%>
                            <div class="result_message">
                                <span class="message_sucessness"><img src="images/editauthor/success.png"/></span>
                                <div>
                                    <span class="result_title">Your Request was Submitted Successfully</span>
                                    <p> Your request to has been submitted to PubGuru Search successfully.</p>
                                    <p>Please confirm registration by check email to active your account! </p>
                                    <p>Thanks. </p>
                                </div>
                                <div  style=" margin-right: 321px; margin-top: 50px;">
                                    <input id="submitbutton_cancle" name="action" type="submit" value="Continue" class="g_button g_button_submit" onclick="refresh('UserController?action=loginform')"/>
                                </div>
                            </div>
                            <% }%>
                            <% if (pageResult.equals(PubGuruConst.RESULT_EDIT)) {%>
                            <div class="result_message">
                                <span class="message_sucessness"><img src="images/editauthor/success.png"/></span>
                                <div>
                                    <span class="result_title">Your Request was Submitted Successfully</span>
                                    <p>Your request to edit Author has been submitted successfully.</p>
                                    <p>Thank you for your feedback, we will look into the issue you just reported. It usually takes up to one week to finish the verification and update the website with correct information. We appreciate your patience.</p>
                                </div>
                                <div  style=" margin-right: 321px; margin-top: 50px;">
                                    <input id="submitbutton_cancle" name="action" type="submit" value="Continue" class="g_button g_button_submit" onclick="refresh('/PubGuru/author.guru?aid=<%=authorId%>')" />
                                </div>
                            </div>
                            <% }%>
                            <% if (pageResult.equals(PubGuruConst.RESULT_IGNORE)) {%>
                            <div class="result_message">
                                <span class="message_sucessness"><img src="images/editauthor/success.png"/></span>
                                <div>
                                    <span class="result_title">You have been ignored this feedback.</span>
                                </div>
                                <div  style=" margin-right: 321px; margin-top: 50px;">
                                    <input id="submitbutton_cancle" name="action" type="submit" value="Continue" class="g_button g_button_submit" onclick="refresh('index')" />
                                </div>
                            </div>
                            <% }%>
                            <% if (pageResult.equals(PubGuruConst.RESULT_COMPETE)) {%>
                            <div class="result_message">
                                <span class="message_sucessness"><img src="images/editauthor/success.png"/></span>
                                <div>
                                    <span class="result_title">This author information has been updated !</span>
                                </div>
                                <div  style=" margin-right: 321px; margin-top: 50px;">
                                    <input id="submitbutton_cancle" name="action" type="submit" value="Continue" class="g_button g_button_submit" onclick="refresh('index')" />
                                </div>
                            </div>
                            <% }%>
                            <% if (pageResult.equals("loginfail")) {%>
                            <div class="result_message">
                                <span class="message_sucessness"><img src="images/editauthor/fail.png"/></span>
                                <div>
                                    <span class="result_title" ><font color ="red">Login fail ! Your Your email or password is wrong.</font></span>
                                </div>
                                <div  style=" margin-right: 321px; margin-top: 50px;">
                                    <input id="submitbutton_cancle" name="action" type="submit" value="Continue" class="g_button g_button_submit" onclick="refresh('UserController?action=loginform')" />
                                </div>
                            </div>
                            <% }%>
                </form>

        <script>
            $(function(){
                $("input[type='submit']").click(function(){
                    return false;
                });
            });
            function refresh(link){
                window.location = link;
                return false;
            }
        </script>


    </body>
</html>

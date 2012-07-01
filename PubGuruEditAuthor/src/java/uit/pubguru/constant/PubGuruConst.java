package uit.pubguru.constant;

public class PubGuruConst {

    public static final String DATABASE                 = "pubgurueditauthor";
    public static final String USER_DB                  = "root";
    public static final String PASS_DB                  = "root";
    public static final String PUBDATABASE              = "cspublicationcrawler";
    public static final String PUBUSER_DB               = "root";
    public static final String PUBPASS_DB               = "root";

    public static final String ERROR_PAGE               = "fail.jsp";
    public static final String LOGIN_PAGE               = "login.jsp";
    public static final String REGISTER_PAGE            = "register.jsp";
    public static final String AUTHOREDIT_PAGE          = "authoredit.jsp";
    public static final String RESULT_PAGE              = "result.jsp";
    public static final String PER_ERror_PAGE           = "error.jsp";
    public static final String BACKEND_PAGE             = "backend.jsp";
    public static final String COFIRMEDIT_PAGE          = "confirmedit.jsp";
    public static final String FEEDBACK_MANAGER_PAGE    = "feedbackmanager.jsp";
    public static final String INDEX_PAGE               = "index.jsp";
    public static final String AUTHORLIST_PAGE          = "authorlist.jsp";
    public static final String BACKEND_TEMPLATE_PAGE    = "backendTemplate.jsp";
    public static final String USER_PAGE                = "usermanager.jsp";
    public static final String AUTHORINFO_PAGE          = "authorinfo.jsp";
    public static final String FRONT_TEMPLATE_PAGE      = "frontTemplate.jsp";
    
    public static final String HTTP_SERVER              = "http://localhost:8080/PubGuruEditAuthor";
    public static final String HTTP_PUBGURU_SERVER      = "http://localhost:8080/PubGuru";
    
    public static final String SMTP_SERVER              = "smtp.gmail.com";
    public static final String EMAIL_ACTIVE_SUBJECT     = "Email Active Pubguru Account"; // when user register, send email to active with this subject
    public static final String EMAIL_SENDEDIT_SUBJECT   = "Email get edit infor";// when user send edit infor about author, send email with this subject
    public static final String EMAIL_EDITED_SUBJECT     = "Email Edit Author Pubguru Account";// when edit infor is edited, send email with this subject
    public static final String EMAIL_FROM               = "pubguru.uit@gmail.com";
    public static final String EMAIL_PASS               = "pubguru.uit";

    public static final int   ACTIVED                   =   1; // when account is actived, this value = 1, else = 0.
    public static final int   ADMIN_PERMISSION          =   1; // user permission = admin. else = 0;
    public static final int   DISENABLE_USER            =   2; //
    public static final int   ENABLE_USER               =   0; //
    public static       int   USER_ID_COFIRM            =   0;
    
    public static final String   RESULT_DEFAULT         =  "DEFAULT";
    public static final String   RESULT_REGISTER        =  "REGISTER";
    public static final String   RESULT_EDIT            =  "EDIT";
    public static final String   RESULT_IGNORE          =  "IGNORE"; //backen
    public static final String   RESULT_COMPETE         =  "COMPLETE"; //backend
    public static final int   EDITED_STATUS             =   1;// edited feedback.
    public static final int   CONFIRM_STATUS            =   0;// confirm feedback
    public static final int   IGNORE_STATUS             =   2;//ignore feedback
    public static final int   ALL_STATUS                =   3;// all feeback status
    public static final int   ROW_PERPAGE               =   25; // user permission = admin. else = 0;
    
    public static final String   GET_AUTHOR_INFO        =   "http://localhost:8080/PubGuruEditAuthor/AuthorController?action=getInfo";
    public static final String   POST_AUTHOR_INFO       =   "http://localhost:8080/PubGuruEditAuthor/AuthorController?action=postInfo";
    
    public static final String CONTENT_ACTIVE(String linkactive)
    {
        return ("You are almost done ! <br> <a href='" + linkactive + "'>" + "Click here to complete your registration !" + "</a> <br> Follow this link, you can log in to <a href='" + PubGuruConst.HTTP_SERVER + "'>" + " Pub Guru </a> using email, password that you have just register.<br> Thanks!");
    }
    public static final String CONTENT_IMPROVE(String username, String authorname,String url)
    {
        return("Dear " + username + " "
                        + ", <br> We have edited info of author : " + authorname + " who you sent edit info afew day ago!"
                        + "<br> <a href='" + url + "'>" + "Click here to view update !" + "</a> <br>"
                        + "<br> Thanks for your attention.");
    }
    public static final String CONTENT_IGNORE(String username, String authorname)
    {
        return("Dear " + username + " "
                    + "! <br> We are so sorry, your request edit athor : " +  authorname + " is not right, we can't update your info!!"
                    + "<br> Thanks for your request.");
    }
}

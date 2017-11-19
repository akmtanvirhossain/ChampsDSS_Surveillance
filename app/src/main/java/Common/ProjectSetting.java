package Common;

/**
 * Created by TanvirHossain on 19/07/2016.
 */
public class ProjectSetting {
    public static String MainInterview  = "1";
    public static String QAInterview    = "2";
    public static String MainInterviewName  = "Main Interview";
    public static String QAInterviewName    = "QA Interview";

    //Main Interview
    //----------------------------------------------------------------------------------------------
    //public static String InterviewType  = MainInterview;
    //public static String ProjectName    = "CHAMPS_DSS";

    //QA Interview
    //----------------------------------------------------------------------------------------------
    public static String InterviewType  = QAInterview;
    public static String ProjectName    = "CHAMPS_DSS_QA";




    public static String Namespace      = "http://chu.icddrb.org/";
    public static String apiName        = ProjectName.toLowerCase();
    public static String NewVersionName = ProjectName.toLowerCase() +"dss_update";
    public static String DatabaseFolder = ProjectName.toUpperCase() +"DSSDB";
    public static String DatabaseName   = ProjectName.toUpperCase() +"Database.db";
    public static String zipDatabaseName= ProjectName.toUpperCase() +"Database.zip";
    public static String Organization   = "ICDDR,B";



}

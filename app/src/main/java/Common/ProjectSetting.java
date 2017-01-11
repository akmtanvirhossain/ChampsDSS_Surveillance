package Common;

/**
 * Created by TanvirHossain on 19/07/2016.
 */
public class ProjectSetting {
    public static String ProjectName    = "CHAMPS";
    public static String Namespace      = "http://chu.icddrb.org/";

    public static String apiName        = ProjectName.toLowerCase();
    public static String NewVersionName = ProjectName.toLowerCase() +"_update";
    public static String DatabaseFolder = ProjectName.toUpperCase() +"DSSDB";
    public static String DatabaseName   = ProjectName.toUpperCase() +"Database.db";
    public static String zipDatabaseName= ProjectName.toUpperCase() +"Database.zip";
    public static String Organization   = "ICDDR,B";
}

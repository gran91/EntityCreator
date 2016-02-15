/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.Config;
import ui.IUIMain;
import zio.XMLManage;

/**
 *
 * @author Jeremy.CHAUT
 */
public class Ressource {

    public static String[] tabConf = {"config.skin",
        "config.lang",
        "config.console",
        "config.pathFile",
        "config.fileName"
    };
    
    public static Config conf = new Config("config.properties", Ressource.tabConf);
    public static Logger logger=LogManager.getLogger("main");
    
    public static final int ALPHA = 0;
    public static final int ALPHA_WITHOUT_NUMERIC = 1;
    public static final int ALPHA_WITHOUT_SPECHAR = 2;
    public static final int ALPHA_WITHOUT_NUM_SPE = 3;
    public static final int NUMERIC = 4;
    public static final int IP = 5;
    public static final int FILE = 6;
    public static final int DIRECTORY = 7;
    public static final int BOOLEAN = 8;

    public static final String XML_MODEL = "xmlModel";
    public static final String XML_LNG = "xml/lng";

    public static IUIMain mainView;
    /*
     * DataType
     */
    public static String DATA_TYPE_PROP = "config.datatype";
    public static String DATA_LIB_PROP = "config.library";
    public static final int XMLDATA = 0;
    public static final int SQLDATA = 1;
    public static final int CSVDATA = 2;
    public static final String[] defaultElmt = {"name"};
    public static String[] headerDefault = {i18n.Language.getLabel(11), "id"};
    /*
     * Customer
     */
    public static final String custXmlRoot = "custs";
    public static final String custXmlChild = "cust";
    public static final String custFileName = "customers";
    public static String pathCustDir = "xml/customers/";
    public static String pathCustFile = "xml/customers/customers.xml";
    public static final XMLManage xmlCust = new XMLManage(custXmlRoot, custXmlChild);
    public static File fileCust = new File(Ressource.pathCustDir);
    /*
     * Environment
     */
    public static final String envXmlRoot = "envs";
    public static final String envXmlChild = "env";
    public static final String pathEnvFile = "/envs.xml";
    public static final String envFileName = "envs";
    public static final XMLManage xmlEnv = new XMLManage(envXmlRoot, envXmlChild);
    public static final String[] envElmt = {"name", "type", "host", "port", "login", "pass", "pathMNE", "logMNE", "passMNE"};
    public static String[] headerEnv = {i18n.Language.getLabel(11),
        i18n.Language.getLabel(82),
        i18n.Language.getLabel(5),
        i18n.Language.getLabel(92),
        i18n.Language.getLabel(7),
        i18n.Language.getLabel(8),
        i18n.Language.getLabel(181),
        i18n.Language.getLabel(7),
        i18n.Language.getLabel(8),
        "id"};
    /*
     * Workspace
     */
    public static final String workXmlRoot = "workspaces";
    public static final String workXmlChild = "workspace";
    public static final String workFileName = "workspace";
    public static String pathWorspaceFile = System.getProperty("file.separator") + "workspace.xml";
    public static final XMLManage xmlWorkspace = new XMLManage(workXmlRoot, workXmlChild);
    public static final String[] workElmt = {"name", "path", "login", "pass"};

    /*
     * System Type
     */
    public static final String sysXmlRoot = "systs";
    public static final String sysXmlChild = "syst";
    public static final XMLManage xmlSys = new XMLManage(sysXmlRoot, sysXmlChild);
    public static String pathSysFile = "xml/sysType.xml";
    public static Object[] oBlk = {""};
    public static final String[] lstSuperClassStd = {"mvx.app.common.Interactive",
        "mvx.app.common.Batch",
        "mvx.app.common.MIBatch",
        "mvx.app.common.Print",
        "mvx.app.common.MvxCL"};
    public static final String protocole = "http";
    public static final String jvmSysProp = "JVM%20System%20Properties";
    public static final String jvmInfo = "jvminfo?addr=";
    public static final int[] envColXls = {9, 5, 92, 7, 8};
    public static final int[] envWorkXls = {11, 182, 7, 8};
    public static final int[] envCompXls = {191, 190};
    public static final int[] envMigrateXls = {192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202};
    /*
     * IMAGE
     */
    public static final String imgLogo = "img/logo.png";
    public static final String imgAdd = "img/add.png";
    public static final String imgDel = "img/del.png";
    public static final String imgUpdate = "img/modif.png";
    public static final String imgSelect = "img/select.png";
    public static final String imgUnselect = "img/unselect.png";
    public static final String imgExcel = "img/excel.png";
    public static final String imgProcess = "img/process.png";
    public static final String imgSave = "img/save.png";
    public static final String imgRefresh = "img/refresh.png";
    public static final String imgColorPicker = "img/color-picker.png";
    /*
     * REGEX
     */
    public static final String regex_email = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$";
    public static final String regex_tel = "([0-9]{2}.){4}[0-9]{2}";
    public static final String regex_ip = "[0-9]{1,3}(?:.[0-9]{1,3}){3}";
    public static final String regex_url = "[a-z]{3,}://[a-z0-9-]+.[.a-z0-9-]+(?::[0-9]*)?";
    public static final String regex_hyperlink = "<a href=([^>]*)>([^<]*)</a>";

    public static final SimpleDateFormat formatTime12 = new SimpleDateFormat("hh:mm:ss");
    public static final SimpleDateFormat formatTime24 = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat formatDateYMD8 = new SimpleDateFormat("yyyyMMdd");
}

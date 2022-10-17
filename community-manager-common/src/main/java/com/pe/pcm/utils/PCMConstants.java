/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.utils;

/**
 * @author Chenchu Kiran.
 */
public class PCMConstants {

    private PCMConstants() { /*Not to create Object*/ }

    public static final String PCM_ADMIN = "PCMAdmin";
    public static final String TP_PKEY_PRE_APPEND = "TP";
    public static final Integer TP_PKEY_RANDOM_COUNT = 6;
    public static final String TP_ENV_PKEY_PRE_APPEND = "ENV";
    public static final Integer TP_ENV_PKEY_RANDOM_COUNT = 8;
    public static final String APP_PKEY_PRE_APPEND = "APP";
    public static final Integer APP_PKEY_RANDOM_COUNT = 5;

    public static final Integer PROTOCOL_PKEY_RANDOM_COUNT = 5;
    public static final String PROTOCOL_AS2_PKEY_PRE_APPEND = "AS2";
    static final String PROTOCOL_SMTP_PKEY_PRE_APPEND = "SMTP";
    static final String PROTOCOL_FTP_PKEY_PRE_APPEND = "FTP";
    static final String PROTOCOL_FTPS_PKEY_PRE_APPEND = "FTP";
    static final String PROTOCOL_SFTP_PKEY_PRE_APPEND = "SFTP";
    static final String PROTOCOL_HTTP_PKEY_PRE_APPEND = "HTTP";
    static final String PROTOCOL_HTTPS_PKEY_PRE_APPEND = "HTTPS";
    static final String PROTOCOL_MQ_PKEY_PRE_APPEND = "MQ";
    static final String PROTOCOL_MAILBOX_PKEY_PRE_APPEND = "MB";
    static final String PROTOCOL_SAP_PKEY_PRE_APPEND = "SAP";
    static final String PROTOCOL_WEBSERVICE_PKEY_PRE_APPEND = "WS";
    static final String PROTOCOL_FILESYSTEM_PKEY_PRE_APPEND = "FS";
    static final String PROTOCOL_CD_PKEY_PRE_APPEND = "CD";
    static final String PROTOCOL_SFG_CD_PKEY_PRE_APPEND = "SCD";
    static final String PROTOCOL_CUSTOM_PROTOCOL_PKEY_PRE_APPEND = "CP";
    static final String PROTOCOL_GOOGLE_DRIVE_PROTOCOL_PKEY_PRE_APPEND = "GD";
    static final String PROTOCOL_EC_PKEY_PRE_APPEND = "EC";
    public static final String RULES_PKEY_PRE_APPEND = "R";
    public static final Integer RULES_PKEY_RANDOM_COUNT = 5;
    public static final String PROCESS_PKEY_PRE_APPEND = "P";
    public static final String PROCESS_PAPIKEY_PRE_APPEND = "PAPI";
    public static final Integer PROCESS_PKEY_RANDOM_COUNT = 8;
    public static final String PROCESS_RULES_PKEY_PRE_APPEND = "PR";
    public static final Integer PROCESS_RULES_PKEY_RANDOM_COUNT = 8;
    public static final String PROCESS_DOCS_PKEY_PRE_APPEND = "PD";
    public static final String PROCESS_API_DOCS_PKEY_PRE_APPEND = "PD";
    public static final Integer PROCESS_DOCS_PKEY_RANDOM_COUNT = 6;
    static final String PROTOCOL_ORA_PKEY_PRE_APPEND = "OR";
    static final String PROTOCOL_AWS_S3_PKEY_PRE_APPEND = "AWS";

    //AS2 Constants
    public static final String PCM_UI = "PCMUI";
    public static final String INSTALL_PROCESS = "InstallProcess";
    public static final String STRING_TRUE = "true";
    public static final String STRING_FALSE = "false";
    public static final String CM_API = "CM-API";
    public static final String DELIVERY = "delivery";
    public static final String NO = "no";

    static final String DATE_FORMAT_PCM = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_ORACLE = "YYYY-MM-DD HH24:MI:SS";
    static final String ONLY_DATE_ORACLE = "MM-dd-yyyy";
    static final String TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";
    static final String DATE_LOCALE_FORMAT = "E MMM dd yyyy HH:mm:ss zzzz";
    static final String DATE_FORMAT_DB2 = "yyyy-MM-dd-kk.mm.ss.SSS";


    public static final String B2B_ACT_HIST_PKEY_PRE_APPEND = "BAH";
    public static final Integer B2B_ACT_HIST_PKEY_RANDOM_COUNT = 10;
    public static final String EDI_ACT_HIST_PKEY_PRE_APPEND = "EDIH";
    public static final Integer EDI_ACT_HIST_PKEY_RANDOM_COUNT = 10;
    public static final String TP_ACT_HIST_PKEY_PRE_APPEND = "TPH";
    public static final Integer TP_ACT_HIST_PKEY_RANDOM_COUNT = 10;
    public static final String APP_ACT_HIST_PKEY_PRE_APPEND = "APH";
    public static final Integer APP_ACT_HIST_PKEY_RANDOM_COUNT = 10;
    public static final String PARTNER_GROUP_PKEY_PRE_APPEND = "GRP";
    public static final Integer PARTNER_GROUP_PKEY_RANDOM_COUNT = 10;
    public static final String PEM_CODE_LIST_PRE_APPEND = "GRP";
    public static final Integer PKEY_COUNT_TEN = 10;
    public static final String SCI_CONTRACT_PRE_APPEND = "SCT";
    public static final String SCI_CONTRACT_EXT_PRE_APPEND = "SCE";
    public static final String SCI_AS2_TRADE_PART_PRE_APPEND = "ATP";

    public static final Integer SCI_RANDOM_COUNT = 17;
    public static final String SCI_TR_OBJ_PRE_APPEND = "TR";
    public static final String SCI_DE_OBJ_PRE_APPEND = "DE";
    public static final String SCI_DC_OBJ_PRE_APPEND = "DC";
    public static final String SCI_PC_OBJ_PRE_APPEND = "PC";
    public static final String SCI_PR_OBJ_PRE_APPEND = "PR";
    public static final String SCI_SI_CERT_PRE_APPEND = "SC";
    public static final String SCI_EX_CERT_PRE_APPEND = "EC";
    public static final String SCI_CA_CERT_PRE_APPEND = "CC";
    public static final String SCI_CERT_KEY_PRE_APPEND = "CE";
    public static final String SCI_YFS_CORP_ADDR_PRE_APPEND = "YF";

    // PCM Regular Words Constants
    static final String TWO_DOTS = "..";
    public static final String Y = "Y";
    public static final String N = "N";
    public static final String UI = "UI";
    public static final String EMPTY = "";
    public static final String EQUAL = "=";
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public static final String MFT = "MFT";
    public static final String NONE = "None";
    public static final String CODE = "code";
    public static final String IGNORE = "IGNORE";
    public static final String PARTNER = "Partner";
    public static final String DEFAULT = "DEFAULT";
    public static final String INBOUND = "Inbound";
    public static final String DOC_TYPE = "docType";
    public static final String OUTBOUND = "Outbound";
    public static final String TEMPLATE = "template";
    public static final String DIRECTORY = "directory";

    public static final String APPLICATION = "Application";
    public static final String DOC_HANDLING = "DocHandling";

    public static final String BASE_DIRECTORY = "baseDirectory";
    public static final String RETRY_INTERVAL = "retryInterval";
    public static final String EVALUATION_MODE = "evaluationMode";
    public static final String ERROR_DESCRIPTION = "errorDescription";
    public static final String ERROR_CODE = "errorCode";
    public static final String FILE_NAME = "FileName";
    public static final String RULE_NAME = "RuleName";
    public static final String DOC_TRAN_SEN_REC = "DocType-Transaction-SenderId-ReceiverId";

    public static final String EXCHANGE_CERT = "exchangeCert";
    public static final String SIGNING_CERTIFICATE = "signingCertificate";
    public static final String CA_CERTIFICATE = "caCertificate";
    public static final String KEY_CERTIFICATE = "keyCertificate";
    public static final String CERT_NAME = "certName";
    public static final String AS2_IDENTIFIER = "AS2Identifier";
    public static final String PROFILE_NAME = "profileName";
    public static final String PROFILE_NAMES = "profileNames";
    public static final String EMAIL_ADDRESS = "emailAddress";
    public static final String ADDRESS_LINE_1 = "addressLine1";
    public static final String ADDRESS_LINE_2 = "addressLine2";
    public static final String PHONE = "phone";

    // Alert Constants
    public static final String REQUEST_OK = "Request processed successfully";
    public static final String STATUS_UPDATE = "Status updated successfully";
    public static final String PARTNER_CREATE = "Partner created successfully";
    public static final String PARTNER_UPDATE = "Partner updated successfully";
    public static final String PARTNER_DELETE = "Partner deleted successfully";
    public static final String APPLICATION_CREATE = "Application created successfully";
    public static final String APPLICATION_UPDATE = "Application updated successfully";
    public static final String APPLICATION_DELETE = "Application deleted successfully";
    public static final String EXCEPTION_OCCURRED = "Exception occurred: '{}'";
    // public static final String INTERNAL_ERROR = "Internal server error, please try again."
    public static final String SERVER_BUSY = "Server is busy, Please try again after some time.";
    public static final String API_NOT_WORKING = "B2B Api is not working properly, Please contact B2B Administration Team.";
    public static final String TP_WORKFLOW_EXIST = "Unable to delete this Partner, Because it has WorkFlow.";
    public static final String APP_WORKFLOW_EXIST = "Unable to delete this Application, Because it has WorkFlows.";
    public static final String REQUEST_PROCESSED_OK = "Request was received and processed";

    // SQL Constants
//    static final String SQL_OR_CLAUSE = " OR "
//    static final String SQL_AND_CLAUSE = " AND "
    static final String SQL_FROM_CLAUSE = " FROM ";
    static final String SQL_WHERE_CLAUSE = " WHERE ";
    public static final String SQL_TO_TIMESTAMP = "TO_TIMESTAMP";
    static final String SQL_SELECT_CLAUSE = "SELECT ";
    public static final String SQL_INSERT_CLAUSE = "Insert";
    public static final String SQL_ORDER_BY = " ORDER BY ";
    public static final String RULE_NAME_COLUMN = "RULE_NAME";
    public static final String SRC_FILE_NAME_COLUMN = "srcfilename";
    public static final String FLOW_TYPE_COLUMN = "flowinout";
    public static final String SENDER_ID_COLUMN = "senderid";
    public static final String RECEIVER_ID_COLUMN = "reciverid";
    public static final String DOC_TRANS_COLUMN = "doctrans";
    public static final String TYPE_OF_TRANSFER_COLUMN = "typeoftransfer";
    public static final String STATUS_COMMENTS_COLUMN = "statusComments";
    public static final String FILE_ARRIVED_COLUMN = "filearrived";


    // Http Header Constants
    public static final String ACCEPT = "Accept";
    public static final String CONTENT_TYPE = "Content-type";
    public static final String CONTENT_LENGTH = "Content-Length";

    public static final String TP = "TP";
    public static final String APP = "APP";
    public static final String PARTNER_CREATED = "Trading Partner created.";
    public static final String APPLICATION_CREATED = "Application Created.";

    public static final String ORACLE = "oracle";
    public static final String SQL_SERVER = "sqlServer";
    public static final String DB2 = "db2";
    public static final String MAILBOX = "Mailbox";

    public static final String INTEGER = "INTEGER";
    public static final String NUMBER = "NUMBER";
    public static final String FLOAT = "FLOAT";
    public static final String DOUBLE = "DOUBLE";
    public static final String VARCHAR = "VARCHAR";
    public static final String VARCHAR2 = "VARCHAR2";
    public static final String DATE = "DATE";
    public static final String TIME = "TIME";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String BOOLEAN = "BOOLEAN";

    public static final String WORKFLOW_COUNT_QUERY = "SELECT count(*) FROM PETPE_TRADINGPARTNER TP, PETPE_APPLICATION AP, PETPE_PROCESS PC , PETPE_PROCESSDOCS PD, PETPE_PROCESSRULES PR WHERE TP.PK_ID = PC.PARTNER_PROFILE AND AP.PK_ID = PC.APPLICATION_PROFILE AND PC.SEQ_ID = PD.PROCESS_REF AND PD.PK_ID = PR.PROCESS_DOC_REF ";

    public static final String TRANSPORT = "transport_";
    public static final String DOC_EXCHANGE = "doc_exchange_";
    public static final String PACKAGING = "packaging_";
    public static final String PROFILE = "profile_";
    public static final String DELIVERY_CHAN = "delivchan_";
    public static final String _CONSUMER = "_CONSUMER";

    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    public static final String AES = "AES";

    public static final String AUTHORIZED_USER_KEYS = "authorizedUserKeys";
    public static final String GROUPS = "groups";
    public static final String USER_IDENTITY = "userIdentity";
    public static final String NODES = "nodes";
    public static final String NODE_NAME = "nodeName";

    public static final String AS2_7070 = ":-7070";
    public static final String AS2_7076 = ":-7076";
    public static final String __AS2 = ":--as2";

    public static final String PASSWORD = "password";

    public static final String CONSUMER = "CONSUMER";
    public static final String PRODUCER = "PRODUCER";

    public static final String PRAGMA_EDGE_S = "EMPTY-EMPTY";

    public static final String[] HEX = {
            "%00", "%01", "%02", "%03", "%04", "%05", "%06", "%07",
            "%08", "%09", "%0A", "%0A", "%0C", "%0D", "%0E", "%0F",
            "%10", "%11", "%12", "%13", "%14", "%15", "%16", "%17",
            "%18", "%19", "%1A", "%1B", "%1C", "%1D", "%1E", "%1F",
            "%20", "%21", "%22", "%23", "%24", "%25", "%26", "%27",
            "%28", "%29", "%2A", "%2B", "%2C", "%2D", "%2E", "%2F",
            "%30", "%31", "%32", "%33", "%34", "%35", "%36", "%37",
            "%38", "%39", "%3A", "%3B", "%3C", "%3D", "%3E", "%3F",
            "%40", "%41", "%42", "%43", "%44", "%45", "%46", "%47",
            "%48", "%49", "%4A", "%4B", "%4C", "%4D", "%4E", "%4F",
            "%50", "%51", "%52", "%53", "%54", "%55", "%56", "%57",
            "%58", "%59", "%5A", "%5B", "%5C", "%5D", "%5E", "%5F",
            "%60", "%61", "%62", "%63", "%64", "%65", "%66", "%67",
            "%68", "%69", "%6A", "%6B", "%6C", "%6D", "%6E", "%6F",
            "%70", "%71", "%72", "%73", "%74", "%75", "%76", "%77",
            "%78", "%79", "%7A", "%7B", "%7C", "%7D", "%7E", "%7F",
            "%80", "%81", "%82", "%83", "%84", "%85", "%86", "%87",
            "%88", "%89", "%8A", "%8B", "%8C", "%8D", "%8E", "%8F",
            "%90", "%91", "%92", "%93", "%94", "%95", "%96", "%97",
            "%98", "%99", "%9A", "%9B", "%9C", "%9D", "%9E", "%9F",
            "%A0", "%A1", "%A2", "%A3", "%A4", "%A5", "%A6", "%A7",
            "%A8", "%A9", "%AA", "%AB", "%AC", "%AD", "%AE", "%AF",
            "%B0", "%B1", "%B2", "%B3", "%B4", "%B5", "%B6", "%B7",
            "%B8", "%B9", "%BA", "%BB", "%BC", "%BD", "%BE", "%BF",
            "%C0", "%C1", "%C2", "%C3", "%C4", "%C5", "%C6", "%C7",
            "%C8", "%C9", "%CA", "%CB", "%CC", "%CD", "%CE", "%CF",
            "%D0", "%D1", "%D2", "%D3", "%D4", "%D5", "%D6", "%D7",
            "%D8", "%D9", "%DA", "%DB", "%DC", "%DD", "%DE", "%DF",
            "%E0", "%E1", "%E2", "%E3", "%E4", "%E5", "%E6", "%E7",
            "%E8", "%E9", "%EA", "%EB", "%EC", "%ED", "%EE", "%EF",
            "%F0", "%F1", "%F2", "%F3", "%F4", "%F5", "%F6", "%F7",
            "%F8", "%F9", "%FA", "%FB", "%FC", "%FD", "%FE", "%FF"
    };

    public static final String S3_SCHEDULER_PK_ID = "S3-KEY";
    public static final String CUSTOM_ERROR_ATTRIBUTE = "CUSTOM_ERROR_ATTRIBUTE";
    public static final String INTERNAL = "INTERNAL";
    public static final String EXTERNAL = "EXTERNAL";

    public static final String EXTERNAL_SI = "EXT_SI";

    public static final String FILE_PUSH = "Files is pushing to mailbox. Please wait, this process will take time(depends on files size)";

    public static final String DOWNLOAD = "Download";
    public static final String UPLOAD = "Upload";
    public static final String SUCCESS = "Success";
    public static final String FAILED= "Failed";
    public static final String FILE_MANAGER = "FILE-MANAGER";
    public static final String SPACE_MAILBOX = " Mailbox";

    public static final String DEFAULT_HOST = "1";

    public static final String REQUEST_PROCESSED_SUCCESSFULLY = "request processed successfully!";
}

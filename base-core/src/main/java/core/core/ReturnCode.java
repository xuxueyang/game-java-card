package core.core;


public class ReturnCode {
    public static final String HAS_ERROR = "9999"; //�ɴ��뷵�ش�����Ϣ
    //��������2XXX
    public static final String SUCCESS = "200"; //�ɹ�

    public static final String DEFAULT_SUCCESS = "2000"; //�ɹ�
    public static final String CREATE_SUCCESS = "2001"; //�����ɹ�
    public static final String UPDATE_SUCCESS = "2002"; //�޸ĳɹ�
    public static final String DELETE_SUCCESS = "2003"; //ɾ���ɹ�
    public static final String GET_SUCCESS = "2004"; //��ȡ�ɹ�
//
//    public static int a=1;
//    public static void wxhiworld(String[] args){
//        System.out.println(a);
//        System.out.println(ab());
//        System.out.println(a);
//    }
//    public static int  ab(){
//        try {
//            return a;
//        }finally {
//            a++;
//        }
//    }
//    1
//        1
//        2
    //Ȩ����֤���� 3XXX

    public static final String ERROR_AOTHEN_MISS_USERNAME = "3000"; //�˺Ų�����
    public static final String ERROR_AOTHEN_MISS_TEL = "3001"; //δ��ϵͳ��ƥ�䵽���û�(���ٵ�¼δƥ���ֻ�����)
    public static final String ERROR_NO_PERMISSIONS_DELETE = "3002"; //û��Ȩ��ɾ����Դ
    public static final String ERROR_DEFAULT_USER_HAS_NO_ROLES = "3003"; //Ĭ�������û�н�ɫ
    public static final String ERROR_ACCT_IS_NOT_ACTIVATED = "3004"; //�˺�δ����
    public static final String ERROR_HAS_NOT_USERS = "3005"; //�˺���δ�ҵ���Ч���
    public static final String ERROR_PASSWORD_NOT_CORRECT_CODE = "3006"; //�˺����벻��ȷ
    public static final String ERROR_AUTHORIZATION = "3007"; //��Ч��Authorization
    public static final String ERROR_USER_HAS_LOGOUT = "3008"; //�û��Ѿ��ǳ�
    public static final String ERROR_HAVE_NO_PERMISSION_OPERATION = "3009"; //û��Ȩ�޽��в���
    public static final String ERROR_HAVE_NO_PERMISSION_OPERATION_SYS_ROLE = "3010"; //û��Ȩ�޲���ϵͳ��ɫ
    public static final String ERROR_HAVE_NO_PERMISSION_OPERATION_OWNER_ROLE = "3011"; //û��Ȩ�޲���owner��ɫ
    public static final String ERROR_HAVE_NO_PERMISSION_OPERATION_MEMBER_ROLE = "3012"; //û��Ȩ�޲���member��ɫ
    public static final String ERROR_HAVE_NO_PERMISSION_OPERATION_OWNER_USER = "3013"; //û��Ȩ�޲���owner�û�
    public static final String ERROR_USER_HAVE_NO_SYS_ROLE = "3014"; //�û�ȱʧϵͳ��ɫ
    public static final String ERROR_NO_PERMISSIONS_UPDATE = "3015"; //û��Ȩ�ޱ༭��Դ



    //-----------------------��Ӧ�ķ��������ϵ�httpStatus Ϊ401-------------------------------
    public static final String ERROR_TOKEN_INVALID = "3016"; //token��Ч

    public static final String ERROR_NOT_FIND_CODE_WITH_APP_ID_CODE = "3017"; //δ֪��appId
    public static final String ERROR_HEADER_NOT_FIND_APP_ID_CODE = "3018"; //header��û���ҵ�����appId
    public static final String ERROR_HEADER_SPACEURI_TENANT_CODE_IS_DEFFRENT_WITH_TOKEN_CODE = "3019";// token���tenantCode�� spaceUri���tenantCode��һ��
    public static final String ERROR_NOT_FIND_USER_BY_SPACEURI_CODE = "3020"; //��ǰspaceUri���޷��ҵ���Ч��ݣ��л����ʱ��
    public static final String ERROR_NOT_FIND_CODE_WITH_SPACE_URI_CODE = "3021"; //����spaceUriδƥ�䵽InstanceCode��TenantCode��SpaceCode
    public static final String ERROR_HEADER_NOT_FIND_SPACE_URI_CODE = "3022"; //header��û���ҵ�����spaceUri
    public static final String ERROR_HEADER_NOT_TENANT_CODE = "3023"; //�����ڵĿռ�

    //-----------------------��Ӧ�ķ��������ϵ�httpStatus Ϊ401-------------------------------

    public static final String ERROR_ACCT_NEED_CHECK = "3030"; //�˺���Ҫ���

    //�쳣����  4XXX
    public static final String ERROR_QUERY = "4000"; //��ѯʧ��
    public static final String ERROR_CREATE = "4001"; //����ʧ��
    public static final String ERROR_UPDATE = "4002"; //����ʧ��
    public static final String ERROR_DELETE = "4003"; //ɾ��ʧ��
    public static final String ERROR_SEND_SMS = "4004"; //���ŷ���ʧ��
    public static final String ERROR_UPLOAD_FILE = "4005"; //�ļ��ϴ�ʧ��
    public static final String ERROR_SEND_EMAIL = "4006"; //�ʼ�����ʧ��
    public static final String ERROR_LOGIN = "4007"; // ��¼ʧ��

    public static final String ERROR_UN_KNOW_PROGRAM = "4999"; //���������쳣


    //���߼���֤���� 50XX���ֶ�   51XX����Դ
    public static final String ERROR_FIELD_EMPTY = "5001"; //�ֶβ���Ϊ��
    public static final String ERROR_FIELD_FORMAT = "5002"; //�ֶθ�ʽ����ȷ
    public static final String ERROR_GRAPH_CODE = "5003"; //ͼ����֤�벻��ȷ
    public static final String ERROR_SMS_CODE = "5004"; //�ֻ���֤�벻��ȷ
    public static final String ERROR_ATTACHMENT_URL_BLANK = "5005"; //����URLΪ��
    public static final String ERROR_LINK_CODE = "5006"; //���Ӳ���ȷ
    public static final String ERROR_LINK_EXPIRED_CODE = "5007"; //���ӹ���
    public static final String ERROR_FIELD_NOT_EMPTY = "5008"; //�ֶα���Ϊ��
    public static final String ERROR_FIELD_STRING_LENGTH = "5009"; //�ֶεĳ��Ȳ�����
    public static final String ERROR_CAPTCHA_CODE = "5010"; //��֤�벻��ȷ
    public static final String ERROR_FIELD_UPDATE = "5011"; //�ֶβ��ܸ���
    public static final String ERROR_CONFIG = "5012"; //�����쳣
    public static final String ERROR_FIELD_RANGE = "5013"; //�ֶε�ֵ���ڹ涨��Χ��

    //�߼���֤���� 6XXX
    public static final String ERROR_ACCT_NOT_EXIST_CODE = "6000"; //�˺Ų�����
    public static final String ERROR_FIELD_EXIST_CODE = "6001"; //�ֶ��Ѿ�����
    public static final String ERROR_DELETE_NOT_EXIST_CODE = "6003"; //ɾ������Դ������
    public static final String ERROR_RESOURCE_NOT_EXIST_CODE = "6004"; //��Դ������
    public static final String ERROR_RESOURCE_HAS_CHILDREN_CODE = "6005"; //��Դ����Ԫ�أ��޷�ɾ��
    public static final String ERROR_RESOURCE_IS_USED_CODE = "6006"; //��Դ�ѱ�ʹ�ã��޷�ɾ��
    public static final String ERROR_CURRENT_USER_NOT_EXIST = "6007"; //��ǰ�û������ڣ�token��δȡ��userId��
    public static final String ERROR_INVALID_STATUS_CODE = "6008"; //��Դ������Ч״̬
    public static final String ERROR_RESOURCE_NOT_ALLOWED_DELETE = "6009"; //��Դ������ɾ��
    public static final String ERROR_RESOURCE_NOT_ALLOWED_UPDATE = "6010"; //��Դ�������޸�
    public static final String ERROR_RESOURCE_EXIST_CODE = "6011"; //��Դ�Ѿ�����
    public static final String ERROR_RESOURCE_NUMBER_UPPER_LIMIT = "6012"; //��Դ��Ŀ����
    public static final String ERROR_NOT_INVITED = "6013"; //�û�δ������
    public static final String ERROR_USER_NOT_EXIST_CODE = "6013"; //�û����(user)������
    public static final String ERROR_PERMISSION_NOT_EXIST_CODE = "6014"; //Ȩ�޲�����
    public static final String ERROR_ROLE_NOT_EXIST_CODE = "6015"; //��ɫ������
    public static final String ERROR_ROLE_HAS_USERS_CODE = "6016"; //��ɫ�����û���������ɾ����ɫ
    public static final String ERROR_UPDATE_SYS_ROLE_CODE = "6017"; //ϵͳ��ɫ��������ɾ�������
    public static final String ERROR_CHECK_NO_CHECK_STATUS_CODE = "6018"; //��¼���Ǵ�����״̬���޷���������
    public static final String ERROR_SPACE_IS_NOT_ACTIVATED = "6019"; //�ռ䲻�Ǽ���״̬
    public static final String ERROR_TENANT_HAS_NO_SPACE_CODE = "6020"; //�⻧�²����ڿռ�
    public static final String ERROR_ACCT_ID_UNMATCHED = "6021"; //�˺������˺�ID��ƥ��
    public static final String ERROR_USER_ID_UNMATCHED = "6022"; //�˺������û�ID��ƥ��
    public static final String ERROR_DATA_HAS_CHILDREN_CODE = "6023"; //���ݴ����ӽڵ�
    public static final String ERROR_ORGANIZATION_SPACE_HAS_EXIST = "6024";//��ҵ�ռ��Ѿ���ע��
    public static final String ERROR_LOGIN_CONFIG_NOT_SUPPORT_EMAIL_CODE = "6030";//��֧��EMAIL��¼ע��
    public static final String ERROR_LOGIN_CONFIG_NOT_SUPPORT_PHONE_CODE = "6031";//��֧���ֻ���¼ע��
    public static final String ERROR_LOGIN_CONFIG_NOT_SUPPORT_PHONE_CODE_CODE = "6032";//��֧�ֿ��ٵ�¼
    public static final String ERROR_LOGIN_CONFIG_SUPPORT_ADMIN_INVITATION_CODE = "6033";//ֻ֧�ֹ���Ա����
    public static final String ERROR_INVITATION_ROLE_CODE = "6034";//�����û������ֻ���ǻ�Ա(member)
    public static final String ERROR_LOGIN_CONFIG_NOT_SUPPORT_INVITATION_CODE = "6035";//��֧������
    public static final String ERROR_LOGIN_CONFIG_NOT_SUPPORT_EMAIL_NVITATION_CODE = "6036";//��֧�����䷽ʽ����
    public static final String ERROR_LOGIN_CONFIG_NOT_SUPPORT_PHONE_NVITATION_CODE = "6037";//��֧���ֻ���ʽ����
    public static final String ERROR_LOGIN_CONFIG_NOT_SUPPORT_REGISTY_CHECK_CODE = "6038";//��֧��ע�����
    public static final String ERROR_CONFIRM_PASSWORD_CODE = "6039";//ȷ������������ֵ�����
    public static final String ERROR_LOGIN_CONFIG_NOT_SUPPORT_NAME_CODE = "6040";//��֧���û�����¼
    public static final String ERROR_PARENT_ID_NOT_EXIST_CODE = "6041";//���ڵ㲻����
    public static final String ERROR_INVITATION_NOT_EXIST_CODE = "6042";//����ID������
    public static final String ERROR_INVITATION_DISABLE_CODE = "6043";  //����ʧЧ
    public static final String ERROR_SUPER_ADMIN_EXISTS_CODE = "6044";  //��������Ա�Ѵ���
    public static final String ERROR_ROLE_IS_NOT_SUPER_ADMIN_CODE = "6045";  //���ǳ�������Ա
    public static final String ERROR_MODIFY_SUPER_ADMIN_CODE = "6046";  //���ܶԳ�������Ա���в���
    public static final String ERROR_DISABLE_ENABLE_ROLE_CODE = "6047";  //��ǰ�Ľ�ɫ���ܽ������ý��ò���
    public static final String ERROR_EAMIL_VERIFIED_CODE = "6048";  //�û���������֤
    public static final String ERROR_CONTENT_CHANGE_STATUS_PUBLISHED_TO_DRAFT = "6049";  //�������ɷ���״̬�޸�Ϊ�ݸ�״̬
    public static final String ERROR_USER_HAS_GROUP = "6050"; // �û��ѷ�����֯�ܹ�
    public static final String ERROR_RESOURCE_UPLOADING = "6051"; // ����ʧ�ܡ���Դ�ϴ���
    public static final String ERROR_EAMIL_NOT_VERIFIED_CODE = "6052";  //�û�����δ��֤
    public static final String ERROR_STATRTIME_IS_GREATER_THAN_ENDTIME = "6053"; //��ʼʱ����ڽ���ʱ��
    public static final String ERROR_LOGIN_CONFIG_NOT_SUPPORT_IC_CARD_CODE = "6054";//��֧�����֤��¼
    public static final String ERROR_LOGIN_CONFIG_NOT_SUPPORT_CREDIT_CODE = "6055";//��֧����ҵ�������¼
    public static final String ERROR_UPDATE_SELF_ROLE_CODE = "6056";//����Ա����ȡ���Լ�
    public static final String ERROR_STATUS_FROM_R_TO_DA = "6057";//(�����ܾ�)�����޸�Ϊ(����)
    public static final String ERROR_ACTIVITY_TIME_GREATER_THAN_REGISTRATION_TIME = "6058";//����ʱ�䲻�ܴ��ڻʱ��
    public static final String ERROR_ACTIVITY_ENDED_EDIT = "6059";//��Ѿ�����,���ܱ༭
    public static final String ERROR_ACTIVITY_PROCESSING_EDIT_ONLY_EXTEND_TIME  = "6060";//������У�ֻ���ӳ�����ʱ���ʱ��
    public static final String ERROR_ACTIVITY_REG_USERNAME_MUST  = "6061";//����������������û���
    public static final String ERROR_ACTIVITY_REG_TEL_MUST  = "6062";//����������������ֻ�
    public static final String ERROR_ACTIVITY_REG_EMAIL_MUST  = "6063";//�������������������
    public static final String ERROR_ACTIVITY_REG_EXCEED_LIMIT_SUM  = "6064";//����������ܳ�����Ʊ��
    public static final String ERROR_ACTIVITY_REG_EXCEED_PER_LIMIT_SUM  = "6065";//����������ܳ�������Ʊ��
    public static final String ERROR_ACTIVITY_REG_NOT_IN_REG_TIME  = "6066";//������������ڱ���ʱ����
    public static final String ERROR_ACTIVITY_REG_CANCEL_KEY_INVALID  = "6067";//������������ڱ���ʱ����
    public static final String ERROR_ERROR_ORDER_NO_HAS_DISABLED  = "6068";//�����Ѿ�ȡ���������ظ�ȡ��
    public static final String ERROR_DELETE_REASON_MAST  = "6069";//ɾ�����ɱ���

    public static final String ERROR_PUBLISH  = "6070";//��ֹ����
    public static final String ERROR_PUBLISH_VERSION  = "6071";//��ֹ�����汾
    public static final String ERROR_UN_PUBLISH = "6072";//δ����
    public static final String ERROR_APP_CONFIG = "6073"; //Ӧ�����ô���

    public static final String ERROR_APP_NOT_EXIST_CODE = "6999"; //Ӧ�ò�����


}

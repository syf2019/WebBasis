package com.ims.common.constant;

/**
 * 
 * 类描述：<b>全局常量表</b> 
 * 创建人：陈骑元
 * 创建时间：2016-6-1 上午01:31:20
 * 修改人：蓝枫 
 * 修改时间：2016-6-1 上午01:31:20
 * 修改备注： 
 * @version
 */
public interface IMSCons {
	
	/**
	 * redis模式单机0单机1集群
	 */
	public static final String REDIS_MODE_SINGLE="0";
	public static final String REDIS_MODE_CLUSTER="1";
	
	/**
	 * 是否常量 0 否 1是
	 */
	public static final String WHETHER_YES="1";
	public static final String WHETHER_NO="0";
	
	public static final String CACHE_PREFIX_HEAD="webplus:";

	/**
	 * 开关 开
	 */
	public static final String SWITCH_ON="on";
	/**
	 * 开关 关
	 */
	public static final String SWITCH_OFF="off";
	
	 public static final String EMPTY = "";
	/**
	 * 布尔值true
	 */
	public static final String TRUE="true";
	/*
	 * 布尔值false
	 */
	public static final String FALSE="false";
	
	public static final String APP_ID_KEY="IMS:id_";
	 
	
    /**
     * 密码秘钥
     */
	public static final String PASSWORD_KEY = "WEBPLUSS";
	/**
	 * 界面风格 经典风格 1
	 */
	public static final String STYLE_CLASSIC="1";
	/**
	 * 界面风格  顶部布局
	 */
	public static final String STYLE_TOP_LAYOUT="2";
	/**
	 * 日期格式
	 */
	
	public static final String DATE = "yyyy-MM-dd";

	/**
	 * 日期时间格式
	 */
	public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期时间格式 精确到分
	 */
	public static final String DATETIMEMIN = "yyyy-MM-dd HH:mm";
	
	/**
	 * 存储过程业务成功状态码：成功
	 */
	public static final int PROC_SUCCESS = 1;

	/**
	 * 业务状态码：成功
	 */
	public static final int SUCCESS = 1;
	
	/**
	 * 业务状态吗：警告
	 */
	public static final int WARN = 0;

	/**
	 * 业务状态码：失败
	 */
	public static final int ERROR = -1;

	/**
	 * 字符布尔值：真
	 */
	public static final String STR_TRUE = "1";

	/**
	 * 字符布尔值：假
	 */
	public static final String STR_FALSE = "0";

	/**
	 * 系统运行模式：开发模式
	 */
	public static final String RUNAS_DEV = "0";

	/**
	 * 系统运行模式：生产模式
	 */
	public static final String RUNAS_PRO = "1";
	/**
	 * 逗号分隔符
	 */
	public static final String MARK_CSV=",";
	/**
	 * 顿号分隔符
	 */
	public static final String MARK_PAUSE="、";
	/**
	 * 邮箱相关的参数
	 */
	public static final class EMAILCONS {
		/**
	     * 密送人
	     */
	    public static final String BCC = "BCC";

	    /**
	     * 抄送人
	     */
	    public static final String CC = "CC";

	    /**
	     * smtp服务端口，默认使用25，为了兼容性，请指定465，qq和163支持25和465，新浪支持465，搜狐支持465，如果没有，默认为465
	     */
	    public static final String DEFAULTE_EMAIL_PORT = "465";

	    /**
	     * socket连接类，使用新浪邮箱必须指定，如果没有默认为javax.net.ssl.SSLSocketFactory
	     */
	    public static final String DEFAULT_EMAIL_SOCKETCLASS = "javax.net.ssl.SSLSocketFactory";

	    /**
	     * 如果smtp连接不成功，会自动转换成socket协议，这里指定它的端口，请指定465，如果没有，默认为465
	     */
	    public static final String DEFAULT_EMAIL_SOCKETPORT = "465";

	    /**
	     * 邮件验证
	     */
	    public static final String EMAIL_AUTH = "mail.smtp.auth";

	    /**
	     * 邮件字符编码
	     */
	    public static final String EMAIL_CHARSET = "text/html;charset=UTF-8";

	    /**
	     * smtp服务端口，默认使用25，为了兼容性，请指定465，qq和163支持25和465，新浪支持465，搜狐支持465
	     */
	    public static final String EMAIL_POST = "mail.smtp.port";

	    /**
	     * 指定邮件接收协议
	     */
	    public static final String EMAIL_PROTOCOL = "mail.store.protocol";

	    /**
	     * 邮件协议发送协议smtp
	     */
	    public static final String EMAIL_SMTP = "smtp";

	    /**
	     * socket连接类，使用新浪邮箱必须指定，默认为javax.net.ssl.SSLSocketFactory
	     */
	    public static final String EMAIL_SOCKETCLASS = "mail.smtp.socketFactory.class";

	    /**
	     * 如果smtp连接不成功，会自动转换成socket协议，这里指定它的端口，请指定465
	     */
	    public static final String EMAIL_SOCKETPORT = "mail.smtp.socketFactory.port";

	    /**
	     * 邮件协议接收协议imap
	     */
	    public static final String IMAP = "imap";
	    public static final String IMAP_NOT_SSL_PROT = "143";
	    public static final String IMAP_SSL_PROT = "993";

	    /**
	     * 163邮箱imap地址
	     */
	    public static final String IMAP163 = "imap.163.com";

	    public static final String NOT_IMAP_NOT_SSL_PORT = "110";
	    public static final String NOT_IMAP_SSL_PORT = "995";

	    /**
	     * 163邮箱pop地址
	     */
	    public static final String POP163 = "pop.163.com";
	    /**
	     * qq邮箱imap地址
	     */
	    public static final String QQIMAP = "imap.qq.com";

	    /**
	     * qq邮箱pop地址
	     */
	    public static final String QQPOP = "pop.qq.com";

	    /**
	     * qq邮箱smtp地址
	     */
	    public static final String QQSMTP = "smtp.qq.com";

	    /**
	     * sina邮箱imap地址
	     */
	    public static final String SINAIMAP = "imap.sina.com";

	    /**
	     * sina邮箱pop地址
	     */
	    public static final String SINAPOP = "pop.sina.com";

	    /**
	     * sina邮箱smtp地址
	     */
	    public static final String SINASMTP = "smtp.sina.com";

	    /**
	     * 163邮箱smtp地址
	     */
	    public static final String SMTP163 = "smtp.163.com";

	    /**
	     * sohu邮箱imap地址
	     */
	    public static final String SOHUIMAP = "imap.sohu.com";

	    /**
	     * sohu邮箱pop地址
	     */
	    public static final String SOHUPOP = "pop.sohu.com";

	    /**
	     * sohu邮箱smtp地址
	     */
	    public static final String SOHUSMTP = "smtp.sohu.com";

	    /**
	     * 收件人
	     */
	    public static final String TO = "TO";


	}
	/**
	 * 是否标识
	 */
	public static final class IS {
		public static final String YES = "1";
		public static final String NO = "0";
	}

	/**
	 * Json输出模式。格式化输出模式。
	 */
	public static final String JSON_FORMAT = "0";

	/**
	 * Ext Reader对象的totalProperty属性名称
	 */
	public static final String READER_TOTAL_PROPERTY = "total";

	/**
	 * Ext Reader对象的root属性名称
	 */
	public static final String READER_ROOT_PROPERTY = "rows";

	/**
	 * Dto对象中的内部变量：交易状态码
	 */
	public static final String APPCODE_KEY = "appcode";

	/**
	 * Dto对象中的内部变量：交易状态信息
	 */
	public static final String APPMSG_KEY = "appmsg";
	
	/**
	 * 请求相应成功标志
	 */
	public static final String REQUEST_SUCCESS = "success";
	/**
	 * 请求错误
	 */
	public static final String REQUEST_ERROR = "error";

	/**
	 * 控制台醒目标记1
	 */
	public static final String CONSOLE_FLAG1 = "● ";

	/**
	 * 控制台醒目标记2
	 */
	public static final String CONSOLE_FLAG2 = "●● ";
	
	/**
	 * 控制台醒目标记3
	 */
	public static final String CONSOLE_FLAG3 = "●●● ";

	/**
	 * UserInfo对象在Session中的key，Dto中的当前UserInfo也使用此Key
	 */
	public static final String USERINFOKEY = "sessionUserInfo";
	
	
	/**
	 * session保存验证码
	 */
	public static final String AUTHCODE = "authCode";

	/**
	 * 获取前端UI选择模型选中的标识字段的数组，前端请求参数key应为：aos_rows_，方能取到。
	 */
	public static final String IMS_ROWS_ = "ims_rows_";
    
	
	/**
	 * 数序运算SQL的参数Dto中的运算表达式Key。
	 */
	public static final String CALCEXPR = "_expr";


	/**
	 * DTO缺省字符串Key
	 */
	public static final String DEFAULT_STRING_KEY = "_default_string_a";

	/**
	 * DTO缺省List Key
	 */
	public static final String DEFAULT_LIST_KEY = "_default_list_a";

	/**
	 * DTO缺省BigDecimal Key
	 */
	public static final String DEFAULT_BIGDECIMAL_KEY = "_default_bigdecimal_a";

	/**
	 * DTO缺省Integer Key
	 */
	public static final String DEFAULT_INTEGER_KEY = "_default_integer_a";

	/**
	 * DTO缺省Boolean Key
	 */
	public static final String DEFAULT_BOOLEAN_KEY = "_default_boolean_a";
	
	/**
	 * 会话中验证码的缺省Key
	 */
	public static final String VERCODE = "_vercode";
	
	/**
	 * WEBAPPCXT是否成功的标志KEY
	 */
	public static final String WEBAPPCXT_IS_SUCCESS_KEY = "_webappcxt_is_success";
	
	/**
	 * ContextPath在系统变量中的Key
	 */
	public static final String CXT_KEY = "cxt";
	
	/**
	 * JOSQL AOSListUtils 中使用的KEY
	 */
	public static final String IMSLIST_KEY = ":IMSList";
	
	/**
	 * 排序器在参数对象中的Key
	 */
	public static final String ORDER_KEY = "_order";
	
	/**
	 * JS头<br>
	 */
	public static final String SCRIPT_START = "<script type=\"text/javascript\">\n";
	
	/**
	 * JS尾<br>
	 */
	public static final String SCRIPT_END = "\n</script>";
	/**
	 * 大写英文字母
	 */
	public static final String UPPER_LETTER = "ABCDEFGHIJKLMNOPKRSTUVWXYZ";
	/**
	 * 小写的英文字母
	 */
	public static final String LOWCASE_LETTER = "abcdefghijklmnopqrstuvwxyz";
	/**
	 * 数字字母
	 */
	public static final String NUMBER_LETTER= "0123456789";
	
	/**
	 * 随机混合的类型
	 *
	 */
	public static  final class RANDOM_TYPE{
		//全部参与混合
		public static final String  FULL= "1";
		//大写
		public static final String UPPER = "2";
		//小写
		public static final String LOWCASE = "3";
		//数字
		public static final String NUMBER = "4";
		//大写与数字混合
		public static final String UPPER_NUMBER = "5";
		//大写与小写混合
		public static final String  UPPER_LOWCASE = "6";
		//小写与数字混合
		public static final String LOWCASE_NUMBER = "7";
		
	}

	
}

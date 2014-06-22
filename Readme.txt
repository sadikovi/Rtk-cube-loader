���������� ��������� ��������� ������ ���� ����� ����������� ��������� � �������� � ������� ��� � BI.
���������� ������������� ����������� � �������� � ����, ������������� ��������� ������ ������ � �����������. 
����� �������� ����� ������������ ����� ����� ��������� ������ �� ����������� ������.

���������� ������:
	1. DB Module - ���, ��� �������� DB ����� (ddl, sql)
	2. Java Module - �������� .war ���� ����������
	3. Properties - �������� CLProperties.ini, CLModels.ini
	4. Javascript (OBI Dashboard) - �������� ������ ��� ���������� (�������, ��������� ���� �� javascript-��� � ���)
	5. Readme - ��������
	6. sample_... .xls - ������� ����������� ������
	

����� (�������������): WebLogic
	WebLogic Server Version: 10.3.5.0
	Java version 1.6.0_20
	Oracle Database 11g Enterprise Edition Release 11.2.0.2.0 - 64bit Production

��������� ���������� � ���������� ��������:
	1. ������������ ������ ���� �������� � ������� � ����� ���� (������������� ��� ����� ��������� ��������� �����) �� ����� DB Module
	2. ���������� ��������������� ����� ������� Weblogic (����������� ���������, ���������� ��������� ���� ���������, ����� �������������� � WORK_DIRECTORY)
	3. � ��� �� ���������� ���������� ���������� ����� "CLProperties.ini", "CLModels.ini"
	4. ������������� data_source � ������� Weblogic
	5. ��������� ��������� �� �������� �� �������� ���������� OBI
	
�������� ���������� � ����������.
	�������� ���������� ��� ������ ������ ����: analytics/rtk-cube-loader (/analytics/rtk-cube-loader)

���������������� ���� ����������
	� ���������������� ����� ���-���������� web.xml � ������������ �������� StartServlet ����� ������ �������� ��� ����������������� ��������� configFilePath (����� ������ ������� .war ���� ����������� � �������� ��������������� ������ � web.xml).
	�������� ������ ��������� ���������� ���� � ����������������� ����� ���������� CLProperties.ini. ���������� ����� ��������� � ���� ��� ������������� ��������.
	��������: <param-value>/MiddlewareHome/user_projects/domains/bifoundation_domain/servers/AdminServer/upload/CLProperties.ini</param-value>

	� ������ ���� �������� �� ������ (<param-value></param-value>) ���������� ���������� ����� ���� CLProperties.ini �� ���� ��������� ���������� (properties/CLProperties.ini).



���� ����������� ���������, ������� ����� ����� ������� � ���������������� ����� "CLProperties.ini" � ��������� ��� � ���������� WORK_DIRECTORY
� "CLModels.ini" ����� ������ �� ������, ���� �������� ��� ������.

����������� ���������:

PRESENTATION_SERVICES

1. BI_ANALYTICS_URL - ������ �� ������ BI

CONNECTION

1. DB_SCHEME_NAME - �������� �����, ��� ����� ����������� ������� ��
2. DB_SCHEME_PASSWORD - ������ ��� ���� �����
3. DB_SOURCE_HOST - ����
4. DB_SOURCE_PORT - ����
5. DB_SOURCE_SERVICE_NAME - service name

DIRECTORIES

1. WORK_DIRECTORY - ������� ���������� (��� �������, ���� ������������� ���� ����������, ���� �� ����� ����������� ��� �������������� �����, ������������ �����������; �� ��������� "/u01/oracle/weblogic/user_projects/domains/bifoundation_domain/servers/AdminServer/upload/")

��� ��������� ����� ������, ���� �� ������ �������� � shell ������� (����� ��������� ��������������� ���������, ��� ��� �� ���������, ��� ���������)
2. BIN_DIRECTORY - ���������� � ��������� (��� Windows �������� ��������, ��� Linux - ������ ���� ����� ��, �� ��� �� ����� ���������)
3. SCRIPT_DIRECTORY - ���������� �� ��������� (��� Windows �������� ��������, ��� Linux - ������ ���� ����� ��, �� ��� �� ����� ���������)

REPOSITORY

1. RPD_NAME - �������� �����������
2. RPD_PASSWORD - ������ � �����������
3. USER_NAME - ������������, ��� ������� ����� ����������� ��������� � ����������� (������������� ������� ������ ������������ � ������� Weblogic)
4. USER_PASSWORD - ������ ��� ������������
5. SERVER_DSN - DSN ������� (�����, ���� � ��� ������ �� Windows � �� ����������� shell ������� ��� �������� ���������)

6. USE_WEB_SERVICES_AGAINST_SHELL - ������������� Web services ������ Shell �������� ��� �������� ��������� (������������� ��������, ��� � ���������� �� ���������)
7. SAVE_METADATA_XML_SCRIPTS_ON_SERVER - ���������� shell �������� � �������� ������� (�� ��������� ���������)

8. CREATE_VARIABLES_ON_APP_DEPLOY - ����������� �������� ���������� (���� ��� ������, ���� � ������ ��������, ������������� ���������� �������� false)



�������������� ���������:

1. ADMIN_ROLE - ���� �������������� ��� ���������� (�� ���������, BIAdministrator)
2. BI_REQUIRED_ROLE - ���� ��� ����������� ����������� (�� ���������, BIAuthor)

���� �� �������� �������� ������ � ��������, ������� �������� ��������� ����
3. DB_STRUCTURE_PACKAGE
4. DB_FNC_CREATE_CUBE
5. DB_FNC_GET_PM_NAMES
6. DB_FNC_GET_MODEL_STRUCTURE
7. DB_CC_LOAD_PACKAGE
8. DB_FNC_LOAD_CUBE
9. DB_SEQ_PRC_ID_NEXTVAL
10. DB_SEQ_PRC_ID_CURRVAL

11. DB_AUTO_COMMIT - ���������� ������ ���������� (�� ��������� ��������)

��� �������������� ������ ������ � ��������� ���������� �������� ��������� � ��������:
SUCCESS_MESSAGES
ERRORS


� �������� � ��������������� ����� ��������, ��� � ��� ���������/���� �������.


��� ���� ����� ��������� data_source (� �������� ��� �������� � Integrator's Guide, ���� ���� ������ - ����������� ���������� ����):
1. ��������� wsil.browsing in Weblogic EM (���� �� ��������, ���� ��������, ��� ����� ���������)
2. ������� ����� JDBC (jndi/bi/server) ����������� � Weblogic Console
	a. ����� � Services -> Data Sources
	b. ���� ��� ���� "bi/server" ����������, �� ������ ��������� ��������� (������� � ������ k)
	c. ������� ����� ���������� Generic Data Source
	d. � ���� Name ������ "bi/server"
	e. � ���� JNDI Name ������ "jdbc/bi/server"
	f. ������� Database Type ��� "Other". ������ Next.
	g. ������� Database driver as "Other". ������ Next.
	h. ���������, ��� Supports Global Transactions ��������.
	i. ������� One-Phase Commit option. ������ Next.
	j. ��������� �������� ����� �� ������� ��, ��� �� ����� ����, ������� ����� ����� ��������� �� ��������� ������� (��� ���������, ��� ��������� ���):
		Database name -- you can enter any value you choose, f.e. "OracleBIInstance"
		
		Host Name -- bi-server url (f.e. msk-02-orabits.tsretail.ru)
		
		Port -- ���� "9703", ���� OBI ���������� �� ���������. ���� ���, �� ����� ��������� ���� � Weblogic EM (Business Intelligence->Components->BIserverInstance), ��, ��� �������, ������������ ���� "9703".
		
		Database User Name -- ��� ������������ � BI. ��� ��� �� ������������, ��� ������ � USER_NAME � ����� "CLProperties.ini"
		
		Password - ������ ������������
		Confirm password - ������������� ������
		
		��� ���� Driver Class Name ������� "oracle.bi.jdbc.AnaJdbcDriver"
		
		��� ���� URL ������� "jdbc:oraclebi://host:port/" (��������, jdbc:oraclebi://msk-02-orabits.tsretail.ru:9703/)
		
	k. ���� ����������
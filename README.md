Rtk-cube-loader
================

Application for creating OLAP cubes based on Excel files. Uses Oracle BIEE 11g API.


Cube loader can load Excel files using web interface (HTML/Javascript/CSS) and let users work with data in Oracle BIEE 11g. Information in Excel file is automatically parsed in Java and loaded in database. Then application creates business model in repository based on Excel table (can create as simple star schemes as very complex snowflakes). After loading file user can build analyses in OBIEE without any additional steps. So it is just "Open OBIEE loading panel -> select excel file with data -> wait until procedure is over (usually it takes couple of minutes for large file) -> create ad-hoc analysis using your subject area based on Excel file". Excel files can be plain tables or pivot tables with multiple heading rows.


Package
================

What is in package:

	1. DB Module - all for deploying in database (ddl, sql scripts)

	2. Java Module - .war file to be deployed on Weblogic server

	3. Properties - includes CLProperties.ini, CLModels.ini

	4. Javascript (OBI Dashboard) - web interface

	5. Readme - readme

	6. sample_... .xls - examples of data that can be loaded


Environment: WebLogic
	
	- WebLogic Server Version: 10.3.5.0
	
	- Java version 1.6.0_20

	- Oracle Database 11g Enterprise Edition Release 11.2.0.2.0 - 64bit Production


How to deploy it and use it
================

Steps:

1. Deploy all the files - packages and tables in Database module in separate (prefereable) scheme

2. Application .war file is deployed in Weblogic server (standard procedure, just have to remember path for installing as it is going to be used in WORK_DIRECTORY)

3. Copy changed (as you can change settings in that properties file) "CLProperties.ini", "CLModels.ini" in directory from above (2.)

4. Set up data_source in Weblogic Console - you should also change information in CLProperties.ini

5. Create web interface (paste code) in Oracle BI dashboard


Context of application and deployment.

Context during deploy should be: analytics/rtk-cube-loader (/analytics/rtk-cube-loader)


Properties file
===
	
	
You should set up value for init parameter "configFilePath" in web.xml (regarding to StartServlet). You can simply open .war file and change that value. Value should be an absolute path to configuration file (CLProperties.ini) as application will be loading it from there. For example, <param-value>/MiddlewareHome/user_projects/domains/bifoundation_domain/servers/AdminServer/upload/CLProperties.ini</param-value>
	
If value is empty (<param-value></param-value>) or invalid application will try to get file from application's context (properties/CLProperties.ini).


There are some settings below that you should change (there are many settings in that file, but these ones are very important) in CLProperties.ini and copy it in WORK_DIRECTORY directory. You can change CLModels.ini or leave it empty as it is used for keeping log of OLAP cubes created.


Important settings:

PRESENTATION_SERVICES

	1. BI_ANALYTICS_URL - http link to the Oracle BIEE server

	
CONNECTION

	1. DB_SCHEME_NAME - scheme name where all the objects are deployed
	
	2. DB_SCHEME_PASSWORD - password for scheme
	
	3. DB_SOURCE_HOST - host for DB server
	
	4. DB_SOURCE_PORT - port for DB server
	
	5. DB_SOURCE_SERVICE_NAME - DB server name

	
DIRECTORIES

	1. WORK_DIRECTORY - work directory of the file (application will be deployed in that directory and all the additional files will be loaded in that directory). By default, it is "/u01/oracle/weblogic/user_projects/domains/bifoundation_domain/servers/AdminServer/upload/".

If you want to work with shell files you have to switch on "Work with shell files" option and set up values below. By default it is off and application uses Oracle BI EE Metadata API to create files in repository. 
	
	2. BIN_DIRECTORY - directory that includes commands (varies from Windows to Linux, pay attention here)
	
	3. SCRIPT_DIRECTORY - scripts directory (it is the same for Linux, could be different for Windows, be careful)


REPOSITORY

	1. RPD_NAME - repository name for BI server

	2. RPD_PASSWORD - repository password
	
	3. USER_NAME - user in OBIEE that application will use to make changes (you better set up one in Weblogic console)

	4. USER_PASSWORD - the user password

	5. SERVER_DSN - server DSN (you have to set this, if you are using Windows and you want to use shell scripts for making changes

	6. USE_WEB_SERVICES_AGAINST_SHELL - using Oracle Metadata API for deploying all changes in repository. By default, it is on. If you want to use Shell scripts for that, switch it off

	7. SAVE_METADATA_XML_SCRIPTS_ON_SERVER - save shell scripts on server (by default it is off)

	8. CREATE_VARIABLES_ON_APP_DEPLOY - creates all necessary variables in repository from application deploy. If it is on - will create with first deploy, if it is off - will create them with the first file load. Be default it is false


Additional parameters (not so important, more tuning):

	1. ADMIN_ROLE - administrator role (by default, BIAdministrator)
	
	2. BI_REQUIRED_ROLE - user role for using this application (by default, BIAuthor)


If you want to change name of packages and procedures, please change those settings as well
	
	3. DB_STRUCTURE_PACKAGE
	4. DB_FNC_CREATE_CUBE
	5. DB_FNC_GET_PM_NAMES
	6. DB_FNC_GET_MODEL_STRUCTURE
	7. DB_CC_LOAD_PACKAGE
	8. DB_FNC_LOAD_CUBE
	9. DB_SEQ_PRC_ID_NEXTVAL
	10. DB_SEQ_PRC_ID_CURRVAL

	11. DB_AUTO_COMMIT - autocommit (will commit every transaction, by default is false, and that value is recommended)


For success and error messages on other languages besides English (e.g. Russian), please change parameters in those sections
	
	SUCCESS_MESSAGES
	ERRORS


Do not worry! You will find all necessary information and helpful comments in CLProperties.ini.


How to setup data_source (all the inforamation is in Integrator's Guide as well, if you are lazy, read below):

	1. Set up wsil.browsing in Weblogic EM (check anyway)
	2. Create new connection JDBC (jndi/bi/server) in Weblogic Console
		a. Go to Services -> Data Sources
		b. If there is already "bi/server" connection, just check it (and go to "k")
		c. Create new connection as "Generic Data Source" type
		d. Enter Name as "bi/server"
		e. Enter JNDI Name as "jdbc/bi/server"
		f. Choose Database Type as "Other". Click Next.
		g. Choose Database driver as "Other". Click Next.
		h. Make sure that Supports Global Transactions is ON.
		i. Choose One-Phase Commit option. Click Next.
		j. Next page might not display all the information that you enterd below, thus check this page, and if something is wrong just type the info you need. Or just check that it is OK:
			Database name -- you can enter any value you choose, f.e. "OracleBIInstance"

			Host Name -- bi-server url (e.g. msk-02-orabits.tsretail.ru)

			Port -- port "9703", in case OBI is installed by default. Otherwise, check port in Weblogic EM (Business Intelligence->Components->BIserverInstance), but usually it is "9703".

			Database User Name -- user name in Oracle BI EE. It is the same user that is entered in USER_NAME in "CLProperties.ini"

			Password - user password
			Confirm password - confirmation of password

			In "Driver Class Name" enter "oracle.bi.jdbc.AnaJdbcDriver"

			For URL field enter "jdbc:oraclebi://host:port/" (e.g. jdbc:oraclebi://msk-02-orabits.tsretail.ru:9703/)

		k. Test connection

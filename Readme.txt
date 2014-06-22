Приложение позволяет загружать эксель файл через специальный интерфейс и работать с данными уже в BI.
Информация автоматически загружается и парсится в базе, автоматически создается модель данных в репозитории. 
После загрузки файла пользователь может сразу создавать анализ на загруженных данных.

Содержание архива:
	1. DB Module - все, что касается DB части (ddl, sql)
	2. Java Module - содержит .war файл приложения
	3. Properties - содержит CLProperties.ini, CLModels.ini
	4. Javascript (OBI Dashboard) - содержит скрипт для интерфейса (главное, проверить пути со javascript-ами и все)
	5. Readme - описание
	6. sample_... .xls - примеры загрузочных файлов
	

Среда (тестировалось): WebLogic
	WebLogic Server Version: 10.3.5.0
	Java version 1.6.0_20
	Oracle Database 11g Enterprise Edition Release 11.2.0.2.0 - 64bit Production

Установка приложения и интерфейса загрузки:
	1. Производится деплой всех структур и пакетов в схеме базы (рекомендуется для этого создавать отдельную схему) из папки DB Module
	2. Приложение устанавливается через консоль Weblogic (стандартная процедура, необходимо запомнить путь установки, будет использоваться в WORK_DIRECTORY)
	3. В эту же директорию копируются измененные файлы "CLProperties.ini", "CLModels.ini"
	4. Настраивается data_source в консоли Weblogic
	5. Создается интерфейс по загрузке на странице инфопанели OBI
	
Контекст приложения и Деплоймент.
	Контекст приложения при деплое должен быть: analytics/rtk-cube-loader (/analytics/rtk-cube-loader)

Конфигурационный файл приложения
	В конфигурационном файле веб-приложения web.xml в конфигурации сервлета StartServlet нужно задать значение для инициализирующего параметра configFilePath (можно просто открыть .war файл архиватором и изменить соответствующую строку в web.xml).
	Значение должно содержать абсолютный путь к конфигурационному файлу приложения CLProperties.ini. Приложение будет ссылаться к нему при инициализации настроек.
	Например: <param-value>/MiddlewareHome/user_projects/domains/bifoundation_domain/servers/AdminServer/upload/CLProperties.ini</param-value>

	В случае если значение не задано (<param-value></param-value>) приложение попытается найти файл CLProperties.ini по пути контекста приложения (properties/CLProperties.ini).



Ниже перечислены настройки, которые нужно будет сделать в конфигурационном файле "CLProperties.ini" и перенести его в директорию WORK_DIRECTORY
В "CLModels.ini" можно ничего не менять, либо оставить его пустым.

Необходимые настройки:

PRESENTATION_SERVICES

1. BI_ANALYTICS_URL - ссылка на сервер BI

CONNECTION

1. DB_SCHEME_NAME - название схемы, где будут создаваться объекты ХД
2. DB_SCHEME_PASSWORD - пароль для этой схемы
3. DB_SOURCE_HOST - хост
4. DB_SOURCE_PORT - порт
5. DB_SOURCE_SERVICE_NAME - service name

DIRECTORIES

1. WORK_DIRECTORY - рабочая директория (как правило, сюда устанавливают само приложение, сюда же будут сохраняться все дополнительные файлы, генерируемые приложением; по умолчанию "/u01/oracle/weblogic/user_projects/domains/bifoundation_domain/servers/AdminServer/upload/")

Эти настройки нужно менять, если вы хотите работать с shell файлами (нужно выставить соответствующую настройку, так как по умолчанию, она выключена)
2. BIN_DIRECTORY - директория с командами (для Windows придется поменять, для Linux - должно быть таким же, но все же стоит проверить)
3. SCRIPT_DIRECTORY - директория со скриптами (для Windows придется поменять, для Linux - должно быть таким же, но все же стоит проверить)

REPOSITORY

1. RPD_NAME - название репозитория
2. RPD_PASSWORD - пароль к репозиторию
3. USER_NAME - пользователь, под которым будут выполняться изменения в репозитории (рекомендуется завести такого пользователя в консоли Weblogic)
4. USER_PASSWORD - пароль для пользователя
5. SERVER_DSN - DSN сервера (нужен, если у вас сервер на Windows и вы используйте shell скрипты для создания изменений)

6. USE_WEB_SERVICES_AGAINST_SHELL - использование Web services вместо Shell скриптов для внесения изменений (рекомендуется включить, что и выставлено по умолчанию)
7. SAVE_METADATA_XML_SCRIPTS_ON_SERVER - сохранение shell скриптов в каталоге сервера (по умолчанию выключено)

8. CREATE_VARIABLES_ON_APP_DEPLOY - особенность создания переменных (либо при деплое, либо с первым запуском, рекомендуется выставлять значение false)



Необязательные параметры:

1. ADMIN_ROLE - роль администратора для приложения (по умолчанию, BIAdministrator)
2. BI_REQUIRED_ROLE - роль для пользования приложением (по умолчанию, BIAuthor)

Если Вы изменили название пакета и процедур, просьба поменять настройки ниже
3. DB_STRUCTURE_PACKAGE
4. DB_FNC_CREATE_CUBE
5. DB_FNC_GET_PM_NAMES
6. DB_FNC_GET_MODEL_STRUCTURE
7. DB_CC_LOAD_PACKAGE
8. DB_FNC_LOAD_CUBE
9. DB_SEQ_PRC_ID_NEXTVAL
10. DB_SEQ_PRC_ID_CURRVAL

11. DB_AUTO_COMMIT - автокоммит каждой транзакции (по умолчанию выключен)

Для русскоязычного текста ошибок и сообщений необходимо поменять параметры в разделах:
SUCCESS_MESSAGES
ERRORS


В принципе в конфигурацинном файле написано, что и как заполнять/даны примеры.


Для того чтобы настроить data_source (в принципе все написано в Integrator's Guide, если лень читать - пользуйтесь алгоритмом ниже):
1. Настроить wsil.browsing in Weblogic EM (если не настроен, если настроен, все равно проверить)
2. Создать новое JDBC (jndi/bi/server) подключение в Weblogic Console
	a. Зайти в Services -> Data Sources
	b. Если уже есть "bi/server" соединение, то просто проверить настройки (перейти к пункту k)
	c. Создать новое соединение Generic Data Source
	d. В поле Name ввести "bi/server"
	e. В поле JNDI Name ввести "jdbc/bi/server"
	f. Выбрать Database Type как "Other". Нажать Next.
	g. Выбрать Database driver as "Other". Нажать Next.
	h. Убедиться, что Supports Global Transactions включена.
	i. Выбрать One-Phase Commit option. Нажать Next.
	j. Следующая страница может не вывести то, что вы ввели выше, поэтому нужно будет заполнить ее следующим образом (или проверить, что заполнено так):
		Database name -- you can enter any value you choose, f.e. "OracleBIInstance"
		
		Host Name -- bi-server url (f.e. msk-02-orabits.tsretail.ru)
		
		Port -- порт "9703", если OBI установлен по умолчанию. Если нет, то нужно проверить порт в Weblogic EM (Business Intelligence->Components->BIserverInstance), но, как правило, используется порт "9703".
		
		Database User Name -- имя пользователя в BI. Это тот же пользователь, что указан в USER_NAME в файле "CLProperties.ini"
		
		Password - пароль пользователя
		Confirm password - подтверждение пароля
		
		Для поля Driver Class Name введите "oracle.bi.jdbc.AnaJdbcDriver"
		
		Для поля URL введите "jdbc:oraclebi://host:port/" (например, jdbc:oraclebi://msk-02-orabits.tsretail.ru:9703/)
		
	k. Тест соединения
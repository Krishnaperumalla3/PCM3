# PCM Installation Guide

This project was generated with Gradle 4.7 and UI is Angular cli v 7.0.4.

##   i. Update Application.yml file
##  ii. Deployment Types
## iii. PCM Logo Change
##  iv. Shutdown Application
===========================================================================================================================


##   i. Update Application.yml file

1.Licence Accepting

		a. We should make accept-licence should be true for pcm installation

2.Configuration for theme, deployment type, and app shutdown password configurations

		a. cm.color: black , This will enable the black theme in UI, PCM colores: red, green, grey, yellow, black
		b. cm.cm-deployment: true , then PCM will not allow to create FTP, FTPS, SFTP profiles from UI.
		c. cm.cmks: CMShdwn , this value will be used as keystore when we call the shutdown resource(iv. Shutdown Application).

3.Server configurations
		
		a. server.port: 8181 , Application will get start with 8181 port, but this value can be overried from startup script.
		b. ssl.enabled: true , Application will try to enable the SSL, or elase below ssl configurations will be ignored.
		c. ssl.key-store: /user/cmks.p12 , Application will try to load the key-store from this location if ssl enabled.
		d. ssl.sey-store-cmks: password , this key-store pasword will be used whn SSL enabled
		e. ssl.key-store-type: PKCS12, here we need to provide above key-store type
		f. serverHeader: IBM Partner Engagement Manager Community Manager , this is constant value please dont change
		g. compression.enabled: true , this should be true , please dont change
		h. compression.min-response-size: 1024 , constant value please dont change

4.Update Liquibase for auto script run

		a. spring.liquibase.enabled=true , If you want to run Database script along with code deployment then make it as true or else false

5.Update the Database details(Datasource)

		a. spring.datasource.type: com.zaxxer.hikari.HikariDataSource , this should be constant, please dont change
		b. spring.datasource.url: URL , please check below 
		c. spring.datasource.username: USER NAME , please check in "Database Types and Configurations"
		d. spring.datasource.driver-class-name: DRIVER , please check "Database Types and Configurations"
		e. jpa.database-platform:  DATABASE_PLATFORM , please check "Database Types and Configurations"

	    Database Types and Configurations
	
		a.ORACLE
			   i.URL 			   : jdbc:oracle:thin:@HostName:1521/SID  (ex= jdbc:oracle:thin:@localhost:1521/XE)
			  ii.DRIVER			   : oracle.jdbc.OracleDriver
			 iii.USER NAME 		   : dbUserName		  
			  iv.DATABASE_PLATFORM : com.pe.pcm.config.database.dialect.Oracle10gExtendedDialect (For 12c : com.pe.pcm.config.database.dialect.Oracle12cExtendedDialect)
		a.MSSQL
			   i.URL 			   : jdbc:sqlserver://HostName;databaseName=DbName  (ex= jdbc:sqlserver://localhost;databaseName=TestDB)
			  ii.DRIVER			   : com.microsoft.sqlserver.jdbc.SQLServerDriver
			 iii.USER NAME 		   : dbUserName
			  iv.DATABASE_PLATFORM : org.hibernate.dialect.SQLServer2012Dialect #org.hibernate.dialect.SQLServerDialect (for lower)
		a.DB2
			   i.URL 			   : jdbc:db2://HostName:Port/DbName  (ex= jdbc:db2://localhost:50000/TestDB)
			  ii.DRIVER			   : com.ibm.db2.jcc.DB2Driver
			 iii.USER NAME 		   : dbUserName
			  iv.DATABASE_PLATFORM : com.pe.pcm.config.database.dialect.DB2ExtendedDialect
			  
		Note: Other configurations under spring.datasource.hikari and spring.jpa are constant(No need to change)
			  
6.Email and thymeleaf Configurations
		
		a. mail.host: SMTP Host Name  (Ex: pragmaedge-com.mail.outlook.com)
		b. mail.port: SMTP Port (Ex: 25)
		c. mail.username: User Name (Ex: pragma.edge@pragmaedge.com or ghgshtysujkjhs)
		d. mail.cmks: Password (Ex: password)	
		e. mail.from: provide from email (we need to provide the valied email when smtp.auth: true, or elase we can provide the dummy mail)
		f. mail.app-contact-mail: pcmtechsupport@pragmaedge.com , App support team mail
		g. mail.mail-signature: IBM Partner Engagement Manager Community Manager Portal support team., this will be used in signature when PCM send a mail
		h. mail.properties.mail.smtp.auth: true , If you want to send a mail with ssl authentication then make it as true or elase false
		i. mail.properties.mail.smtp.ssl.starttls.enabled ,  If you want to send a mail with ssl authentication then make it as true or else false
		i. mail.thymeleaf.cache: true , this is constant, please dont change
			  

7.Login and SiteMinder Config

		a. login.sm.enable: false, If the customer has Siteminder login then make it as true or else false	
		b. login.sm.param: SM_USER , the header tag name which SM used to insert the user name in header
		c. login.max-false-attempts: 5 , This will we the max login false attempts allowed by application, if it excedes application will block that user
		d. login.reset-false-attempts: 60 , Application will reset the max false login attempts user in the given minutes
		
8.Basic Authentication Configurations

		a. Basic Authentication: If we deploy application on PEM then below properties will be used for Authentication. If application deploy on PCM then we can remove the values for below properties
			1. basic.auth.username: pemuser
		    2. basic.auth.cmks: password
		b. Token Base Authentication: If we deploy application on PCM then below properties will be used to generate the token, If we deploy app on PCm then we can remove the values for below properties
			1. secretkey: CACE9E5A149ED201C4033C1A1E02C9BE  
  			2. session-expire: 60 # Minutes (Token session Expiry)
		  
		Secret Key Generation: Run below command to generate the Secret Key
		    --> openssl rand -base64 32
		  
		
9.Sterling B2Bi Configurations (Parent tag : sterling-b2bi)

		a. sfg-v6-update: true , If we create a profile using PCM SFG APIs then this tag will allow you to update the SFG profile through B2Bi Profile API
		b. core-bp.inbound: CM_MailBox_GET_RoutingRule_Inbound , Inbound mailbox bootstrap business process
		c. core-bp.outbound: CM_MailBox_GET_RoutingRule_Outbound , Outbound mailbox bootstrap business process
		d. Sterling: User Passphrase Validation , This passphrase will be used while creating profile in SI 
			1. user.cmks: Pragma@05 , This value should get from Sterling security.properties file where the property name is passphrase
			2. user.cmks-validation: true , If you want to validate aboove passphrase when applicaton get starts then make this value as true or else false
			3. user.cmks-validation-profile: TestProfile, We have to provide the SFTP profile which is available in SI with password as Expl@re
		e. B2Bi API Configurations: parent tag : sterling-b2bi.b2bi-api:
			1. active: true , This will say whether B2Bi API available or not
			2. auth-host: this tag will accept key Value pair here we need to configure the External user User Hostname as key and order as value
				'[SEAS Authentication]': 1
				ldapAuthentication: 2
			3. B2B rest API Config Details
				api:
				  username: cm_user , user name to authenticate the above API
				  cmks: password , above user password
				  baseUrl: http://localhost:10074/B2BAPIs/svc  , Base URL of the B2Bi API
			4. B2Bi SFG Apis availablity and Community config
				b2bi-sfg-api:
      			  active: true , if we say true then SFG Apis available along with B2Bi APIs
      			  community-name: CM_PEMCommunity #SFG Community Name, which will be used while creating profile in SFG through APIs
		f. b2b.as2.active = true , If you want to enable the As2 partner oboarding using B2Bi API then enable then make this as true or else false
		d.SFG_CONNECT_DIRECT Protocol internal and external profiles information
			  cd:
				net-map-name: Test_CD
				proxy:
				  internal:
					server-host: 10.0.0.1
					server-port: 1364
					secure-plus-option: ENABLED
					ca-cert: CA_cd_0099
					system-certificate: B2BHttp
					security-protocol: TLS 1.2
					cipher-suites: ECDHE_RSA_WITH_3DES_EDE_CBC_SHA
				  external:
					server-host: 10.0.0.1
					server-port: 1364
					secure-plus-option: ENABLED
					ca-cert: CA_cd_0099
					system-certificate: B2BHttp
					security-protocol: TLS 1.2
					cipher-suites: ECDHE_RSA_WITH_3DES_EDE_CBC_SHA
			Note: The above properties values will provided by SFG CD Profile Onborder , if we dont have SFG CD the we can keep all the values as empty

10.SSP API Configurations
		
		a. ssp.active: true , if we have SSP APIs enable then make it as true or else false
		b. SSP API Config
			api:
			  username: admin , User name to authenticate the API
			  cmks: password , Password of the above user
    		  baseUrl: https://localhost:6443/sspcmrest/sspcm/rest , Base URL of the SSP API
			  
11.Update Adpaters Names

		a.We configured with default Adapers names given by Pragma Edge Inc, If the adapter names got changed in SI then update that Adapters here.
		    adapters:
		  	  ftpServerAdapterName: PragmaFTPServerAdapter
			  ftpClientAdapterName: FTP Client Adapter
			  ftpsServerAdapterName: Pragma_FTPS_ServerAdapter
			  ftpsClientAdapterName: FTP Client Adapter
			  sftpServerAdapterName: Pragma_SFTPServerAdapter
			  sftpClientAdapterName: Pragma_SFTPClientAdapter
			  as2ServerAdapterName: Pragma_AS2ServerAdapter
			  as2ClientAdapterName: Pragma_AS2ClientAdapter
			  as2HttpClientAdapter: HTTPClientAdapter
			  cdClientAdapterName: Pragma_CDClientAdapter
			  httpServerAdapterName: Pragma_HTTPServerSync
			  httpsServerAdapterName: Pragma_HTTPSServerSync
			  mqAdapterName: Pragma_MQAdapter
			  wsServerAdapterName: Pragma_HTTPSServerSync
			  fsAdapter: PragmaFileSystem
			  sfgSftpClientAdapterName: Pragma_SFTPClientAdapter
			  sfgSftpServerAdapterName: Pragma_SFTPServerAdapter
			  sfgFtpClientAdapterName: Pragma_FTPClientAdapter
			  sfgFtpServerAdapterName: PragmaFTPServerAdapter
			  sfgFtpsClientAdapterName: Pragma_FTPSClientAdapter
			  sfgFtpsServerAdapterName: Pragma_FTPS_ServerAdapter

12.Alerts Configurations

		a. Currently this sevice on hold but we will release this soon. so we can keep them all as false like below
			alerts:
			  email:
				enable:
				  create: false
				  update: false
				  delete: false
				  reports: false

13.WorkFlow: MFT/DH Transactions Duplicate allow
		
		a.If you want to allow Duplicate MFT Transactions with in the flow then update workFlow.duplicate.mft = true or else make it false.
		b.If you want to allow Duplicate DH Transactions with in the application then update workFlow.duplicate.docHandling = true or else make it false.
   
14.File Transfer search time range

		a.If you want to change the time range in tranferinfo search then update file-transfer.search.time-range= 6 #hours
		
15.SAML

		a.Provide the metadata file location saml.idp.metadata=D:\FederationMetadata.xml
		b.Provide the Entity name whic we provide in IDP saml.idp.entity-id=PcmEntityIdp
		c.Provide the Application deployed host Name saml.host=52.40.156.177
		d.Provide the Application access URL 
			  i.saml.url.client=https://52.40.156.177:7080
			 ii.saml.url.entity=https://52.40.156.177:7080
		e.Provide SSL information 
			  i.saml.ssl.key-store= D:/config/tls/saml/localhost-keystore.jks #Absolute path of the JKS file
			 ii.saml.ssl.saml.ssl.key-password=pass@localhost
			iii.saml.ssl.store-password=store@localhost
			 iv.saml.ssl.key-alias=pcm-localhost
			 
16.Pem Configurations

		a.Remote server information for script run
			 i.Provide the key pem.remote.server.pem-key: D:\test\eks_docker.pem
			ii.Provide base directory pem.remote.server.base-directory.path: D:/test/data/test/hh/
		b.Pem SQL operations on database, provide the Db details
			   i.pem.datasource.url: database url Ex: jdbc:oracle:thin:@sareuhhgvzsm.amazonaws.com:1521/ORCL
			  ii.pem.datasource.username: db user name Ex: dbuser1
			 iii.pem.datasource.password: db password Ex: dbPassword
			  iv.pem.datasource.driver-class-name: db driver class name Ex: oracle.jdbc.driver.OracleDriver
		c.Pem Web Service APIs: if we have pEM webservice Apis then update the below properties, if we deploy application on PCM then we can keep the below properties as empty
			   i.pem.api-ws.active: true
			  ii.pem.api-ws.base-url: https://hostName:19443/pemws/sponsors/pragma
			 iii.pem.api-ws.base-username: test_user
			  iv.pem.api-ws.base-peks: pa@pemTest
		
17.File Decryption PGP and AES

		   file:
			  archive:
				scheduler: #Scheduler to call the Delete script which can delete the files from source file and destination file archive according to the file age configured in PCM UI
				  cron: "0 0 0 ? * * *" #"0 0 0 ? * * *" #At 00:00:00am every day, "* * * * * ? *" Every second
				  delete-files-job:
					active: false
					script-file-loc: /usr/CMArchiveDelete.sh #Absolute path of Delete script file
				pgp:
				  private-key: #Absolute path of PGP public key which will be used while decrypting PCM files set in the source file and destination file archive rules with encryption on
				  cmks: #PGP key passphrase
				aes:
				  secret-key: 3p+KB8sEYgX7R6Jh0MJRSQ== #Key for decrypting PCM files set in the source file and destination file archive rules with encryption on
				  salt: 9XboGbY6CkAqYi6WB2tTiQ== #Salt value for decrypting PCM files set in the source file and destination file archive rules with encryption on
 
		
18.Pcm Log file store location configuration

		a.Dowload the logback-spring.xml logfile from bitbucket and store in jarfile location(Let jar file location is D:\jar)
		b.Edit the logback-spring.xml file and update the filename in <file> tag
			1.If you want to store the file in any other location then update the file tag with Absolute path of the log file
				Ex: D:\pcm\community-manager-api.log
			2.If you want to store the log file in the same location of the PCM jar file then just provide the name of the log file
				Ex: community-manager-api.log
		c.Provide the logback-spring.xml file absolute path in application.yml file where logging.config= D:\jar\logback-spring.xml
	
19: Enable SSO with SSP and SEAS

		sso-ssp-seas:
		  ssp:
			logout-endpoint: /Signon/logout.html #SSP Logout endpoint ,default value is : /Signon/logout.html
			user-header-name: SM_USER #User header name config in SSP, default value is : SM_USER
			token-cookie-name: SSOTOKEN #Token cookie name config in SSP, default value is : SSOTOKEN
		  seas:
			auth-profile: communityManager #Authentication Profile Name in SEAS
			host: 35.200.20.211 #SEAS Host Name
			port: 61365 #61365 #SEAS Port
			ssl:
			  enabled: false #SSL enable or not in SEAS
			  protocol: #SEAS Protocol (Optional)
			  cipher-suits:  #SEAS Cipher Suits (Optional)
			  trust-store:
				name: #SEAS truststore file name (Absolute path)
				cmks: #SEAS truststore password
				alias: #SEAS truststore alias
				type: #SEAS truststore type
			  key-store:
				name: #SEAS keystore file name (Absolute path)
				cmks: #SEAS keystore password.
				alias: #SEAS keystore alias
				type: #SEAS keystore type
		  user-request:
			user: #Custom properties config in SEAS
			  email: email #Email property name config in SEAS
			  role: role #Role property name config in SEAS
			  first-name: firstName #FirstName property name config in SEAS
			  last-name: lastName #LastName property name config in SEAS
			  phone: phone #Phone property name config in SEAS
			  external-id: externalId #FirstName property name config in SEAS
			  preferred-language: preferredLanguage #Language property name config in SEAS(Optional)
			user-roles: #LDAP roles mapping to PCM (CM Role - LDAP Role)
			  super_admin: superAdmin
			  admin: admin
			  on_boarder: creator
			  business_admin: bAdmin
			  business_user: bUser
			  data_processor: processor
			  data_processor_restricted: processorRes

		
##  ii. Deployment Types

We can use this deployment for both PCM and PEM , Bellow are the scripts to run this as PCM and PEM, (Let : Database jar files avilable in D:\jars\cm-dependencies or usr/jars/cm-dependencies)

		a.PCM 
			WINDOWS : `java -Dspring.profiles.active=pcm -Dserver.port=port -Dserver.host=hostName -Dspring.datasource.cmks=<dbPassword> -Dspring.config.location=application.yml -cp community-manager-api-1.0.0.jar "-Dloader.path=D:\jars\cm-dependencies" org.springframework.boot.loader.PropertiesLauncher`
			LINUX   : `nohup java -Dspring.profiles.active=pcm -Dserver.port=port -Dserver.host=hostName -Dspring.datasource.cmks=<dbPassword> -Dspring.config.location=application.yml -cp community-manager-api-1.0.0.jar "-Dloader.path=/usr/jars/cm-dependencies" org.springframework.boot.loader.PropertiesLauncher &`
		b.PEM
			WINDOWS : `java -Dspring.profiles.active=default -Dserver.port=port -Dserver.host=hostName -Dspring.datasource.cmks=<dbPassword> -Dspring.config.location=application.yml -cp community-manager-api-1.0.0.jar "-Dloader.path=D:\jars\cm-dependencies" org.springframework.boot.loader.PropertiesLauncher`
			LINUX	: `nohup java -Dspring.profiles.active=default -Dserver.port=port -Dserver.host=hostName -Dspring.datasource.cmks=<dbPassword> -Dspring.config.location=application.yml -cp community-manager-api-1.0.0.jar "-Dloader.path=D:\jars\cm-dependencies" org.springframework.boot.loader.PropertiesLauncher &`
			
			EX : java -Dspring.profiles.active=pcm -Dserver.port=7080 -Dserver.host=100.100.100.100 -Dspring.datasource.cmks=Password -Dspring.config.location=application.yml -cp community-manager-api-1.0.0.jar "-Dloader.path=D:\jars\cm-dependencies" org.springframework.boot.loader.PropertiesLauncher

			Note: Place both Jar file and applicatrion.yml file in same location and run the above commands according to your platform and Application type
		c. Profile Types:
			1. pcm : This will enable the JWT token base Authentication in PCM
			2. cm-api : This will enable Basic Authentication in PCM
			3. sso-ssp-seas : This will enable both JWT and Basic Authentication in PCM
			
## iii. PCM Logo Change:
	a.If we you want to change the PCM log then we have to create the folder as "logo" where the PCM Jar file located and place the logo file in "logo" folder and rename the logo file as "logo or Logo" . Now logout and relogin into PCM then you can see the new logo which we placed in logo folder.
	
##  iv. Shutdown the Application
	a.Execute the below command from CLI to shutdown the Application(update user your pcm host and port)
		--> curl -X POST "http://localhost:PORT/pcm/app/shutdown-context?keyPhrase=.A4(SI@KPa#9-Z&shutdownUser=cmsysadmin" -H "accept: application/json"
		
		
		
		
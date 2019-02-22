# Tomcat

1. deploy

autoDeploy set to "true" and a running Tomcat allows for:

Deployment of .WAR files copied into the Host appBase.
发布放入appBase中的war包
Deployment of exploded web applications which are copied into the Host appBase.
发布放入appBase的解压的应用程序
Re-deployment of a web application which has already been deployed from a .WAR when the new .WAR is provided. In this case the exploded web application is removed, and the .WAR is expanded again. Note that the explosion will not occur if the Host is configured so that .WARs are not exploded with a unpackWARs attribute set to "false", in which case the web application will be simply redeployed as a compressed archive.
重新发布新的war包，删除旧的解压包，然后解压新的。如果unpackWARs设置成false， 将不解压
Re-loading of a web application if the /WEB-INF/web.xml file (or any other resource defined as a WatchedResource) is updated.
重新发布，如果/WEB-INF/web.xml文件被更新
Re-deployment of a web application if the Context Descriptor file from which the web application has been deployed is updated.
重新发布，如果 Context Descriptor file 被更新
Re-deployment of dependent web applications if the global or per-host Context Descriptor file used by the web application is updated.
重新发布，如果global or per-host Context Descriptor file被更新
Re-deployment of a web application if a Context Descriptor file (with a filename corresponding to the Context path of the previously deployed web application) is added to the $CATALINA_BASE/conf/[enginename]/[hostname]/ directory.
重新发布，如果 Context Descriptor file  被放入 $CATALINA_BASE/conf/[enginename]/[hostname]/

Undeployment of a web application if its document base (docBase) is deleted. Note that on Windows, this assumes that anti-locking features (see Context configuration) are enabled, otherwise it is not possible to delete the resources of a running web application.
解除发布，如果docBase被删除

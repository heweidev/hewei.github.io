# shiro

一站式登录和权限控制框架


# 单点登录

单一的登录服务sso，
如果需要登录跳转到登录页面，并把当前页面地址作为回调
用户登录后，重定向到登录之前的地址并增加ticket参数。
通过ticket可以从登录服务器获得登录用户的基本信息，同时ticket也用于校验登录是否成功

以下是校验过程的日志：
2019-01-10 10:49:02 [http-nio-8084-exec-204] DEBUG o.j.c.c.v.Cas30ServiceTicketValidator - Placing URL parameters in map.
2019-01-10 10:49:02 [http-nio-8084-exec-204] DEBUG o.j.c.c.v.Cas30ServiceTicketValidator - Calling template URL attribute map.
2019-01-10 10:49:02 [http-nio-8084-exec-204] DEBUG o.j.c.c.v.Cas30ServiceTicketValidator - Loading custom parameters from configuration.
2019-01-10 10:49:02 [http-nio-8084-exec-204] DEBUG o.j.c.c.v.Cas30ServiceTicketValidator - Constructing validation url: https://mis.qianhai.com.cn/jsh-sso/p3/serviceValidate?ticket=ST-264-wwh2wlhqPpHRRDla9ej1-sz030005&service=http%3A%2F%2F10.90.18.55%3A8084%2Fdi%2Ffm%2Findex
2019-01-10 10:49:02 [http-nio-8084-exec-204] DEBUG o.j.c.c.v.Cas30ServiceTicketValidator - Retrieving response from server.
2019-01-10 10:49:03 [http-nio-8084-exec-204] DEBUG o.j.c.c.v.Cas30ServiceTicketValidator - Server response: <cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'>
    <cas:authenticationSuccess>
        <cas:user>fengyp001</cas:user>
        <cas:attributes>
            <cas:isFromNewLogin>false</cas:isFromNewLogin>
            <cas:mail>fengyp001@qianhai.com.cn</cas:mail>
            <cas:authenticationDate>2019-01-10T10:49:02.979+08:00[Asia/Shanghai]</cas:authenticationDate>
            <cas:authenticationMethod>LdapAuthenticationHandler</cas:authenticationMethod>
            <cas:displayName>冯银朋</cas:displayName>
            <cas:successfulAuthenticationHandlers>LdapAuthenticationHandler</cas:successfulAuthenticationHandlers>
            <cas:longTermAuthenticationRequestTokenUsed>false</cas:longTermAuthenticationRequestTokenUsed>
            <cas:cn>冯银朋</cas:cn>
            </cas:attributes>
    </cas:authenticationSuccess>
</cas:serviceResponse>

**ticket只能使用一次***

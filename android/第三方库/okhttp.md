
···
    // RealCall.java
  Response getResponseWithInterceptorChain() throws IOException {
    // Build a full stack of interceptors.
    List<Interceptor> interceptors = new ArrayList<>();
    interceptors.addAll(client.interceptors());
    interceptors.add(retryAndFollowUpInterceptor);                // 重试管理 包括重定向
    interceptors.add(new BridgeInterceptor(client.cookieJar()));        /**
 Bridges from application code to network code. First it builds a network request from a user request. Then it proceeds to call the network. Finally it builds a user response from the network response.
 将request参数换成标准的http头， 根据标准http头换成response存储。 cookie管理 */
    interceptors.add(new CacheInterceptor(client.internalCache())); // Cache管理
    interceptors.add(new ConnectInterceptor(client));
    if (!forWebSocket) {
      interceptors.addAll(client.networkInterceptors());
    }
    interceptors.add(new CallServerInterceptor(forWebSocket));

    Interceptor.Chain chain = new RealInterceptorChain(
        interceptors, null, null, null, 0, originalRequest);
    return chain.proceed(originalRequest);
  }
  ···

## interceptor添加的顺序和调用顺序 

按照添加顺序依次被调用，调用是层叠的(递归的)。 比如加入的顺序是A,B,C。 则先调用A，然后B，最后C，然而C最先返回，最后是A
所以NetworkInterceptor后被调用，先返回. 调用栈

  
## 内置interceptor

###  自动重定向 302


## Overview
HTTP is the way modern applications network. It’s how we exchange data & media. Doing HTTP efficiently makes your stuff load faster and saves bandwidth.

OkHttp is an HTTP client that’s efficient by default:

- HTTP/2 support allows all requests to the same host to share a socket. （共用连接）
- Connection pooling reduces request latency (if HTTP/2 isn’t available). （连接池）
- Transparent GZIP shrinks download sizes. （Gzip压缩）
- Response caching avoids the network completely for repeat requests. （Cache管理）

OkHttp perseveres when the network is troublesome: it will silently recover from common connection problems. If your service has multiple IP addresses OkHttp will attempt alternate addresses if the first connect fails. This is necessary for IPv4+IPv6 and for services hosted in redundant data centers. OkHttp initiates new connections with modern TLS features (SNI, ALPN), and falls back to TLS 1.0 if the handshake fails.
（从常见问题中恢复， 多ip情况）

Using OkHttp is easy. Its request/response API is designed with fluent builders and immutability. It supports both synchronous blocking calls and async calls with callbacks.

OkHttp supports Android 2.3 and above. For Java, the minimum requirement is 1.7.

### 默认设置
最大同时连接数 64
单主机同时连接数 5

### 推荐只创建一个OkHttpClient 实例
> OkHttp performs best when you create a single {@code OkHttpClient} instance and reuse it for
all of your HTTP calls. This is because each client holds its own connection pool and thread
pools. Reusing connections and threads reduces latency and saves memory. Conversely, creating a
client for each request wastes resources on idle pools.

共有连接池和线程减少延时和内存占用


### Cache
只Cache GET请求

>   

 Cache-Control   = "Cache-Control" ":" 1#cache-directive
    cache-directive = cache-request-directive
         | cache-response-directive
    cache-request-directive =
           "no-cache"                          ; Section 14.9.1
         | "no-store"                          ; Section 14.9.2
         | "max-age" "=" delta-seconds         ; Section 14.9.3, 14.9.4
         | "max-stale" [ "=" delta-seconds ]   ; Section 14.9.3
         | "min-fresh" "=" delta-seconds       ; Section 14.9.3
         | "no-transform"                      ; Section 14.9.5
         | "only-if-cached"                    ; Section 14.9.4
         | cache-extension                     ; Section 14.9.6
     cache-response-directive =
           "public"                               ; Section 14.9.1
         | "private" [ "=" <"> 1#field-name <"> ] ; Section 14.9.1
         | "no-cache" [ "=" <"> 1#field-name <"> ]; Section 14.9.1
         | "no-store"                             ; Section 14.9.2
         | "no-transform"                         ; Section 14.9.5
         | "must-revalidate"                      ; Section 14.9.4
         | "proxy-revalidate"                     ; Section 14.9.4
         | "max-age" "=" delta-seconds            ; Section 14.9.3
         | "s-maxage" "=" delta-seconds           ; Section 14.9.3
         | cache-extension                        ; Section 14.9.6
    cache-extension = token [ "=" ( token | quoted-string ) ]

- max-age
  Indicates that the client is willing to accept a response whose age is no greater than the specified time in seconds. Unless max- stale directive is also included, the client is not willing to accept a stale response.
- min-fresh
  Indicates that the client is willing to accept a response whose freshness lifetime is no less than its current age plus the specified time in seconds. That is, the client wants a response that will still be fresh for at least the specified number of seconds.
- max-stale
  Indicates that the client is willing to accept a response that has exceeded its expiration time. If max-stale is assigned a value, then the client is willing to accept a response that has exceeded its expiration time by no more than the specified number of seconds. If no value is assigned to max-stale, then the client is willing to accept a stale response of any age.


问题：
1. 先cache后net
2. post请求cache

## Https握手
1. 客户端向server发送tls的版本和支持的加密算法， 以及随机数C
2. server返回选用的加密方法和随机数S，同时返回server证书
3. 客户端检验证书的合法性，然后生成随机数Pre-Master，用证书中的公钥加密送给server。用随机数生成的秘钥对之前交互数据的hash进行加密
4. server收到随机数后，用私钥解密，然后生成加密秘钥。然后用秘钥解密的hash和自己计算的hash进行校验
5. 客户端校验


![Img](http://img.blog.csdn.net/20160908134036615?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)


## Https 2.0
  header压缩
  multi exhange on the same connection
  unsolicited push of representations from servers to clients  从服务器推送数据到client


  ## Inner Interceptor

### RetryAndFollowUpInterceptor  处理网络错误和重试
    ip不可达，尝试其他ip
    连接过期
    代理服务器不可用
    3xx response code
    连接超时 408

 ### Bridge Interceptor
 > Bridges from application code to network code. First it builds a network request from a user request. Then it proceeds to call the network. Finally it builds a user response from the network response.

 user <---> Net
    // 默认keepalive
    if (userRequest.header("Connection") == null) {
      requestBuilder.header("Connection", "Keep-Alive");
    }

    填充host、cookie，user-agent等

    // 如果没有指定Accept-Encoding， 开启gzip压缩
    // 开启gzip后，同时负责对body进行解压缩。这个时候新的content-Length变成-1
    if (userRequest.header("Accept-Encoding") == null && userRequest.header("Range") == null) {
      transparentGzip = true;
      requestBuilder.header("Accept-Encoding", "gzip");
    }

### CacheInteceptor


### ConnectInterceptor
    // 将HttpCodec和connection加入到chain中
    RealInterceptorChain realChain = (RealInterceptorChain) chain;
    Request request = realChain.request();
    StreamAllocation streamAllocation = realChain.streamAllocation();

    // We need the network to satisfy this request. Possibly for validating a conditional GET.
    boolean doExtensiveHealthChecks = !request.method().equals("GET");
    HttpCodec httpCodec = streamAllocation.newStream(client, chain, doExtensiveHealthChecks);
    RealConnection connection = streamAllocation.connection();

    return realChain.proceed(request, streamAllocation, httpCodec, connection);


### CallServerIntercptor
   发送网络请求


### 连接池和复用

####核心类  
- HttpCodec
Encodes HTTP requests and decodes HTTP responses.
- Connection
The sockets and streams of an HTTP, HTTPS, or HTTPS+HTTP/2 connection. May be used for multiple
HTTP request/response exchanges. Connections may be direct to the origin server or via a proxy
- Transmitter
Bridge between OkHttp's application and network layers. This class exposes high-level application layer primitives: connections, requests, responses, and streams.
- Exchange
Transmits a single HTTP request and a response pair. This layers connection management and events
on {@link ExchangeCodec}, which handles the actual I/O.
- ConnectPool
 Manages reuse of HTTP and HTTP/2 connections for reduced network latency. HTTP requests that
 share the same {@link Address} may share a {@link Connection}. This class implements the policy
 of which connections to keep open for future use.

- ExchangeFinder 
/**
 * Attempts to find the connections for a sequence of exchanges. This uses the following strategies:
 *
 * <ol>
 *   <li>If the current call already has a connection that can satisfy the request it is used.
 *       Using the same connection for an initial exchange and its follow-ups may improve locality.
 *
 *   <li>If there is a connection in the pool that can satisfy the request it is used. Note that
 *       it is possible for shared exchanges to make requests to different host names! See {@link
 *       RealConnection#isEligible} for details.
 *
 *   <li>If there's no existing connection, make a list of routes (which may require blocking DNS
 *       lookups) and attempt a new connection them. When failures occur, retries iterate the list
 *       of available routes.
 * </ol>
 *
 * <p>If the pool gains an eligible connection while DNS, TCP, or TLS work is in flight, this finder
 * will prefer pooled connections. Only pooled HTTP/2 connections are used for such de-duplication.
 *
 * <p>It is possible to cancel the finding process.
 */

 连接复用的策略
 1. 如果call本身的连接满足条件，复用。（重试逻辑有用）
 2. 从连接池中取
 3. 创建新的


Transmitter 在call生成的时候创建：

  static RealCall newRealCall(OkHttpClient client, Request originalRequest, boolean forWebSocket) {
    // Safely publish the Call instance to the EventListener.
    RealCall call = new RealCall(client, originalRequest, forWebSocket);
    call.transmitter = new Transmitter(client, call);
    return call;
  }

Exchange在ConnectInterceptor中创建：
  @Override public Response intercept(Chain chain) throws IOException {
    RealInterceptorChain realChain = (RealInterceptorChain) chain;
    Request request = realChain.request();
    Transmitter transmitter = realChain.transmitter();

    // We need the network to satisfy this request. Possibly for validating a conditional GET.
    boolean doExtensiveHealthChecks = !request.method().equals("GET");
    Exchange exchange = transmitter.newExchange(chain, doExtensiveHealthChecks);

    return realChain.proceed(request, transmitter, exchange);
  } 


#### 上传文件
请求示范：

POST http://192.168.31.198:8080/uploadfile HTTP/1.1
Host: 192.168.31.198:8080
Connection: keep-alive
Content-Length: 444
Cache-Control: max-age=0
Origin: http://192.168.31.198:8080
Upgrade-Insecure-Requests: 1
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary0y7bV7VcUPJem9EH
User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
Referer: http://192.168.31.198:8080/upload.html
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9,en;q=0.8

------WebKitFormBoundary0y7bV7VcUPJem9EH
Content-Disposition: form-data; name="fileUpload"; filename="output.json"
Content-Type: application/json

[{"outputType":{"type":"APK"},"apkInfo":{"type":"MAIN","splits":[],"versionCode":5,"versionName":"V1.0","enabled":true,"outputFile":"location_debug_V1.0.apk","fullName":"debug","baseName":"debug"},"path":"location_debug_V1.0.apk","properties":{}}]
------WebKitFormBoundary0y7bV7VcUPJem9EH--

    







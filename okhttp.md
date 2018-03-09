
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
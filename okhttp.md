
···
    // RealCall.java
  Response getResponseWithInterceptorChain() throws IOException {
    // Build a full stack of interceptors.
    List<Interceptor> interceptors = new ArrayList<>();
    interceptors.addAll(client.interceptors());
    interceptors.add(retryAndFollowUpInterceptor);
    interceptors.add(new BridgeInterceptor(client.cookieJar()));
    interceptors.add(new CacheInterceptor(client.internalCache()));
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
所以NetworkInterceptor后被调用，先返回

  
## 内置interceptor

###  自动重定向 302
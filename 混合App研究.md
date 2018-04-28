# Js和native通信

这篇文章总结的比较全http://blog.csdn.net/u012506234/article/details/51447079



总的来说有两种方法：
- mWebView.addJavascriptInterface(new JsBridge(), "bxbxbai");
    - 优点：简单，同步调用，返回值透传
    - 缺点：4.2以下系统有安全问题，
    **js调用native不在主线程**

- Override WebViewClient或WebChromeClient的方法，然后截获调用请求
```
 webview.setWebChromeClient(new WebChromeClient() {
   public void onProgressChanged(WebView view, int progress) {
     // Activities and WebViews measure progress with different scales.
     // The progress meter will automatically disappear when we reach 100%
     activity.setProgress(progress * 1000);
   }
 });

    WebViewClient
    shouldOverrideUrlLoading(WebView view, String url)
```

可以用js创建一个隐藏iframe, 然后在iframe中加载特定的url


调用是异步的，但是都是在主线程


```
(function (b) {
	console.log('begin to init.')

	var _setResultIframe;
	var callback_map = [];

	console.log('a = ' + a)
	
	var a = document;
	_setResultIframe = a.createElement("iframe"),
			_setResultIframe.id = "__WeixinJSBridgeIframe_SetResult",
			_setResultIframe.style.display = "none",
			a.documentElement.appendChild(_setResultIframe);
	
	function _load(name, param, callback) {
		var id = callback_map.length;
		callback_map[id] = callback;
	
		_setResultIframe.src = 'app://api/' + name + '?data=' + encodeURI(JSON.stringify(param)) + '&callback=' + id; 
	}
	
	function _callback_handler(id, data) {
		var fun = callback_map[id];
		if (typeof fun == 'function') {
			callback_map[id] = null;
			fun(data); 
		} else {
			console.log('logic error!!!');
		}
	}

	b.app = {
		load: _load,
		callback_handler: _callback_handler,
	}

	console.log('init finished. b.app = ' + b.app)
})(window);
```

注入以上代码就可以在js中用window.app调用相关方法


## 一次h5兼容性拍错经历

现象：h5加载后logcat报
```
Uncaught SyntaxError: Unexpected token =", source: xxx(1)
``` 
Android7.0 没有问题，但是Android5.0不行
由于没有相关经验，对错误信息没有深入挖掘，决定用最笨的方法进行拍错
用二分法在代码中逐步加日志排错，最后发现：
如下代码：
```
function(obj = {}) {
	// xxxx
}
```

原来Android5.0 的webview不支持js默认参数
回头再看错误提示发现提示其实已经说明是‘=’的问题。由于代码进行了压缩，代码只有1行。

正确的排错方法是：将js换成未压缩版本，然后就能很快定位到代码行



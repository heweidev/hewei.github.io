# 定位App

## 规范设计的重要性
    设计规范
        间距，
        颜色、
        字号、
        颜色

## 百度地图
    只能获得一次定位。
    没有在mainifest中增加

## 线性布局的分割线
    两个属性divider 和 showDivider 
    divider 不接收color资源

## 判断定位是否开启
        public static boolean isLocationEnable(Context context) {
            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

## 在onActivityResult中操作view
    onActivityResult先于onResume调用，可能会导致时序问题。
    比如，由于onPause的时候地图的状态进行了变更，在onActivityResult定位将无效（没办法让地图定位到当前位置）

## 多用户App
    写配置的时候需要区分设备相关和用户相关
    用户注销的时候要清掉内存中用户相关的内容
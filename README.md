# NetBase_Network_Demo

即时网络监听架构的封装实现

### 支持:

- 支持一个界面添加多次监听
- 任意界面添加监听

### 实现原理：

- >= 5.0 , ConnectivityManager.NetworkCallback 监听实现
- <  5.0 , BroadcastReceiver 动态监听
- 在方法中使用更加简单，就再结合IOC注解，去动态监听注册位置的方法



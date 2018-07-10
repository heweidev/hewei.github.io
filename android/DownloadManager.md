# DownloadManager

Android集中下载管理

provider， UI， 下载服务（service）

1. 下载进程
2. 下载管理UI
3. 任务创建者

## 通过provider进行进程间通信（IPC）和RPC

    任务创建 ——> 通过provider在数据库中创建一条记录
    任务管理（启、停，删） ->  通过provider更新字段
    任务进度和状态反馈  -> 写入provider
    任务文件扫描后的URI

    provider更新后会通知cursor发生变化，从而更新UI，实现进程间通信

## 任务管理和调度
    DownloadService
    DownloadThread

## FileProvider
    将下载文件做成provider以便其他进程访问


## 集中管理通知（Notification）


## Receiver，监控网络变化。 MediaScanner将下载文件加入到媒体库

1.7 call
	okhttp使用call完成任务模型
	* 同步
	直到响应，线程块是可读的
	* 异步
	在任何线程进行排队请求，当响应可读时，在另一个线程回调；
1.8 调度
	同步调用需要较多的资源，但等待时间较短
	异步调用，调度实现了最大同时请求策略，可以设置每个Web服务器最大值（默认值为5），和整体（默认为64）
3.3 访问头
	添加
	header（name，value）
	addHeader（name，value）
	读取
	header（name）      //没有则返回null
	header（name）


# OkHttp       //网络库

## 特点
	同一主机共享一个套接字；
	通过连接池减少请求延迟
	GZIP压缩下载文件的大小
	缓存响应数据来减少重复的网络请求

## 环境配置

添加依赖
implementation 'com.squareup.okhttp3:okhttp:3.10.0'
添加网络权限 
<uses-permission android:name="android.permission.INTERNET"/>


## 使用

 **API**
	* OkHttpClient 
	自定义属性需要使用builder 模式创建对象
	* Request类
	使用OkHttp创建Request请求
		设置请求地址：HttpUrl
    	设置请求方式：默认为GET请求
    	构造请求头：Headers
    	构造请求体：RequestBody
    * Call接口
	在发起请求时，整个框架主要通过Call来封装每一次的请求。
	RealCall是Call接口的实现类
	Call call = okHttpClient.newCall(request);
	* Response类
		请求返回的响应码(成功的200或没有找到内容的404)，头信息，和可选的实体。

**基本操作步骤**
	* 创建OKHTTPClient对象
		OkHttpClient okHttpClient=new OkHttpClient();
	* 创建Request请求对象
		1>> Request.Builder builder=new Request.Builder();
			builder.url("http://www.baidu.com");
			Request request=builder.build();
		2>> Request request=new Request.Builder()
						.url("http://www.baidu.com")
						.build();
	* 创建Call对象
		1>>**同步请求**
		Call call=okHttpClient.newCall(request);
		new Thread (){
			public void run(){
				try {
	            Response response=call.execute();          //同步请求方式；同一时刻只能有一个任务执行
	            String result= response.body().string();    //得到具体响应数据
	            Log.e("lww", result);
	            } catch (IOException e) {
	                 e.printStackTrace();
            	}
			}
		}.start();
		

        2>>**异步请求**
        Call call=okHttpClient.newCall(request);
            call.enqueue(new Callback() {
            	@Override
            	public void onFailure(Call call, IOException e) {
            			//当请求失败自动回调该方法
            			Log.i(TAG, "请求失败 ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                        //当请求成功并返回响应自动回调该方法
                        String result= response.body().string(); //得到具体响应数据 
                        Log.i(TAG, "请求成功");
                }
            });


**表单提交**
	 new Thread() {
            @Override
            public void run() {
               OkHttpClient okHttpClient=new OkHttpClient();
                FormBody formBody = new FormBody.Builder()
                        .add("name", "NAME")
                        .add("pwd", "12346")
                        .build();

                Request request = new Request.Builder()
                        .url(URL)            //传入服务器URL
                        .post(formBody)       //提交表单对象
                        .build();

        
                Call call = okHttpClient.newCall(request);
               
                try {
                    Response response = call.execute();
                    String result = response.body().string();
                    Log.i(TAG, result);            //打印服务器传回的表单提交成功提示
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
**文件上传**
	 new Thread() {
            @Override
            public void run() {
            	OkHttpClient okHttpClient=new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/jpeg");
                String imgFile = getFilesDir().getAbsolutePath() + "/img.jpg";//获得app本地文件夹路径下文件即可
                RequestBody requestBody = RequestBody.create(
                        mediaType, new File(imgFile)
                );

                Request request = new Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i(TAG, "onFailure: FAILED");//失败提示
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i(TAG, "onResponse: " + response.body().string());//打印服务器传回的文件提交成功提示
                    }
                });


            }
        }.start();
**文件下载**
	new Thread(){
		@Override
		public void run(){
			OkHttpClient okHttpClient=new OkHttpClient();

			Request request=new Request.Builder()
                .url(URL)
                .build();

            Call call=okHttpClient.newCall(request);
             call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: FAILED");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse: SUCCESS");
               //TODU :response变量的输入流输出流读取并保存至相关文件操作

            }
        });

		}
	}.start();








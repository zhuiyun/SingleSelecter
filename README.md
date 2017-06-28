# SingleSelecter

 Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
  
  

Step 2. Add the dependency

	dependencies {
	        compile 'com.github.zhuiyun:SingleSelecter:1.0.1'
	}
	
	
	
1.基本数据类型的集合,使用SingleListSelector 

    SingleListSelector singleListSelector = new SingleListSelector(this, list);
    singleListSelector.setListener(this);//回调监听 
    singleListSelector.show();//展示dialog

2.自定义数据类型的使用:

    SingleSelector singleSelector=new SingleSelector(this, List<Data>);
        singleSelector.setListener(this);
        singleSelector.show();
	
#注意:自定义数据类型时,需要把你的数据类型继承SelectString这个类,并重写其中的getString,方便拿到要展示的数据,自定义数据类型和基本数据类型的控件不要混用,不通用

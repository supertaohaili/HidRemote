
#---------------------------------基本指令----------------------------------
 # 设置混淆的压缩比率 0 ~ 7
-optimizationpasses 5
# 混淆后类名都为小写   Aa aA
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
#不做预校验的操作
-dontpreverify
# 混淆时不记录日志
-verbose
# 混淆采用的算法.
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保留代码行号，方便异常信息的追踪
-keepattributes SourceFile,LineNumberTable
#dump文件列出apk包内所有class的内部结构
-dump class_files.txt
#seeds.txt文件列出未混淆的类和成员
-printseeds seeds.txt
#usage.txt文件列出从apk中删除的代码
-printusage unused.txt
#mapping文件列出混淆前后的映射
-printmapping mapping.txt
#保护注解
-keepattributes *Annotation*
#----------------------------------------------------------------------------

#---------------------------------默认保留区，避免混淆Android基本组件---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**

#不提示V4包下错误警告
-dontwarn android.support.v4.**
#保持下面的V4兼容包的类不被混淆
-keep class android.support.v4.**{*;}
#避免混淆所有native的方法,涉及到C、C++
-keepclasseswithmembernames class * {
native <methods>;
}

-keepclassmembers class * extends android.app.Activity{
public void *(android.view.View);
}

#避免混淆枚举类
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}
#避免混淆自定义控件类的get/set方法和构造函数
#混淆保护自己项目的部分代码以及引用的第三方jar包library-end##################
-keep public class * extends android.view.View{
*** get*();
void set*(***);
public <init>(android.content.Context);
public <init>(android.content.Context, android.util.AttributeSet);
public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet);
public <init>(android.content.Context, android.util.AttributeSet, int);
}
#避免混淆序列化类
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#不混淆Serializable和它的实现子类、其成员变量
-keepclassmembers class * implements java.io.Serializable {
static final long serialVersionUID;
private static final java.io.ObjectStreamField[] serialPersistentFields;
private void writeObject(java.io.ObjectOutputStream);
private void readObject(java.io.ObjectInputStream);
java.lang.Object writeReplace();
java.lang.Object readResolve();
}
#使用GSON、fastjson等框架时，所写的JSON对象类不混淆，否则无法将JSON解析成对应的对象
-keepclassmembers class * {
public <init>(org.json.JSONObject);
}
#不混淆资源类
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
void *(**On*Event);
}

#移除log
-assumenosideeffects class android.util.Log{
public static int v(...);
public static int i(...);
public static int d(...);
public static int w(...);
public static int e(...);
}
#避免混淆泛型 如果混淆报错建议关掉
-keepattributes Signature

#----------------------------------------------------------------------------

# AndroidX混淆
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

-keep class com.android.superli.btremote.bean.** { *; }

# EventBus
-keepclassmembers class ** {
@org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
<init>(java.lang.Throwable);
}

# Glide
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.**{*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Gson
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { <fields>; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# OkHttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

# banner
-dontwarn com.youth.banner.**
-keep class com.youth.banner.**{*;}


#友盟统计
-keep class com.umeng.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#//SDK 9.2.4及以上版本自带oaid采集模块，不再需要开发者再手动引用oaid库，所以可以不添加这些混淆
#-keep class com.zui.**{*;}
#-keep class com.miui.**{*;}
#-keep class com.heytap.**{*;}
#-keep class a.**{*;}
#-keep class com.vivo.**{*;}

-keep public class com.android.superli.btremote.R$*{
public static final int *;
}

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

-dontwarn androidx.viewpager2.**
-keep class androidx.viewpager2.** {*;}
-dontwarn androidx.recyclerview.widget.RecyclerView
-keep class androidx.recyclerview.widget.RecyclerView{*;}
-dontwarn com.youth.banner.**
-keep class com.youth.banner.** {*;}

# Fragmentation
-keep class * extends android.support.v4.app.FragmentManager{ *; }

-keep class me.jessyan.autosize.** { *; }
-keep interface me.jessyan.autosize.** { *; }
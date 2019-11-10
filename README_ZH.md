

# [OneActionBar](https://github.com/onestravel/OneActionBar) 使用说明



[English Document](README.md)

## 简介
**自定义ActionBar，支持属性，java代码方式配置，可实现标题，标题带返回键，搜索，搜索带返回键的ActionBar；配置简单，方便使用，可适应大多数场景。**

## 效果图

###![效果图](/images/20190825/image-20190915194738463.png)

1. 标准标题头，背景透明 ``style="@style/OneActionBar.Normal"``
2. 标准标题头，蓝色背景 ``style="@style/OneActionBar.Normal.Blue"``
3. 带返回键标题头，透明背景 ``style="@style/OneActionBar.Back"``
4. 带返回键标题头，蓝色背景 ``style="@style/OneActionBar.Back.Blue"``
5. 带搜索框标题头，透明背景 ``style="@style/OneActionBar.Search"``
6. 带搜索框标题头，蓝色背景 ``style="@style/OneActionBar.Search.Blue"``
7. 搜索框带返回键标题头，透明背景 ``style="@style/OneActionBar.Back.Search"``
8. 搜索框带返回键标题头，蓝色背景 ``style="@style/OneActionBar.Back.Search.Blue"``

## 引入方式

TAG:     [![](https://jitpack.io/v/onestravel/OneActionBar.svg)](https://jitpack.io/#onestravel/OneActionBar)

#### gradle

- 在项目中的根目录下的 build.gradle (与model同级的) 中增加如下配置

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```

- 在model 中的build.gradle 中增加依赖

```
dependencies {
	implementation 'com.github.onestravel:OneActionBar:TAG'
}

```

##  属性说明

| 属性名                     | 字段说明                                                     | 示例                                       |
| -------------------------- | ------------------------------------------------------------ | ------------------------------------------ |
| app:barBackColor           | ActionBar 返回键颜色，图标和字体的颜色                       | app:barBackColor="#000000"                 |
| app:barBackIconWidth       | ActionBar 返回键图标的宽度                                   | app:barBackIconWidth="25dp"                |
| app:barBackIconHeight      | ActionBar 返回键图标的高度                                   | app:barBackIconHeight="25dp"               |
| app:barBackText            | ActionBar 返回键文字                                         | app:barBackText="返回"                     |
| app:barBackTextSize        | ActionBar 返回键文字大小                                     | app:barBackTextSize="13sp"                 |
| app:barTitle               | ActionBar 标题文字                                           | app:barTitle="首页"                        |
| app:barTitleColor          | ActionBar 标题文字颜色                                       | app:barTitleColor="#000000"                |
| app:barTitleSize           | ActionBar 标题文字大小                                       | app:barTitleSize="22sp"                    |
| app:barType                | ActionBar 样式，总共有四种样式：TYPE_NORMAL(普通样式)、TYPE_BACK(普通带返回键样式)、TYPE_SEARCH(搜索样式)、TYPE_BACK_SEARCH(带返回键搜索样式)、 | app:barType="TYPE_NORMAL"                  |
| app:barActionBtnColor      | ActionBar 标题右侧操作按钮图标/文字颜色                      | app:barActionBtnColor="#FF0000"            |
| app:barActionBtnText       | ActionBar 标题右侧操作按钮文字                               | app:barActionBtnText="下一步"              |
| app:barActionBtnTextSize   | ActionBar 标题右侧操作按钮文字大小                           | app:barActionBtnTextSize="15sp"            |
| app:barBottomLineWidth     | ActionBar 底部分割线的宽度                                   | app:barBottomLineWidth="1dp"               |
| app:barBottomLineColor     | ActionBar 底部分割线的颜色                                   | app:barBottomLineColor="#1a1a1a"           |
| app:barActionBtnIcon       | ActionBar 标题右侧操作按钮icon                               | app:barActionBtnIcon="@drawable/icon_next" |
| app:barActionBtnIconWidth  | ActionBar 标题右侧操作按钮宽度                               | app:barActionBtnIconWidth="25dp"           |
| app:barActionBtnIconHeight | ActionBar 标题右侧操作按钮高度                               | app:barActionBtnIconHeight="25dp"          |
| app:barSearchBackground    | ActionBar 搜索样式背景                                       | app:barSearchBackground="#FFFFFF"          |
| app:barSearchHint          | ActionBar 搜索样式搜索框提示文字                             | app:barSearchHint="请输入搜索内容"         |
| app:barSearchText          | ActionBar 搜索样式搜索框文字                                 | app:barSearchText="今日新闻"               |
| app:barSearchHintColor     | ActionBar 搜索样式搜索框提示文字颜色                         | app:barSearchHintColor="#E1E1E1"           |
| app:barSearchTextColor     | ActionBar 搜索样式搜索框文字颜色                             | app:barSearchTextColor="#000000"           |
| app:barSearchTextSize      | ActionBar 搜索样式搜索框文字大小                             | app:barSearchTextSize="20sp"               |
| android:background         | ActionBar 背景颜色                                           | android:background="#FFFFFF"               |

## 样式说明

根据图片效果，先自定义样式说明如下

注：标号对应图片效果数字号码

1. 标准标题头，背景透明 ``style="@style/OneActionBar.Normal"``
2. 标准标题头，蓝色背景 ``style="@style/OneActionBar.Normal.Blue"``
3. 带返回键标题头，透明背景 ``style="@style/OneActionBar.Back"``
4. 带返回键标题头，蓝色背景 ``style="@style/OneActionBar.Back.Blue"``
5. 带搜索框标题头，透明背景 ``style="@style/OneActionBar.Search"``
6. 带搜索框标题头，蓝色背景 ``style="@style/OneActionBar.Search.Blue"
7. 搜索框带返回键标题头，透明背景 ``style="@style/OneActionBar.Back.Search"``
8. 搜索框带返回键标题头，蓝色背景 ``style="@style/OneActionBar.Back.Search.Blue"``

## 示例

1、布局xml

```xml
<cn.onestravel.one.actionbar.OneActionBar
        style="@style/OneActionBar.Back.Blue"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        app:barActionBtnText="下一步"
        app:barTitle="调查问卷" />
```

2、样式

```Xml
<style name="OneActionBar.Back">
  			<item name="barTitleSize">22sp</item>
        <item name="barActionBtnTextSize">16sp</item>
        <item name="barType">TYPE_BACK</item>
        <item name="barBackTextSize">14sp</item>
        <item name="barBackText">返回</item>
</style>
<style name="OneActionBar.Back.Blue" parent="OneActionBar.Back">
    <item name="android:background">#0080ff</item>
    <item name="barBackColor">@android:color/white</item>
    <item name="barTitleColor">@android:color/white</item>
    <item name="barActionBtnColor">@android:color/white</item>
</style>
```



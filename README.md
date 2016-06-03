# QQBubbleView

## Outline

利用三阶贝塞尔曲线模仿QQ空间直播时右下角的礼物冒泡特效

## ScreenShot

![](http://diycode.b0.upaiyun.com/photo/2016/55b80c4c270e41e429c468973f215cc7.gif)

## Usage

* 引入依赖

```
compile 'yasic.library.BubbleView:bubbleview:0.0.4'
```

* 启动动画

```
void startAnimation(final int rankWidth, final int rankHeight)
void startAnimation(final int rankWidth, final int rankHeight, int count)
void startAnimation(final int rankWidth, final int rankHeight, int delay, int count)
```

* 使用默认图片样式

```
BubbleView setDefaultDrawableList()
```

* 自定义图片样式

```
BubbleView setDrawableList(drawableList)
```

* 设置上升动画持续时间

```
BubbleView setRiseDuration(int riseDuration)
```

* 设置初始点相对于底边边距

```
BubbleView setBottomPadding(int px)
```

* 设置三个随机初始点的间距

```
BubbleView setOriginsOffset(int px)
```

* 设置缩放动画最大最小比例
```
BubbleView setScaleAnimation(float maxScale, float minScale)
```

* 设置两个上升动画之间的时间间隔

```
BubbleView setAnimationDelay(int delay)
```

* 设置图片数目最大最小值

```
void setMaxHeartNum(int maxHeartNum)
void setMinHeartNum(int minHeartNum)
```

* 设置每一个view的宽高

```
BubbleView setItemViewWH(int viewWidth, int viewHeight)
```

## Point

* 继承自Relativelayout

* 使用三阶贝塞尔曲线模拟运动路径，三阶贝塞尔曲线公式如下


![](http://img.my.csdn.net/uploads/201008/28/0_1282984326C3m1.gif)

* 参考博客http://www.html-js.com/article/1628

* 强烈推荐一个模拟贝塞尔曲线的网站，可以在线模拟出想要的曲线http://myst729.github.io/bezier-curve/


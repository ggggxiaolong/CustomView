# CustomView
一步步开始自定义View
### 第一步
从最基本的画点，线，矩形，圆角矩形，椭圆，圆，圆环，弧开始练习。最后画一个简单的饼图。
![线](http://oajsxp5w8.bkt.clouddn.com/S60823-180935.jpg?imageView2/2/w/400) ![饼图](http://oajsxp5w8.bkt.clouddn.com/S60823-175652.jpg?imageView2/2/w/400)

* 获取自定义View的宽度
在自定义View中重写onSizeChanged方法

```
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w; height = h;
    }
```
* 在自定义View的onDraw()方法中画出相应的图形，因为需要画的图形比较多为了避免if else判断使用策略模式。添加一个类DrawHelper 添加DrawInterface接口（使用抽象类也是可以的），添加需要画的图形的实现。

```
public final class DrawHelper {
    interface DrawInterface {
        void draw(Canvas canvas, int width);
    }

    public static class DrawPoint implements DrawInterface {

        @Override
        public void draw(Canvas canvas, int width) {
            Paint paint = new Paint();
            paint.setColor(0xff70dcdc);
            paint.setStrokeWidth(10);
            int half = width / 2;
            canvas.drawPoints(new float[]{
                    100, 100,
                    half, 100,
                    half, 200,
                    half, 300,
                    half, 400,
            }, paint);
        }
    }
    //..
｝
```
[查看详细代码](https://github.com/ggggxiaolong/CustomView/blob/master/app/src/main/java/com/github/ggggxiaolong/customview/view/DrawHelper.java)

* 在自定义View中添加切换的公共方法

```
    public void translate(DrawHelper.DrawInterface drawInterface){
        mDrawInterface = drawInterface;
        invalidate();
    }
```
###参考 [@GcsSloop](http://weibo.com/GcsSloop) 的 [安卓学习笔记](https://github.com/GcsSloop/AndroidNote) 
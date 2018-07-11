package com.mygit.photoandcamera.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mygit.photoandcamera.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by admin on 2015/11/4.
 */
public class Utils {

    public static boolean notEmpty(CharSequence str) {
        if (str != null && str.length() > 0) {
            return true;
        } else
            return false;
    }

    public static boolean notEmpty(String str) {
        if (str != null && str.trim().length() > 0) {
            return true;
        } else
            return false;
    }

    public static boolean notEmpty(EditText text) {
        if (text.getText() != null && notEmpty(text.getText().toString())) {
            return true;
        } else
            return false;
    }

    public static boolean notEmpty(TextView text) {
        if (text.getText() != null && notEmpty(text.getText().toString())) {
            return true;
        } else
            return false;
    }

    public static void print(Object str) {
        System.out.println(str);
    }

    public static boolean match(String str1, String str2) {
        if (str1 != null && str2 != null) {
            if (str1.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证手机格式  正则表达式
     */
    public static boolean isMobileNO(String mobile) {
        String telRegex = "[1][23456789]\\d{9}";
        if (TextUtils.isEmpty(mobile))
            return false;
        else
            return mobile.matches(telRegex);
    }

    /**
     * 验证字符串是否数字和字母组合
     *
     * @param str
     * @return
     */
    public static boolean isNumAlphabetMix(String str) {
        String telRegex = "(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,23}";
        if (TextUtils.isEmpty(str))
            return false;
        else
            return str.matches(telRegex);
    }

    /**
     * 验证字符串是否纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        String telRegex = "^[0-9]*$";
        if (TextUtils.isEmpty(str))
            return false;
        else
            return str.matches(telRegex);
    }

    /**
     * 判断是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 只允许输入英文数字和汉字
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws
            PatternSyntaxException {
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 判断是否有网络连接 true网通的
     */
    public static boolean IsNetworkAvailble(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if ((info != null) && (info.isConnected())) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.e("",
                    "Get network state exception! \r\nDid you forgot to add \r\nandroid.permission.ACCESS_NETWORK_STATE in AndroidManifest.xml��\r\n");
        }
        Toast.makeText(context, "网络未连接",
                Toast.LENGTH_SHORT).show();
        return false;
    }

    // 缩放图片
    public static Bitmap zoomImg(String img, int newWidth) {
        if (Utils.notEmpty(img)) {
            // 图片源
            Bitmap bm = BitmapFactory.decodeFile(img);
            if (null != bm) {
                // 获得图片的宽高
                int width = bm.getWidth();
                int height = bm.getHeight();
                // 计算缩放比例
                float scaleWidth = ((float) newWidth) / width;
                float scaleHeight = scaleWidth;
                // 取得想要缩放的matrix参数
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                // 得到新的图片
                Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
                return newbm;
            } else {
                return null;
            }

        }
        return null;
    }

    /**
     * 获取系统版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * @param v1
     * @param v2
     * @return true if v1 is less than v2, else false
     */
    public static boolean compareVersion(String v1, String v2) {
        if (v1.compareToIgnoreCase(v2) < 0) {
            return true;
        }
        return false;
    }


    /**
     * 加法
     *
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     *              使用.doubleValue() 会失去最后小数点后的0
     * @return
     */
    public static double add(double d1, double d2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 减法
     *
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return 使用.doubleValue() 会失去最后小数点后的0
     */
    public static double sub(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * double 乘法
     *
     * @param d1
     * @param d2
     * @return 使用.doubleValue() 会失去最后小数点后的0
     */
    public static double mul(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
//        LogUtil.i("b1==" + (bd1));
//        LogUtil.i("b1==" + (bd2));
//        LogUtil.i("b1==" + (bd1.multiply(bd2).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue());
//        LogUtil.i("b3==" + bd1.multiply(bd2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue());

        return bd1.multiply(bd2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * double 除法
     *
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     *              使用.doubleValue() 会失去最后小数点后的0
     * @return
     */
    public static double div(double d1, double d2, int scale) {
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理
        if (d2 == 0) {
//            MyToast.show("分母不可为0");
            return 0;
        }
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));

        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 设置magin
     *
     * @return
     */
    public static void setMagin(View view, int left, int top, int right, int bottom) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.leftMargin = left;
        params.topMargin = top;
        params.rightMargin = right;
        params.bottomMargin = bottom;
        view.setLayoutParams(params);
    }

    /**
     * 设置magin
     *
     * @return
     */
    public static void setMaginLins(View view, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.leftMargin = left;
        params.topMargin = top;
        params.rightMargin = right;
        params.bottomMargin = bottom;
        view.setLayoutParams(params);
    }


    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = ((Activity) context).getWindowManager();
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = ((Activity) context).getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }


    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenhight(Context context) {
        WindowManager wm = ((Activity) context).getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }

    public static int getViewWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        return width;
    }

    public static int getViewHight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        return height;
    }

    public static void setViewWidth(View view, int width) {

        //这里是headview防止为空加的处理
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        if (width > 0)
            linearParams.width = width;// 控件的宽强制设成
        view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }

    public static void setViewHight(View view, int hight) {
        //这里是headview防止为空加的处理
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ListView.LayoutParams(
                    ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.MATCH_PARENT));
        }
        ListView.LayoutParams linearParams = (ListView.LayoutParams) view.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.height = hight;// 控件的宽强制设成
        view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }

    public static void setViewHightWeb(View view, int hight) {
        //这里是headview防止为空加的处理
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        if (hight > 0)
            linearParams.height = hight;// 控件的宽强制设成
        view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

    }

    public static void setViewHightLins(View view, int hight) {

        //这里是headview防止为空加的处理
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ListView.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.height = hight;// 控件的宽强制设成
        view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }


    public static float getScreenDensity(Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return scale;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将bitmap转换成byte数组
     *
     * @param bmp
     */
    public static byte[] bitmapToBytes(Bitmap bmp) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里

        bmp.recycle();//自由选择是否进行回收

        byte[] result = output.toByteArray();//转换成功了
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


    //存储图片
    public static void writeBitmapToFile(Bitmap bitmap, File f) {
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            LogUtil.e("tag-image.size===压缩后" + FileUtil.getAutoFileOrFilesSize(f.getAbsolutePath()));
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * [压缩,保存]
     * 用于图片上传前的文件压缩
     *
     * @param bitmap   原图Bitmap
     * @param KBLength 期望大小
     * @param savePath 压缩文件路径
     * @return 压缩后文件
     */
    public static File saveCompressBitmap(Bitmap bitmap, int KBLength, String savePath) {
        File file;
        file = BitmapUtils.compressBmpToFile(bitmap, savePath, Bitmap.CompressFormat.JPEG, 500, true, KBLength);
        return file;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStr(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static void nuke() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * 关闭输入法
     *
     * @param context
     * @param view
     */
    public static void hideInputMethod(Context context, View view) {
        //如果输入法打开则关闭，如果没打开则打开
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        //关闭输入法窗口
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.SHOW_FORCED);
    }


    /**
     * 显示输入法
     *
     * @param context
     * @param view
     */
    public static void showInputMethod(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 软键盘开关，打开则关闭，关闭则打开
     *
     * @param context
     */
    public static void toggleInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean existSoftInput(Activity context) {
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = context.getWindow().getDecorView().getHeight();
        if (rect.bottom != winHeight) {
            return true;
        }
        return false;
    }

    //集合转字符串
    public static String ListToString(List<String> list) {
        if (list == null || list.size() <= 0) {
            return "";
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                if (i == (list.size() - 1)) {
                    stringBuffer.append(list.get(i));
                } else {
                    stringBuffer.append(list.get(i) + ",");
                }
            }
            return stringBuffer.toString();
        }
    }

    //字符串转数组
    public String[] strToArray(List<String> strList) {
        if (strList == null || strList.size() <= 0) {
            return new String[1];
        }

        return strList.toArray(new String[0]);
    }

    //TextView的左右上下放置图片
    public static void setDrwableRight(Context context, int mipmap, CheckBox view) {
        view.setCompoundDrawables(null, null, setDrwable(context, mipmap, view), null);
    }

    public static void setDrwableTop(Context context, int mipmap, TextView view) {
        view.setCompoundDrawables(null, setDrwable(context, mipmap, view), null, null);
    }

    public static void setDrwableLeft(Context context, int mipmap, TextView view) {
        view.setCompoundDrawables(setDrwable(context, mipmap, view), null, null, null);
    }

    public static Drawable setDrwable(Context context, int mipmap, TextView view) {
        Drawable drawable = null;
        if (mipmap != -1) {
            try {
                drawable = context.getResources().getDrawable(mipmap);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return drawable;
    }


    /**
     * 验证真实姓名格式
     */
    public static boolean isTrueName(String name) {
        String telRegex = "^([\\u4e00-\\u9fa5]{2,13}|[a-zA-Z\\.\\s]{4,13})$";
        if (TextUtils.isEmpty(name))
            return false;
        else
            return name.matches(telRegex);
    }

    /**
     * 验证身份证格式
     */
    public static boolean isTrueIDCard(String idcard) {
        String telRegex = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";
        if (TextUtils.isEmpty(idcard))
            return false;
        else
            return idcard.matches(telRegex);
    }

    //字体加粗
    public static void setTextFold(TextView view) {
        TextPaint tp = view.getPaint();
        tp.setFakeBoldText(true);
    }

    //字体变粗变细
    public static void setTextFold(TextView view, Boolean b) {
        TextPaint tp = view.getPaint();
        tp.setFakeBoldText(b);
    }

    //复制字符串
    public static void copyOid(Context mContext, String oid) {
        if (Utils.notEmpty(oid)) {
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);  //粘贴板
            // 创建普通字符型ClipData  api11是android3.0多，肯定不存在了
            ClipData mClipData;
            mClipData = ClipData.newPlainText("Label", oid);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
//            MyToast.show("复制成功");
        } else {
//            MyToast.show("复制内容不能为空");
        }

    }


    /**
     * HH:mm:ss
     * Java Calendar 类时间操作
     *
     * @param countdownTime
     * @return
     */
    public static String getHms(long countdownTime) {
        long temp = 0;
        StringBuffer sb = new StringBuffer();
        temp = countdownTime / 3600;
        sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");

        temp = countdownTime % 3600 / 60;
        sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");

        temp = countdownTime % 3600 % 60;
        sb.append((temp < 10) ? "0" + temp : "" + temp);
        return sb.toString();
    }

}

package com.mygit.photoandcamera.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by admin on 2018/5/29.
 */

public class BitmapUtils {
    /**
     * Bitmap转成File
     *
     * @param bitmap     原图Bitmap
     * @param SavePath   保存路径
     * @param format     文件格式（JPEG,PNG,WEBP）
     * @param quality    压缩基数
     * @param isCompress 是否压缩
     * @param KBLength   期望最大长度
     * @return 保存成功返回File，失败返回null
     */
    public static File compressBmpToFile(Bitmap bitmap, String SavePath, Bitmap.CompressFormat format, int quality, boolean isCompress, int KBLength) {
        try {
            if (TextUtils.isEmpty(SavePath)) {
                return null;
            }
            File file = new File(SavePath);//将要保存图片的路径
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int options = quality;
            if (options < 0 || options > 100) {
                options = 100;
            }
            if (isCompress) {
                if (KBLength < 100) {
                    KBLength = 100;
                }
                LogUtil.e("TAG-开始压缩");
                bitmap.compress(format, options, byteArrayOutputStream);
                LogUtil.e("TAG-压缩前图片大小：" + byteArrayOutputStream.toByteArray().length / 1024 + "kb");
                while (byteArrayOutputStream.toByteArray().length / 1024 > KBLength) {
                    LogUtil.e("TAG-压缩中图片大小：" + byteArrayOutputStream.toByteArray().length / 1024 + "kb");
                    byteArrayOutputStream.reset();
                    options = options > 10 ? options - 10 : 1;
                    bitmap.compress(format, options, byteArrayOutputStream);
                }
                LogUtil.e("TAG-压缩完图片大小：" + byteArrayOutputStream.toByteArray().length / 1024 + "kb");
            } else {
                LogUtil.e("TAG-不压缩");
                bitmap.compress(format, options, byteArrayOutputStream);
            }
            LogUtil.e("file path==" + file.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArrayOutputStream.toByteArray());
            fos.flush();
            fos.close();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [压缩]
     * 根据图片路径，压缩比例，压缩图片
     *
     * @param filePath   图片路径
     * @param SampleSize 压缩比例
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressBitmap(String filePath, int SampleSize) {
        LogUtil.e("tag-拍照****filePath****" + filePath);
        LogUtil.e("获取文件大小===="+ FileUtil.getAutoFileOrFilesSize(filePath));
        return BitmapUtils.compressBitmap(filePath, 0, 0, SampleSize);
    }

    /**
     * 根据期望的宽度和高度或者压缩比例压缩图片
     *
     * @param filePath   文件路径
     * @param width      期望宽度
     * @param height     期望高度
     * @param SampleSize 缩放比例
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressBitmap(String filePath, int width, int height, int SampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        LogUtil.e("tag-拍照****decodeFile filePath****" + filePath);
        BitmapFactory.decodeFile(filePath, options);
        LogUtil.e("tag-拍照****options****" + options.outHeight + "****" + options.outWidth);
        calculateInSampleSize(options, SampleSize, width, height);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 处理压缩使用的Options
     *
     * @param options    原图Options
     * @param SampleSize 缩放比例
     * @param reqWidth   期望宽度
     * @param reqHeight  期望高度
     */
    //计算图片的缩放值
    protected static void calculateInSampleSize(BitmapFactory.Options options, int SampleSize, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= 700 || width <= 700) {
            options.inSampleSize = 1;
            return;
        }
        LogUtil.e("TAG-压缩前W:" + width);
        LogUtil.e("TAG-压缩前H:" + height);
        float inSampleSize = 1;
        if (SampleSize <= 0) {
            if (height > reqHeight || width > reqWidth) {
                if (reqHeight > 0 && reqWidth > 0) {
                    float heightRatio = (float) height / (float) reqHeight;
                    float widthRatio = (float) width / (float) reqWidth;
                    inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
                }
            }
        } else {
            inSampleSize = SampleSize;
        }
        options.outHeight = (int) (height / inSampleSize);
        options.outWidth = (int) (width / inSampleSize);
        LogUtil.e("TAG-压缩后W:" + options.outWidth);
        LogUtil.e("TAG-压缩后H:" + options.outHeight);
        options.inSampleSize = Math.round(inSampleSize);
    }

}

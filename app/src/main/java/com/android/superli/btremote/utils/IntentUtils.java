package com.android.superli.btremote.utils;

import android.content.Intent;
import android.net.Uri;

/**
 * <pre>
 *     desc  : 意图相关工具类
 * </pre>
 */
public final class IntentUtils {
    /**
     * 获取分享文本的意图
     *
     * @param content 分享文本
     * @return intent
     */
    public static Intent getShareTextIntent(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

//    /**
//     * 获取分享图片的意图
//     *
//     * @param content   文本
//     * @param imagePath 图片文件路径
//     * @return intent
//     */
//    public static Intent getShareImageIntent(String content, String imagePath) {
//        return getShareImageIntent(content, FileUtils.getFileByPath(imagePath));
//    }
//
//    /**
//     * 获取分享图片的意图
//     *
//     * @param content 文本
//     * @param image   图片文件
//     * @return intent
//     */
//    public static Intent getShareImageIntent(String content, File image) {
//        if (!FileUtils.isFileExists(image)) return null;
//        return getShareImageIntent(content, Uri.fromFile(image));
//    }

    /**
     * 获取分享图片的意图
     *
     * @param content 分享文本
     * @param uri     图片uri
     * @return intent
     */
    public static Intent getShareImageIntent(String content, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }


}

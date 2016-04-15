//package com.twtstudio.coder.saishi_android.ui.common;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//
//import com.twtstudio.coder.saishi_android.ui.Image.ShowImgListActivity;
//
///**
// * Created by clifton on 16-4-12.
// */
//public class DiyWebView extends WebView {
//        // WebViewImageClick lisener;
//        Context context;
//        int maxWidth = 100;
//        ProgressBar bar;
//        String[] list = {};
//
//        public DiyWebView(Context context) {
//            this(context, null);
//        }
//
//        public DiyWebView(Context context, AttributeSet attrs) {
//            super(context, attrs);
//            this.context = context;
//            init();
//        }
//
//        // 初始化一些设置
//        @SuppressLint("SetJavaScriptEnabled")
//        public void init() {
//            WebSettings settings = getSettings();
//            // javasrcipt
//            settings.setJavaScriptEnabled(true);
//            // 下面三個放大縮小
//            // settings.setUseWideViewPort(false);
//            // settings.setLoadWithOverviewMode(true);
//            // settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//            // settings.setLoadsImagesAutomatically(true);
//            settings.setDefaultFontSize(17);
//            settings.setJavaScriptCanOpenWindowsAutomatically(true);
//            settings.setDomStorageEnabled(true);
//            setVerticalScrollBarEnabled(false);
//            setVerticalScrollbarOverlay(false);
//            setHorizontalScrollBarEnabled(false);
//            setHorizontalScrollbarOverlay(false);
//            this.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
//            setWebChromeClient(new WebChromeClient());
//            setWebViewClient(new WebViewClient() {
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    super.onPageFinished(view, url);
//                    // html加载完成之后，添加监听图片的点击js函数
//                    loadUrl("javascript:android.resize(document.body.getBoundingClientRect().height)");
//                    addImageClickListner();
//                    invalidate();
//                }
//
//            });
////            addJavascriptInterface(this, "android");
//            // 获取屏幕宽度，设置图片显示最大宽度
//            DisplayMetrics dm = new DisplayMetrics();
//            dm = context.getResources().getDisplayMetrics();
//            maxWidth = px2dip(dm.widthPixels * 6 / 7);
//            // setupWebView();
//        }
//
//        // 将px转换为dp
//        public int px2dip(float pxValue) {
//            final float scale = context.getResources().getDisplayMetrics().density;
//            return (int) (pxValue / scale + 0.5f);
//        }
//
//        public void resize(final float height) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    setLayoutParams(new LinearLayout.LayoutParams(
//                            getResources().getDisplayMetrics().widthPixels,
//                            (int) (height * getResources().getDisplayMetrics().density)));
//                }
//            });
//        }
//
//        // 注入js函数监听
//        private void addImageClickListner() {
//            // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
//            this.loadUrl("javascript:(function(){"
//                    + "                var objs = document.getElementsByTagName(\"img\"); "
//                    + "                function click(a){this.clickPos=function(){ window.android.toshow(a);}}        "
//                    + "                        var arr=[];                        " + "                        for(var i=0;i<objs.length;i++){"
//                    + "            arr[i]= objs[i].src;        " + "                var col = new click(i); "
//                    + "            objs[i].onclick=col.clickPos;}"
//                    + "                 window.android.getList(arr)})()");
//        }
//
//        // + "    arr+= this.src;"
//        // window.android.getList(arr)
//        // 加载富文本数据
//        public void loadText(String text) {
//            loadDataWithBaseURL(null, text, "text/html", "utf-8", null);
//        }
//
//        // 重写富文本内容，给data加上属性文件
//        @Override
//        public void loadDataWithBaseURL(String baseUrl, String data,
//                                        String mimeType, String encoding, String failUrl) {
//            StringBuffer sb = new StringBuffer();
//            sb.append("<html><head>");
//            sb.append("\n");
//            sb.append("<style type=\"text/css\"> img {max-width:");
//            sb.append(maxWidth + "px; position:relative;} </style>");
//            sb.append("\n");
//            sb.append("</head>");
//            sb.append("\n");
//            sb.append("<body width=600px>");
//            sb.append("<div  style=\"color:#a1a1a1");
//            sb.append("\">");
//            sb.append(data);
//            sb.append("</div>");
//            sb.append("</body>");
//            sb.append("</html>");
//            super.loadDataWithBaseURL(baseUrl, sb.toString(), mimeType, encoding,
//                    failUrl);
//        };
//
//        public void toshow(int pos) {
//            Intent intent = new Intent(context, ShowImgListActivity.class);
//            intent.putExtra("position", pos);
//            intent.putExtra("fileUrl", list);
//            context.startActivity(intent);
//        }
//
//        // 获取到所有图片路径
//        public void getList(String[] str) {
//            list = str;
//        }
//
//        // public interface WebViewImageClick {
//        // public void show(String uri);
//        // }
//    }

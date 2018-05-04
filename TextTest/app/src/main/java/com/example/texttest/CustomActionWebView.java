package com.example.texttest;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * Created by 江婷婷 on 2018/5/4.
 */

public class CustomActionWebView extends WebView {
    public CustomActionWebView(Context context) {
        super(context);
    }
//
//    public CustomActionWebView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public CustomActionWebView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    @Override
//    public ActionMode startActionMode(ActionMode.Callback callback) {
//        return resolveActionMode(actionMode);
//    }
//
//    @Override
//    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback, int type) {
//        return resolveActionMode(actionMode);
//    }
//
//    private ActionMode resolveActionMode(ActionMode actionMode) {
//        if (actionMode != null) {
//            final Menu menu = actionMode.getMenu();
//            mActionMode = actionMode;
//            menu.clear();
//            for (int i = 0; i < mActionList.size(); i++) {
//                menu.add(mActionList.get(i));
//            }
//            for (int i = 0; i < menu.size(); i++) {
//                MenuItem menuItem = menu.getItem(i);
//                menuItem.setOnMenuItemClickListener(new Item.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        getSelectedData((String) item.getTitle());
//                        releaseAction();
//                        return true;
//                    }
//                });
//            }
//        }
//        mActionMode = actionMode;
//        return actionMode;
//    }
//
//    public void linkJSInterface() {
//        addJavascriptInterface(new ActionSelectInterface(this), "JSInterface");
//    }
//
//
//    /**
//     * js选中的回调接口
//     */
//    private class ActionSelectInterface {
//
//        CustomActionWebView mContext;
//
//        ActionSelectInterface(CustomActionWebView c) {
//            mContext = c;
//        }
//
//        @JavascriptInterface
//        public void callback(final String value, final String title) {
//            if(mActionSelectListener != null) {
//                mActionSelectListener.onClick(title, value);
//            }
//        }
//    }
//
//    /**
//     * 点击的时候，获取网页中选择的文本，回掉到原生中的js接口
//     * @param title 传入点击的item文本，一起通过js返回给原生接口
//     */
//    private void getSelectedData(String title) {
//
//        String js = "(function getSelectedText() {" +
//                "var txt;" +
//                "var title = \"" + title + "\";" +
//                "if (window.getSelection) {" +
//                "txt = window.getSelection().toString();" +
//                "} else if (window.document.getSelection) {" +
//                "txt = window.document.getSelection().toString();" +
//                "} else if (window.document.selection) {" +
//                "txt = window.document.selection.createRange().text;" +
//                "}" +
//                "JSInterface.callback(txt,title);" +
//                "})()";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            evaluateJavascript("javascript:" + js, null);
//        } else {
//            loadUrl("javascript:" + js);
//        }
//    }
}

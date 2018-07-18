package com.mygit.photoandcamera.populwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mygit.photoandcamera.ICommitWindow;
import com.mygit.photoandcamera.R;
import com.mygit.photoandcamera.utils.Utils;

/**
 * Created by admin on 2017/3/30.
 */

public class CommitWindow extends PopupWindow {

    private TextView tv_lins;
    private TextView tv_commit_title;
    private TextView tv_commit_explain;
    private LinearLayout lin_clear_succees;
    private LinearLayout lin_clear_cancirm;
    private TextView clear;
    private TextView not_clear;
    private Activity mContext;
    private int postion;
    private ICommitWindow iCommitWindow;

    public CommitWindow(Context context, String title, String explain, String noName, String yesName) {

        mContext = (Activity) context;

        View view = View.inflate(mContext,
                R.layout.view_commit_popuwindow, null);
        lin_clear_cancirm = (LinearLayout) view.findViewById(R.id.lin_clear_cancirm);

        tv_commit_title = (TextView) view.findViewById(R.id.tv_commit_title);
        tv_commit_explain = (TextView) view.findViewById(R.id.tv_commit_explain);
        not_clear = (TextView) view.findViewById(R.id.not_clear);
        clear = (TextView) view.findViewById(R.id.clear);
        tv_lins = (TextView) view.findViewById(R.id.tv_lins);

        if (Utils.notEmpty(title)) {
            tv_commit_title.setText(title);
            tv_commit_title.setVisibility(View.VISIBLE);
        } else {
            tv_commit_title.setVisibility(View.GONE);
        }
        if (Utils.notEmpty(explain)) {
            tv_commit_explain.setText(explain);
            tv_commit_explain.setVisibility(View.VISIBLE);
        } else {
            tv_commit_explain.setVisibility(View.GONE);
        }

        if (yesName != null) {
            clear.setText(yesName);
            clear.setVisibility(View.VISIBLE);
        } else {
            clear.setVisibility(View.GONE);
        }

        if (noName != null) {
            not_clear.setText(noName);
            not_clear.setVisibility(View.VISIBLE);
        } else {
            not_clear.setVisibility(View.GONE);
        }

        if (noName == null || yesName == null) {
            tv_lins.setVisibility(View.GONE);
        }

        lin_clear_succees = (LinearLayout) view.findViewById(R.id.lin_clear_succees);

        lin_clear_cancirm.setVisibility(View.VISIBLE);
        lin_clear_succees.setVisibility(View.GONE);

        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setContentView(view);
        not_clear.setOnClickListener(new MyOnClick());
        clear.setOnClickListener(new MyOnClick());
    }

    public CommitWindow(Context context, String title, int explain, String noName, String yesName) {

        mContext = (Activity) context;

        View view = View.inflate(mContext,
                R.layout.view_commit_popuwindow, null);
        lin_clear_cancirm = (LinearLayout) view.findViewById(R.id.lin_clear_cancirm);

        tv_commit_title = (TextView) view.findViewById(R.id.tv_commit_title);
        tv_commit_explain = (TextView) view.findViewById(R.id.tv_commit_explain);
        not_clear = (TextView) view.findViewById(R.id.not_clear);
        clear = (TextView) view.findViewById(R.id.clear);
        tv_lins = (TextView) view.findViewById(R.id.tv_lins);

        if (title != null) {
            tv_commit_title.setText(title);
            tv_commit_title.setVisibility(View.VISIBLE);
        } else {
            tv_commit_title.setVisibility(View.GONE);
        }
        if (explain != 0) {
            tv_commit_explain.setText(explain);
            tv_commit_explain.setVisibility(View.VISIBLE);
        } else {
            tv_commit_explain.setVisibility(View.GONE);
        }

        if (yesName != null) {
            clear.setText(yesName);
            clear.setVisibility(View.VISIBLE);
        } else {
            clear.setVisibility(View.GONE);
        }

        if (noName != null) {
            not_clear.setText(noName);
            not_clear.setVisibility(View.VISIBLE);
        } else {
            not_clear.setVisibility(View.GONE);
        }

        if (noName == null || yesName == null) {
            tv_lins.setVisibility(View.GONE);
        }

        lin_clear_succees = (LinearLayout) view.findViewById(R.id.lin_clear_succees);

        lin_clear_cancirm.setVisibility(View.VISIBLE);
        lin_clear_succees.setVisibility(View.GONE);

        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setContentView(view);
        not_clear.setOnClickListener(new MyOnClick());
        clear.setOnClickListener(new MyOnClick());
    }

    private ICommitWindow interFaceCommit;

    public void setInterFaceCommitWindow(ICommitWindow interFaceCommit) {
        this.iCommitWindow = interFaceCommit;
    }


    private class MyOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.clear:
                    iCommitWindow.setClassListner("yes", postion);
                    dismiss();
                    break;
                case R.id.not_clear:
                    iCommitWindow.setClassListner("no", 0);
                    dismiss();
                    break;
            }
        }
    }


    public void show(View parent) {
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    public void show(View parent, int postion) {
        if (parent == null) {
            return;
        }
        showAtLocation(parent, Gravity.CENTER, 0, 0);
        this.postion = postion;
    }

    public void show(View parent, String msg) {
        if (parent == null) {
            return;
        }
        if (msg != null)
            tv_commit_explain.setText(msg);
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


}

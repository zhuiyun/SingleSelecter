package com.jbstore.o2o.mylibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gwy on 2017/6/22.
 */

public class SingleSelector<T extends SelectString>{
    Activity mContext;
    Button sure, cancel;
    Dialog dialog;
    SingleSelectView selectView;
    List<T> str = new ArrayList<>();

    public SingleSelector(Activity mContext, List<T> str, int layId) {
        this.mContext = mContext;
        this.str = str;
        initView(layId);
    }

    private void initView(int layId) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.singselector, null);
        sure = (Button) view.findViewById(R.id.sure);
        cancel = (Button) view.findViewById(R.id.cancel);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int select = selectView.getSelect();
                if (select < str.size()) {
                    listener.select(str.get(select).getString(), select);
                    dismiss();
                }else{
                    Toast.makeText(mContext, "未选择项", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });
        selectView = (SingleSelectView) view.findViewById(R.id.myView);
        selectView.setData(str);
        dialog = new AlertDialog.Builder(mContext, R.style.Dialog_Fullscreen).setView(view).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setWindowAnimations(R.style.ShowDialog);
    }



    public void show() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                return;
            }
            dialog.show();
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.width = mContext.getWindowManager().getDefaultDisplay().getWidth();
            layoutParams.height = mContext.getWindowManager().getDefaultDisplay().getHeight() / 2;
            layoutParams.x = 0;
            layoutParams.y = mContext.getWindowManager().getDefaultDisplay().getHeight();
            dialog.getWindow().setAttributes(layoutParams);
        }
    }

    public void dismiss() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                Log.e("gao", "dismiss: ");
            } else {
                Log.e("gao", "dismiss111: ");
                return;
            }
        }
    }




 /*   @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:
                int select = selectView.getSelect();
                Log.e("gao", "onClick: " + select+str.size());
                if (select < str.size()) {
                    Log.e("gao", "onClick: ");
                    listener.select(str.get(select).getString(), select);
                }
                break;
            case R.id.cancel:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                break;
        }

    }*/

    public interface SelectListener {
        void select(String s, int index);
    }

    SelectListener listener;

    public void setListener(SelectListener listener) {
        this.listener = listener;
    }


}

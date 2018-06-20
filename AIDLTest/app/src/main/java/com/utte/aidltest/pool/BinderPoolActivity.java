package com.utte.aidltest.pool;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.utte.aidltest.R;

public class BinderPoolActivity extends AppCompatActivity {
    private static final String TAG = "BinderPoolActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        //另开线程运行，因为是耗时的
        new Thread(new Work()).start();
    }

    private class Work implements Runnable {
        @Override
        public void run() {
            //获取连接池对象
            BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
            ICombine combine = CombineImpl.asInterface(binderPool.queryBinder(BinderPool.BINDER_COMBINE));
            ICompute compute = ComputeImpl.asInterface(binderPool.queryBinder(BinderPool.BINDER_COMPUTE));
            try {
                String combineStr = combine.combine("jtt", "zsz");
                int computeInt = compute.add(1, 2);
                Log.d(TAG, "doWork: " + combineStr + "  " + computeInt);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}

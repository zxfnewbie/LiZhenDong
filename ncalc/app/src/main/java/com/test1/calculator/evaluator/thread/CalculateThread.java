package com.test1.calculator.evaluator.thread;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.test1.calculator.CalculatorPresenter;
import com.test1.calculator.evaluator.EvaluateConfig;
import com.test1.calculator.evaluator.MathEvaluator;
import com.google.common.collect.Lists;

import java.util.ArrayList;

import static android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;
import static android.os.Process.setThreadPriority;



public class CalculateThread extends BaseThread {

    public CalculateThread(CalculatorPresenter presenter, EvaluateConfig config,
                           ResultCallback resultCallback) {
        super(presenter, config, resultCallback);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(String expr) {
        execute(expr, mConfig);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(String expr, final EvaluateConfig config) {
        Command<ArrayList<String>, String> task = new Command<ArrayList<String>, String>() {
            @Override
            public ArrayList<String> execute(String input) {
                return Lists.newArrayList(MathEvaluator.getInstance().evaluateWithResultAsTex(input, config));
            }
        };

        //将其余部分传递给Thread
        Thread thread = new Thread(task, resultCallback);
        thread.executeOnExecutor(EXECUTOR, expr);
    }

    @Override
    public void execute(@Nullable Command<ArrayList<String>, String> command, String expr) {

        //将其余部分传递给Thread
        Thread thread = new Thread(command, resultCallback);
        thread.executeOnExecutor(EXECUTOR, expr);
    }


    /**
     * 所有繁重的工作负载微积分函数将使用的线程的泛化。
     */
    private static class Thread extends AsyncTask<String, Void, ArrayList<String>> {
        public Exception error; //如果发生任何异常
        private Command<ArrayList<String>, String> task;
        private ResultCallback resultCallback;


        public Thread(Command<ArrayList<String>, String> task,
                      ResultCallback resultCallback) {
            this.task = task;
            this.resultCallback = resultCallback;
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public final ArrayList<String> doInBackground(String... params) {
            try {
                setThreadPriority(THREAD_PRIORITY_MORE_FAVORABLE); //最高优先级
                return task.execute(params[0]);
            } catch (Exception e) {
                error = e;
            } catch (StackOverflowError e) {
                error = new IllegalArgumentException(e.getMessage());
            }
            return null;
        }

        @Override
        public void onPostExecute(ArrayList<String> result) {
            if (result == null) {
                resultCallback.onError(error);
            } else {
                resultCallback.onSuccess(result);
            }
        }
    }


}

package com.lid.redux.library.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

public class L {

    public static final boolean ENABLE_LOG = true;
    public static final String TAG_PREFIX = "";
    public static final String TAG_REMAIN = "";

    public enum LogType {
        Verbose, Debug, Info, Warn, Error
    }

    private static class Logger {

        // ------------------------------------------- auto tag
        public static void log(LogType type, StackTraceElement element, String msg) {
            _logString(type, generateTag(element), msg);
        }

        public static void log(LogType type, StackTraceElement element, Object msg) {
            _logString(type, generateTag(element), msg.toString());
        }

        public static void log(LogType type, StackTraceElement element, String[] msg) {
            for (String i : msg) {
                _logString(type, generateTag(element), i);
            }
        }

        public static <T> void log(LogType type, StackTraceElement element, List<T> msg) {
            for (T m : msg) {
                log(type, element, m);
            }
        }

        public static <K, V> void log(LogType type, StackTraceElement element, Map<K, V> msg) {
            for (K m : msg.keySet()) {
                log(type, element, "key=" + m.toString() + ",value=" + msg.get(m).toString());
            }
        }

        // ------------------------------------------- custom tag
        public static void log(LogType type, String tag, String msg) {
            _logString(type, tag, msg);
        }

        public static void log(LogType type, String tag, Object msg) {
            _logString(type, tag, msg.toString());
        }

        public static void log(LogType type, String tag, String[] msg) {
            for (String i : msg) {
                _logString(type, tag, i);
            }
        }

        public static <T> void log(LogType type, String tag, List<T> msg) {
            for (T m : msg) {
                log(type, tag, m);
            }
        }

        public static <K, V> void log(LogType type, String tag, Map<K, V> msg) {
            for (K m : msg.keySet()) {
                log(type, tag, msg.get(m));
            }
        }


        private static boolean _isCurrentlyOnMainThread() {
            return Looper.myLooper() == Looper.getMainLooper();
        }

        private static void _logString(LogType type, String tag, String msg) {

            if (!TextUtils.isEmpty(TAG_REMAIN))
            {
                if (!TAG_REMAIN.equals(tag))
                {
                    return;
                }
            }

            String thread = _isCurrentlyOnMainThread()? " (main thread) " : " (NOT main thread) ";
            msg = msg + thread;

            switch (type) {
                case Verbose:
                    Log.v(tag, msg);
                    break;
                case Debug:
                    Log.d(tag, msg);
                    break;
                case Info:
                    Log.i(tag, msg);
                    break;
                case Warn:
                    Log.w(tag, msg);
                    break;
                case Error:
                    Log.e(tag, msg);
                    break;
                default:
                    break;
            }
        }

        private static String generateTag(StackTraceElement caller) {
            String callerClazzName = caller.getClassName();
            callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
            // UniSoundVoice$UniSoundRecognizerListener
            callerClazzName = callerClazzName.substring(0, callerClazzName.contains("$") ? callerClazzName.indexOf("$"):callerClazzName.length());
            return TAG_PREFIX + callerClazzName;
        }
    }

    private L() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static StackTraceElement getStackTrace() {
        return Thread.currentThread().getStackTrace()[4];
    }

    // ----------------------------------------- auto log
    // ----------------------------------------- info
    public static void i(String msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Info, getStackTrace(), msg);
        }
    }

    public static void i(String[] msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Info, getStackTrace(), msg);
        }
    }

    public static void i(Object msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Info, getStackTrace(), msg);
        }
    }

    public static<T> void i(List<T> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Info, getStackTrace(), msg);
        }
    }

    public static<K, V> void i(Map<K, V> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Info, getStackTrace(), msg);
        }
    }

    // ----------------------------------------- Verbose
    public static void v(String msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Verbose, getStackTrace(), msg);
        }
    }

    public static void v(String[] msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Verbose, getStackTrace(), msg);
        }
    }

    public static void v(Object msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Verbose, getStackTrace(), msg);
        }
    }

    public static<T> void v(List<T> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Verbose, getStackTrace(), msg);
        }
    }

    public static<K, V> void v(Map<K, V> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Verbose, getStackTrace(), msg);
        }
    }

    // ----------------------------------------- Debug
    public static void d(String msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Debug, getStackTrace(), msg);
        }
    }

    public static void d(String[] msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Debug, getStackTrace(), msg);
        }
    }

    public static void d(Object msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Debug, getStackTrace(), msg);
        }
    }

    public static<T> void d(List<T> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Debug, getStackTrace(), msg);
        }
    }

    public static<K, V> void d(Map<K, V> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Debug, getStackTrace(), msg);
        }
    }


    // ----------------------------------------- Warn
    public static void w(String msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Warn, getStackTrace(), msg);
        }
    }

    public static void w(String[] msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Warn, getStackTrace(), msg);
        }
    }

    public static void w(Object msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Warn, getStackTrace(), msg);
        }
    }

    public static<T> void w(List<T> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Warn, getStackTrace(), msg);
        }
    }

    public static<K, V> void w(Map<K, V> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Warn, getStackTrace(), msg);
        }
    }


    // ----------------------------------------- Error
    public static void e(String msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Error, getStackTrace(), msg);
        }
    }

    public static void e(String[] msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Error, getStackTrace(), msg);
        }
    }

    public static void e(Object msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Error, getStackTrace(), msg);
        }
    }

    public static<T> void e(List<T> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Error, getStackTrace(), msg);
        }
    }

    public static<K, V> void e(Map<K, V> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Error, getStackTrace(), msg);
        }
    }








    // -------------------------------------------------------- custom tag
    // ----------------------------------------- info
    public static void i(String tag, String msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Info, TAG_PREFIX + tag, msg);
        }
    }

    public static void i(String tag, String[] msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Info, TAG_PREFIX + tag, msg);
        }
    }

    public static void i(String tag, Object msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Info, TAG_PREFIX + tag, msg);
        }
    }

    public static<T> void i(String tag, List<T> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Info, TAG_PREFIX + tag, msg);
        }
    }

    public static<K, V> void i(String tag, Map<K, V> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Info, TAG_PREFIX + tag, msg);
        }
    }

    // ----------------------------------------- Verbose
    public static void v(String tag, String msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Verbose, TAG_PREFIX + tag, msg);
        }
    }

    public static void v(String tag, String[] msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Verbose, TAG_PREFIX + tag, msg);
        }
    }

    public static void v(String tag, Object msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Verbose, TAG_PREFIX + tag, msg);
        }
    }

    public static<T> void v(String tag, List<T> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Verbose, TAG_PREFIX + tag, msg);
        }
    }

    public static<K, V> void v(String tag, Map<K, V> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Verbose, TAG_PREFIX + tag, msg);
        }
    }



    // ----------------------------------------- Debug
    public static void d(String tag, String msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Debug, TAG_PREFIX + tag, msg);
        }
    }

    public static void d(String tag, String[] msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Debug, TAG_PREFIX + tag, msg);
        }
    }

    public static void d(String tag, Object msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Debug, TAG_PREFIX + tag, msg);
        }
    }

    public static<T> void d(String tag, List<T> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Debug, TAG_PREFIX + tag, msg);
        }
    }

    public static<K, V> void d(String tag, Map<K, V> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Debug, TAG_PREFIX + tag, msg);
        }
    }


    // ----------------------------------------- Warn
    public static void w(String tag, String msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Warn, TAG_PREFIX + tag, msg);
        }
    }

    public static void w(String tag, String[] msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Warn, TAG_PREFIX + tag, msg);
        }
    }

    public static void w(String tag, Object msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Warn, TAG_PREFIX + tag, msg);
        }
    }

    public static<T> void w(String tag, List<T> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Warn, TAG_PREFIX + tag, msg);
        }
    }

    public static<K, V> void w(String tag, Map<K, V> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Warn, TAG_PREFIX + tag, msg);
        }
    }


    // ----------------------------------------- Error
    public static void e(String tag, String msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Error, TAG_PREFIX + tag, msg);
        }
    }

    public static void e(String tag, String[] msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Error, TAG_PREFIX + tag, msg);
        }
    }

    public static void e(String tag, Object msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Error, TAG_PREFIX + tag, msg);
        }
    }

    public static<T> void e(String tag, List<T> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Error, TAG_PREFIX + tag, msg);
        }
    }

    public static<K, V> void e(String tag, Map<K, V> msg) {
        if (ENABLE_LOG) {
            Logger.log(LogType.Error, TAG_PREFIX + tag, msg);
        }
    }


    private static class TestObject
    {

        public String title;
        public String desc;

        public TestObject(String title, String desc) {
            this.title = title;
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "TestObject{" +
                    "title='" + title + '\'' +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }

    public static void test()
    {
        L.i("-------- no tag log test begin");
        L.i("this is a no tag log");

        L.i("-------- test string[]");
        String[] str = { "1", "2", "3", "4", "5", "6", "7" };
        L.i(str);

        L.i("-------- test List<String>");
        List<String> stringList = new ArrayList<String>();
        for (String i:str)
        {
            stringList.add(i);
        }
        L.i(stringList);

        L.i("-------- test object");
        L.i(new TestObject("title", "desc"));

        L.i("-------- test List<object>");
        List<TestObject> objectList = new ArrayList<TestObject>();
        for (String i:str)
        {
            objectList.add(new TestObject("title"+i,"desc"+i));
        }
        L.i(objectList);

        L.i("-------- test Map<key,object>");
        Map<String,TestObject> testObjectMap = new HashMap<String,TestObject>();
        for (String i:str)
        {
            testObjectMap.put(i,new TestObject("title"+i,"desc"+i));
        }
        L.i(testObjectMap);

        L.i("-------- no tag log test end");

        L.i("lll", "-------- log test begin");
    }


}
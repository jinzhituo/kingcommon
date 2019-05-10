package com.zhuanbang.kingcommonlib.db.core;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.zhuanbang.kingcommonlib.base.BaseApplication;
import com.zhuanbang.kingcommonlib.config.Constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：ZhengQunWei on 2017/9/29 09:48
 */
public class DaoManagerFactory {
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private String path;
    private SQLiteDatabase mSQLiteDatabase;
    private static DaoManagerFactory instance;
    private Map<String, Session<?>> cache = new HashMap<>();


    private DaoManagerFactory(File file) {
        this.path = file.getAbsolutePath();
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdir();
        }
        getDateBase();
//        if (!file.exists())
        executeAssetsSQL(mSQLiteDatabase, Constant.DB_PATH);
    }

    public synchronized <T> Session<T> getDataHelper(Class<T> clazz) {
        String clazzName = clazz.getName();
        synchronized (this) {
            try {
                Session<T> writer = (Session<T>) cache.get(clazzName);
                if (null == writer) {
                    writer = new Session(clazz, mSQLiteDatabase);
                    cache.put(clazzName, writer);
                }
                return writer;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static synchronized DaoManagerFactory getInstance() {
        if (instance == null) {
            instance = new DaoManagerFactory(new File(Environment.getExternalStorageDirectory(), Constant.DB_NAME));
        }
        return instance;
    }


    /**
     * 读取数据库文件（.sql），并执行sql语句
     */
    private void executeAssetsSQL(SQLiteDatabase db, String schemaName) {
        BufferedReader in = null;
        try {
//            in = new BufferedReader(new InputStreamReader(BaseApplication.getAppContext().getAssets().open
//                    (schemaName)));

            System.out.println("路径:" + schemaName);
            String line;
            String buffer = "";
            while ((line = in.readLine()) != null) {
                buffer += line;
            }
            String[] sqls = buffer.split(";");

            for (String sql : sqls) {
                if (sql.contains("insert")) {
                    //大于490处理
                    int indexOf = sql.indexOf(" values ");
                    if (indexOf > 0) {
                        String subSql1 = sql.substring(0, indexOf);
                        String subSql2 = sql.substring(indexOf + " values ".length(), sql.length());

                        String[] array = subSql2.split("\\),\\(");
                        if (array.length > 490) {

                            // 490- 499 /2 -1 1
                            //

                            int count = ((array.length - 1) / 490) + 1;
                            for (int i = 0; i < count; i++) {
                                //array[iv]  - array[iv*490]
                                String currentSql = subSql1 + " values ";
                                for (int j = i * 490; j < (i + 1) * 490; j++) {
                                    if (j < array.length) {
                                        String ar = array[j];
                                        ar = ar.startsWith("(") ? ar.substring(1, ar.length()) : ar;
                                        ar = ar.endsWith(";") ? ar.substring(0, ar.length() - 1) : ar;
                                        ar = ar.endsWith(")") ? ar.substring(0, ar.length() - 1) : ar;


                                        currentSql += "(" + ar + "),";
                                    }

                                }

                                currentSql = currentSql.substring(0, currentSql.length() - 1) + ";";

                                db.execSQL(currentSql);
                            }
                        } else {
                            db.execSQL(sql);
                        }

                    }
                } else {
                    db.execSQL(sql);
                }

            }
        } catch (IOException e) {
            Log.e("db-error", e.toString());
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                Log.e("db-error", e.toString());
            }
        }
    }


    /**
     * 打开数据库
     */

    private synchronized void getDateBase() {
        if (mOpenCounter.incrementAndGet() == 1) {

            this.mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(path, null);
        }
    }

    public synchronized void closeDataBase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            mSQLiteDatabase.close();

        }
    }
}

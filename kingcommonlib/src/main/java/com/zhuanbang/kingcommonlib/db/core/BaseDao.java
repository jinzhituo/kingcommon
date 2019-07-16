package com.zhuanbang.kingcommonlib.db.core;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseDao<T> implements IBaseDao<T> {
    private SQLiteDatabase mSQLiteDatabase;
    private boolean isInit = false;
    protected String tableName;
    private Class<T> entityClass;
    private Map<String, Field> mFiledMap;

    public synchronized void init(Class<T> entity, SQLiteDatabase sqLiteDatabase) {
        if (!isInit) {
            this.mSQLiteDatabase = sqLiteDatabase;
            this.tableName = entity.getAnnotation(DBTable.class).value();
            this.entityClass = entity;
            mSQLiteDatabase.execSQL(createDataBase());
            mFiledMap = new HashMap<>();
            initCacheMap();
        }
    }

    public synchronized Long insert(T entity) {
        Map<String, String> map = getValues(entity);
        ContentValues contentValues = getContentValues(map);
        mSQLiteDatabase.beginTransaction();
        Long id = mSQLiteDatabase.insert(tableName, null, contentValues);
        mSQLiteDatabase.endTransaction();
        return id;
    }

    public synchronized int update(T entity, T where) {
        Map<String, String> map = getValues(entity);
        Map<String, String> args = getValues(where);
        Condition condition = new Condition(args);
        int result = -1;
        result = mSQLiteDatabase.update(tableName, getContentValues(map), condition.getWhereClause(), condition
                .getWhereArgs());

        return result;
    }

    public synchronized List<T> queryList(T where, int pageSize, int pageNum) {
        List<T> data = null;
        Map<String, String> map = getValues(where);
        Condition condition = new Condition(map);
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + tableName + " where " + condition.getWhereClause
                () + " order by id " + " limit " + pageSize * (pageNum - 1) + "," + pageSize, condition.getWhereArgs());
        try {
            if (cursor.getCount() > 0) {
                data = new ArrayList<T>();
                while (cursor.moveToNext()) {
                    T object = (T) where.getClass().newInstance();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        String columnName = cursor.getColumnName(i);
                        int type = cursor.getType(i);
                        if (type == Cursor.FIELD_TYPE_BLOB) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getBlob(i));
                        } else if (type == Cursor.FIELD_TYPE_FLOAT) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getFloat(i));
                        } else if (type == Cursor.FIELD_TYPE_INTEGER) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getInt(i));
                        } else if (type == Cursor.FIELD_TYPE_STRING) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getString(i));
                        }

                    }
                    data.add(object);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return data;
    }

    public synchronized List<T> queryList(T clazz, String where, int pageSize, int pageNum) {
        List<T> data = null;
        Map<String, String> map = getValues(clazz);
        Condition condition = new Condition(map);
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + tableName + " where " + where + " order by id " +
                " limit " + pageSize * (pageNum - 1) + "," + pageSize, new String[]{});
        try {
            if (cursor.getCount() > 0) {
                data = new ArrayList<T>();
                while (cursor.moveToNext()) {
                    T object = (T) clazz.getClass().newInstance();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        String columnName = cursor.getColumnName(i);
                        int type = cursor.getType(i);
                        if (type == Cursor.FIELD_TYPE_BLOB) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getBlob(i));
                        } else if (type == Cursor.FIELD_TYPE_FLOAT) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getFloat(i));
                        } else if (type == Cursor.FIELD_TYPE_INTEGER) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getInt(i));
                        } else if (type == Cursor.FIELD_TYPE_STRING) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getString(i));
                        }

                    }
                    data.add(object);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return data;
    }

    public synchronized T queryObject(T where) {
        T object = null;
        Map<String, String> map = getValues(where);
        Condition condition = new Condition(map);
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + tableName + " where " + condition.getWhereClause
                (), condition.getWhereArgs());
        try {
            object = (T) where.getClass().newInstance();
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        String columnName = cursor.getColumnName(i);
                        int type = cursor.getType(i);
                        if (type == Cursor.FIELD_TYPE_BLOB) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getBlob(i));
                        } else if (type == Cursor.FIELD_TYPE_FLOAT) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getFloat(i));
                        } else if (type == Cursor.FIELD_TYPE_INTEGER) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getInt(i));
                        } else if (type == Cursor.FIELD_TYPE_STRING) {
                            setter(object, mFiledMap.get(columnName).getName(), cursor.getString(i));
                        }


                    }

                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return object;
    }

    public void deleteAll() {

    }

    public void deleteObject(T where) {

    }

    private void setter(Object obj, String toSet, Object value) {
        Method target = null;
        Method get = null;
        try {
            get = obj.getClass().getMethod("get" + upperFirst(toSet));
            Class<?> c = get.getReturnType();
            target = obj.getClass().getMethod("set" + upperFirst(toSet), c);
            if (c.getName().equals("java.lang.Long")) target.invoke(obj, Long.parseLong(value.toString()));
            else target.invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String upperFirst(String toUpper) {
        return toUpper.substring(0, 1).toUpperCase() + toUpper.substring(1);
    }

    private Map<String, String> getValues(T entity) {
        HashMap<String, String> result = new HashMap<>();
        Iterator fieldsIterator = mFiledMap.values().iterator();
        while (fieldsIterator.hasNext()) {
            Field columnToField = (Field) fieldsIterator.next();
            String cacheKey = null;
            String cacheValue = null;
            if (columnToField.getAnnotation(DBFiled.class) != null) {
                cacheKey = columnToField.getAnnotation(DBFiled.class).value();
            } else {
                cacheKey = columnToField.getName();
            }
            try {
                if (null == columnToField.get(entity)) {
                    continue;
                }
                cacheValue = columnToField.get(entity).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            result.put(cacheKey, cacheValue);
        }
        return result;
    }

    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValue = new ContentValues();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            String value = map.get(key);
            if (null != value) {
                contentValue.put(key, value);
            }
        }
        return contentValue;
    }

    protected void initCacheMap() {
        String sqlite = "select * from " + this.tableName + " limit 1,0";
        String sql = "select * from " + this.tableName + " limit 1,0";
        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(sql, null);
            String[] columnNames = cursor.getColumnNames();
            Field[] columnFields = entityClass.getDeclaredFields();
            for (Field field : columnFields) {
                field.setAccessible(true);
            }
            for (String columnName : columnNames) {
                for (Field field : columnFields) {
                    String fieldName;
                    if (field.getAnnotation(DBFiled.class) != null) {
                        fieldName = field.getAnnotation(DBFiled.class).value();
                    } else {
                        fieldName = field.getName();
                    }
                    if (columnName.equals(fieldName)) {
                        mFiledMap.put(columnName, field);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public abstract String createDataBase();

    class Condition {
        private String whereClause;
        private String[] whereArgs;

        public Condition(Map<String, String> map) {
            ArrayList list = new ArrayList();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" 1=1 ");
            Set set = map.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = map.get(key);
                if (value != null) {
                    stringBuilder.append(" and " + key + "=? ");
                    list.add(value);
                }
            }
            this.whereClause = stringBuilder.toString();
            this.whereArgs = (String[]) list.toArray(new String[list.size()]);
        }

        public String getWhereClause() {
            return whereClause;
        }

        public void setWhereClause(String whereClause) {
            this.whereClause = whereClause;
        }

        public String[] getWhereArgs() {
            return whereArgs;
        }

        public void setWhereArgs(String[] whereArgs) {
            this.whereArgs = whereArgs;
        }
    }
}

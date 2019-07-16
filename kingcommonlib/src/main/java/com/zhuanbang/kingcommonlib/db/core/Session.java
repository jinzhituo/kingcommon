package com.zhuanbang.kingcommonlib.db.core;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Session<T> implements IBaseDao<T> {
    private SQLiteDatabase mSQLiteDatabase;
    private boolean isInit = false;
    protected String tableName;
    private Class<T> entityClass;
    private Map<String, Field> mFiledMap;
    String primaryKey;

    public Session(Class<T> entity, SQLiteDatabase sqLiteDatabase) {
        if (!isInit) {
            this.mSQLiteDatabase = sqLiteDatabase;
            this.tableName = entity.getAnnotation(DBTable.class).value();
            this.entityClass = entity;
            mSQLiteDatabase.execSQL(createTable());
            mFiledMap = new HashMap<>();
            initCacheMap();
        }
    }

    public synchronized Long insert(T entity) {
        Map<String, String> map = getValues(entity);
        ContentValues contentValues = getContentValues(map);
        Long id = mSQLiteDatabase.insert(tableName, null, contentValues);
        return id;
    }

    public synchronized int update(T entity, T where) {
        Map<String, String> map = getValues(entity);
        Map<String, String> args = getValues(where);
        Session.Condition condition = new Session.Condition(args);
        int result = -1;
        result = mSQLiteDatabase.update(tableName, getContentValues(map), condition.getWhereClause(), condition
                .getWhereArgs());

        return result;
    }

    public synchronized int update(T entity, String sqlWhere, String[] args) {
        Map<String, String> map = getValues(entity);
        int result = -1;
        result = mSQLiteDatabase.update(tableName, getContentValues(map), sqlWhere, args);
        return result;
    }

    public synchronized List<T> queryList(T where, int pageSize, int pageNum) {
        List<T> data = null;
        Map<String, String> map = getValues(where);
        Session.Condition condition = new Session.Condition(map);
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + tableName + " where " + condition.getWhereClause
                () + " order by  " + primaryKey + " limit " + pageSize * (pageNum - 1) + "," + pageSize, condition
                .getWhereArgs());
        try {
            if (cursor.getCount() > 0) {
                data = new ArrayList<T>();
                while (cursor.moveToNext()) {
                    T object = (T) where.getClass().newInstance();

                    data.add(formatData(cursor, object));
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

    public synchronized List<T> queryList(Class<T> clazz, String where, int pageSize, int pageNum) {
        List<T> data = null;
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + tableName + " where " + where + " order by id " +
                " limit " + pageSize * (pageNum - 1) + "," + pageSize, new String[]{});
        try {
            if (cursor.getCount() > 0) {
                data = new ArrayList<T>();
                while (cursor.moveToNext()) {
                    T object = (T) clazz.newInstance();
                    data.add(formatData(cursor, object));
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

    public synchronized T queryObject(Class<T> clazz, String where) {
        T object = null;
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + tableName + " where " + where, new String[]{});
        try {
            object = clazz.newInstance();
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    object = formatData(cursor, object);
                }
            } else {
                return null;
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
        return object;
    }

    public synchronized T queryObject(T where) {
        T object = null;
        Map<String, String> map = getValues(where);
        Session.Condition condition = new Session.Condition(map);
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + tableName + " where " + condition.getWhereClause
                (), condition.getWhereArgs());
        try {
            object = (T) where.getClass().newInstance();
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    object = formatData(cursor, object);
                }
            } else {
                return null;
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
        return object;
    }

    private T formatData(Cursor cursor, T object) {
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
        return object;
    }


    public void deleteAll() {
        mSQLiteDatabase.execSQL("delete from " + tableName);
    }

    public void deleteObject(T where) {
        Map<String, String> map = getValues(where);
        Session.Condition condition = new Session.Condition(map);
        mSQLiteDatabase.delete(tableName, condition.whereClause, condition.whereArgs);
    }

    @Override
    public String createTable() {
        StringBuilder stringBuilder = new StringBuilder(" CREATE TABLE IF NOT EXISTS ");
        stringBuilder.append(tableName);
        stringBuilder.append(" ( ");
        Field[] columnFields = entityClass.getDeclaredFields();
        for (Field field : columnFields) {
            field.setAccessible(true);
        }
        for (Field field : columnFields) {
            //列属性
            if (field.getAnnotation(DBFiled.class) != null) {
                String type = field.getGenericType().toString();
                stringBuilder.append(" ");
                stringBuilder.append(field.getAnnotation(DBFiled.class).value());
                stringBuilder.append(" ");
                if (type.equals("class " + String.class.getName())) {
                    stringBuilder.append(" text ,");
                } else if (type.equals("class " + Integer.class.getName())) {
                    stringBuilder.append(" Integer ,");
                } else if (type.equals("class " + Long.class.getName())) {
                    stringBuilder.append(" Long ,");
                } else if (type.equals("class " + Boolean.class.getName())) {
                    stringBuilder.append(" BLOB ,");
                }
            }
            //主键
            if (field.getAnnotation(DBPrimaryKey.class) != null) {
                String keyName = field.getAnnotation(DBPrimaryKey.class).value();
                stringBuilder.append(" ");
                stringBuilder.append(keyName);
                stringBuilder.append(" Long PRIMARY KEY NOT NULL ,");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append(" );");
        Log.d("createTable--->", stringBuilder.toString());
        return stringBuilder.toString();
    }

    private void setter(Object obj, String toSet, Object value) {
        Method target = null;
        Method get = null;
        try {
            get = obj.getClass().getMethod("get" + upperFirst(toSet));
            Class<?> c = get.getReturnType();
            target = obj.getClass().getMethod("set" + upperFirst(toSet), c);
            if (c.getName().equals("java.lang.Long"))
                target.invoke(obj, Long.parseLong(value.toString()));
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
            } else if (columnToField.getAnnotation(DBPrimaryKey.class) != null) {
                cacheKey = columnToField.getAnnotation(DBPrimaryKey.class).value();
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
                    } else if (field.getAnnotation(DBPrimaryKey.class) != null) {
                        fieldName = field.getAnnotation(DBPrimaryKey.class).value();
                        primaryKey = fieldName;
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

package ru.galaxy773.multiplatform.api.utils.reflection;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

@SuppressWarnings("unchecked")
@UtilityClass
public class ReflectionUtil {
	
	public Object getValue(Object object, String name) {
		try {
			Field field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object getValue(Class<?> objectClass, Object object, String name) {
		try {
			Field field = objectClass.getDeclaredField(name);
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object getStaticValue(Class<?> clazz, String name) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Field getField(Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setStaticValue(Class<?> clazz, String fieldName, Object value) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(null, value);
			field.setAccessible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void setFieldValue(Object object, String fieldName, Object value) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(object, value);
			field.setAccessible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void setFieldValue(Class<?> objectClass, Object object, String fieldName, Object value) {
		try {
			Field field = objectClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(object, value);
			field.setAccessible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public <T> T getFieldValue(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return (T) field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T> T getFieldValue(Class<?> objectClass, Object object, String fieldName) {
		try {
			Field field = objectClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			return (T) field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//вверху аналогично
	//хз, достаточно ли тут будет getClass при наследовании
	public Object getMethodReturn(Class<?> clazz, String methodName, Object... parameters) {
		try {
			Method method = clazz.getMethod(methodName, Arrays.stream(parameters).map(Object::getClass).toArray(Class[]::new));
			method.setAccessible(true);
			return method.invoke(clazz, parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//поэтому на всякий случай так
	public Object getMethodReturn(Class<?> classObject, Object object, String methodName, Object... parameters) {
		try {
			Method method = classObject.getMethod(methodName,
					Arrays.stream(parameters).map(Object::getClass).toArray(Class[]::new));
			method.setAccessible(true);
			return method.invoke(object, parameters);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public <T> Class<? extends T[]> getArrayClass(Class<T> clazz) {
		return (Class<? extends T[]>) Array.newInstance(clazz, 0).getClass();
	}
}

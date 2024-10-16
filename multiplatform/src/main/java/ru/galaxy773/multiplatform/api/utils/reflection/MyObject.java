package ru.galaxy773.multiplatform.api.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Logger;

@SuppressWarnings("unchecked")
public class MyObject {

	private Object object;
	private Class<?> clazz;

	public MyObject(Object object) {
		this.object = object;
		this.clazz = object.getClass();
	}

	public MyObject(Class<?> clazz) {
		this.object = null;
		this.clazz = clazz;
	}

	public static MyObject wrap(Object player) {
		return new MyObject(player);
	}

	public static MyObject wrap(Class<?> clazz) {
		return new MyObject(clazz);
	}
    
	public MyObject getField(String name) {
		try {
			Field field = null;
			Class<?> c = this.clazz;
			do {
				try {
					field = c.getDeclaredField(name);
				} catch(NoSuchFieldException ignored) {
				} catch(Exception e) {
					e.printStackTrace();
				}
				if(field != null)
					break;

			}
			while((c = c.getSuperclass()) != null);

			if(field == null) { throw new NoSuchFieldException(name); }

			boolean isSetAccessible = false;
			if(!field.isAccessible()) {
				field.setAccessible(true);
				isSetAccessible = true;
			}
			Object get = field.get(this.object);
			if(isSetAccessible)
				field.setAccessible(false);

			return get == null ? null : new MyObject(get);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setField(String name, Object value) {
		try {
			Class<?> c = this.clazz;
			Field field = null;
			do {
				try {
					field = c.getDeclaredField(name);
				} catch(Exception ignored) {}
				if(field != null) {
					break;
				}
			}
			while((c = c.getSuperclass()) != null);
			boolean isSetAccessible = false;
			if(!field.isAccessible()) {
				field.setAccessible(true);
				isSetAccessible = true;
			}
			field.set(this.object, value instanceof MyObject ? ((MyObject) value).getObject() : value);
			if(isSetAccessible) {
				field.setAccessible(false);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public MyObject invokeMethod(String name, Object... args) {
		try {

			Method method = null;
			this.fixArgs(args);

			Class<?> c = this.clazz;
			one:
			do {
				two:
				for(Method m : c.getDeclaredMethods()) {
					if(!m.getName().equals(name))
						continue;
					if(m.getParameterCount() != args.length)
						continue;
					for(int i = 0; i < m.getParameterCount(); i++)
						if(args[i] != null && !m.getParameterTypes()[i].isInstance(args[i]))
								continue two;
					method = m;
					break one;
				}
			}
			while((c = c.getSuperclass()) != null);

			if(method == null)
				throw new NullPointerException("Method for MyObject is null");

			boolean isSetAccessible = false;
			if(!method.isAccessible()) {
				method.setAccessible(true);
				isSetAccessible = true;
			}
			Object returnObject = method.invoke(this.object, args);
			if(isSetAccessible)
				method.setAccessible(false);

			return returnObject == null ? null : new MyObject(returnObject);
		} catch(Exception e) {
			Logger.getGlobal().warning("Error in: " + this.getObject().getClass());
			e.printStackTrace();
			return null;
		}
	}
	
	public <T> T getObject(Class<T> cast) {
		return (T) object;
	}

	public <T> T getObject() {
		return (T) object;
	}

	private void fixArgs(Object[] args) {
		for(int i = 0; i < args.length; i++)
			if(args[i] instanceof MyObject)
				args[i] = ((MyObject) args[i]).getObject();
	}
}
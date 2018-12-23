package com.blackwaterpragmatic.test;

import org.mockito.Mock;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MockHelper {

	public static Object[] allDeclaredMocks(final Object test) {

		final Field[] declaredFields = test.getClass().getDeclaredFields();

		final ArrayList<Object> mockList = new ArrayList<>();

		for (final Field field : declaredFields) {
			if (null != field.getAnnotation(Mock.class)) {
				field.setAccessible(true);
				try {
					mockList.add(field.get(test));
				} catch (final IllegalAccessException e) {
					throw new RuntimeException(e.toString(), e);
				}
			}
		}

		return mockList.toArray();

	}

}

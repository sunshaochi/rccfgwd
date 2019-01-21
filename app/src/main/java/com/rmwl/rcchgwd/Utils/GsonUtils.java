package com.rmwl.rcchgwd.Utils;




import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;


/**
 * json工具类
 * 
 * 
 */
public class GsonUtils {

	private static Gson gson;
	static {
		if (gson == null) {
			gson = new Gson();
		}
	}

	/**
	 * 转成json
	 *
	 * @param object
	 * @return
	 */
	public static String bean2Json(Object object) {
		String gsonString = null;
		if (gson != null) {
			gsonString = gson.toJson(object);
		}
		return gsonString;
	}

	/**
	 * 转成bean
	 *
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> T gsonToBean(String gsonString, Class<T> cls) {
		T t = null;
		if (gson != null) {
			t = gson.fromJson(gsonString, cls);
		}
		return t;
	}

	/**
	 * 转成list
	 *
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T>List<T> gsonToList(String gsonString, Class<T> cls) {
		List<T> list=null;
		list.clear();
		if (gson != null) {
			list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
			}.getType());
		}
		return list;
	}

	/**
	 * 转成list中有map的
	 *
	 * @param gsonString
	 * @return
	 */
	public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
		List<Map<String, T>> list = null;
		if (gson != null) {
			list = gson.fromJson(gsonString,
					new TypeToken<List<Map<String, T>>>() {
					}.getType());
		}
		return list;
	}

	/**
	 * 转成map的
	 *
	 * @param gsonString
	 * @return
	 */
	public static <T> Map<String, T> gsonToMaps(String gsonString) {
		Map<String, T> map = null;
		if (gson != null) {
			map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
			}.getType());
		}
		return map;
	}
}

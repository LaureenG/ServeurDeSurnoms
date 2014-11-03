package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sds.ErrorCode;

public class Data {
	private class Entry {
		public String key;
		public String value;

		public Entry(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}

	private static Data instance;
	private List<Entry> associations;

	public Data() {
		this.associations = new ArrayList<Entry>();
	}

	public static Data getInstance() {
		if (instance == null) {
			instance = new Data();
		}
		return instance;
	}

	private boolean nameExists(String name) {
		for (Entry e : this.associations) {
			if (e.key.equals(name)) {
				return true;
			}
		}
		return false;
	}

	private boolean surnameExists(String surname) {
		for (Entry e : this.associations) {
			if (e.value.equals(surname)) {
				return true;
			}
		}
		return false;
	}

	public int putSurname(String name, String surname) {
		if (surnameExists(surname)) {
			return ErrorCode.BAD_REQUEST;
		}
		this.associations.add(new Entry(name, surname));
		return ErrorCode.OK;
	}

	public int postName(String name, String newName) {
		int toRet = ErrorCode.NOT_FOUND;
		for (Entry e : this.associations) {
			if (e.key.equals(name)) {
				e.key = newName;
				toRet = ErrorCode.OK;
			}
		}
		return toRet;
	}

	public int postSurname(String surname, String newSurname) {
		int toRet = ErrorCode.NOT_FOUND;
		for (Entry e : this.associations) {
			if (e.value.equals(surname)) {
				e.value = newSurname;
				toRet = ErrorCode.OK;
			}
		}
		return toRet;
	}

	public int getAll(HashMap<String, List<String>> map) {
		Set<String> nom = new HashSet<String>();
		getName(nom);
		List<String> l = new ArrayList<String>(nom);
		for (String n : l) {
			List<String> ll = new ArrayList<String>();
			for (Entry e : this.associations) {
				if (e.key.equals(n)) {
					ll.add(e.value);
				}
			}
			map.put(n, ll);
		}
		return ErrorCode.OK;
	}

	public int getName(Set<String> set) {
		for (Entry e : this.associations) {
			set.add(e.key);
		}
		return ErrorCode.OK;
	}

	public int getSurname(Set<String> set, String name) {
		if (name != null) {
			for (Entry e : this.associations) {
				if (e.key.equals(name)) {
					set.add(e.value);
				}
			}
			return ErrorCode.OK;
		} else {
			for (Entry e : this.associations) {
				set.add(e.value);
			}
			return ErrorCode.OK;
		}
	}

	public int deleteSurname(String surname) {
		for (int i = 0; i < this.associations.size(); i++) {
			if (this.associations.get(i).value.equals(surname)) {
				this.associations.remove(i);
				return ErrorCode.OK;
			}
		}
		return ErrorCode.BAD_REQUEST;
	}

	public int deleteName(String name) {
		if (!nameExists(name)) {
			return ErrorCode.BAD_REQUEST;
		}
		for (int i = 0; i < this.associations.size(); i++) {
			if (this.associations.get(i).key.equals(name)) {
				this.associations.remove(i);
			}
		}
		return ErrorCode.OK;
	}
}

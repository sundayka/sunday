package startup;

import java.util.UUID;

public class NoteUtil {
	
	public static String createId() {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		return id;
	}
	
	public static void main(String[] args) {
		System.out.println(createId());
	}

}

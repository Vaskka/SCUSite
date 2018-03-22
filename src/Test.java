import com.vaskka.spiderscu.CourseTable;
import com.vaskka.spiderscu.Lession;
import com.vaskka.spiderscu.Spider;


public class Test {
	private static void print(String s) {
		System.out.println(s);
	}
	
	public static void main(String argc[]) {
		Spider testSpider = new Spider("2017141463062", "160518");
		
		CourseTable table = testSpider.getCourseTable();
		
		for (Lession l : table.getMonday()) {
			print(l.getLessionNum());
			print(l.getName());
			print(l.getIndex());
			print(l.getClassRoom());
			print(l.getWeekNum());
			print("---------------");
		}
		
	}

}

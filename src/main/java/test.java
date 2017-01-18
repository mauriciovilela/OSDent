import java.util.Calendar;
import java.util.Date;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println(dayWeek == 2);
	}

}

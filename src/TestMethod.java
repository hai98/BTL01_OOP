

import java.io.File;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TestMethod {
	public static void main(String[] args) throws URISyntaxException {
		LocalDate d1 = LocalDate.now();
		System.out.println(d1);
		LocalDate d2 = LocalDate.of(2017, Month.NOVEMBER, 23);
		System.out.println(d2);
		System.out.println(ChronoUnit.DAYS.between(d1, d2));

	}
}

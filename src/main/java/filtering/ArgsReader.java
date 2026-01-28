package filtering;

/**
 * класс разбора аргументов командной строки
 */
class ArgsReader {
	String path = "";
	String prefix = "";
	boolean addToExist = false;
	boolean showShortStatistic = false;
	boolean showFullStatistic = false;
	
	private String prevArg;
	
	void read(String[] args) {
		String prevArg;
		for(String a: args) {
			if(a.startsWith("-")) {
				checkFlag(a);
				continue;
			}
			
			
		}
	}
	
	private void checkFlag(String flag) {
		if(flag.equals("-a"))
			addToExist = true;
		else if(flag.equals("-s"))
			showShortStatistic = true;
		else if(flag.equals("-f"))
			showFullStatistic = true;
		else if(flag.startsWith("-"))
			prevArg = flag;
	}

}

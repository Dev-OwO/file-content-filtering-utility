package filtering;

import java.util.LinkedList;
import java.util.List;

/**
 * класс разбора аргументов командной строки
 */
class ArgsReader {
	String path = "";
	String prefix = "";
	boolean addToExist = false;
	boolean showShortStatistic = false;
	boolean showFullStatistic = false;
	List<String> files = new LinkedList<>();
	List<String> wrongArgs = new LinkedList<>();
	
	private String prevArg;
	
	void read(String[] args) {
		for(String a: args) {
			if(a.startsWith("-")) {
				checkFlag(a);
				continue;
			}
			
			if(prevArg == null)
				files.add(a);
			else if(prevArg.equals("-o"))
				path = a;
			else if(prevArg.equals("-p"))
				prefix = a;
			prevArg = null;
		}
	}
	
	private void checkFlag(String flag) {
		if(prevArg != null) {
			wrongArgs.add(prevArg);
			prevArg = null;
		}
		
		if(flag.equals("-a"))
			addToExist = true;
		else if(flag.equals("-s"))
			showShortStatistic = true;
		else if(flag.equals("-f"))
			showFullStatistic = true;
		else if(flag.equals("-o"))
			prevArg = flag;
		else if(flag.equals("-p"))
			prevArg = flag;
		else
			wrongArgs.add(flag);
	}

}

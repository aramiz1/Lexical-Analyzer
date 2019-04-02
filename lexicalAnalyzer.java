
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
public class lexicalAnalyzer {

	private ArrayList<String> tokens;
	private String keywordsList = "    abstract   continue   for          new         switch\r\n" + 
			"    assert     default    if           package     synchronized\r\n" + 
			"    boolean    do         goto         private     this\r\n" + 
			"    break      double     implements   protected   throw\r\n" + 
			"    byte       else       import       public      throws\r\n" + 
			"    case       enum       instanceof   return      transient\r\n" + 
			"    catch      extends    int          short       try\r\n" + 
			"    char       final      interface    static      void\r\n" + 
			"    class      finally    long         strictfp    volatile\r\n" + 
			"    const      float      native       super       while";
	
	public lexicalAnalyzer() {
		tokens = new ArrayList<String>();
	}
	
	public void checkForMatches(String currentValue) {
		currentValue = currentValue.replaceAll("//.*", " ");
		
		String word = "";
		String checkCurrent = "";
		
		//iterate file
		for(int i=0;i<currentValue.length();i++) {
			checkCurrent = "" + currentValue.charAt(i);
			//match whitespace char
			if(checkCurrent.matches("\\s+")) {
				if(!word.matches("")) {
					classify(word);
				}
				word = "";
			}
			else {
				//else classify word
				word += currentValue.charAt(i);
			}
			
		}
	}
	
	public void classify(String word) {
		if(keywordsList.contains(word)) {
			tokens.add(word + "            keyword");
		}
		else if(word.matches("int|double|char|long|float|String|boolean")) {
			tokens.add(word + "            datatype");
		}//[=;()]
		else if(word.equals("[") || word.equals("]")) {
			tokens.add(word + "              bracket");
		}
		else if(word.equals("=")) {
			tokens.add(word + "              equal_sign");
		}
		else if(word.equals(";")) {
			tokens.add(word + "              semicolon");
		}
		else if(word.equals("(") || word.equals(")")) {
			tokens.add(word + "              parenthesis");
		}
		else if(word.equals("{") || word.equals("}")) {
			tokens.add(word + "              curly bracket");
		}
		
		else if(word.equals("%")) {
			tokens.add(word + "              mod_op");
		}
		else if(word.equals("*")) {
			tokens.add(word + "              mult_op");
		}
		else if(word.equals("/")) {
			tokens.add(word + "              div_op");
		}
		else if(word.equals("+")) {
			tokens.add(word + "              plus_op");
		}
		else if(word.equals("-")) {
			tokens.add(word + "              sub_op");
		}
		
		else if(word.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
			tokens.add(word + "              identifier");
		}
		else if(word.matches("[0-9]*")) {
			tokens.add(word + "              int_literal");
		}
		
	}
	
	public static void analyzeFile(String path) {
		
		String codes=readFile(path);
		lexicalAnalyzer LA = new lexicalAnalyzer();
		LA.checkForMatches(codes);
		
		
		ArrayList<String> tokenSequence = LA.getTokens();
		
		//print output
		System.out.println("Lexemes       Tokens");
		for(int x=0;x<tokenSequence.size();x++) {
			System.out.println(tokenSequence.get(x));
		}
		
	}
	
	//read file
	public static String readFile(String path) {
		StringBuilder builder = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String currentLine;
			while((currentLine = br.readLine()) != null) {
				builder.append(currentLine + "\n");
			}
			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public ArrayList<String> getTokens(){
		return tokens;
	}
	
	public static void main(String[] args) {
		//read file from path, print output in method analyzeFile
		analyzeFile("C:\\Users\\A\\Desktop\\workspace\\sampleLexical.txt");
		

	}
}
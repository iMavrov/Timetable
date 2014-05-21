package test;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mavrov
 */
public class AccessTxtParser {
    
    public AccessTxtParser() {
        tokens = null;
        tokenIndex = 0;
    }
    
    public void parseAccessTxtFile(String accessTxtFilePath) {
        tokens = new ArrayList<>();
        tokenIndex = 0;
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(accessTxtFilePath))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] lineTokens = line.split(TOKEN_SPLIT_REGULAR_EXPRESSION);
                for (int lineTokenIndex = 0; lineTokenIndex < lineTokens.length; ++lineTokenIndex) {
                    tokens.add(lineTokens[lineTokenIndex]);
                }
                
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файлът \"" + accessTxtFilePath + "\" не съществува или не може да бъде отворен!");
            tokens.clear();
        } catch (IOException e) {
            System.err.println("Възникна в/и грешка при четенето на файл \"" + accessTxtFilePath + "\"");
            tokens.clear();
        }
    }
    
    public boolean hasNextToken() {
        return ((tokens != null) && (tokenIndex < tokens.size()));
    }
    
    public String getNextToken() {
        return tokens.get(tokenIndex++);
    }
    
    private static final String TOKEN_SPLIT_REGULAR_EXPRESSION = "\\s*,\\s*";
    
    private List<String> tokens;
    private int tokenIndex;
}

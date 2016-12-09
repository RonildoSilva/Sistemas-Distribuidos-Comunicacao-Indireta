package ufc.sd.cind.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ManipuladorArquivos {
	
	public Map<String, String> carregar() throws IOException{

		Map<String, String> dicionario = new HashMap<String, String>();
		
		BufferedReader in = new BufferedReader(new FileReader("dicionario.txt"));
        String line = "";
        while ((line = in.readLine()) != null) {
            String parts[] = line.split(":");
            dicionario.put(parts[0], parts[1]);
        }
        in.close();
		
		return dicionario;
	}
}
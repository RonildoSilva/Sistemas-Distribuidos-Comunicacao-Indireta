package ufc.sd.cind.util;

import java.io.IOException;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ManipuladorArquivos mq = new ManipuladorArquivos();
		Map<String, String> dic = mq.carregar();
		//System.out.println(dic.toString());
		System.out.println(dic.get("men"));
		
	}

}

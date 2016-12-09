package ufc.sd.cind.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ManipuladorArquivos {
	
	public void gerarPontos(int quantidadeCidades){
		PrintWriter writer = null;

		try {
			writer = new PrintWriter("dicionario.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < quantidadeCidades; i++) {
			int min = 1;
			int max = quantidadeCidades-1;
			int finalX = min + (int)(Math.random() * max); 

			writer.print(finalX+" ");
		}

		writer.println();
		
		for (int i = 0; i < quantidadeCidades; i++) {
			int minX = 1;
			int maxX = quantidadeCidades-1;
			int finalY = minX + (int)(Math.random() * maxX);
			
			writer.print(finalY+" ");
		}

		writer.close();
	}

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

	public void escreverResultado(String nodes){

		PrintWriter writer = null;

		try {
			writer = new PrintWriter("results/resultado.html", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.println("<!doctype html><html lang='en'><head> <meta charset='utf-8'> "
				+ "<title>Resultados - Caixeiro Viajante com Subida de Encosta</title> "
				+ "<link rel='stylesheet' href='SimpleDiagram-0.2.css'> <style> "
				+ "body { padding: 20px; font-family: Arial, Helvetica, sans-serif; }"
				+ " h1 { margin-top: 0; } </style></head><body> <h1>Caixeiro Viajante</h1> "
				+ "<p>Resultado</p> <div id='diagram'></div> <script src='d3.min.js'></script>"
				+ "<script src='SimpleDiagram-0.2.min.js'></script> "
				+ "<script> var diagram = new SimpleDiagram('#diagram', "
				+ "{ addGrid: true, cellSize: 35, numColumns: 200, numRows: 200, interactive: false });"
				+ " var nodes = [");

		writer.println(nodes);

		writer.println("]; nodes.forEach(function(node) { diagram.addNode({ "
				+ "name: node.name, label: node.name, row: node.row, column: node.column }); "
				+ "}); nodes.forEach(function(node) { if (!node.connectsTo) return; "
				+ "diagram.addLine({ from: node.name, to: node.connectsTo }); }); </script></body></html>");

		writer.close();
	}
}
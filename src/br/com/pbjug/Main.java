package br.com.pbjug;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class Main {

	public static void main( String[] args ) {
		
		FileUtils.deleteQuietly( new File( "output/lista.pdf" ) );
		FileUtils.deleteQuietly( new File( "output/crachas.pdf" ) );
		
		Parser parser = new Parser( "resources/participants.xml" );
		
		Map<String, List<Inscrito>> inscritos = parser.parse();
	
		new ListaDePresencaGenerator(inscritos).generate( "output/lista.pdf" );
		new CrachasGenerator(inscritos).generate( "output/crachas.pdf" );

		System.out.println( "Foi" );
		
	}
	
}

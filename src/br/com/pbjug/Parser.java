package br.com.pbjug;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.WordUtils;

public class Parser {

	private String[] files ;

	public Parser(String ... files) {
		this.files = files;
	}

	private List<Inscrito> process() {
		
		List<Inscrito> inscritos = new ArrayList<Inscrito>();

		
		for ( String file : files ) {
			
			XMLInputFactory factory = XMLInputFactory.newFactory();

			try {

				String content = StringEscapeUtils.unescapeXml( FileUtils.readFileToString( new File(file) ) );
				
				XMLStreamReader parser = factory.createXMLStreamReader( new ByteArrayInputStream( content.getBytes() ) );
				
				String currentTag = null;
				int columnCount = 0;
				Inscrito inscrito = null;			
				
				while ( parser.hasNext() ) {
					
					int currentEvent = parser.next();
					
					switch ( currentEvent ) {
					case XMLStreamReader.START_ELEMENT: 
						
						currentTag = parser.getLocalName();
						
						if ( "row".equals( currentTag ) ) {
							columnCount = 0;
							inscrito = new Inscrito();						
						}
						
						break;
					case XMLStreamReader.END_ELEMENT:

						currentTag = parser.getLocalName();
						
						if ( "row".equals( currentTag ) ) {
							inscritos.add( inscrito );
						}
						
						if ( "column".equals( currentTag ) ) {
							columnCount++;
						}					
						
						break;
					case XMLStreamReader.CHARACTERS:
						
						if ( "column".equals( currentTag ) ) {
							
							String text = parser.getText().trim().replaceAll( "\n" , " "); 
							
							switch( columnCount ) {
							case 0:
								inscrito.setId( Integer.valueOf( text ) );
								break;
							case 1:							
								inscrito.setFirstName( WordUtils.capitalizeFully( text ) );
								break;
							case 2:
								inscrito.setLastName( WordUtils.capitalizeFully( text ) );
								break;
							case 3:
								inscrito.setEmail( text );
								break;
							}
													
						}
						
						break;
					}
					
				}
				
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}			
			
		}
		
		Collections.sort(inscritos);
		
		return inscritos;
		
	}
	
	public Map<String,List<Inscrito>> parse() {

		List<Inscrito> inscritos = this.process();
		
		Map<String,List<Inscrito>> resultado = new LinkedHashMap<String, List<Inscrito>>();
		
		for ( Inscrito i : inscritos ) {
			
			List<Inscrito> lista = resultado.get( i.getInicial() );
			
			if ( lista == null ) {
				lista = new ArrayList<Inscrito>();
				resultado.put( i.getInicial(), lista );
			}
			
			lista.add( i );
			
		}
		
		return resultado;
	}

}

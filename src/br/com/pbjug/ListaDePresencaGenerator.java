package br.com.pbjug;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ListaDePresencaGenerator {

	private Map<String, List<Inscrito>> inscritos;
	private Font headerFont = new Font( FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK );


	public ListaDePresencaGenerator(Map<String, List<Inscrito>> inscritos) {
		this.inscritos = inscritos;
	}

	public void generate(String fileName) {

		try {
			
			Document document = new Document( PageSize.A4.rotate() );
			PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			
			int count = 1;			
			
			for ( Entry<String, List<Inscrito>> entry : this.inscritos.entrySet() ) {
												
				count = this.renderTable(document, entry.getValue(), count, "Lista de presença para letra " + entry.getKey() );
				
			}			
			
			List<Inscrito> inscritos = new ArrayList<Inscrito>();
			Inscrito inscrito = new Inscrito();
			inscrito.setFirstName("");
			inscrito.setLastName("");
			inscrito.setEmail("");

			for ( int x = 0; x < 21; x++ ) {
				inscritos.add( inscrito );
			}
			
			count = this.renderTable(document, inscritos , count, "Lista de presença complementar");
			count = this.renderTable(document, inscritos , count, "Lista de presença complementar");

			
			document.close();
			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int renderTable( Document document, List<Inscrito> inscritos, int count, String titulo ) throws Exception {
		Image image = Image.getInstance( "resources/logo.png" );
		image.setAlignment( Element.ALIGN_CENTER );
		image.scaleToFit( 300, 50 );
		
		document.add( image );
		
		Paragraph abertura = new Paragraph();
		
		abertura.setAlignment( Element.ALIGN_CENTER );
		abertura.setFont(headerFont);
		
		abertura.add( new Chunk( "N-ésimo encontro do PBJUG - 09/07/2011" ) );
		abertura.add( Chunk.NEWLINE );
		abertura.add( new Chunk( titulo ) );
		
		document.add(abertura);

		document.add( Chunk.NEWLINE );
		
		PdfPTable table = new PdfPTable( 6 );
		table.setWidthPercentage(100);
		
		table.getDefaultCell().setBackgroundColor( BaseColor.GRAY );
		
		for ( int x = 0; x < 2; x++ ) {
		
			table.addCell( "ID" );
			table.addCell( "Nome" );
			table.addCell( "Email" );
			table.addCell("Assinatura");
			table.addCell( "Universidade/Empresa");
			table.addCell( "Cidade" );
			
		} 
		
		table.setHeaderRows(2);
		table.setFooterRows(1);
		
		table.getDefaultCell().setBackgroundColor( null );
		
		table.setWidths( new float[] { 4, 22, 22, 18, 18, 16 } );
		
		for ( Inscrito i : inscritos) {
			
			table.addCell( String.valueOf( count ) );
			table.addCell( i.getFullName() );
			table.addCell( i.getEmail() );
			table.addCell("");
			table.addCell("");
			table.addCell("");
			
			count++;
		}				
		
		document.add( table );
		
		document.newPage();
		
		return count;
	}
	
}

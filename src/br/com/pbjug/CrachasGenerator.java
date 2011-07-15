package br.com.pbjug;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CrachasGenerator {

	private Map<String, List<Inscrito>> inscritos;
	private Font nameFont = new Font( FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLACK );
	private Font headerFont = new Font( FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.GRAY );	

	public CrachasGenerator(Map<String, List<Inscrito>> inscritos) {
		this.inscritos = inscritos;
	}
	
	//288x144
	
	public void generate( String output ) {
		
		try {
			
			Document document = new Document( PageSize.LETTER, 12F, 12F, 36F, 36F );
			PdfWriter.getInstance(document, new FileOutputStream(output));
			document.open();
	
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage( 100F );
			table.setWidths( new float[] { 50F, 50F } );					
			
			int count = 1;
			
			for ( Entry<String, List<Inscrito>> entry : this.inscritos.entrySet() ) {
				
				for ( Inscrito i : entry.getValue() ) {
					
					this.addCell(table,  i.getFullName() , count);
					
					count++;
					
				}				
								
			}				
			
			for ( int x = count; x <= 500; x++ ) {
				this.addCell( table, "", x );
			}
			
			document.add(table);
			
			document.close();
			
		} catch ( Exception e ) {
			throw new RuntimeException(e);
		}
		
	}
	
	private void addCell( PdfPTable table, String name, int number ) {
		
		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment( Element.ALIGN_CENTER );

		paragraph.add( Chunk.NEWLINE );
		
		paragraph.add( new Chunk( "N-ésimo encontro do PBJUG", this.headerFont ) );
		paragraph.add( Chunk.NEWLINE );
		paragraph.add( Chunk.NEWLINE );
		
		paragraph.add( new Chunk( name , this.nameFont ) );
		paragraph.add( Chunk.NEWLINE );
		paragraph.add( Chunk.NEWLINE );

		paragraph.add( new Chunk( String.valueOf( number ), this.nameFont ) );
		
		PdfPCell cell = new PdfPCell();
		cell.setBorderWidth(0);
		cell.setFixedHeight( 144 );
		cell.addElement( paragraph );
		
		cell.setCellEvent( new CellBackground() );
		
		table.addCell( cell );		
		
	}
	
}

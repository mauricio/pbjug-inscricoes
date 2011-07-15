package br.com.pbjug;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

public class CellBackground implements PdfPCellEvent {

	@Override
	public void cellLayout(PdfPCell cell, Rectangle rectangle,
			PdfContentByte[] canvas) {

		PdfContentByte background = canvas[PdfPTable.BACKGROUNDCANVAS];
		try {						
						
			Image image = Image.getInstance("resources/logo_only.png");
			image.setAbsolutePosition( rectangle.getLeft() + 96 , rectangle.getTop() - 144 );
			image.scaleToFit( 120 , 120);
			
			background.saveState();
			background.addImage(image);
			background.restoreState();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}

package br.com.pbjug;

public class Utils {

	public static String transliterate( String content ) {
		
		return content.replaceAll( "(ã|á|â|à)" , "a")
			   .replaceAll( "(é|ê)", "e" )
			   .replaceAll( "(í)", "i" )
			   .replaceAll( "(ó|ô|õ)", "o" )
			   .replaceAll( "(ú)", "u" );
		
	}
	
}
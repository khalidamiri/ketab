package org.ketab.ketab;

import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.ketab.book.Book;
import org.ketab.book.BookManager;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class TheDoc {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws COSVisitorException 
	 * @throws PrinterException 
	 * @throws NamingException 
	 */
/*	public static void main(String[] args) throws IOException, COSVisitorException, PrinterException, NamingException {
		
		Hashtable props = new Hashtable();
		props.put(InitialContext.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		
		Context ctx = new InitialContext(props);
		
		
		BookManager bookMgr = (BookManager) ctx.lookup("BookManagerBeanLocal");
		
		Book book = bookMgr.getBook(2);
		System.out.println(book.getBookTitle());
		
		InputStream is = null;
		is.read(book.getBookBlob());
		

		PDDocument pdd = new PDDocument().load(is);

		// DOM Implementation, acts as a bootstrap for creating DOM documents
		DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
		
		// SVG Document
		Document svgDoc = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
		
		// SVG Generator context used by SVGGraphics2D to generate SVG
		SVGGeneratorContext svgGenCtx = SVGGeneratorContext.createDefault(svgDoc);
		svgGenCtx.setEmbeddedFontsOn(true);
		
		for(int i = 0; i < pdd.getNumberOfPages(); i++){
			String svgFileName = "page-" + i + ".svg";
			(new File(svgFileName)).createNewFile();
			
			// SVG Generator
			SVGGraphics2D svgGenerator = new SVGGraphics2D(svgGenCtx, false);
			
			// Get the printable interface form PDDocument to print the pdf content to SVGGraphics2D
			Printable page = pdd.getPrintable(i);
			page.print(svgGenerator, pdd.getPageFormat(i), i);
			svgGenerator.stream(svgFileName);
		}
		pdd.close();
	}
*/
}

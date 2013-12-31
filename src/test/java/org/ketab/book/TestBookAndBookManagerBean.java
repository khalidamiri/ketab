package org.ketab.book;

import static org.junit.Assert.*;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.ExtractText;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDLayoutAttributeObject;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.util.PDFImageWriter;
import org.apache.pdfbox.util.PDFTextStripper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ketab.author.AuthorManager;
import org.ketab.author.AuthorManagerBean;
import org.ketab.book.Book;
import org.ketab.book.BookManager;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

@RunWith(Arquillian.class)
public class TestBookAndBookManagerBean{

//	private Context ctx;
	@EJB
	private BookManager bookMgrBean;
	
	private long binaryBookId;

	@Deployment
	public static JavaArchive createDeployment(){
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addClasses(BookManager.class, BookManagerBean.class)
				.addAsResource("META-INF/persistence.xml");
		return jar;
	}

/*	@Before
	public void instantiate() throws NamingException{
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");

		ctx = new InitialContext(props);
		bookMgrBean = (BookManager)ctx.lookup("BookManagerBeanLocal");
	}
*/
	
	@Test
	public void testFind() throws IOException {
		Book book = new Book();
		book.setTitle("JUnit in Action");
		bookMgrBean.addBook(book);
		assertNotNull("Should get back a book.", bookMgrBean.getBook(book.getBookId()));
	}
	
	@Test
	public void testBinaryBookStorage() throws javax.naming.NamingException, IOException {
		FileInputStream fis = new FileInputStream(new File("testBook.pdf"));
		byte[] bookByte = IOUtils.toByteArray(fis);

		Book book = new Book();
		book.setBookBlob(bookByte);
		book.setTitle("Khunkaar");
//		bookMgrBean = (BookManager)ctx.lookup("BookManagerBeanLocal");
		bookMgrBean.addBook(book);

		Book bookReturn = bookMgrBean.getBook(book.getBookId());
		byte[] bookByteReturn = bookReturn.getBookBlob();
		fis.close();

		binaryBookId = book.getBookId();

		assertArrayEquals("Should get back the same book as stored.", bookByteReturn, bookByte);
	}
	
	@Test
	public void testRemoveBook() throws IOException, NamingException{
		Book book = new Book();
		book.setTitle("Book to be removed");
//		bookMgrBean = (BookManager)ctx.lookup("BookManagerBeanLocal");
		bookMgrBean.addBook(book);

		bookMgrBean.delBook(book.getBookId());

		assertNull("Shouldn't return a book with title 'Book to be removed', if 'remove' works.", bookMgrBean.getBook(book.getBookId()));
	}

	@Test
	public void testUpdateBook() throws IOException, NamingException{
		Book book = new Book();
		book.setTitle("Book to be Updated");
//		bookMgrBean = (BookManager)ctx.lookup("BookManagerBeanLocal");
		bookMgrBean.addBook(book);
		
		Book bookToUpdate = bookMgrBean.getBook(book.getBookId());
		bookToUpdate.setBookTitle("Book just updated");

		bookMgrBean.updateBook(bookToUpdate);
		
		assertEquals("Shouldn't return a book with title 'Book to be removed', if 'remove' works.", bookMgrBean.getBook(book.getBookId()).getTitle(), bookToUpdate.getTitle());
	}

	@Test
	public void testListBooks() throws IOException, NamingException{
//		bookMgrBean = (BookManager)ctx.lookup("BookManagerBeanLocal");
		List<Book> books = bookMgrBean.listBooks("bookId", "asc");

		for(int i = 0; i < books.size(); i++){
			assertTrue(books.get(i).getClass().isInstance(new Book()));
		}

	}
	
	@Test
	public void testBookToSVG() throws IOException, PrinterException, FontFormatException{
		
		Book book = bookMgrBean.getBook(binaryBookId);
		InputStream is = new ByteArrayInputStream(book.getBookBlob());
		PDDocument pdd = new PDDocument().load(is);
		

		// Extract the text from pdf and store it as a text file
		PDFTextStripper stripper = new PDFTextStripper();
        stripper.setSortByPosition(true);
        String text = stripper.getText(pdd);
                
        PDLayoutAttributeObject pdl = new PDLayoutAttributeObject();
        pdl.setTextAlign(text);
        
        FileOutputStream fos = new FileOutputStream(new File("extractedText.txt"));
        fos.write(text.getBytes());
        fos.flush();
        fos.close();


        // Convert and store pdf apges as images using PDFImageWriter
/*        PDFImageWriter pdfi = new PDFImageWriter();
        
        pdfi.writeImage(pdd, "png", null, 1, pdd.getNumberOfPages(), "page-");
*/        
        

/*
		// DOM Implementation, acts as a bootstrap for creating DOM documents
		DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
		
		// SVG Document
		Document svgDoc = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
		svgDoc.createElement("svg");
		
		// SVG Generator context used by SVGGraphics2D to generate SVG
		SVGGeneratorContext svgGenCtx = SVGGeneratorContext.createDefault(svgDoc);
		svgGenCtx.setEmbeddedFontsOn(true);
		
		for(int i = 0; i < pdd.getNumberOfPages(); i++){
			String svgFileName = book.getTitle() + " " + i + ".svg";
			(new File(svgFileName)).createNewFile();
			
			// SVG Generator
			SVGGraphics2D svgGenerator = new SVGGraphics2D(svgGenCtx, false);
			InputStream isf = new FileInputStream("fonts/Pghotai.ttf");
			svgGenerator.setFont(Font.createFont(Font.TRUETYPE_FONT, isf));
			
			// Get the printable interface form PDDocument to print the pdf content to SVGGraphics2D
			Printable page = pdd.getPrintable(i);
			page.print(svgGenerator, pdd.getPageFormat(i), i);
			svgGenerator.stream(svgFileName);
		}
*/		
        is.close();
		pdd.close();
	}

}
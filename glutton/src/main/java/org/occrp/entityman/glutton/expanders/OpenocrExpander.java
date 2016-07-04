package org.occrp.entityman.glutton.expanders;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.extractor.EmbeddedDocumentExtractor;
import org.apache.tika.extractor.ParsingEmbeddedDocumentExtractor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.occrp.entityman.AExpander;
import org.occrp.entityman.model.IngestedFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

public class OpenocrExpander extends AExpander {

	protected Logger log = LogManager.getLogger(getClass().getName());
	private Parser parser = new AutoDetectParser();
	private Detector detector = ((AutoDetectParser) parser).getDetector();
	private TikaConfig config = TikaConfig.getDefaultConfig();

	@Override
	public void expandSuper(IngestedFile file) {
		Tika tika = new Tika();
		FileInputStream fis = null;
		
		// skip OCR if doOcr param is false
		if (file.getEntities().containsKey("doOcr") && 
				Boolean.FALSE.equals(file.getEntities().get("doOcr"))) return;
		
		try {
			fis = new FileInputStream(file.getFile());
			String contentType = tika.detect(fis);

			if ("application/pdf".equals(contentType)) {
				PDFParser pdfParser = new PDFParser();
				pdfParser.getPDFParserConfig().setExtractInlineImages(true);
				pdfParser.getPDFParserConfig().setExtractAnnotationText(true);

				ParseContext c = new ParseContext();
				ContentHandler h = new BodyContentHandler(-1);
				c.set(Parser.class, pdfParser);
				MyEmbeddedDocumentExtractor ex = new MyEmbeddedDocumentExtractor(c, file);
				c.set(EmbeddedDocumentExtractor.class, ex);

				// ContentHandler handler = new DefaultHandler();
				Metadata metadata = new Metadata();
				// ParseContext context = new ParseContext();

				fis = new FileInputStream(file.getFile());
				pdfParser.parse(fis, h, metadata, c);

				log.info("Metadata fileCount {} : {} ", metadata.get("fileCount"), metadata);
				log.info("OcredText fileCount {} : {} ", ex.getFileCount(), ex.getOcredText());
				log.info("Ingested file count : {} ",
						file.getExpandedData().get(AExpander.EXPKEY_OCREDTEXT));
			}

		} catch (Exception e) {
			log.error("Failed to expand {} file : ", getName(), file.getFileUri(), e);

		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}

	}

	private class MyEmbeddedDocumentExtractor extends ParsingEmbeddedDocumentExtractor {
		private int fileCount = 0;
		private List<String> ocredText = new ArrayList<>();
		IngestedFile file;

		private MyEmbeddedDocumentExtractor(ParseContext context, IngestedFile file) {
			super(context);
			this.file = file;
		}

		@Override
		public boolean shouldParseEmbedded(Metadata metadata) {
			return true;
		}

		@Override
		public void parseEmbedded(InputStream stream, ContentHandler handler,
				Metadata metadata, boolean outputHtml)
						throws SAXException, IOException {

			// try to get the name of the embedded file from the metadata
			String name = metadata.get(Metadata.RESOURCE_NAME_KEY);

			fileCount++;
			
			if (name == null) {
				name = "file_" + fileCount;
			} else {
				// make sure to select only the file name (not any directory
				// paths
				// that might be included in the name) and make sure
				// to normalize the name
				name = FilenameUtils.normalize(FilenameUtils.getName(name));
			}

			// now try to figure out the right extension for the embedded file
			MediaType contentType = detector.detect(stream, metadata);

			if (name.indexOf('.') == -1 && contentType != null) {
				try {
					name += config.getMimeRepository().forName(
							contentType.toString()).getExtension();
				} catch (MimeTypeException e) {
					log.error(e);
				}
			}

			log.info("Created embeded object : {}", name);

			
//			File outputDir = new File("/tmp/entityman_ocr");
//			FileUtils.forceMkdir(outputDir);
//			stream.mark(0);
//			File dstFile = new File("/tmp/entityman_ocr/" + name);
//			// FileUtils.copyInputStreamToFile(stream, dstFile);
//			Files.copy(stream, Paths.get(dstFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
//			stream.reset();

//			metadata.set("fileCount", String.valueOf(fileCount));
			// TODO more

//			List<Object> images = (List<Object>) file.getExpandedData().get(AExpander.EXPKEY_IMAGES);
//			if (images == null) {
//				images = new ArrayList<>();
//				file.getExpandedData().put(EXPKEY_IMAGES, images);
//			}
//
//			images.add(stream);
			
			// String ocredText = callOpenOcr(stream, contentType,name);
			String ocredText = callTess4j(stream);

			List<String> ocredTexts = (List<String>) file.getExpandedData()
					.get(AExpander.EXPKEY_OCREDTEXT);

			if (ocredTexts == null) {
				ocredTexts = new ArrayList<>();
				file.getExpandedData().put(AExpander.EXPKEY_OCREDTEXT, ocredTexts);
			}

			if (ocredText != null) {
				ocredTexts.add(ocredText);
				file.getExpandedData().put(EXPKEY_SIMPLETEXT, 
						file.getExpandedData().get(EXPKEY_SIMPLETEXT) +
					"\n\nOCRED TEXT PART "+(fileCount+1)+"\n\n"+ocredText);
			}
			
		}

		public int getFileCount() {
			return fileCount;
		}

		public List<String> getOcredText() {
			return ocredText;
		}

		// private String urlOpenOcr =
		// "https://ocr.occrp.org:443/ocr-file-upload";
		private String urlOpenOcr = "http://127.0.0.1:38080/ocr-file-upload";

		private String callOpenOcr(InputStream is, MediaType mt, String name) {
			String res = null;
			CloseableHttpClient client = HttpClients.createDefault();

			try {
				HttpPost method = new HttpPost();
				final URI uri = new URI(urlOpenOcr);
				method.setURI(uri);

				method.setHeader("Content-Type", "multipart/related");

				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				builder.setMode(HttpMultipartMode.STRICT);
				builder.addPart(name,
						new StringBody("{ \"engine\": \"tesseract\" }",
								ContentType.APPLICATION_JSON));
				// "text", "{ \"engine\": \"tesseract\" }",
				// ContentType.APPLICATION_JSON)));
				builder.addBinaryBody("upfile", is,
						ContentType.create(mt.getBaseType().toString()), name);
				//
				HttpEntity entity = builder.build();

				method.setEntity(entity);

				String r = client.execute(method, new ResponseHandler<String>() {
					@Override
					public String handleResponse(HttpResponse res)
							throws ClientProtocolException, IOException {

						String result = null;

						int status = res.getStatusLine().getStatusCode();

						if (status >= 200 && status < 300) { // ok
							result = EntityUtils.toString(res.getEntity());
						} else { // failed
							log.error("Failed request {} , reason : {}", uri, res.getStatusLine());
						}

						return result;
					}
				});

				res = r;
			} catch (Exception e) {
				log.error("Failed request {} , reason : {}", urlOpenOcr, e);
			}

			log.trace("received response : {}", res);

			return res;
		}

	}

	/**
	 * Rating method for ocr text
	 * count the word rating based on word length and dictionary.
	 * TODO Dictionary words
	 * @param text
	 * @return
	 */
	public long rateOcr(String text) {
		long res = 0;
		
		if (text==null || text.length()==0) return res;
		
		String words[] = StringUtils.split(text, " .,!?");
		
		for (String word : words) {
			// TODO Dictionary rating
			int l = word.length();
			if (l>1 && l<=15) {
				res+=l;
			}
			
		}
		
		return res;
	}
	
	public Image toGrayScale(Image src, int midpoint) {
		ImageFilter filter = new GrayFilter(true, 50);  
		ImageProducer producer = new FilteredImageSource(src.getSource(), filter);  
		Image image = Toolkit.getDefaultToolkit().createImage(producer);  
		return image;
//		return src;
	}
	
	/** TODO
	 * More OCR improve :
	 * http://stackoverflow.com/questions/9480013/image-processing-to-improve-tesseract-ocr-accuracy
	 * http://stackoverflow.com/questions/4756268/how-to-resize-the-buffered-image-n-graphics-2d-in-java/4756906#4756906
	 * 
	 */
	
	
	private List<Integer> grayMidpoints = Arrays.asList(50);
	
	private String callTess4j(InputStream is) {

		String result = null;
		long resultRating = 0;
		try {
			log.info("OCR Start");
			Tesseract instance = Tesseract.getInstance(); // JNA
			// Interface Mapping
			// Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping
			
			instance.setTessVariable("tessedit_char_whitelist", 
					" abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.!/?\\-_+=:;'\"|()<>"
					+ "țȚșȘăĂîÎ");
			
			instance.setDatapath("/usr/local/share");
//			instance.setConfigs(Arrays.asList("letters"));
			
			BufferedImage bi = ImageIO.read(is);
			
			for (int midpoint : grayMidpoints) {
				String tres = instance.doOCR(toBufferedImage(
						toGrayScale(bi, midpoint)));
		
				long trating = rateOcr(tres);
				log.info("OCR Result rating for midpoint {} : {}",midpoint,trating);
		
				if (trating>resultRating) {
					result = tres;
					resultRating = trating;
				}
			}

			log.info("OCR Result : \n {}", result);

		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

	public List<Integer> getGrayMidpoints() {
		return grayMidpoints;
	}

	public void setGrayMidpoints(List<Integer> grayMidpoints) {
		this.grayMidpoints = grayMidpoints;
	}
	
}

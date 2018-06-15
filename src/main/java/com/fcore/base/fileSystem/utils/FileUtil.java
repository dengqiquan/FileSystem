package com.fcore.base.fileSystem.utils;


import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class FileUtil {

	/**
	 * 上传文件 
	 * @param suffix
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String uploudFile(String filePath, String suffix, HttpServletRequest request)throws Exception {
		// 上传文件保存路径
		String savePath = filePath;
		File file = new File(savePath);
		// 获取文件对象
		if (!file.exists()) {
			// 创建文件夹
			file.mkdirs();
		}
		String fileName = UUID.randomUUID().toString() + suffix;
		savePath = savePath + fileName;
		file = new File(savePath);
		BufferedInputStream fileIn;
		try {
			fileIn = new BufferedInputStream(request.getInputStream());
			BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buf = new byte[1024];
			Long size = 0l;
			while (true) {
				// 读取数据
				int bytesIn = fileIn.read(buf, 0, 1024);
				if (bytesIn == -1) {
					break;
				} else {
					size += bytesIn;
					fileOut.write(buf, 0, bytesIn);
				}
			}
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  savePath;
	}
	
	/**
	 * 文件单位大小换算
	 * @param fileLength
	 * @return 包含单位的字符串
	 */
	public String conversion(Long fileLength){
		String str = "";
		if(fileLength>1024){
			str = fileLength%1024 +"B";
			fileLength = fileLength/1024;
			if(fileLength>1024){
				str = fileLength%1024+"KB" + str;
				fileLength = fileLength/1024;
				if(fileLength>1024){
					return fileLength/1024 + fileLength%1024 +"MB" +str;
				}else {
					return fileLength+"MB" +str;
				}
			}else {
				return fileLength+"KB" + str;
			}
		}else {
			return fileLength + "B";
		}
	}

	public static String getFileType(String fileType) {
		String str = "";
		if(StringUtils.isNotEmpty(fileType)){
			if(fileType.contains("word")){
				str = "word";
			}else if(fileType.contains("image")){
				str = "image";
			}else if(fileType.contains("excel") || fileType.contains("sheet")){
				str = "excel";
			}else if(fileType.contains("pdf")){
				str = "pdf";
			}else if(fileType.contains("ppt") || fileType.contains("powerpoint")){
				str = "ppt";
			}else if(fileType.contains("text")){
				str = "txt";
			}else if(fileType.contains("mp4")){
				str = "mp4";
			}else if(fileType.contains("mp3")){
				str = "mp3";
			}
		}
		return str;
	}
	
	/**
	 * 创建目录
	 * @param path
	 */
	public static boolean createDir(String path){
		File file = new File(path);
		if(!file .exists()  && !file .isDirectory()) {
			return file.mkdirs();
		}
		return true;
	}
	
	/**
	 * 修改文件夹名称
	 * @param path
	 * @param newPath
	 * @return
	 */
	public static boolean renameDir(String path,String newPath) {
		File srcDir = new File(path);  
        return srcDir.renameTo(new File(newPath));  
	}
	
	/**
	 * 删除文件或文件夹
	 * @param path
	 */
	public static void deleteAllFilesOfDir(File path) {  
	    if (!path.exists())  
	        return;  
	    if (path.isFile()) {  
	        path.delete();  
	        return;  
	    }  
	    File[] files = path.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        deleteAllFilesOfDir(files[i]);  
	    }  
	    path.delete();  
	} 
	
	private static boolean getWordLicense(){
		boolean result = false;
		try {
			 ClassLoader loader = Thread.currentThread().getContextClassLoader();
			 InputStream license = new FileInputStream(loader.getResource("license.xml").getPath());// 凭证文件
			 License aposeLic = new License();
             aposeLic.setLicense(license);
             result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static boolean getCellsLicense(){
		boolean result = false;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream license = new FileInputStream(loader.getResource("license.xml").getPath());// 凭证文件
			com.aspose.cells.License aposeLic = new com.aspose.cells.License();
			aposeLic.setLicense(license);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	private static boolean getSlidesLicense(){
		boolean result = false;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream license = new FileInputStream(loader.getResource("license.xml").getPath());// 凭证文件
			com.aspose.slides.License aposeLic = new com.aspose.slides.License();
			aposeLic.setLicense(license);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * convert ppt to pdf
	 * @param originalPath
	 * @param dirPath
	 */
	public static void slides2pdf(String originalPath,String dirPath){
		if (!getSlidesLicense()) {
			return;
		}
		try {
			long old = System.currentTimeMillis();
			FileInputStream fileInput = new FileInputStream(originalPath);
			File outputFile = new File(dirPath);
			if(!outputFile.exists()){
				outputFile.createNewFile();
			}
			Presentation pres = new Presentation(fileInput);
			FileOutputStream fileOS = new FileOutputStream(outputFile);
			pres.save(fileOS, com.aspose.slides.SaveFormat.Pdf);
			long now = System.currentTimeMillis();
			System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + outputFile.getPath());
			fileOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * convert word to pdf
	 * @param originalPath
	 * @param dirPath
	 */
	public static void word2pdf(String originalPath,String dirPath){
		if (!getWordLicense()) {
			return;
		}
		try {
			long old = System.currentTimeMillis();
			FileInputStream fileInput = new FileInputStream(originalPath);
			Document doc = new Document(fileInput);
			File outputFile = new File(dirPath);
			if(!outputFile.exists()){
				outputFile.createNewFile();
			}
			FileOutputStream fileOS = new FileOutputStream(outputFile);
			doc.save(fileOS, SaveFormat.PDF);
			long now = System.currentTimeMillis();
			System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + outputFile.getPath());
			fileOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
	}
	
	/**
	 * convert cells to pdf
	 * @param originalPath
	 * @param dirPath
	 */
	public static void cells2pdf(String originalPath,String dirPath){
		if (!getCellsLicense()) {
			return;
		}
		try {
			long old = System.currentTimeMillis();
			FileInputStream fileInput = new FileInputStream(originalPath);
			Workbook wb = new Workbook(fileInput);
			
			File outputFile = new File(dirPath);
			if(!outputFile.exists()){
				outputFile.createNewFile();
			}
			FileOutputStream fileOS = new FileOutputStream(outputFile);
			wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
			long now = System.currentTimeMillis();
			System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + outputFile.getPath());
			fileOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * convert txt file to pdf 
	 * @param originalPath
	 * @param dirPath
	 */
	public static void txt2pdf(String originalPath,String dirPath){
		com.lowagie.text.Document document = null;
		BufferedReader read = null;
		long old = System.currentTimeMillis();
		try {
			document = new com.lowagie.text.Document(PageSize.A4, 80, 80, 60, 30);
			PdfWriter.getInstance(document, new FileOutputStream(dirPath));
			document.open();
			BaseFont bfChinese = BaseFont.createFont(ReadCreatePdf.class.getResource("/") + "/simsun.ttc,1",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font FontChinese = new Font(bfChinese, 18, Font.NORMAL);
			
			FileInputStream fstream = new FileInputStream(originalPath);
			DataInputStream in = new DataInputStream(fstream);
			read = new BufferedReader(new InputStreamReader(in, "gb2312"));
			String line = null;
			while ((line = read.readLine()) != null) {
				Paragraph t = new Paragraph(line, FontChinese);
				t.setAlignment(Element.ALIGN_LEFT);
				t.setLeading(20.0f);
				document.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				read.close();
				document.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		long now = System.currentTimeMillis();
		System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + dirPath);
	}
	
	/**
	 * PDF to Image(png)
	 * @param pdfPath
	 * @param imagePath
	 */
	public static void pdf2png(String pdfPath,String imagePath){
		long old = System.currentTimeMillis();
		// 将pdf装图片 并且自定义图片得格式大小
		File file = new File(pdfPath);
		try {
			PDDocument doc = PDDocument.load(file);
			PDFRenderer renderer = new PDFRenderer(doc);
			int pageCount = doc.getNumberOfPages();
			for (int i = 0; i < pageCount; i++) {
				BufferedImage image = renderer.renderImageWithDPI(i, 250); // Windows
				//BufferedImage srcImage = resize(image, 240, 240);// 产生缩略图
				ImageIO.write(image, "PNG", new File(imagePath+i+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		long now = System.currentTimeMillis();
		System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + imagePath);
	}
	
	/**
	 * 产生缩略图
	 * @param source
	 * @param targetW
	 * @param targetH
	 * @return
	 */
	public static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}
	
}

/*
Copyright (c) 2013 Lawrence Angrave

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

/**
 * Creates new images that are smaller than the original images.
 * 
 * Note you may get better quality results using a commercial or opensource
 * image manipulation program (e.g. Photoshop or imagemagick).
 * 
 * Tested on '.jpg' and '.png' files
 * 
 * @author angrave
 * 
 */
public class Reducer {

	public static void main(String[] args) {
		// Reduce the width and height by this factor
		double factor = 3;
		// Where to find the source images
		File inputDir = new File("originals");

		// Where to store the output images
		File outputDir = new File("output");
		outputDir.mkdir();

		// Process each file inside the originals/ directory (sub-directories
		// are ignored)
		for (File img : inputDir.listFiles()) {
			if (img.isFile())
				process(img, outputDir, factor);
		}

	}

	private static void process(File inputFile, File baseDir, double factor) {
		// Create a subdirectory e.g. "4.0"
		File subDir = new File(baseDir, "" + factor);
		subDir.mkdir();

		// The output name e.g. 'banana.jpg' will be the same as the original
		String name = inputFile.getName();
		File outputFile = new File(subDir, name);
		reduce(inputFile, outputFile, factor);
	}

	private static void reduce(File imageFile, File outputFile, double factor) {
		System.out.println("Processing " + imageFile);
		BufferedImage source;
		try {
			source = ImageIO.read(imageFile);

			int sourceW = source.getWidth();
			int sourceH = source.getHeight();

			int w = (int) (sourceW / factor + 0.5);
			int h = (int) (sourceH / factor + 0.5);

			boolean hasAlpha = source.getColorModel().hasAlpha();
			System.out.println("Has alpha (i.e. transparency):" + hasAlpha);
			int format = hasAlpha ? BufferedImage.TYPE_INT_ARGB
					: BufferedImage.TYPE_INT_RGB;

			BufferedImage output = new BufferedImage(w, h, format);
			Graphics2D g = output.createGraphics();

			// Ask Java to use its best but slowest scaling
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.drawImage(source, 0, 0, w, h, null);

			writeImage(outputFile, output);

			System.out.println("Source width,height : " + sourceW + ","
					+ sourceH);
			System.out.println("Output width,height : " + w + "," + h);
			// Assume 4 bytes per pixel
			System.out.println("Memory requirements : " + (h * w * 4 / 1024)
					+ " KB");
			System.out.println("Output file:" + outputFile + ", file size : "
					+ outputFile.length() / 1024 + " KB\n");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		}
	}

	/**
	 * Writes out an image to the file using the file extension to determine the
	 * image format.
	 * 
	 * @param outputFile
	 *            the target file. The filetype is determined by the extension
	 *            e.g. png, jpg
	 * @param image
	 *            the image to save
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void writeImage(File outputFile, BufferedImage image)
			throws FileNotFoundException, IOException {

		// A lot of 'boilerplate' code to ask Java to save an image!

		// Extract the file extension e.g. 'jpg' 'png'
		String name = outputFile.getName().toLowerCase();
		String suffix = name.substring(name.lastIndexOf('.') + 1);

		boolean isJPG = suffix.endsWith("jpg");
		
		Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix(suffix);
		if (!writers.hasNext())
			System.err.println("Unrecognized image file extension " + suffix);

		// Check we can create a new, empty output file
		outputFile.createNewFile();

		ImageWriter writer = writers.next();
		writer.setOutput(new FileImageOutputStream(outputFile));

		ImageWriteParam param = writer.getDefaultWriteParam();
		// png files don't support compression and will throw an exception if we
		// try to set compression mode
		if (isJPG) {
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(1); // High quality
		}
		IIOImage iioImage = new IIOImage(image, null, null);
		writer.write(null, iioImage, param);
	}
}

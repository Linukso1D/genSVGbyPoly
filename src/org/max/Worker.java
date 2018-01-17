package org.max;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.max.gen.Helper;
import org.max.gen.Workarea;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Class description goes here.
 * Created by Linukso1D.
 *
 * @author Max Sorokin
 * @version 1.0 16 Jan 2018
 */
public class Worker {

	private Workarea workarea;
	private Color[][] sourceColors;
	private int step;
	private boolean isRunning = false;
	private double fitness;
	private Scene mainFrame;

	public void doAction(File imageSrc) throws IOException {
		BufferedImage image = ImageIO.read(imageSrc);

		Helper.Width = image.getWidth();
		Helper.Height = image.getHeight();
		sourceColors = new Color[image.getWidth()][image.getHeight()];


		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int clr = image.getRGB(i, j);
				int red = (clr & 0x00ff0000) >> 16;
				int green = (clr & 0x0000ff00) >> 8;
				int blue = clr & 0x000000ff;
				Color c1 = new Color(red, green, blue);
				sourceColors[i][j] = c1;
			}
		}

		step = 1;
		fitness = Double.MAX_VALUE;


		if (workarea == null) {
			workarea = new Workarea();
			workarea.SetRandom();
		}

		if (sourceColors.length > 1) {
			isRunning = true;
		}


		while (isRunning) {
			Workarea newarea;
			newarea = (Workarea) workarea.clone();
			newarea.Mutate();

			if (newarea.IsChange) {
				double newfitness = newarea.Fitness(sourceColors);

				if (newfitness <= fitness) {
					workarea = newarea;
					fitness = newfitness;

					if (step++ % 5 == 0) {
						System.out.println("fitness: " + fitness + " polygons: " + workarea.Polygons.size());
						Show();
						step = 1;
					}
				}
			}
		}
	}

	public void Show() {
		Image image = SwingFXUtils.toFXImage(workarea.Draw(), null);
		((ImageView) mainFrame.lookup("#generatedimage")).setImage(image);
	}

	public void setMainFrame(Scene mainFrame) {
		this.mainFrame = mainFrame;
	}

	public Workarea getWorkarea() {
		return workarea;
	}
	public void setWorkarea(Workarea w) {
		this.workarea = w;
	}

	public String toSvg() {
		return workarea.toSvg();
	}
}

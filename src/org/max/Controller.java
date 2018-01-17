package org.max;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.max.gen.Workarea;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Controller {
	File selectedFile;
	Worker w;
	boolean dialog = true;

	@FXML
	private void loadImage(ActionEvent event) {
		System.out.println("LoadImage event: ");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			try {
				BufferedImage bufferedImage = ImageIO.read(selectedFile);
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				((ImageView) Main.mainView.lookup("#originImage")).setImage(image);
			} catch (IOException ignored) {
			}
		}
	}


	@FXML
	private void exportSvg(ActionEvent event) {
		if (!dialog) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Image");
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Save data", "*.svg")
			);
			File file = fileChooser.showSaveDialog(null);
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				bw.write(w.toSvg());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Save must be after start process!");
			alert.showAndWait();
		}
	}

	@FXML
	private void saveData(ActionEvent event) {
		if (!dialog) {
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Data");
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("Save data", "*.ser")
				);
				File file = fileChooser.showSaveDialog(null);
				this.serialize(w.getWorkarea(), file.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Save must be after start process!");
			alert.showAndWait();
		}
	}

	@FXML
	private void loadData(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Load saved data", "*.ser"));
			Object wo = deserialize(fileChooser.showOpenDialog(null).getPath());
			if (wo instanceof Workarea) {
				if (w == null) {
					w = new Worker();
				}
				w.setMainFrame(Main.mainView);
				w.setWorkarea((Workarea) wo);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void start(ActionEvent event) {
		if (selectedFile != null && selectedFile.canRead()) {
			Task<Boolean> task = new Task<Boolean>() {
				@Override
				public Boolean call() throws IOException {
					if (w == null) {
						w = new Worker();
					}
					w.setMainFrame(Main.mainView);
					w.doAction(selectedFile);
					return true;
				}
			};
			dialog = false;
			new Thread(task).start();
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("At first you need select image!");
			alert.showAndWait();
		}

	}

	private Object deserialize(String fileName) throws IOException,
			ClassNotFoundException {
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}

	private void serialize(Object obj, String fileName)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		fos.close();
	}

}

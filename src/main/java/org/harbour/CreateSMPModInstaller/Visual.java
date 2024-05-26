package org.harbour.CreateSMPModInstaller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class Visual extends Application {
	private Data programData = new Data();
	protected ArrayList<File> versionSelectAL;
	protected ArrayList<String> versionSelectName; // empty array used to show name of fabric loader
	protected File selectedModLoaderFile;
	protected File chosenModFolder;
	protected ArrayList<File> mods; 
	@Override
    public void start(Stage mainStage) {
		
		
    	// Final pane for mainStage
    	Pane mainStageFP = new Pane();
    	
    	// left half: modLoader & version selector
    	// replace mlvsVBox with modLoaderVersionSelectVBox
    	VBox mlvsVBox = new VBox(50);
    	mlvsVBox.setPrefSize(350, 255);
    	mlvsVBox.setLayoutX(60);
    	mlvsVBox.setLayoutY(50);
    	mlvsVBox.setAlignment(Pos.CENTER);
    	
    	//RadioButton createProfileRadio = new RadioButton("Create New Mod Profile?");
    	RadioButton createProfileRadio = new RadioButton("Create New Mod Profile?");
    	
    	//replace mlHBox with mLSelectionHBox
    	HBox mlHBox = new HBox(20);
    	mlHBox.setOpacity(.5);
    	mlHBox.setDisable(true);
    	
    	ComboBox mlLS = new ComboBox(programData.getModLoaderOptions());
    	mlLS.setPrefWidth(200);
    	
    	Label mlLabel = new Label("Fabric/Forge");
    	
    	mlHBox.getChildren().addAll(mlLS, mlLabel);
    	
    	// replace vsHBox with verSelectHBox
    	HBox vrHBox = new HBox(20);
    	vrHBox.setOpacity(.5);
    	vrHBox.setDisable(true);
    	
    	ComboBox vrLS = new ComboBox();
    	vrLS.setPrefWidth(500);
    	
    	Label vrLabel = new Label("Version Required");
    	
    	vrHBox.getChildren().addAll(vrLS, vrLabel);
    	
    	mlvsVBox.getChildren().addAll(createProfileRadio, mlHBox, vrHBox);
    	
    	// right half: folder selection for mods
    	
    	// replace rhVBox with mfSelector
    	VBox rhVBox = new VBox(90);
    	rhVBox.setPrefSize(285, 255);
    	rhVBox.setLayoutX(450);
    	rhVBox.setLayoutY(80);
    	
    	Label modFolderSelectLabel = new Label("Select folder where desired mods are located");
    	modFolderSelectLabel.setStyle("-fx-font-size:20");
    	modFolderSelectLabel.textAlignmentProperty().setValue(TextAlignment.CENTER);
    	modFolderSelectLabel.setWrapText(true);
    	
    	HBox modFolderSelectHBox = new HBox();
    	modFolderSelectHBox.setAlignment(Pos.CENTER);
    	
    	TextField selectedModFolderTF = new TextField();
    	selectedModFolderTF.setEditable(false);
    	
    	Button selectedModFolderButton = new Button("Select Folder");
    	
    	modFolderSelectHBox.getChildren().addAll(selectedModFolderTF, selectedModFolderButton);
    	
    	rhVBox.getChildren().addAll(modFolderSelectLabel, modFolderSelectHBox);
    	
    	// Bottom
    	VBox bVBox = new VBox(30);
    	bVBox.setAlignment(Pos.CENTER);
    	bVBox.setLayoutX(280);
    	bVBox.setLayoutY(480);
    	
    	RadioButton verifySelect = new RadioButton("Are Needed Settings Selected?");
    	verifySelect.setStyle("-fx-font-size: 18");
    	
    	Button apply = new Button("APPLY");
    	apply.setOpacity(0.5);
    	apply.setDisable(true);
    	
    	bVBox.getChildren().addAll(verifySelect, apply);
    	
    	
    	// set Scene
    	mainStageFP.getChildren().addAll(mlvsVBox, rhVBox, bVBox);
    	mainStage.setScene(new Scene(mainStageFP, 800,600));
    	mainStage.show();
    	
    	// set up second stage
        Stage copyCompleteStage = new Stage();
        copyCompleteStage.initModality(Modality.APPLICATION_MODAL);
        //Platform.runLater(() ->{}); could be a way to update my label
        BorderPane stageTwoFinal = new BorderPane();
        
        VBox stageTwoVBox = new VBox(20);
        stageTwoVBox.setAlignment(Pos.CENTER);
        Label stageTwoLabel = new Label();
        stageTwoLabel.setText("All mod in selected folder have been copied successfully!");
        
        stageTwoLabel.setStyle("-fx-font-size: 20");
        Button stageTwoButton = new Button("CLOSE");
        
        stageTwoVBox.getChildren().addAll(stageTwoLabel, stageTwoButton);
        
        stageTwoFinal.setCenter(stageTwoVBox);
        
        Scene scene2 = new Scene(stageTwoFinal, 400, 200);
        copyCompleteStage.setScene(scene2);
        
        // set up thrid stage
		Stage noModsSelectedStage = new Stage();
		noModsSelectedStage.initModality(Modality.APPLICATION_MODAL);
		
		Pane noModsPane = new Pane();
		VBox noModsVBox = new VBox();
		Label noModsLabel = new Label("No folder was selected. Please try again");
		Button noModsButton = new Button("Go Back");
		noModsVBox.getChildren().addAll(noModsLabel, noModsButton);
		noModsPane.getChildren().add(noModsVBox);
		noModsSelectedStage.setScene(new Scene(noModsPane, 200, 200));
    	
    	/**
    	 * 
    	 * Event Handlers
    	 * 
    	 */
    	
    	// Create Profile Radio Button
    	createProfileRadio.setOnMouseClicked(e -> {
    		if (createProfileRadio.isSelected()) {
    			mlHBox.setOpacity(1);
    			mlHBox.setDisable(false);
    		} else {
    			
    			mlHBox.setOpacity(.5);
    			mlHBox.setDisable(true);
    			mlLS.getSelectionModel().clearSelection();
    			vrHBox.setOpacity(.5);
    			vrHBox.setDisable(true);
    			vrLS.getSelectionModel().clearSelection();
    			
    			
    			
    		}
    	});
    	
    	// mod loader list select
    	mlLS.setOnAction(e -> {
    		Object selectedModLoader = mlLS.getSelectionModel().getSelectedItem();
    		vrHBox.setOpacity(1);
    		vrHBox.setDisable(false);
    		
    		if (selectedModLoader.toString().contains("Fabric")) {
    			String rootFileLocation = "";
    			try {
    				rootFileLocation = ClassLoader.getSystemClassLoader().getResource(".").toURI().getPath().replaceFirst("/", "");
    			} catch (URISyntaxException e1) {
    				e1.printStackTrace();
    			}
    			File fabricLoaderFolder = new File(rootFileLocation + "/modloaders/Fabric");
    			versionSelectName = new ArrayList<>(); // empty array used to show name of fabric loader
    			//ArrayList<File> versionSelectAL = new ArrayList<>(Arrays.asList(fabricLoaderFolder.listFile()));
    			
    			versionSelectAL = new ArrayList<>(Arrays.asList(fabricLoaderFolder.listFiles()));
    			for (File mlFile : versionSelectAL) {
					versionSelectName.add(mlFile.getName());
				}
    			ObservableList<String> versionSelect = FXCollections.observableArrayList(versionSelectName);
    			
    			vrLS.setItems(versionSelect);
    			
    		} else {
    			System.out.println("Forge was selected");
    		}
    	});
    	
    	// Mod Folder Select Button
    	selectedModFolderButton.setOnMouseClicked(e -> {
    		DirectoryChooser selectedFolder = new DirectoryChooser();
    		
    		chosenModFolder = selectedFolder.showDialog(mainStage);
            
            selectedModFolderTF.setText(chosenModFolder.toString());
    		
    	});
    	
    	// Verify button
    	verifySelect.setOnMouseClicked(e -> {
    		if (verifySelect.isSelected()) {
    			apply.setOpacity(1);
    			apply.setDisable(false);
    		} else {
    			apply.setOpacity(.5);
    			apply.setDisable(false);
    		}
    	});
    	
    			// Apply Button
    	apply.setOnMouseClicked(e -> {
    		// Things we need: .minecraft folder (/mods, /versions), our selected mods folder
    		File appData = new File(System.getenv("AppData"));
    		File mcModFolder = new File(appData + "/.minecraft/mods");
    		File mcVersionFolder = new File(appData + "/.minecraft/versions");
    		
    		
    		try {
    		mods = new ArrayList<>(Arrays.asList(chosenModFolder.listFiles()));
    		} catch (NullPointerException npe) {
    			noModsSelectedStage.show();
    		}

    		
    		// we want to get the file location (versionSelectAL: has full path) based on the versionSelect (just file name)
    		// we then create a file based on this info
    		if (createProfileRadio.isSelected() && mlLS.getSelectionModel().isEmpty() == false && vrLS.getSelectionModel().isEmpty() == false) {
    			System.out.println("All options selected!");
    			versionSelectAL.forEach(event -> {
        			if (event.toString().contains(vrLS.getSelectionModel().getSelectedItem().toString())) {
        				selectedModLoaderFile = event.getAbsoluteFile();
        				System.out.println("Path: " + selectedModLoaderFile);
        			}
        		});
        		//String selectedModLoaderString = "\\" + selectedModLoaderFile.getName();
        		String selectedModLoaderString = selectedModLoaderFile.getName();
        		File copyToMCVersionFolder = new File(mcVersionFolder + selectedModLoaderString);
        		
        		try {
        			//FileUtils.copyDirectory(selectedModLoaderFile, mcModFolder);
        			FileUtils.copyToDirectory(selectedModLoaderFile, mcVersionFolder);
        			System.out.println("Check Folder");
        		//Files.copy(selectedModLoaderFile, mcVersionFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
        		} catch (IOException e2) {
        			e2.printStackTrace();
        		}
        		
        		
        		//System.out.println();
        		//System.out.println(versionSelectALIndex);
    			
    		}
    		if(selectedModFolderTF.getText().equals(null) == false) {
    			mods.forEach(eh -> {
    				if (eh.getAbsolutePath().contains(".jar")) {
    					String currentFile = "\\" + eh.getName();
    					File copyToMCModFolder = new File(mcModFolder + currentFile);
    					try {
    						Files.copy(eh.toPath(), copyToMCModFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
    					} catch (Exception ex ) {
    						ex.printStackTrace();
    					}
    				}
    			});
    			
    		} else {

    			noModsSelectedStage.show();
    		}
    		
    		copyCompleteStage.show();
    		
    		selectedModFolderTF.clear();
    		
    	});
    	stageTwoButton.setOnMouseClicked(e -> {
    		copyCompleteStage.close();
    	});
    	
    	noModsButton.setOnMouseClicked(e->{noModsSelectedStage.close();});
    	
    	
    	
    	//
    	
    }

    public static void main(String[] args) {
        launch();
    }

}
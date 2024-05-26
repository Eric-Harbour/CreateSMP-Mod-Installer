package org.harbour.CreateSMPModInstaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Data {

	//private ArrayList<T> modLoaderOptionsArrayList;
	private ObservableList<String> modLoaderOptions;
	
	public Data() {
		this.modLoaderOptions = FXCollections.observableArrayList("Fabric", "Forge");
	}
	
	public ObservableList getModLoaderOptions() {
		
		return modLoaderOptions;
	}
}

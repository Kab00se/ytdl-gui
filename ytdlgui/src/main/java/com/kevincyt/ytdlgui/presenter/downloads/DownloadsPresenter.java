package com.kevincyt.ytdlgui.presenter.downloads;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.kevincyt.ytdlgui.model.YtdlService;
import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DownloadsPresenter implements Initializable {
	
	@FXML private TableView<AbstractYtdlJob> ytdlJobTable;
	
	@Inject private YtdlService ytdlService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ytdlJobTable = new TableView<AbstractYtdlJob>(ytdlService.getJobQueue().getJobList());
		
		// Add columns
		TableColumn<AbstractYtdlJob, Integer> exampleColumn = new TableColumn<AbstractYtdlJob, Integer>("Job ID");
		exampleColumn.setCellValueFactory(new PropertyValueFactory<AbstractYtdlJob, Integer>("id"));;
		
		ytdlJobTable.getColumns().addAll(exampleColumn);
	}

}

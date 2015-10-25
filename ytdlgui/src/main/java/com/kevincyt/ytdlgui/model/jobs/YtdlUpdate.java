package com.kevincyt.ytdlgui.model.jobs;

public class YtdlUpdate extends AbstractYtdlJob {

	public YtdlUpdate(int id, String ytdlPathString){
		super(id, ytdlPathString, "--update");
	}
}

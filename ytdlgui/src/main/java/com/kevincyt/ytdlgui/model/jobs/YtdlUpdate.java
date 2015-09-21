package com.kevincyt.ytdlgui.model.jobs;

public class YtdlUpdate extends AbstractYtdlJob {

	public YtdlUpdate(String ytdlPathString){
		super(ytdlPathString, "--update");
	}
}

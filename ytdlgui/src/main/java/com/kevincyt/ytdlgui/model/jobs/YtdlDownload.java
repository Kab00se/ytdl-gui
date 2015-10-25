package com.kevincyt.ytdlgui.model.jobs;

import java.util.Arrays;
import java.util.List;

public class YtdlDownload extends AbstractYtdlJob {
	
	public YtdlDownload(int id, String ytdlPathString, List<String> arguments){
		super(id, ytdlPathString, arguments);
	}
	
	public YtdlDownload(int id, String ytdlPathString, String... arguments){
		this(id, ytdlPathString, Arrays.asList(arguments));
	}

}

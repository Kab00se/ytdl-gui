package com.kevincyt.ytdlgui.model.jobs;

import java.util.Arrays;
import java.util.List;

public class YtdlDownload extends AbstractYtdlJob {
	
	public YtdlDownload(String ytdlPathString, List<String> arguments){
		super(ytdlPathString, arguments);
	}
	
	public YtdlDownload(String ytdlPathString, String... arguments){
		this(ytdlPathString, Arrays.asList(arguments));
	}

}

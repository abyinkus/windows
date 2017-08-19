package com.oocode;

import com.oocode.WindowsModels.FrameSize;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Main {
	public static void main(String[] args) throws Exception {
		int width = Integer.parseInt(args[0]); // the width of the window
		int height = Integer.parseInt(args[1]); // the height of the window
		int totalWindows = Integer.parseInt(args[2]); // the number of windows
														// of this size
		String modelName = args[3]; // the model name of these windows

		OkHttpClient client = new OkHttpClient();

		// the thickness of the frame depends on the model of window
		FrameSize frameSize = FrameSize.getByModel(modelName);
		int thicknessWidth = frameSize.frameWidth;
		int thicknessHeight = frameSize.frameHeight;

		// the glass pane is the size of the window minus allowance for
		// the thickness of the frame
		int totalArea = (width - thicknessWidth) * (height - thicknessHeight) * totalWindows;

		// we suppose the type of these windows is plain
		String windowsType = getConformType(height, width, totalArea);

		RequestBody requestBody = BodyBuilder.bodyBuilder(width, height, totalWindows, thicknessWidth, thicknessHeight,
				windowsType);

		// we suppose it's a small order
		String url = "https://immense-fortress-19979.herokuapp.com/order";
		// if it is not we change the url
		if (isLarge(windowsType, totalArea)) {
			url = "https://immense-fortress-19979.herokuapp.com/large-order";
		}
		Request request = new Request.Builder().url(url).method("POST", RequestBody.create(null, new byte[0]))
				.post(requestBody).build();

		try (Response response = client.newCall(request).execute()) {
			try (ResponseBody body = response.body()) {
				assert body != null;
				System.out.println(body.string());
			}
		}
	}
	
	private static boolean isLarge(String windowsType, int totalArea){
		return (windowsType.equals("plain") && totalArea > 20000) || (windowsType.equals("toughened") && totalArea > 18000);
	}
	
	private static String getConformType(int h, int w, int totalArea){
		if (h > 120 || totalArea > 3000) {
			return "toughened";
		}
		return "plain";
	}

	public static int thicknessWidth(String r) {
		if (r.equals("Churchill")) {
			return 4;
		}
		if (r.equals("Victoria")) {
			return 2;
		}
		if (r.equals("Albert")) {
			return 3;
		}
		throw null; // model name isn't known
	}

	public static int thicknessHeight(String r) {
		if (r.equals("Churchill")) {
			return 3;
		}
		if (r.equals("Victoria")) {
			return 3;
		}
		if (r.equals("Albert")) {
			return 4;
		}
		throw null; // model name isn't known
	}
}

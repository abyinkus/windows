package com.oocode;

public class WindowsModels {
	public static enum FrameSize {
		CHURCHILL("Churchill", 4, 3), VICTORIA("Victoria", 2, 3), ALBERT("Albert", 3, 4);

		public String modelName;
		public int frameWidth;
		public int frameHeight;

		private FrameSize(String modelName, int frameWidth, int frameHeight) {
			this.modelName = modelName;
			this.frameWidth = frameWidth;
			this.frameHeight = frameHeight;
		}

		public static FrameSize getByModel(String modelName) {

			for (FrameSize frameSize : FrameSize.values()) {
				if (frameSize.modelName.equals(modelName))
					return frameSize;
			}
			return null;
		}
	}
}

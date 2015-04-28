package com.wolaidai;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Operation;

public class WaterMarkApp
{
	private static final Logger logger = Logger.getLogger(WaterMarkApp.class);
	private static final boolean ENABLE_GRAPHICS_MAGICK = true;
	private static ConvertCmd s_convertCmd = new ConvertCmd(ENABLE_GRAPHICS_MAGICK);
	private static CompositeCmd s_compositeCmd = new CompositeCmd(ENABLE_GRAPHICS_MAGICK);

	public static void main(String[] args)
	{
		long l = System.currentTimeMillis();
		generate();
		logger.info("add the watermark for all files success. spend " + (System.currentTimeMillis() - l) + "ms");
	}

	private static void generate()
	{
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.addStyle("medium", "300x300>");
		pictureStyle.addStyle("thumb", "100x100>");
		List<String> resultFileList = readResultFile();
		for (String resultFile : resultFileList)
		{
			boolean result = addWaterMark(resultFile, buildOutputPath(resultFile, "_original_watermark"));
			if (result)
			{
				pictureStyle
						.getStyles()
						.stream()
						.forEach(
								item -> {
									resize(buildOutputPath(resultFile, "_original_watermark"),
											buildOutputPath(resultFile, "_" + item.getKey() + "_watermark"),
											item.getValue());
								});
			}
		}
	}

	private static List<String> readResultFile()
	{
		try
		{
			return FileUtils.readLines(new File(Config.getResultFile()));
		}
		catch (Exception e)
		{
			logger.info("read the result file failed.");
			return Collections.emptyList();
		}
	}

	public static boolean addWaterMark(String srcFilePath, String destFilePath)
	{
		boolean result = false;
		IMOperation operation = new IMOperation();
		operation.dissolve(20);
		// operation.gravity("center");
		operation.tile();
		operation.addImage(3);
		try
		{
			s_compositeCmd.run(operation, Config.getWatermarkPic(), srcFilePath, destFilePath);
			result = true;
		}
		catch (IOException | InterruptedException | IM4JavaException e)
		{
			logger.error("add watermark exception." + srcFilePath, e);
		}
		logger.info("add watermark:" + destFilePath);
		return result;
	}

	public static boolean resize(String srcFilePath, String destFilePath, String styleRule)
	{
		boolean result = false;
		IMOperation operation = new IMOperation();
		operation.addImage(Operation.IMG_PLACEHOLDER);
		operation.addRawArgs("-resize", styleRule);
		operation.addImage(Operation.IMG_PLACEHOLDER);
		try
		{
			s_convertCmd.run(operation, srcFilePath, destFilePath);
			logger.info("resize file:" + destFilePath);
			result = true;
		}
		catch (IOException | InterruptedException | IM4JavaException e)
		{
			logger.error("resize this file exception" + srcFilePath, e);
		}

		return result;
	}

	private static String buildOutputPath(String srcFile, String replace)
	{
		return srcFile.replace("_original", replace);
	}
}

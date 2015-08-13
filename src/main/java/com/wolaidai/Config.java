package com.wolaidai;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class Config
{
	private static Properties m_prop;
	
	
	public static String getDocumentFolder()
	{
		return m_prop.getProperty("documents.folder");
	}
	
	public static String getWatermarkPic()
	{
		return m_prop.getProperty("watermark.file");
	}
	
	public static String getResultFile()
	{
		return System.getProperty("user_dir") + File.separator + "result.txt";
	}
	
	public static String getFailedFilePath()
	{
		return System.getProperty("user_dir") + File.separator + "failed.txt";
	}
	
	public static String getPoolSize()
	{
		return m_prop.getProperty("thread.pool.size");
	}
	
	static
	{
		loadConfigFile();
	}

	public static void loadConfigFile()
	{
		m_prop = new Properties();
		InputStream input = null;

		try
		{
			String filename = System.getProperty("user_dir") + File.separator + "config.properties";
			input = new FileInputStream(new File(filename));
			m_prop.load(input);
			System.out.println("load the config file success!");
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}

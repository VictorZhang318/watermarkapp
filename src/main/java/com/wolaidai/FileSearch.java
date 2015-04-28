package com.wolaidai;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class FileSearch
{
	private static final Logger logger = Logger.getLogger(FileSearch.class);
	private static final List<String> fileNameToSearch = Arrays.asList("id_back_proof_original",
			"id_front_proof_original");
	private List<String> result = new ArrayList<String>();
	private File searchResultFile;

	public File getSearchResultFile()
	{
		return searchResultFile;
	}

	public void setSearchResultFile(File searchResultFile)
	{
		this.searchResultFile = searchResultFile;
	}

	public List<String> getResult()
	{
		return result;
	}

	public static void main(String[] args) throws IOException
	{
		long l = System.currentTimeMillis();
		FileSearch fileSearch = new FileSearch();
		File resultFile = new File(Config.getResultFile());
		// try different directory and filename :)
		fileSearch.searchDirectory(new File(Config.getDocumentFolder()), resultFile);
		FileUtils.writeLines(resultFile, fileSearch.getResult());
		int count = fileSearch.getResult().size();
		logger.info("find [" + count + "] file spend" + (System.currentTimeMillis() - l));
	}

	public void searchDirectory(File directory, File resultFile) throws IOException
	{

		if (directory.isDirectory())
		{
			search(directory, resultFile);
		}
		else
		{
			logger.error(directory.getAbsoluteFile() + " is not a directory!");
		}
	}

	private void search(File file, File resultFile) throws IOException
	{
		if (file.isDirectory())
		{
			// do you have permission to read this directory?
			if (file.canRead())
			{
				for (File temp : file.listFiles())
				{
					if (temp.isDirectory())
					{
						search(temp, resultFile);
					}
					else
					{
						if (fileNameToSearch.contains(extractFileName(temp.getName())))
						{
							result.add(temp.getAbsolutePath());
							logger.info(temp.getAbsolutePath());
						}
					}
				}
			}
			else
			{
				logger.error(file.getAbsoluteFile() + "Permission Denied");
			}
		}

	}

	private static String extractFileName(String fileName)
	{
		int index = fileName.lastIndexOf(".");
		if (index == -1)
		{
			return fileName;
		}
		return fileName.substring(0, index);
	}

}
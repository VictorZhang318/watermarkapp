package com.wolaidai;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;

public class Test
{
	public static void main(String[] args)
	{
//		String resultTxt ="/Users/victor/Downloads/result.txt";
//		String stevenTxt = "/Users/victor/Downloads/4.txt";
//		
//		List<String> resultList = readResultFile(resultTxt);
//		List<String> stevenList = readResultFile(stevenTxt);
//		for(String str:stevenList)
//		{
//			if(!resultList.contains(str))
//			{
//				System.out.println(str);
//			}
//		}
		
		List<String> resultList = new ArrayList<String>();
		resultList.add("1");
		resultList.add("1");
		System.out.println(resultList.stream().distinct().count());
	}
	
	private static List<String> readResultFile(String path)
	{
		try
		{
			return FileUtils.readLines(new File(path));
		}
		catch (Exception e)
		{
			return Collections.emptyList();
		}
	}
	
	private static boolean isPrime(int number) {        
	    return number > 1
	            && IntStream.range(2, number).noneMatch(
	                    index -> number % index == 0);
	}
	
}

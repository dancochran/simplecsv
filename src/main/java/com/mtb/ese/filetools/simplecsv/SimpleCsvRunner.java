package com.mtb.ese.filetools.simplecsv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class SimpleCsvRunner implements CommandLineRunner
{
	public static final String SEPARATOR = ",";
	
	@Override
	public void run(String... args) throws Exception
	{
		// usage params: #fields minlen maxlen #rows filename
		int numFields = 2;
		int minLen = 3;
		int maxLen = 12;
		int numRows = 6;
		String filename = "output.csv";
		
		if (args.length < 5)
		{
			System.out.println("Not all arguments passed, using defaults where needed. Arguments: #fields minlen maxlen #rows");
		}
		
		int i = 0;
		for (String arg : args)
		{
			try
			{
				int argval;
				switch (i)
				{
					case 0: argval = Integer.parseInt(arg);
							numFields = argval;
							break;
					case 1: argval = Integer.parseInt(arg);
							minLen = argval;
							break;
					case 2: argval = Integer.parseInt(arg);
							maxLen = argval;
							break;
					case 3: argval = Integer.parseInt(arg);
							numRows = argval;
							break;
					case 4: filename = arg;
							break;
					default: System.out.println("Argument [" + i + "] not used, ignoring");
							break;
				}
			}
			catch (NumberFormatException nfex)
			{
				System.out.println("Argument [" + i + "] invalid, using default value");
			}
			
			i++;
		}
		
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter(filename));
		}
		catch (IOException ioex)
		{
			System.out.println("Error creating file");
			ioex.printStackTrace();
			System.exit(-1);
		}
		
		for (int j = 0; j < numRows; j++)
		{
			StringBuilder sb = new StringBuilder();
			for (int k = 1; k <= numFields; k++)
			{
				String generatedString = RandomStringUtils.randomAlphabetic(minLen, maxLen+1);
				sb.append(generatedString);
				if (k != numFields)
				{
					sb.append(SEPARATOR);
				}
			}
			sb.append(System.lineSeparator());
			
			//System.out.println(sb.toString());
			writer.write(sb.toString());
		}
	
		try
		{
			writer.close();
		}
		catch (IOException ioex)
		{
			// ...
		}
		
	}

}

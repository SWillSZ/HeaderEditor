import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringJoiner;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FileEditorTools 
{
	
	// taken from http://stackoverflow.com/questions/779519/delete-directories-recursively-in-java
	public static void delete(File f) throws Exception 
	{
		  if (f.isDirectory()) 
		  {
			  for (File c : f.listFiles())
		      delete(c);
		  }
		  if (!f.delete())
		  throw new Exception("Failed to delete file: " + f);
	}
	
	// Needs Work, temporary proof of concept
	static boolean unZip(File toUnzip)
	{
		try
		{
		ZipFile zipFile = new ZipFile(toUnzip);
		// Room for Improvement on this method
		zipFile.extractAll(toUnzip.getAbsolutePath()+"TemporaryFolderHeaderEditor");
		return true;
		}
		catch (Exception E)
		{
		return false;
		}
	}
	
	// Needs Work, temporary proof of concept
	static boolean reZip(File toRezip) 
	{
		try
		{
		ZipFile zipFile = new ZipFile(toRezip.getAbsolutePath()+"new");
		String folderToAddFrom = toRezip.getAbsolutePath()+"TemporaryFolderHeaderEditor";
		File[] filesToAdd = (new File(folderToAddFrom)).listFiles();
		ZipParameters parameters = new ZipParameters();	
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			for (int count = 0;count<filesToAdd.length;count++)
			{
				if (filesToAdd[count].isFile())
				{
				zipFile.addFile(filesToAdd[count], parameters);
				}
				else
				{
				zipFile.addFolder(filesToAdd[count], parameters);
				}
			}
		delete(new File(folderToAddFrom));
		}
		catch (Exception E)
		{
		System.out.println("An error occurred. Make into dialog boxes");
		}
	return false;
	}
	
	static String fileToString(File toOpen)
	{
		try
		{
		FileReader toReadFrom = new FileReader(toOpen);
		BufferedReader toRead=new BufferedReader(toReadFrom);
		// We convert the document into a single string
		String[] strArray =  new String[10000];
		int lineNumber = 0;
		strArray[lineNumber]=toRead.readLine();
		String soFar=strArray[lineNumber];
		// Get each line in the file
			while (soFar!=null)
			{
			lineNumber++;
			strArray[lineNumber]=toRead.readLine();
			soFar=strArray[lineNumber];
			}
		// Combine the lines in the file
		StringJoiner joiner = new StringJoiner("");
			for (int count=0;count<lineNumber;count++)
			{
			joiner.add(strArray[count]);
			} 
		String data = joiner.toString(); 
		toRead.close();
		return data;
		}
		catch (Exception E)
		{
		return null;
		}
	}
	
	static FileHeaderData findHeaders(String toParse, File fileFrom)
	{
	FileHeaderData dataToReturn = new FileHeaderData();
	Document doc = Jsoup.parse(toParse);
    Elements headers = doc.select("w|t");
    dataToReturn.numHeaders = headers.size();
    int currentPos = 0;
		for (Element header : headers)
		{
		dataToReturn.headersFound[currentPos]=header.text();
		currentPos++;
		}
	dataToReturn.foundIn = fileFrom;
	return dataToReturn;
	}
	
	
	// Returns the file header, or null if there is none
	public static FileHeaderData returnHeader(File toOpen)
	{
	unZip(toOpen);
	String fileContents = fileToString(new File(toOpen+"TemporaryFolderHeaderEditor/word/header1.xml"));
	reZip(toOpen);
		if (fileContents != null && fileContents.length()>0)
		{
		FileHeaderData toReturn = findHeaders(fileContents, toOpen);
		return toReturn;
		}
	return null;
	}
	
	
	
}

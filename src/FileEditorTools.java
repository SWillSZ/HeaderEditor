import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.jsoup.Jsoup;

public class FileEditorTools 
{
	// Needs Work, temporary proof of concept
	public static boolean unZip(File toUnzip)
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
	public static boolean reZip(File toRezip) 
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
		
		}
		catch (Exception E)
		{
		System.out.println("An error occurred. Make into dialog boxes");
		}
	return false;
	}
	
	public static String[] returnHeaders(File toOpen)
	{
	unZip(toOpen);
	reZip(toOpen);
	return new String[]{""};
	}
	
}

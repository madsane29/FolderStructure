package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
	
	// The task took approximately 3 hours to finish
	
	public static TreeItem getWritableFolderStructure(List<String> readableFolders, List<String> writableFolders) {
		BuildTreeItemService btis = new BuildTreeItemService();
		
		TreeItem root = new TreeItem("/");
		
		// In case if there are paths with folders in writableFolders while these are missing from readableFolders
		readableFolders.addAll(writableFolders.stream().filter(e -> !readableFolders.contains(e)).collect(Collectors.toList()));
		
		// Have to build up a tree that contains all writable folders
		for (String pathAndFolder : writableFolders) {
			// Verifying if all the folders in the path are readable
			if (btis.areParentsReadable(pathAndFolder, readableFolders)) {
				// Build all folders into the tree (even the ones that are only readable but have writable descendant)
				btis.buildTree(pathAndFolder, root);
			}
		}
		
		//System.out.println(root.toString());
		
		return root;
	}


	
	public static void main(String[] args) {
		List<String> readable = new ArrayList<String>() {
			{
				add("/readableRoot");
				add("/readableRoot/readableButNotWritableChild");				
			}
		};
		List<String> writable = new ArrayList<String>() {
			{
				add("/readableRoot/readableAndWritableChild");
				add("/readableRoot/readableAndWritableChild/readableRootWritableParent");
				add("/nonExistentRoot/child");
				
				//add("/var/tom");
				//add("/var/lib/jenkins");
				//add("/var/lib/jenkins2");
				//add("/var/lib/jenkins2/tomcat");
				//add("/var/lib/jenkins1/bla");
			}
		};
		
		getWritableFolderStructure(readable, writable);
	}
}

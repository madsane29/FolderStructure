package main;

import java.util.List;

public class BuildTreeItemService {
	
	
	/**
	 * Verifies if all the folders that is in the given String parameter is readable.
	 * If yes, will return true. If not, will return false;
	 * 
	 * @param pathAndFolderName The path with folder name.
	 * @param readableFolders The list of readable folders.
	 * @return true if all folders are readable that's in the path, otherwise false
	 */
	public boolean areParentsReadable(String pathAndFolderName, List<String> readableFolders) {
		String[] paths = pathAndFolderName.split("/");
		String path = "";

		for (int i = 1; i < paths.length-1; i++) {

			path += "/" + paths[i];
			
			if (!readableFolders.contains(path)) {
				return false;
			}
		}
		return true;
	}

	
	
	/**
	 * Builds into the given tree the folders that are found in the given String parameter
	 * 
	 * @param pathAndFolderName The path with the folder name.
	 * @param root The root of the tree.
	 */
	public void buildTree(String pathAndFolderName, TreeItem root) {

		String[] pathArray = pathAndFolderName.split("/");
		String path = "";

		for (int i = 1; i < pathArray.length; i++) {
			path += "/" + pathArray[i];
			
			// In case the folder is not built into the tree
			if (!isExists(root, path))
				// If it has only one '/', it means it is at the end of the given path (at the writable folder) and it is on the right level
				if (countSlashes(path) == 1) {
					root.getChildren().add(new TreeItem(path));
				} 
				// There are more than one '/' in the path so it is not on the right level of the tree.
				// Have to go deeper so first it finds the child that's name is equals with the first part of the path
				// and calls the buildTree() method on it
				else {
					for (TreeItem child : root.getChildren()) {
						if (child.getName().equals(getFirstPart(path))) {
							buildTree(removeFirstPart(path), child);
						}
					}
				}
			}
		}
	
	/**
	 * Verifies if the folder exists in between children of the given root
	 * 
	 * @param root The root
	 * @param folder Name of the folder
	 * @return true if it's already exists, otherwise false
	 */
	private boolean isExists(TreeItem root, String folder) {
		for (TreeItem child : root.getChildren()) {
			if (child.getName().equals(folder)) {
				return true;
			}
		}
		return false;
	}

	
	// 
	/**
	 * Used to remove the string between the first and second '/' character, including the first '/'
	 * 
	 * @param pathAndFolderName The path with the folder name
	 * @return The given string after the second '/', including first '/'
	 */
	private String removeFirstPart(String pathAndFolderName) {
		StringBuilder sb = new StringBuilder();
		
		int count = 0;
		for (int i = 0; i < pathAndFolderName.length(); i++) {
			if (pathAndFolderName.charAt(i) == '/')	count++;
			if (count >= 2)
				sb.append(pathAndFolderName.charAt(i));
		}
		
		return sb.toString();
	}

	
	/**
	 * Returns the string what's between the first and second '/' character, including the first '/'
	 * 
	 * @param pathAndFolderName The path with the folder name
	 * @return The given string before the second '/'
	 */
	private String getFirstPart(String pathAndFolderName) {
		int count = 0;
		for (int i = 0; i < pathAndFolderName.length(); i++) {
			if (pathAndFolderName.charAt(i) == '/')
				count++;
			if (count == 2)
				return pathAndFolderName.substring(0, i);
		}
		return "/";
	}

	
	/**
	 * Counts the character '/' in a given string
	 * 
	 * @param str The string
	 * @return Number of '/' chars in the string
	 */
	private long countSlashes(String str) {
		return str.chars().filter(c -> c == '/').count();
	}
}

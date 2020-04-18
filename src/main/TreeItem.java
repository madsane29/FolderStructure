package main;

import java.util.ArrayList;
import java.util.List;

public class TreeItem {
	private String name;
	private List<TreeItem> children;

	public TreeItem(String name) {
		this.name = name;
		children = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		String temp = "";
		if (children.size() > 0) {
			temp = "Parent name: " + name + " -> Children: ";

			for (TreeItem treeItem : children) {
				temp += treeItem.name;
				if (!treeItem.equals(children.get(children.size() - 1)))
					temp += ", ";

			}
			temp += "\n";

			for (TreeItem treeItem : children) {
				temp += treeItem.toString();
			}
		}
		return temp;
	}

	public String getName() {
		return name;
	}

	public List<TreeItem> getChildren() {
		return children;
	}

}

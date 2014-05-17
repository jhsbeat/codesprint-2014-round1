package com.hosang.skp.codesprint.tree;

public class TreeNode {

	public char letter;
	public TreeNode[] links;
	public boolean fullWord;
    
    public TreeNode(char letter)
    {
        this.letter = letter;
        links = new TreeNode[26];
        this.fullWord = false;
    }
}

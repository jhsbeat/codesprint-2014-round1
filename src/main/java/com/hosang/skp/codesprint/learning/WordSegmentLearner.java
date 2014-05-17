package com.hosang.skp.codesprint.learning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.hosang.skp.codesprint.tree.TreeNode;

public class WordSegmentLearner {

	private String corpusFilePath;

	public WordSegmentLearner(String[] args) {
		super();
		corpusFilePath = args[0];
	}
	
	public boolean run(){
		try {
//			TreeNode tree = createTree();
//			
//			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(this.corpusFilePath))));
//			String readLine = "";
//			while((readLine = br.readLine()) != null){
//				readLine = readLine.toLowerCase().replaceAll("[^A-Za-z]", " ");
//				for(String word : readLine.split(" ")){
//					if("".equals(word)){
//						continue;
//					}
//					insertWord(tree, word);
//				}
//			}
//			br.close();
//			
//	        char[] branch = new char[50];
////	        printTree(tree, 0, branch);
//	        
//	        String searchWord = "mixedbulgogistew";
//	        if (find(tree, searchWord))
//	        {
//	            System.out.println("The word was found");
//	        }
//	        else
//	        {
//	            System.out.println("The word was NOT found");
//	        }
			
			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(this.corpusFilePath))));
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.corpusFilePath + ".trained.txt"));
			WordCounter wordCounter = new WordCounter();
			String readLine = "";
			while((readLine = br.readLine()) != null){
				readLine = readLine.toLowerCase().replaceAll("[^A-Za-z]", " ");
				for(String word : readLine.split(" ")){
					if("".equals(word)){
						continue;
					}
					wordCounter.addWord(word.trim());
				}
			}
			br.close();
			
			for(String word : wordCounter.getWordCountMap().keySet()){
				
				if(word.length() == 1){
					System.out.println("##### " + word);
				}
				bw.write(word + "\t" + wordCounter.getWordCountMap().get(word) + "\n");
			}
			bw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	static TreeNode createTree()
    {
        return(new TreeNode('\0'));
    }
    
    static void insertWord(TreeNode root, String word)
    {
        int offset = 97;
        int l = word.length();
        char[] letters = word.toCharArray();
        TreeNode curNode = root;
        
        for (int i = 0; i < l; i++)
        {
            if (curNode.links[letters[i]-offset] == null)
                curNode.links[letters[i]-offset] = new TreeNode(letters[i]);
            curNode = curNode.links[letters[i]-offset];
        }
        curNode.fullWord = true;  
    }

    static boolean find(TreeNode root, String word)
    {
        char[] letters = word.toCharArray();
        int l = letters.length;
        int offset = 97;
        TreeNode curNode = root;
        
        int i;
        for (i = 0; i < l; i++)
        {
            if (curNode == null)
                return false;
            curNode = curNode.links[letters[i]-offset];
        }
        
        if (i == l && curNode == null)
            return false;
        
        if (curNode != null && !curNode.fullWord)
            return false;
        
        return true;
    }
    
    static void printTree(TreeNode root, int level, char[] branch)
    {
        if (root == null)
            return;
        
        for (int i = 0; i < root.links.length; i++)
        {
            branch[level] = root.letter;
            printTree(root.links[i], level+1, branch);    
        }
        
        if (root.fullWord)
        {
            for (int j = 1; j <= level; j++)
                System.out.print(branch[j]);
            System.out.println();
        }
    }
	
}
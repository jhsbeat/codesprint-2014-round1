package com.hosang.skp.codesprint.solver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.hosang.skp.codesprint.tree.TreeNode;

public class WordSegmentSolver {

	private String modelFilePath;
	private String queryFilePath;
	private Map<String, Integer> wordCountMap;
	private TreeNode tree;

	public WordSegmentSolver(String[] args) {
		super();
		this.modelFilePath = args[0];
		this.queryFilePath = args[1];
		this.wordCountMap = new HashMap<String, Integer>();
	}
	
	public boolean run(){
		try {

			// Create a new Prefix Tree
			tree = new TreeNode('\0');
			
			// Load Model data into the memory
			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(this.modelFilePath))));
			String readLine = "";
			while((readLine = br.readLine()) != null){
				try {
					insertWord(tree, readLine.split("\t")[0], Integer.parseInt(readLine.split("\t")[1]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			br.close();
			
//			char[] branch = new char[50];
//	        printTree(tree, 0, branch);
	        
			// Solve the problem
			br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(this.queryFilePath))));
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.queryFilePath + ".answers.txt"));
			String query = "";
			String answer = "";
			
			int mismatchCount = 0;
			while((readLine = br.readLine()) != null){
				if("".equals(readLine.trim())){
					continue;
				}
				query = readLine.split("\t")[0];
				answer = this.solve(query);
				if(!answer.equals(readLine.split("\t")[1])){
					mismatchCount++;
					System.out.println("!!! Different: " + query + "\t" + readLine.split("\t")[1] + "\t" + answer);
				}
				bw.write(query + "\t" + answer + "\n");
			}
			br.close();
			bw.close();
			
			System.out.println("##### Total " + mismatchCount + " lines are mismatched.");
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Query String 을 기준으로 정답 문자열을 반환한다.
	 * @param query
	 * @return
	 */
	private String solve(String query){
		Answer answer = new Answer();
//		this.recursiveSegmentation(query, "", answer, 0, query.length() / 2);
		StackParam param = new StackParam();
		param.setQuery(query);
		param.setPrefix("");
		param.setAnswer(answer);
		param.setMatchCount(0);
		param.setWordFreq(0);
		param.setLimit(query.length() / 2);
		this.nonRecursiveSegmentation(param);
		return answer.getAnswer();
	}
	
	
	
	/**
	 * Query 로 부터 segment 를 분리하는 Non-recursive 메서드
	 * 
	 * Segmentation 기준 우선순위
	 * Score = Dictionary상에 존재하는 단어의 개수 / Segmentation 된 단어의 개수
	 * 1. score 값이 높은 것 우선
	 * 2. score 값이 같다면, 전체 word의 개순가 적은 것이 우선
	 * @param firstParam
	 */
	private void nonRecursiveSegmentation(StackParam firstParam){
		
		Stack<StackParam> stack = new Stack<StackParam>();
		stack.push(firstParam);
		
		while( !stack.isEmpty() ) {

			StackParam param = stack.pop();
			String segment = "";
			for(int i = 1 ; i <= param.getQuery().length() ; i++){
				segment = param.getQuery().substring(0, i);	// m, mi, mix, mixe, mixed, mixedb, mixedbu, mixedbul, mixedbulg...
				
				if(find(tree, segment, true)){
					TreeNode curNode = get(tree, segment, true);
					
//					System.out.println("##### " + curNode.wordCount);
//					System.out.println("#### Before wordFreq: " + param.getWordFreq() + ", current wordFreq: " + (param.getWordFreq() + curNode.wordCount));
					
					// 전체 문자열인 경우
					if(i == param.getQuery().length()){
						
						int wordCount = (param.getPrefix() + segment).split(" ").length;
						int wordFreq = param.getWordFreq() + curNode.wordCount;
						float score =  (float)(param.getMatchCount()+1) / (float)wordCount;
//						System.out.println("#### Before score: " + param.getAnswer().getScore() + ", current score: " + score);
						
						if(param.getAnswer().getScore() < score || (param.getAnswer().getScore() == score && param.getAnswer().getWordCount() > wordCount)
								|| (param.getAnswer().getScore() == score && param.getAnswer().getWordCount() == wordCount && param.getAnswer().getWordFreq() < wordFreq)){
							param.getAnswer().setAnswer(param.getPrefix() + segment);
							param.getAnswer().setScore(score);
							param.getAnswer().setWordCount(wordCount);
							param.getAnswer().setWordFreq(wordFreq);
						}
						break;
//						return;
					}else if(segment.length() > 1 || "a".equals(segment)){
						
						StackParam newParam = new StackParam();
						newParam.setQuery(param.getQuery().substring(i, param.getQuery().length()));
						newParam.setPrefix(param.getPrefix() + segment + " ");
						newParam.setAnswer(param.getAnswer());
						newParam.setMatchCount(param.getMatchCount()+1);
						newParam.setWordFreq(param.getWordFreq() + curNode.wordCount);
						newParam.setLimit(param.getLimit());
						
						stack.push(newParam);
					}
					
				}
			}

		}
		
		
	}
	
//	/**
//	 * Query 로 부터 segment 를 분리하는 재귀 메서드
//	 * @param query
//	 * @param length
//	 * @param prefix
//	 */
//	private void recursiveSegmentation(String query, String prefix, Answer answer, int matchCount, int limit){
//		// Segmentation 기준 우선순위
//		// Score = Dictionary상에 존재하는 단어의 개수 / Segmentation 된 단어의 개수 
//		// score 값이 높은 것 우선 
//		// score 값이 같다면, 전체 word의 개순가 적은 것이 우선
//		
//		String segment = "";
//		for(int i = 1 ; i <= query.length() ; i++){
//			segment = query.substring(0, i);	// m, mi, mix, mixe, mixed, mixedb, mixedbu, mixedbul, mixedbulg...
//			if(find(tree, segment)){
//				
////				if(!validPrefix(prefix)){
////					return;
////				}
//
//				// 전체 문자열인 경우
//				if(i == query.length()){
//					
//					int wordCount = (prefix + segment).split(" ").length;
//					float score =  (float)(matchCount+1) / (float)wordCount;
//					
//					if(score < 1.0 && score > 0.0){
//						System.out.println(score);
//					}
//					if(answer.getScore() < score || (answer.getScore() == score && answer.getWordCount() > wordCount)){
//						answer.setAnswer(prefix + segment);
//						answer.setScore(score);
//						answer.setWordCount(wordCount);
//					}
//					return;
//				}
//				this.recursiveSegmentation(query.substring(i, query.length()), prefix + segment + " ", answer, matchCount+1, limit);
//			}
//		}
//	}
	
    static void insertWord(TreeNode root, String word, int wordCount)
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
        curNode.wordCount = wordCount;
    }

    static boolean find(TreeNode root, String word, boolean exactMatch)
    {
        char[] letters = word.toCharArray();
        int l = letters.length;
        int offset = 97;
        TreeNode curNode = root;
        
        int i;
        for (i = 0; i < l; i++)
        {
            if (curNode == null){
                return false;
            }
            curNode = curNode.links[letters[i]-offset];

            // 앞부분으로만 일치하는 경우
            if(!exactMatch && i+1 == l && curNode != null){
            	return true;
            }
        }
        
        if (i == l && curNode == null)
            return false;
        
        if (curNode != null && !curNode.fullWord)
            return false;
        
        return true;
    }
    
    static TreeNode get(TreeNode root, String word, boolean exactMatch)
    {
        char[] letters = word.toCharArray();
        int l = letters.length;
        int offset = 97;
        TreeNode curNode = root;
        
        int i;
        for (i = 0; i < l; i++)
        {
            if (curNode == null){
                return null;
            }
            curNode = curNode.links[letters[i]-offset];

            // 앞부분으로만 일치하는 경우
            if(!exactMatch && i+1 == l && curNode != null){
            	return new TreeNode('\0');
            }
        }
        
        if (i == l && curNode == null)
            return null;
        
        if (curNode != null && !curNode.fullWord)
            return null;
        
        return curNode;
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
            System.out.println(", " + root.wordCount);
        }
    }
}

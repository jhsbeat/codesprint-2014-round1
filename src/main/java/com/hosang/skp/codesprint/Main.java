package com.hosang.skp.codesprint;

import java.util.Arrays;

import com.hosang.skp.codesprint.learning.WordSegmentLearner;
import com.hosang.skp.codesprint.solver.WordSegmentSolver;

public class Main {

	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		String executor = args[0];
		
		if("learn".equals(executor)){
			WordSegmentLearner learner = new WordSegmentLearner(Arrays.copyOfRange(args, 1, args.length));
			learner.run();
		}else if("solve".equals(executor)){
			WordSegmentSolver solver = new WordSegmentSolver(Arrays.copyOfRange(args, 1, args.length));
			solver.run();
		}else{
			System.err.println("[Argument Error] The first parameter value must be 'learn' or 'solve'.");
		}
		
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("##### Total elapsed time: " + (estimatedTime / 1000) + " seconds (" + estimatedTime + " milliseconds)");
	}
	
}

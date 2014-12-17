package org.scientist.math.learning.taste.bookcrossing;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli2.OptionException;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.scientist.math.learning.taste.TasteOptionParser;

public final class BookCrossingRecommenderEvaluatorRunner {

	private static final Logger log = Logger
			.getLogger(BookCrossingRecommenderEvaluatorRunner.class);

	private BookCrossingRecommenderEvaluatorRunner() {
		// do nothing
	}

	public static void main(String... args) throws IOException, TasteException,
			OptionException {
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		File ratingsFile = TasteOptionParser.getRatings(args);
		DataModel model = ratingsFile == null ? new BookCrossingDataModel(false)
				: new BookCrossingDataModel(ratingsFile, false);

		double evaluation = evaluator.evaluate(
				new BookCrossingRecommenderBuilder(), null, model, 0.9, 0.3);
		log.info(String.valueOf(evaluation));
	}

}
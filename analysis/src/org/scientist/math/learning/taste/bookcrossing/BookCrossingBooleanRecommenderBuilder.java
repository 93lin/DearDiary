package org.scientist.math.learning.taste.bookcrossing;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

final class BookCrossingBooleanRecommenderBuilder implements RecommenderBuilder {

  @Override
  public Recommender buildRecommender(DataModel dataModel) throws TasteException {
    return new BookCrossingBooleanRecommender(dataModel);
  }

}
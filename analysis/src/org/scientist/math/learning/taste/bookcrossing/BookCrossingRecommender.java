package org.scientist.math.learning.taste.bookcrossing;

import java.util.Collection;
import java.util.List;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * A simple {@link Recommender} implemented for the Book Crossing demo.
 * See the <a href="http://www.informatik.uni-freiburg.de/~cziegler/BX/">Book Crossing site</a>.
 */
public final class BookCrossingRecommender implements Recommender {

  private final Recommender recommender;

  public BookCrossingRecommender(DataModel bcModel) throws TasteException {
    UserSimilarity similarity = new CachingUserSimilarity(new EuclideanDistanceSimilarity(bcModel), bcModel);
    UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, 0.2, similarity, bcModel, 0.2);
    recommender = new GenericUserBasedRecommender(bcModel, neighborhood, similarity);
  }
  
  @Override
  public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
    return recommender.recommend(userID, howMany);
  }

  public List<RecommendedItem> recommend(long userID, int howMany, boolean includeKnownItems) throws TasteException {
    return recommend(userID, howMany, null, includeKnownItems);
  }
  
  @Override
  public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
    return recommender.recommend(userID, howMany, rescorer);
  }
  
  public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer, boolean includeKnownItems)
    throws TasteException {
    return recommender.recommend(userID, howMany, rescorer);
  }
  
  @Override
  public float estimatePreference(long userID, long itemID) throws TasteException {
    return recommender.estimatePreference(userID, itemID);
  }
  
  @Override
  public void setPreference(long userID, long itemID, float value) throws TasteException {
    recommender.setPreference(userID, itemID, value);
  }
  
  @Override
  public void removePreference(long userID, long itemID) throws TasteException {
    recommender.removePreference(userID, itemID);
  }
  
  @Override
  public DataModel getDataModel() {
    return recommender.getDataModel();
  }
  
  @Override
  public void refresh(Collection<Refreshable> alreadyRefreshed) {
    recommender.refresh(alreadyRefreshed);
  }
  
  @Override
  public String toString() {
    return "BookCrossingRecommender[recommender:" + recommender + ']';
  }
  
}
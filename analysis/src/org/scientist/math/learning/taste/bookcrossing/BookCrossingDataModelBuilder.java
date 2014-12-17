package org.scientist.math.learning.taste.bookcrossing;

import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

final class BookCrossingDataModelBuilder implements DataModelBuilder {

  @Override
  public DataModel buildDataModel(FastByIDMap<PreferenceArray> trainingData) {
    return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(trainingData));
  }

}
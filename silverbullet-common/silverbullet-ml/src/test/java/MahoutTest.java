import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by jeffyuan on 2018/11/4.
 */
public class MahoutTest {

    final static int NEIGHBORHOOD_NUM = 2;//临近的用户个数
    final static int RECOMMENDER_NUM = 3;//推荐物品的最大个数

    @Test
    public void testUserCF() throws TasteException, IOException {

        String file = "src/data/testCF.csv";
        DataModel model = new FileDataModel(new File(file));//数据模型
        UserSimilarity user = new EuclideanDistanceSimilarity(model);//用户相识度算法
        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(NEIGHBORHOOD_NUM, user, model);
        //用户近邻算法
        Recommender r = new GenericUserBasedRecommender(model, neighbor, user);//用户推荐算法
        LongPrimitiveIterator iter = model.getUserIDs();///得到用户ID

        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List<RecommendedItem> list = r.recommend(uid, RECOMMENDER_NUM);
            System.out.printf("uid:%s", uid);
            for (RecommendedItem ritem : list) {
                System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
            }
            System.out.println();
        }
    }

    @Test
    public void testItemCF() throws IOException, TasteException {
        String file = "src/data/testCF.csv";
        DataModel model = new FileDataModel(new File(file));//数据模型
        ItemSimilarity item=new EuclideanDistanceSimilarity(model);//用户相识度算法
        Recommender r=new GenericItemBasedRecommender(model,item);//物品推荐算法
        LongPrimitiveIterator iter =model.getUserIDs();
        while(iter.hasNext()){
            long uid=iter.nextLong();
            List<RecommendedItem> list = r.recommend(uid, 1);
            System.out.printf("uid:%s",uid);
            for (RecommendedItem ritem : list) {
                System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
            }
            System.out.println();
        }
    }
}

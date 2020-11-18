import entity.Temp;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author yangxw
 * @Date 2020-11-18 下午3:58
 * @Description
 * @Version 1.0
 */

public class TempTest {


    @Test
    /**
     * 缓存销毁-sqlSession.close()
     */
    public void testCacheCloseCommit() throws IOException, NoSuchFieldException, IllegalAccessException {
        InputStream stream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
        SqlSession sqlSession = factory.openSession();
        entity.Temp temp = sqlSession.selectOne("dao.TempDao.getById", 1);
        System.out.println(temp);
        sqlSession.commit();
//        sqlSession.rollback();

        // insert update delete

        entity.Temp temp2 = sqlSession.selectOne("dao.TempDao.getById", 1);
        System.out.println(temp2);

        System.out.println(temp == temp2);

    }


    @Test
    /**
     * 缓存销毁-sqlSession.close()
     */
    public void testCacheClose() throws IOException, NoSuchFieldException, IllegalAccessException {
        InputStream stream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
        SqlSession sqlSession = factory.openSession();
        entity.Temp temp = sqlSession.selectOne("dao.TempDao.getById", 1);
        System.out.println(temp);

        Field executor = sqlSession.getClass().getDeclaredField("executor");
        executor.setAccessible(true);

        CachingExecutor cachingExecutor = (CachingExecutor) executor.get(sqlSession);
        Field delegate = cachingExecutor.getClass().getDeclaredField("delegate");
        delegate.setAccessible(true);
        SimpleExecutor simpleExecutor = (SimpleExecutor) delegate.get(cachingExecutor);
        Field localCache = simpleExecutor.getClass().getSuperclass().getDeclaredField("localCache");
        localCache.setAccessible(true);
        PerpetualCache perpetualCache = (PerpetualCache) localCache.get(simpleExecutor);
        Field cache = perpetualCache.getClass().getDeclaredField("cache");
        cache.setAccessible(true);
        Map<Object, Object> map = (Map<Object, Object>) cache.get(perpetualCache);
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "  !!!=!!!  " + entry.getValue());
        }
        sqlSession.close();


        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "  !!!=!!!  " + entry.getValue());
        }
        //        entity.Temp temp2 = sqlSession.selectOne("dao.TempDao.getById", 1);
//        System.out.println(temp2);
//
//        System.out.println(temp == temp2);

    }

    @Test
    /**
     * 缓存只针对select
     */
    public void testCacheBegin() throws IOException {
        InputStream stream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
        SqlSession sqlSession = factory.openSession();
        sqlSession.update("dao.TempDao.getById", 1);
        sqlSession.update("dao.TempDao.getById", 1);


    }


    @Test
    /**
     * sql语句必须完全相同
     */
    public void tesSql() throws IOException {
        InputStream stream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
        SqlSession sqlSession = factory.openSession();


        Map map1 = new HashMap();
        map1.put("id", 1);
        map1.put("type", 1);


        Temp temp = sqlSession.selectOne("dao.TempDao.getById5", map1);
        System.out.println(temp);


        Map map2 = new HashMap();
        map2.put("id", 1);
        map2.put("type", 2);
        Temp temp2 = sqlSession.selectOne("dao.TempDao.getById5", map2);
        System.out.println(temp2);

        System.out.println(temp == temp2);


    }


    @Test
    /**
     * 分页相同
     */
    public void testList() throws IOException {
        InputStream stream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
        SqlSession sqlSession = factory.openSession();

        RowBounds rowBounds = new RowBounds(0, 1);


        List<Temp> temp = sqlSession.selectList("dao.TempDao.getById4", null, rowBounds);
        System.out.println(temp);


        RowBounds rowBounds2 = new RowBounds(0, 2);
        List<Temp> temp2 = sqlSession.selectList("dao.TempDao.getById4", null, rowBounds2);
        System.out.println(temp2);

        System.out.println(temp == temp2);


    }

    @Test
    /**
     * 要求传入sql语句的参数要一致才能用到缓存
     */
    public void testParams() throws IOException {
        InputStream stream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
        SqlSession sqlSession = factory.openSession();
//        entity.Temp temp = sqlSession.selectOne("dao.TempDao.getById", 1);
//        System.out.println(temp);
//
//        entity.Temp temp2 = sqlSession.selectOne("dao.TempDao.getById", 2);
//        System.out.println(temp2);
//        System.out.println(temp==temp2);


        Map map1 = new HashMap();
        map1.put("id", 1);
        map1.put("test", 1);

        Map map2 = new HashMap();
        map2.put("id", 1);
        map2.put("test", 2);

        entity.Temp temp3 = sqlSession.selectOne("dao.TempDao.getById3", map1);
        System.out.println(temp3);


        entity.Temp temp4 = sqlSession.selectOne("dao.TempDao.getById3", map2);
        System.out.println(temp4);


    }

    @Test
    /**
     * 查询id相同
     */
    public void testStatementId() throws IOException {
        InputStream stream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
        SqlSession sqlSession = factory.openSession();
        entity.Temp temp = sqlSession.selectOne("dao.TempDao.getById", 1);
        System.out.println(temp);

        entity.Temp temp2 = sqlSession.selectOne("dao.TempDao.getById2", 1);
        System.out.println(temp2);

        System.out.println(temp == temp2);

    }
}

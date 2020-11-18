package dao;

import entity.Temp;

import java.util.List;
import java.util.Map;

/**
 * @Author yangxw
 * @Date 2020-11-18 下午3:55
 * @Description
 * @Version 1.0
 */
public interface TempDao {
    public Temp getById(int id);
    public Temp getById2(int id);

    public Temp getById3(Map<String,Integer> map);

    public List<Temp>  getById4();

    public Temp getById5(Map<String,Integer> map);


}

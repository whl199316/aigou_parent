package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.domain.Product;
import cn.itsource.aigou.domain.ProductType;
import cn.itsource.aigou.mapper.ProductTypeMapper;
import cn.itsource.aigou.service.IProductTypeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author yhptest
 * @since 2019-01-13
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> treeData()
    {

        // 1 递归方案效率低,要发多次sql
        //return getTreeDataRecursion(0L);
        // 2 循环方案,只需发一条sql
        return getTreeDataLoop(0L);
    }

    private List<ProductType> getTreeDataLoop(long l) {
        //返回数据 一级类型,下面挂了子子孙孙类型
        List<ProductType> result = new ArrayList<>();
        //1 获取所有的类型
        List<ProductType> productTypes = productTypeMapper.selectList(null);
        //2)遍历所有的类型
        Map<Long,ProductType> productTypesDto = new HashMap<>();
        for (ProductType productType : productTypes) {
            productTypesDto.put(productType.getId(), productType);
        }
        for (ProductType productType : productTypes) {
            Long pid = productType.getPid();
            // ①如果没有父亲就是一级类型 放入返回列表中
            if (pid.longValue() == 0){
                result.add(productType);
            }else{
                // ②如果有父亲就是把自己当做一个儿子就ok
                //方案1:遍历比较所有所有里面获取 两层for 10*10
//                for (ProductType productTypeTmp : productTypes) { 1 10 2 10 310 40 10
//                    if (productTypeTmp.getId()==pid){
//                        productTypeTmp.getChildren().add(productType);
//                    }
//                }
                //方案2:通过Map建立id和类型直接关系,以后通过pid直接获取父亲 10+10
                ProductType parent = productTypesDto.get(pid);
                parent.getChildren().add(productType);
            }

        }
        return result;
    }

    /**
     * 递归获取无限极数据
     *    ①自己调用自己
     *    ②要有出口
     * @return
     */
    private List<ProductType> getTreeDataRecursion(Long id) {

        //0
        List<ProductType> children = getAllChildren(id); //1 2
        //出口
        if (children == null || children.size()<1){
            return null;
        }
        for (ProductType productType : children) {
            //1 3 4 自己调用自己
            List<ProductType> children1 = getTreeDataRecursion(productType.getId());
            productType.setChildren(children1);
        }
        return children;

    }

    private List<ProductType> getAllChildren(Long pid){
        Wrapper wrapper = new EntityWrapper<ProductType>();
        wrapper.eq("pid", pid);
        return productTypeMapper.selectList(wrapper);
    }

}

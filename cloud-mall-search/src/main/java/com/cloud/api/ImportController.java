package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.catalog.Sku;
import com.cloud.module.ESDocument;
import com.cloud.service.IndexService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/index")
public class ImportController {

    @Autowired
    CatalogService catalogService;

    @Autowired
    IndexService<Sku> indexService;

    @PostMapping("/full/catalog")
    public ResponseResult catalogFullIndex() {
        ResponseResult<List<Sku>> skuResults =  this.catalogService.findAllSkuList();
        if (skuResults == null || CollectionUtils.isEmpty(skuResults.getData())) {
            return new ResponseResult(400,"没有产品数据");
        }
        List<Sku> skuList = skuResults.getData();
        ESDocument document = null;
        for (Sku sku : skuList) {
            document = new ESDocument(String.valueOf(sku.getId()),"catalog",sku);
            indexService.addDocument(document);
        }
        return new ResponseResult().success("Catalog库索引成功!");
    }
}

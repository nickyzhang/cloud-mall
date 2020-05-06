package com.cloud.model.order;

import com.cloud.BaseModel;
import lombok.Data;

@Data
public class OrderInvoiceItem extends BaseModel {

    private static final long serialVersionUID = -3727081442803552620L;

    private Long itemId;

    /** 应税商品或者其他服务等名称 */
    private String goodsName;

    /** 规格型号 */
    private String specification;

    /** 单位 */
    private String unit;

    /** 数量 */
    private int quantity;

    /** 单价 */
    private String unitPrice;

    /** 商品、劳务等应税金额 */
    private String invoiceAmount;

    /** 商品、劳务等应税税率 */
    private String taxRate;

    /** 商品、劳务等应税税额 */
    private String taxAmount;

    /** 折扣金额 */
    private String discountAmount;

    /** 折扣税率 */
    private String discountTaxRate;

    /** 折扣抵扣税额，冲了销售，也要冲增值税 */
    private String discountTaxAmount;
}

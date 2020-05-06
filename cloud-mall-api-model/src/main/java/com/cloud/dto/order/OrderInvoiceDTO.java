package com.cloud.dto.order;

import com.cloud.BaseModel;
import com.cloud.model.order.OrderInvoiceItem;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderInvoiceDTO extends BaseModel {

    /** 发票id */
    private Long invoiceId;

    /** 用户发票模板号码 */
    private Long templateId;

    /** 发票号码 */
    private String invoiceNo;

    /** 发票代码 */
    private String code;

    /** 订单号 */
    private Long orderNo;

    /** 买方名称*/
    private String buyerName;

    /** 买方纳税人识别号 */
    private String buyerTaxNo;

    /** 买方注册地址 */
    private String buyerAddress;

    /** 买方注册电话 */
    private String buyerMobile;

    /** 买方开户行 */
    private String buyerDepositBank;

    /** 买方行号 */
    private String buyerBankAccount;

    /** 卖方纳税人识别号 */
    private String sellerTaxNo;

    /** 卖方注册地址 */
    private String sellerAddress;

    /** 卖方注册电话 */
    private String sellerMobile;

    /** 卖方开户行 */
    private String sellerDepositBank;

    /** 卖方行号 */
    private String sellerBankAccount;

    /** 开票日期 */
    private LocalDate invoiceDate;

    /** 开票项目列表 */
    private List<OrderInvoiceItem> itemList;
}

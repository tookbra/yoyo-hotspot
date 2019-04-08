package com.mars.yoyo.hotspot.order.paypal;

import com.mars.yoyo.hotspot.order.BaseTest;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import junit.textui.ResultPrinter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author tookbra
 * @date 2018/5/17
 * @description
 */
public class PayTest extends BaseTest {

    @Autowired
    APIContext apiContext;

    @Test
    public void pay() throws PayPalRESTException {
        Payment payment = new Payment();
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("50");

//        Item item = new Item();
//        item.setName("押金");
//        item.setQuantity("1");
//        item.setCurrency("USD");
//        item.setPrice("50");
//        List<Item> items = new ArrayList<Item>();
//        items.add(item);
//
//        ItemList itemList = new ItemList();
//        itemList.setItems(items);

        Transaction transaction = new Transaction();
        transaction.setDescription("This is the payment transaction description.");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        // ###Redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        String guid = UUID.randomUUID().toString().replaceAll("-", "");
        redirectUrls.setCancelUrl("http://www.baidu.com?id="+guid);
        redirectUrls.setReturnUrl("http://www.baidu.com?id="+guid);

        // ###Payment
        // A Payment Resource; create one using
        // the above types and intent as 'sale'
        payment.setIntent("sale");
        payment.setTransactions(transactions);
        payment.setPayer(payer);
        payment.setRedirectUrls(redirectUrls);

        // 创建张贴到API服务
        // 使用一个有效的访问令牌
        // 返回的对象包含状态的支付;
        Payment createdPayment = payment.create(apiContext);
        // ###Payment Approval Url
        Iterator<Links> links = createdPayment.getLinks().iterator();
        while (links.hasNext()) {
            Links link = links.next();
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                System.out.println(link.getHref());
            }
        }
    }

    @Test
    public void test1() {
        Payment payment = new Payment();
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId("HJPWDXLRM5SFC");
        try {
            payment.setId("PAY-94C40537HR761173VLMBGC6Y");
            Payment createdPayment = payment.execute(apiContext, paymentExecution);
            System.out.println(createdPayment);
        } catch (PayPalRESTException e) {
        }
    }
}

package com.shenfeng.yxw.bases.designModal.modal.use;

import com.shenfeng.yxw.bases.designModal.modal.Cart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class Main {


    @GetMapping("right")
    public Cart right(@RequestParam("userId") int userId) {
//        String userCategory = Db.getUserCategory(userId);
//        AbstractCart cart = (AbstractCart) applicationContext.getBean(userCategory + "UserCart");
//        return cart.process(userId, items);
        return null;
    }
}

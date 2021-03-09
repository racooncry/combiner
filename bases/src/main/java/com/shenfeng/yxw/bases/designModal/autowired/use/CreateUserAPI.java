package com.shenfeng.yxw.bases.designModal.autowired.use;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@BankAPI(url = "/bank/createUser", desc = "创建用户接口")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserAPI extends AbstractAPI {
    @BankAPIField(order = 1, type = "S", length = 10)
    private String name;
    @BankAPIField(order = 2, type = "S", length = 18)
    private String identity;
    @BankAPIField(order = 4, type = "S", length = 11)
    private String mobile;
    @BankAPIField(order = 3, type = "N", length = 5)
    private int age;



}